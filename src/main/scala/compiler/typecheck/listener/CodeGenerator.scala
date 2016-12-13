package compiler.typecheck.listener

import java.io.{File, FileOutputStream, PrintStream}

import antlr4.MiniJavaBaseListener
import antlr4.MiniJavaParser._
import compiler.typecheck.scope.{Klass, Scope}
import compiler.typecheck.symbol.{ParamSymbol, PropertySymbol, VarSymbol}
import compiler.typecheck.utils.KlassMap
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTreeProperty
import org.objectweb.asm.Opcodes._
import org.objectweb.asm.commons.GeneratorAdapter
import org.objectweb.asm.{ClassWriter, Label, Opcodes, Type}
import compiler.typecheck.scope.Method
import compiler.typecheck.visitor.TypeChecker

import scala.collection.JavaConverters._
import scala.collection.immutable.IndexedSeq
import scala.collection.mutable

/**
  * The code generator class.
  *
  * @param klasses
  * The klass map used globally.
  * @param scopes
  * The scopes parse tree prop used globally
  * @param callerTypes
  * The caller types tree prop used globally.
  */
class CodeGenerator(klasses: KlassMap, scopes: ParseTreeProperty[Scope], callerTypes: ParseTreeProperty[Klass]) extends MiniJavaBaseListener with Opcodes {
  var currentScope: Option[Scope] = None
  var classWriter: ClassWriter = _
  var currentMethod: Method.ASMMethod = _
  var methodGenerator: GeneratorAdapter = _
  var labelStack: mutable.Stack[Label] = mutable.Stack()
  //boolean isArg
  var argCount: Int = 0

  override def enterMainClass(ctx: MainClassContext): Unit = {
    val mainKlass = klasses.get(ctx.ID(0).getText).get

    val klassName = mainKlass.name
    classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
    classWriter.visit(V1_1, ACC_PUBLIC, klassName, null, "java/lang/Object", null)

    methodGenerator = new GeneratorAdapter(ACC_PUBLIC, CodeGenerator.INIT, null, null, classWriter)
    methodGenerator.loadThis()
    methodGenerator.invokeConstructor(Type.getType(classOf[Object]), CodeGenerator.INIT)
    methodGenerator.returnValue()
    methodGenerator.endMethod()

    methodGenerator = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, org.objectweb.asm.commons.Method.getMethod("void main (String[])"), null, null, classWriter)
  }

  override def exitMainClass(ctx: MainClassContext): Unit = {
    methodGenerator.returnValue()
    methodGenerator.endMethod()
    classWriter.visitEnd()
    val mainKlass = klasses.get(ctx.ID(0).getText).get
    val klassName = mainKlass.name
    val klassFile = new File(s"$klassName.class")
    val fileOutputStream = new FileOutputStream(klassFile)
    fileOutputStream.write(classWriter.toByteArray)
    fileOutputStream.close()
  }

  override def enterBaseClass(ctx: BaseClassContext): Unit = enterScope(ctx) {

    val klass = klasses.get(ctx.ID.getText).get
    val klassName = klass.name

    classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
    classWriter.visit(V1_1, ACC_PUBLIC, klassName, null, "java/lang/Object", null)

    methodGenerator = new GeneratorAdapter(ACC_PUBLIC, CodeGenerator.INIT, null, null, classWriter)
    methodGenerator.loadThis()
    methodGenerator.invokeConstructor(Type.getObjectType(klassName), CodeGenerator.INIT)
    methodGenerator.returnValue()
    methodGenerator.endMethod()
  }

  override def enterChildClass(ctx: ChildClassContext): Unit = enterScope(ctx) {

    val klass = klasses.get(ctx.ID(0).getText).get
    val klassName = klass.name

    val superKlassName = klass.parentScope match {
      case None => "java/lang/Object"
      case Some(superKlass) => superKlass.name
    }

    classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
    classWriter.visit(V1_1, ACC_PUBLIC, klassName, null, superKlassName, null)

    methodGenerator = new GeneratorAdapter(ACC_PUBLIC, CodeGenerator.INIT, null, null, classWriter)
    methodGenerator.loadThis()
    methodGenerator.invokeConstructor(Type.getObjectType(superKlassName), CodeGenerator.INIT)
    methodGenerator.returnValue()
    methodGenerator.endMethod()
  }

  override def exitChildClass(ctx: ChildClassContext): Unit = exitScope(exitClass(ctx))

  override def exitBaseClass(ctx: BaseClassContext): Unit = exitScope(exitClass(ctx))

  def exitClass(ctx: ParserRuleContext): Unit = exitScope {
    val classID = ctx match {
      case ctx: BaseClassContext => ctx.ID().getText
      case ctx: ChildClassContext => ctx.ID(0).getText
    }

    classWriter.visitEnd()
    val klass = klasses.get(classID).get
    val klassName = klass.name
    val klassFile = new File(s"$klassName.class")
    val fileOutputStream = new FileOutputStream(klassFile)
    fileOutputStream.write(classWriter.toByteArray)
    fileOutputStream.close()
  }

  override def exitPropertyDecl(ctx: PropertyDeclContext): Unit = {
    val currScope = currentScope match {
      case None => throw new AssertionError("Scope not defined at property declaration enter")
      case Some(scope) => scope
    }

    currScope.deepFind(ctx.ID().getText) match {
      case None => throw new AssertionError(s"Symbol not defined for ${ctx.ID().getText}")
      case Some(symbol) =>
        classWriter.visitField(
          ACC_PROTECTED, symbol.name, symbol.kType.asAsmType.getDescriptor, null, null
        ).visitEnd()
    }
  }

  override def exitVarDecl(ctx: VarDeclContext): Unit = {
    val currScope = currentScope match {
      case None => throw new AssertionError("Scope not defined at variable declaration exit")
      case Some(scope) => scope
    }

    currScope.deepFind(ctx.ID().getText) match {
      case None => throw new AssertionError(s"Symbol not defined for ${ctx.ID().getText}")
      case Some(symbol: VarSymbol) =>
        val kType = symbol.kType.asAsmType
        val localID = methodGenerator.newLocal(kType)
        symbol.localID = localID
    }
  }

  override def enterMethodDecl(ctx: MethodDeclContext): Unit = enterScope(ctx) {
    currentScope match {
      case None => throw new AssertionError(s"Method not defined for ${ctx.ID().getText}")
      case Some(m: Method) =>
        currentMethod = m.signature.toAsmMethod
        methodGenerator = new GeneratorAdapter(ACC_PUBLIC, currentMethod, null, null, classWriter)

        val paramsCtx: IndexedSeq[MethodParamContext] = ctx.methodParam().asScala.toIndexedSeq
        paramsCtx.indices.foreach((id) => placeParam(id, paramsCtx(id))(m, ctx))
    }
  }

  private def placeParam(id: Int, mCTX: MethodParamContext)(m: Method, ctx: MethodDeclContext): Unit = {
    m.deepFind(mCTX.ID().getText) match {
      case None => throw new AssertionError(s"Method not defined for ${ctx.ID().getText}")
      case Some(symbol: PropertySymbol) => symbol.paramID = id
    }
  }

  override def enterPrintToConsole(ctx: PrintToConsoleContext): Unit = {
    methodGenerator.getStatic(Type.getType(classOf[System]), "out", Type.getType(classOf[PrintStream]))
  }

  override def exitPrintToConsole(ctx: PrintToConsoleContext): Unit = {
    methodGenerator.invokeVirtual(Type.getType(classOf[System]), org.objectweb.asm.commons.Method.getMethod("void println (int)"))
  }

  override def enterPropertyDecl(ctx: PropertyDeclContext): Unit = {
    methodGenerator.loadThis()
  }

  override def exitVarDefinition(ctx: VarDefinitionContext): Unit = {
    currentScope match {
      case None => throw new AssertionError("Scope not defined at variable declaration exit")
      case Some(currScope) =>
        currScope.deepFind(ctx.ID().getText) match {
          case Some(property: PropertySymbol) =>
            val enclosingKlass = TypeChecker.getEnclosingKlass(Some(currScope))
            methodGenerator.putField(enclosingKlass.asAsmType, property.name, property.kType.asAsmType)
          case Some(localSymbol: VarSymbol) => methodGenerator.storeLocal(localSymbol.localID, localSymbol.kType.asAsmType)
          case Some(paramSymbol: ParamSymbol) => methodGenerator.storeArg(paramSymbol.paramID)
        }
    }
  }

  override def enterArrayDefinition(ctx: ArrayDefinitionContext): Unit = {
    currentScope match {
      case None => throw new AssertionError("Scope not defined at variable declaration exit")
      case Some(currScope) =>
        currScope.deepFind(ctx.ID().getText) match {
          case Some(property: PropertySymbol) =>
            val enclosingKlass = TypeChecker.getEnclosingKlass(Some(currScope))
            methodGenerator.loadThis()
            methodGenerator.getField(enclosingKlass.asAsmType, property.name, property.kType.asAsmType)
          case Some(localSymbol: VarSymbol) => methodGenerator.loadLocal(localSymbol.localID, localSymbol.kType.asAsmType)
          case Some(paramSymbol: ParamSymbol) => methodGenerator.loadArg(paramSymbol.paramID)
        }
    }
  }

  override def exitArrayDefinition(ctx: ArrayDefinitionContext): Unit = {
    methodGenerator.arrayStore(Type.INT_TYPE)
  }

  override def enterIfStatement(ctx: IfStatementContext): Unit = {
    val enterElse = methodGenerator.newLabel()
    val exitElse = methodGenerator.newLabel()
    //methodGenerator.ifZCmp(GeneratorAdapter.EQ, enterElse)
    labelStack push exitElse
    labelStack push enterElse
    labelStack push exitElse
    labelStack push enterElse
  }

  override def enterIfBlock(ctx: IfBlockContext): Unit = {
    methodGenerator.ifZCmp(GeneratorAdapter.EQ, labelStack.pop)
  }

  override def exitIfBlock(ctx: IfBlockContext): Unit = {
    methodGenerator.goTo(labelStack.pop())
  }

  override def enterElseBlock(ctx: ElseBlockContext): Unit = {
    methodGenerator.mark(labelStack.pop())
  }

  override def exitElseBlock(ctx: ElseBlockContext): Unit = {
    methodGenerator.mark(labelStack.pop())
  }

  override def enterWhileLoopHead(ctx: WhileLoopHeadContext): Unit = {
    val enterWhile = methodGenerator.mark()
    val exitWhile = methodGenerator.newLabel()
    labelStack push exitWhile
    labelStack push enterWhile
    labelStack push exitWhile
  }

  override def enterWhileBlock(ctx: WhileBlockContext): Unit = {
    methodGenerator.ifZCmp(GeneratorAdapter.EQ, labelStack.pop)
  }

  override def exitWhileLoopHead(ctx: WhileLoopHeadContext): Unit = {
    methodGenerator.goTo(labelStack.pop)
    methodGenerator.mark(labelStack.pop)
  }

  override def exitAndExpr(ctx: AndExprContext): Unit = {
    methodGenerator.math(GeneratorAdapter.AND, Type.BOOLEAN_TYPE)
  }

  override def enterLessThanExpr(ctx: LessThanExprContext): Unit = {
    //Just do the roundabout long and hard way.
    val trueLabel = methodGenerator.newLabel()
    val endLabel = methodGenerator.newLabel()
    methodGenerator.ifCmp(Type.INT_TYPE, GeneratorAdapter.LT, trueLabel)
    methodGenerator.push(false)
    methodGenerator.goTo(endLabel)
    methodGenerator.mark(trueLabel)
    methodGenerator.push(true)
    methodGenerator.mark(endLabel)
  }

  override def exitPlusExpression(ctx: PlusExpressionContext): Unit = {
    methodGenerator.math(GeneratorAdapter.ADD, Type.INT_TYPE)
  }

  override def exitSubtractExpression(ctx: SubtractExpressionContext): Unit = {
    methodGenerator.math(GeneratorAdapter.SUB, Type.INT_TYPE)
  }

  override def exitMultiplyExpression(ctx: MultiplyExpressionContext): Unit = {
    methodGenerator.math(GeneratorAdapter.MUL, Type.INT_TYPE)
  }

  override def exitArrayAccessExpression(ctx: ArrayAccessExpressionContext): Unit = {
    methodGenerator.arrayLoad(Type.INT_TYPE)
  }

  override def exitArrLenExpression(ctx: ArrLenExpressionContext): Unit = {
    methodGenerator.arrayLength()
  }

  override def exitMethodCallExpression(ctx: MethodCallExpressionContext): Unit = {
    val klass = callerTypes.get(ctx)

    klass.deepFind(ctx.ID().getText) match {
      case None => throw new AssertionError(s"Symbol not defined ${ctx.ID().getText} in class [${klass.name}]")
      case Some(m: Method) => methodGenerator.invokeVirtual(klass.asAsmType, m.asAsmMethod)
    }
  }

  override def enterIntLiteral(ctx: IntLiteralContext): Unit = {
    methodGenerator.push(Integer.parseInt(ctx.INT_LIT().getText))
  }

  override def enterBooleanLit(ctx: BooleanLitContext): Unit = {
    val predicate = Boolean.parseBoolean(ctx.BooleanLiteral().getText())
    methodGenerator.push(predicate);
  }

  override def enterIdLiteral(ctx: IdLiteralContext): Unit = {

  }

  override def exitMethodDecl(ctx: MethodDeclContext): Unit = exitScope {
    methodGenerator.returnValue()
    methodGenerator.endMethod()
  }

  private def enterScope(ctx: ParserRuleContext)(block: => Unit): Unit = {
    currentScope = Option(scopes.get(ctx))
    block
  }

  private def exitScope(block: => Unit) {
    currentScope match {
      case None => throw new AssertionError("Exiting a scope that was not defined!")
      case Some(currScope) =>
        block
        currentScope = currScope.parentScope
    }
  }
}

object CodeGenerator {
  def INIT: Method.ASMMethod = org.objectweb.asm.commons.Method getMethod "void <init> ()"
}