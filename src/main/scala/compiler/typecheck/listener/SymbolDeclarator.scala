package compiler.typecheck.listener

import antlr4.MiniJavaParser._
import antlr4.{MiniJavaBaseListener, MiniJavaParser}
import compiler.typecheck.scope.Scope.LinkedSymbolMap
import compiler.typecheck.scope.{Block, Klass, Method, Scope}
import compiler.typecheck.symbol.{PropertySymbol, VarSymbol}
import compiler.typecheck.utils.KlassMap
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTreeProperty
import compiler.typecheck.utils.AntlrConversions._

import scala.util.{Success, Try}
import scala.collection.JavaConversions._

/**
  * -- Visitor whose responsibility is to ensure --
  */
class SymbolDeclarator(val klassMap: KlassMap, val scopes: ParseTreeProperty[Scope], val parser: MiniJavaParser) extends MiniJavaBaseListener {
  var currentScope: Option[Scope] = None

  override def enterCaseClassDecl(ctx: CaseClassDeclContext): Unit = {
    implicit val _ctx = ctx

    val caseKlass = ctxToKlass(_ctx)

    caseKlass match {
      case None => SymbolDeclarator.throwInvalidStateErr
      case Some(klass: Klass) =>
        setCaseKlassContructor(klass, ctx)
        scopes += ctx -> klass
    }

  }

  override def enterMainClass(ctx: MainClassContext): Unit = setScope(ctx)

  override def enterBaseClass(ctx: BaseClassContext): Unit = setScope(ctx)

  override def enterChildClass(ctx: ChildClassContext): Unit = {
    implicit val _ctx = ctx
    val childClass = setScope(ctx)
    val parentKlass = getSuperKlass(childClass)

    parentKlass match {
      case None => SymbolDeclarator.throwInvalidStateErr
      case Some(superKlass) =>
        SymbolDeclarator.hasCycleParadox(ctx, childClass, childClass.parentScope)
        childClass.superKlass = parentKlass
    }
  }

  override def enterVarDecl(ctx: VarDeclContext): Unit = {
    val typeName = ctx.`type`().getText
    val symbolName = ctx.ID().getText

    currentScope match {
      case None => SymbolDeclarator.throwInvalidStateErr
      case Some(currScope) =>
        currScope.shallowFind(symbolName) match {
          case None =>
            val symbolType = klassMap get typeName get
            val varSymbol = VarSymbol(symbolName, symbolType)
            currScope.define(varSymbol)
          case Some(_) =>
            println("Multiple declarations for: " + symbolName)
            SymbolDeclarator.throwInvalidStateErr
        }
    }
  }

  override def enterMethodDecl(ctx: MethodDeclContext): Unit = {
    val typeName = ctx.`type`().getText
    val methodType = klassMap get typeName
    implicit val _ctx = ctx

    methodType match {
      case None => SymbolDeclarator.throwInvalidStateErr
      case Some(klassType) =>
        val methodName = Method.getSignatureSimple(ctx)

        currentScope match {
          case None => SymbolDeclarator.throwInvalidStateErr
          case Some(methodParent: Klass) =>
            methodParent.shallowFind(methodName) match {
              case None =>
                val method = new Method(methodName, klassType, methodParent, new LinkedSymbolMap())
                setMethodParameters(method, ctx)
                methodParent define method
                println(s"Method name: ${method.name} ${method.paramDefs.map((x) => x._2.kType)}")
                setScope(method)(ctx)
              case Some(alreadyDecl) => SymbolDeclarator.throwInvalidStateErr
            }
        }
    }
  }

  override def enterBasicBlock(ctx: BasicBlockContext): Unit = handleBlockScope(ctx)

  override def exitBasicBlock(ctx: BasicBlockContext): Unit = handleExitScope()

  override def exitChildClass(ctx: ChildClassContext): Unit = handleExitScope()

  override def exitMainClass(ctx: MainClassContext): Unit = handleExitScope()

  override def exitBaseClass(ctx: BaseClassContext): Unit = handleExitScope()

  override def exitMethodDecl(ctx: MethodDeclContext): Unit = handleExitScope()

  /**
    *
    * @param parserRuleContext
    * @param parentScope
    */
  private def handleBlockScope(implicit parserRuleContext: ParserRuleContext, parentScope: Scope = currentScope.get) = {
    val blockScope = new Block(parentScope)
    implicit val _ctx = parserRuleContext
    setScope(blockScope)(_ctx)
  }

  /**
    *
    */
  private def handleExitScope() = {
    currentScope = currentScope match {
      case Some(currScope) => currScope.parentScope
      case None => SymbolDeclarator.throwInvalidStateErr
    }
  }

  /**
    *
    * @param method
    * @param ctx
    */
  private def setScope(method: Scope)(ctx: ParserRuleContext) = {
    currentScope = Some(method)
    scopes += ctx -> method
  }

  /**
    *
    * @param ctx
    * @return
    */
  private def setScope(ctx: ParserRuleContext) = {
    val klass = ctxToKlass(ctx)

    klass match {
      case None => SymbolDeclarator.throwInvalidStateErr
      case Some(targetClass) =>
        currentScope = klass
        scopes += ctx -> targetClass
        targetClass
    }
  }

  /**
    *
    * @param method
    * @param ctx
    * @return
    */
  def setMethodParameters(method: Method, ctx: MethodDeclContext) = {
    val parameterList = ctx.methodParam().map(ctxToMethodParam)
    method.paramDefs ++= parameterList
  }

  /**
    *
    * @param method
    * @param ctx
    * @return
    */
  def setConstructorParamters(method: Method, ctx: CaseClassDeclContext) = {
    val parameterList = ctx.caseProperty.map(ctxToCaseParam)
    method.paramDefs ++= parameterList
  }

  /**
    *
    * @param ctx
    * @return
    */
  private def ctxToMethodParam(ctx: MethodParamContext) = {
    val typeName = ctx.`type`().getText
    val klassOpt = klassMap get typeName

    val symbolName = ctx.ID().getText

    klassOpt match {
      case Some(typeKlass) => (symbolName, PropertySymbol(symbolName, typeKlass))
      case None => SymbolDeclarator.throwInvalidStateErr
    }
  }

  /**
    *
    * @param ctx
    * @return
    */
  private def ctxToCaseParam(ctx: CasePropertyContext) = {
    val typeName = ctx.`type`().getText
    val klassOpt = klassMap get typeName

    val symbolName = ctx.ID().getText

    klassOpt match {
      case Some(typeKlass) => (symbolName, PropertySymbol(symbolName, typeKlass))
      case None => SymbolDeclarator.throwInvalidStateErr
    }
  }

  /**
    *
    * @param ctx
    * @return
    */
  private def ctxToKlass(ctx: ParserRuleContext): Option[Klass] = {
    ctx match {
      case mainCtx: MainClassContext =>
        val klassName = mainCtx.ID(0).getText
        klassMap get klassName
      case baseCtx: BaseClassContext =>
        val klassName = baseCtx.ID.getText
        klassMap get klassName
      case childCtx: ChildClassContext =>
        val klassName = childCtx.ID(0).getText
        klassMap get klassName
      case caseCtx: CaseClassDeclContext =>
        val klassName = caseCtx.ID().getText
        klassMap get klassName
      case _ => SymbolDeclarator.throwInvalidStateErr
    }
  }

  /**
    *
    * @param klass
    * @param ctx
    * @return
    */
  private def getSuperKlass(klass: Klass)(implicit ctx: ChildClassContext): Option[Klass] = {
    val superKlassName = ctx.ID(1).getText
    klassMap get superKlassName
  }

  /**
    *
    * @param caseKlassType
    * @param ctx
    */
  private def setCaseKlassContructor(caseKlassType: Klass, ctx: CaseClassDeclContext) = {
    val methodName = Method.getSignatureSimple(ctx)
    val method = new Method(methodName, caseKlassType, caseKlassType, new LinkedSymbolMap())

    setConstructorParamters(method, ctx)
    println(s"Method name: ${method.name} ${method.paramDefs.map((x) => x._2.kType)}")

    caseKlassType define method
  }
}

object SymbolDeclarator {
  /**
    *
    * @param ctx
    *            The context associated with a main class.
    * @return
    */
  def getKlass(ctx: MainClassContext): Boolean = ctx.ID.size() > 1

  /**
    * Throws an invalid state err. For debugging purposes only.
    * @throws RuntimeException
    *         Thrown if called
    */
  def throwInvalidStateErr = throw new RuntimeException("Invalid state for SymbolDeclarator visitor")

  /**
    * Recursively checks the inheritance tree of a klass to ensure there does not exist a class that inherits
    * one of it's base klasses.
    *
    * @param ctx
    *            The context node associated with a child class.
    * @param baseKlass
    *                  The base klass container object.
    * @param parentKlass
    *                    The parent klass container object of the baseKlass
    * @return
    *         A successful unit if there is no paradox in the inheritance past.
    */
  def hasCycleParadox(ctx: ChildClassContext, baseKlass: Klass, parentKlass: Option[Klass]): Try[Unit] = {
    parentKlass match {
      case None => Success(Unit)
      case Some(parent) =>
        parent == baseKlass match {
          case true => throwInvalidStateErr
          case false => hasCycleParadox(ctx, baseKlass, parent.superKlass)
        }
    }
  }
}
