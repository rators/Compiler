package compiler.typecheck.visitor

import antlr4.MiniJavaParser._
import antlr4.{MiniJavaBaseVisitor, MiniJavaParser}
import compiler.typecheck.error._
import compiler.typecheck.scope.{Block, Klass, Method, Scope}
import compiler.typecheck.utils.KlassMap
import org.antlr.v4.runtime.{ParserRuleContext, Token}
import org.antlr.v4.runtime.tree.{ParseTreeProperty, TerminalNode}
import compiler.typecheck.scope.Scope
import compiler.typecheck.symbol._

import scala.annotation.tailrec
import scala.collection.JavaConversions._

class TypeChecker(
                   val klassMap: KlassMap, val scopes: ParseTreeProperty[Scope],
                   val callerTypes: ParseTreeProperty[Klass], parser: MiniJavaParser
                 ) extends MiniJavaBaseVisitor[Klass] {

  var currScope: Option[Scope] = None
  implicit val _map = klassMap

  override def visitMainClass(ctx: MainClassContext): Klass = enterScope(ctx)

  override def visitBaseClass(ctx: BaseClassContext): Klass = {
    enterScope(ctx)
  }

  override def visitChildClass(ctx: ChildClassContext): Klass = {
    enterScope(ctx)
  }

  override def visitCaseClassDecl(ctx: CaseClassDeclContext): Klass = enterScope(ctx)

  override def visitParenExpr(ctx: ParenExprContext): Klass = visit(ctx.expr())



  override def visitType(ctx: TypeContext): Klass = {
    Option(ctx.ID) match {
      case None => klassMap get ctx.getText match {
        case None => throw UnresolvedSymbolError(ctx.getText, ctx.ID().getSymbol)
        case Some(klass) => klass
      }
      case identifier: Some[TerminalNode] =>
        klassMap.get(ctx.ID.getText) match {
          case None => null
          case Some(klass) => klass
        }
    }
  }

  override def visitMethodDecl(ctx: MethodDeclContext): Klass = {
    currScope = Option(scopes get ctx)
    val sigTypeName = ctx.`type`().getText
    val returnType = visit(ctx.expr())
    visitChildren(ctx)

    klassMap get sigTypeName match {
      case Some(sigType) =>
        if(returnType <| sigType) sigType
        else throw InvalidReturnType(sigType, returnType, ctx.getStop)
    }
  }

  override def visitVarDefinition(ctx: VarDefinitionContext): Klass = {
    currScope match {
      case None => throw new AssertionError("Invalid type checker state.")
      case Some(scope) =>
        val varName = ctx.ID().getSymbol.getText
        scope.deepFind(varName) match {
          case None => throw UnresolvedSymbolError(ctx.ID().getText, ctx.ID().getSymbol)
          case Some(leftSymbol) =>
            val leftType = leftSymbol.kType
            visit(ctx.expr()) match {
              case Klass(leftType.name, _) => leftType
              case rightType: Klass =>
                val typeErr = InvalidVarDefinition(leftType.name, rightType.name, ctx.getStop)

                if (rightType <| leftType) {
                  rightType
                } else throw typeErr
            }
        }
    }
  }

  def initialize(symbol: Symbol): Unit = {
    currScope match {
      case None => throw new AssertionError("Invalid type checker state.")
      case Some(scope) => scope.initialize(symbol)
    }
  }

  override def visitMultiplyExpression(ctx: MultiplyExpressionContext): Klass = {
    val leftType = visit(ctx.expr(0))
    val rightType = visit(ctx.expr(1))
    TypeChecker.binaryOperatorCheck(leftType, rightType, ctx.MULT.getSymbol, "*")
  }

  override def visitSubtractExpression(ctx: SubtractExpressionContext): Klass = {
    val leftType = visit(ctx.expr(0))
    val rightType = visit(ctx.expr(1))
    TypeChecker.binaryOperatorCheck(leftType, rightType, ctx.MINUS.getSymbol, "-")
  }

  override def visitPlusExpression(ctx: PlusExpressionContext): Klass = {
    val leftType = visit(ctx.expr(0))
    val rightType = visit(ctx.expr(1))
    TypeChecker.binaryOperatorCheck(leftType, rightType, ctx.PLUS.getSymbol, "+")
  }

  override def visitLessThanExpr(ctx: LessThanExprContext): Klass = {
    val leftType = visit(ctx.expr(0))
    val rightType = visit(ctx.expr(1))
    TypeChecker.binaryOperatorCheckBool(leftType, rightType, ctx.LESS_THAN.getSymbol, "<")
  }

  override def visitGreaterThanExpr(ctx: GreaterThanExprContext): Klass = {
    val leftType = visit(ctx.expr(0))
    val rightType = visit(ctx.expr(1))
    TypeChecker.binaryOperatorCheckBool(leftType, rightType, ctx.GREAT_THAN.getSymbol, ">")
  }

  override def visitArrayAccessExpression(ctx: ArrayAccessExpressionContext): Klass = {
    val arrType = visit(ctx.atom())
    val indexType = visit(ctx.expr())

    arrType match {
      case Klass("int[]", _) => indexType match {
        case Klass("int", _) => klassMap get "int" get
        case Klass(actual, _) => throw InvalidArrayIndexType(actual, ctx.expr().getStart)
      }
      case actual: Klass => throw InvalidType(actual, klassMap get "int[]" get, ctx.atom().getStart)
    }
  }

  override def visitArrLenExpression(ctx: ArrLenExpressionContext): Klass = {
    val intArr: Klass = visit(ctx.expr())
    intArr match {
      case Klass("int[]", _) => klassMap get "int" get
      case actual: Klass => throw InvalidType(actual, klassMap get "int[]" get, ctx.expr().getStart)
    }
  }

  override def visitIntLiteral(ctx: IntLiteralContext): Klass = {
    visitChildren(ctx)
    klassMap get "int" get
  }

  override def visitBooleanLit(ctx: BooleanLitContext): Klass = {
    visitChildren(ctx)
    klassMap get "boolean" get
  }

  override def visitThisCall(ctx: ThisCallContext): Klass = {
    visitChildren(ctx)
    getEnclosingKlass(currScope)
  }

  override def visitIdLiteral(ctx: IdLiteralContext): Klass = {
    val symbolName: String = ctx.ID().getSymbol.getText
    currScope match {
      case None => throw new AssertionError("Invalid type checker state.")
      case Some(scope) => scope.deepFind(symbolName) match {
        case None => symbolName match {
          case "true" => klassMap get "boolean" get
          case "false" => klassMap get "boolean" get
          case _ => throw UnresolvedSymbolError(symbolName, ctx.getStart)
        }
        case Some(symbol) => symbol.kType
      }
    }
  }

  override def visitIntegerArr(ctx: IntegerArrContext): Klass = {
    val sizeType = visit(ctx.expr())

    sizeType match {
      case Klass("int", _) => klassMap get "int[]" get
      case Klass(actual, _) => throw InvalidArrayIndexType(actual, ctx.expr().getStop)
    }
  }

  override def visitConstructorCall(ctx: ConstructorCallContext): Klass = {
    val klassType = klassMap get ctx.ID().getText

    klassType match {
      case None => throw UnresolvedSymbolError(ctx.ID().getSymbol.getText, ctx.ID().getSymbol)
      case Some(klass) => klass
    }
  }

  override def visitNotExpr(ctx: NotExprContext): Klass = {
    val bool: Klass = visit(ctx.expr())
    bool match {
      case Klass("boolean", _) => bool
      case k: Klass => throw UnaryOperatorError(k, ctx.expr().getStart)
    }
  }

  override def visitMethodCallExpression(ctx: MethodCallExpressionContext): Klass = {
    val targetsType: Klass = visit(ctx.expr(0))
    val methodName = ctx.ID.getText


    callerTypes.put(ctx, targetsType)

    targetsType.deepFind(methodName) match {
      case Some(method: Method) => handleMethodCall(ctx, method)
      case _ => throw UnresolvedSymbolError(methodName, ctx.ID.getSymbol)
    }
  }

  def handleMethodCall(ctx: MethodCallExpressionContext, method: Method): Klass = {
    val actualTypes: List[Klass] = ctx.expr().tail
      .map(visit).toList

    val expectedTypes: List[Klass] = method.argsDefList.toList
    val check: List[Boolean] = actualTypes.zip(expectedTypes)
      .map((pair) => pair._1 <| pair._2)

    check.contains(false) || actualTypes.length != expectedTypes.length match {
      case true => throw InvalidArgumentsList(method, expectedTypes, actualTypes, ctx.getStart)
      case false => method.kType
    }
  }

  override def visitIfStatement(ctx: IfStatementContext): Klass = {
    val predType = visit(ctx.expr())

    if(predType.name != "boolean")
      throw InvalidType(predType, klassMap get "boolean" get, ctx.expr().getStart)

    visitChildren(ctx)

    null
  }

  override def visitWhileLoopHead(ctx: WhileLoopHeadContext): Klass = {
    val predType = visit(ctx.expr())

    if(predType.name != "boolean")
      throw InvalidType(predType, klassMap get "boolean" get, ctx.expr().getStart)

    visitChildren(ctx)

    null
  }


  /**
    * Gets the enclosing klass for a scope.
    *
    * @param scope
    * A scope.
    * @return
    * The enclosing class for this scope.
    */
  @tailrec private def getEnclosingKlass(scope: Option[Scope]): Klass = {
    scope match {
      case Some(method: Method) => getEnclosingKlass(method.parentScope)
      case Some(block: Block) => getEnclosingKlass(block.parentScope)
      case Some(klass: Klass) => klass
      case _ => throw new AssertionError("Invalid type checker state.")
    }
  }

  private def enterScope(ctx: ParserRuleContext) = {
    currScope = Option(scopes get ctx)
    currScope match {
      case None => throw new AssertionError("Invalid type checker state.")
      case Some(scope) =>
        visitChildren(ctx) //run type check visitor on children
    }
    null
  }
}

object TypeChecker {
  /**
    * Gets the enclosing klass for a scope.
    *
    * @param scope
    * A scope.
    * @return
    * The enclosing class for this scope.
    */
  @tailrec def getEnclosingKlass(scope: Option[Scope]): Klass = {
    scope match {
      case Some(method: Method) => getEnclosingKlass(method.parentScope)
      case Some(block: Block) => getEnclosingKlass(block.parentScope)
      case Some(klass: Klass) => klass
      case _ => throw new AssertionError("Invalid type checker state.")
    }
  }

  def binaryOperatorCheck(leftType: Klass, rightType: Klass, offender: Token, operand: String)(implicit klassMap: KlassMap): Klass = {
    (leftType, rightType) match {
      case (Klass("int", _), Klass("int", _)) => klassMap get "int" get
      case _ => throw BinaryOperatorError(leftType, rightType, offender, operand)
    }
  }

  def binaryOperatorCheckBool(leftType: Klass, rightType: Klass, offender: Token, operand: String)(implicit klassMap: KlassMap): Klass = {
    (leftType, rightType) match {
      case (Klass("int", _), Klass("int", _)) => klassMap get "boolean" get
      case _ => throw BinaryOperatorError(leftType, rightType, offender, operand)
    }
  }
}
