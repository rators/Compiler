package compiler.typecheck.listener

import javax.swing.JTextField

import antlr4.MiniJavaParser._
import antlr4.{MiniJavaBaseListener, MiniJavaParser}
import compiler.typecheck.error.CyclicInheritanceError
import compiler.typecheck.scope.Scope.LinkedSymbolMap
import compiler.typecheck.scope.{Block, Klass, Method, Scope}
import compiler.typecheck.symbol.{ParamSymbol, PropertySymbol, VarSymbol}
import compiler.typecheck.utils.KlassMap
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTreeProperty
import compiler.typecheck.utils.AntlrConversions._

import scala.util.{Failure, Success, Try}
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
        childClass.superKlass = parentKlass
        SymbolDeclarator.hasCycleParadox(ctx, childClass, childClass.parentScope)
    }
  }

  override def enterVarDecl(ctx: VarDeclContext): Unit = {
    val typeName = ctx.`type`().getText
    val symbolName = ctx.ID().getText
    currentScope match {
      case None => SymbolDeclarator.throwInvalidStateErr
      case Some(currScope) =>
        currScope.shallowFind(symbolName) match {
          case None => klassMap get typeName match {
            case None => SymbolDeclarator.throwInvalidStateErr
            case Some(symbolType) =>
              val varSymbol = VarSymbol(symbolName, symbolType)
              currScope.define(varSymbol)
          }
          case Some(_) =>
            SymbolDeclarator.throwInvalidStateErr
        }
    }
  }

  override def enterPropertyDecl(ctx: PropertyDeclContext): Unit = {
    val typeName = ctx.`type`().getText
    val symbolName = ctx.ID().getText
    currentScope match {
      case None => SymbolDeclarator.throwInvalidStateErr
      case Some(currScope) =>
        currScope.shallowFind(symbolName) match {
          case None => klassMap get typeName match {
            case None =>
              SymbolDeclarator.throwInvalidStateErr
            case Some(symbolType) =>
              val varSymbol = PropertySymbol(symbolName, symbolType)
              currScope.define(varSymbol)
          }
          case Some(_) =>
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
                setScope(method)(ctx)
              case Some(alreadyDecl) => SymbolDeclarator.throwInvalidStateErr //invalid method override exception
            }
        }
    }
  }

  override def enterBasicBlock(ctx: BasicBlockContext): Unit = handleBlockScope(ctx)

  override def exitBasicBlock(ctx: BasicBlockContext): Unit = handleExitScope()

  override def exitChildClass(ctx: ChildClassContext): Unit = {
    val currentChild = currentScope.get.asInstanceOf[Klass]
    Klass.validMethodOverloading(currentChild, currentChild.parentScope.get, ctx.ID(0).getSymbol) match {
      case Failure(e) => throw e
      case _ => handleExitScope()
    }
  }

  override def exitMainClass(ctx: MainClassContext): Unit = handleExitScope()

  override def exitBaseClass(ctx: BaseClassContext): Unit = handleExitScope()

  override def exitMethodDecl(ctx: MethodDeclContext): Unit = handleExitScope()

  /**
    * Handles the entry into a block scope.
    *
    * @param parserRuleContext
    *                          The context associated with a block scope.
    * @param parentScope
    *                    The parent scope, generally the current scope.
    */
  private def handleBlockScope(implicit parserRuleContext: ParserRuleContext, parentScope: Scope = currentScope.get) = {
    val blockScope = new Block(parentScope)
    implicit val _ctx = parserRuleContext
    setScope(blockScope)(_ctx)
  }

  /**
    * Handles the exiting of a scope.
    */
  private def handleExitScope() = {
    currentScope = currentScope match {
      case Some(currScope) => currScope.parentScope
      case None => SymbolDeclarator.throwInvalidStateErr
    }
  }

  /**
    * Sets the scope to the method parameter.
    *
    * @param method
    *               The method to set.
    * @param ctx
    *            The context associated with a parser rule context.
    */
  private def setScope(method: Scope)(ctx: ParserRuleContext) = {
    currentScope = Some(method)
    scopes += ctx -> method
  }

  /**
    * Sets the scope to the scope associated with the context paramters. Returns the newly
    * created scope.
    *
    * @param ctx
    *            The context associated with a parser rule context.
    * @return
    *         The class created from the context.
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
    * Sets the parameters for a method.
    *
    * @param method
    *               The method to set parameters for.
    * @param ctx
    *            The context associated with a case class constructor declaration.
    *
    */
  def setMethodParameters(method: Method, ctx: MethodDeclContext) = {
    val parameterList = ctx.methodParam().map(ctxToMethodParam)
    method.paramDefs ++= parameterList
  }

  /**
    * Sets the parameters for a case class constructor.
    *
    * @param method
    *               The method to set parameters for.
    * @param ctx
    *            The context associated with a case class contructor declaration.
    *
    */
  def setConstructorParameters(method: Method, ctx: CaseClassDeclContext) = {
    val parameterList = ctx.caseProperty.map(ctxToCaseParam)
    method.paramDefs ++= parameterList
  }

  /**
    * Converts a context into a method parameter.
    *
    * @param ctx
    *            The context associated with a method parameter.
    * @return
    *         A method parameter.
    */
  private def ctxToMethodParam(ctx: MethodParamContext) = {
    val typeName = ctx.`type`().getText
    val klassOpt = klassMap get typeName

    val symbolName = ctx.ID().getText

    klassOpt match {
      case Some(typeKlass) => (symbolName, ParamSymbol(symbolName, typeKlass))
      case None => SymbolDeclarator.throwInvalidStateErr
    }
  }

  /**
    * Converts a case property context to a name -> symbol pair.
    *
    * @param ctx
    *            The context associated with a case constructor property parameter.
    * @return
    *         A pair defined: (param symbol name, the symbol).
    *
    */
  private def ctxToCaseParam(ctx: CasePropertyContext): (String, PropertySymbol) = {
    val typeName = ctx.`type`().getText
    val klassOpt = klassMap get typeName

    val symbolName = ctx.ID().getText

    klassOpt match {
      case Some(typeKlass) => (symbolName, PropertySymbol(symbolName, typeKlass))
      case None => SymbolDeclarator.throwInvalidStateErr
    }
  }

  /**
    * Gets the klass associated with a klass context.
    *
    * @param ctx
    *            The context associated with a klass
    * @return
    *         A klass if one exists with an id in the context.
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
    * Gets the super klass of a klass if it exists.
    *
    * @param klass
    *              The klass which should have a super klass it inherits from.
    * @param ctx
    *            The context associated with the klass.
    * @return
    *         The super klass of a klass if it exists.
    */
  private def getSuperKlass(klass: Klass)(implicit ctx: ChildClassContext): Option[Klass] = {
    val superKlassName = ctx.ID(1).getText
    klassMap get superKlassName
  }

  /**
    * Creates and sets the constructor for a case klass. The symbol for the constructor method
    * is created then added to the scope of the case klass.
    *
    * @param caseKlassType
    *                       The type of the case klass.
    * @param ctx
    *            The context associated with a case klass.
    */
  private def setCaseKlassContructor(caseKlassType: Klass, ctx: CaseClassDeclContext) = {
    val methodName = Method.getSignatureSimple(ctx)
    val method = new Method(methodName, caseKlassType, caseKlassType, new LinkedSymbolMap())
    setConstructorParameters(method, ctx)

    caseKlassType define method
  }
}

object SymbolDeclarator {

  /**
    * Throws an invalid state err. For debugging purposes only.
    *
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
          case true => Failure(throw CyclicInheritanceError(baseKlass, ctx.ID(0).getSymbol))
          case false => Success(Unit)
        }
    }
  }
}
