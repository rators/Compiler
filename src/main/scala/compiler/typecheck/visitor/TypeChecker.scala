package compiler.typecheck.visitor

import antlr4.MiniJavaParser._
import antlr4.{MiniJavaBaseVisitor, MiniJavaParser}
import compiler.typecheck.scope.{Block, Klass, Method, Scope}
import compiler.typecheck.utils.KlassMap
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.{ParseTreeProperty, TerminalNode}

/**
  *
  */
class TypeChecker(
                   val klassMap: KlassMap, val scopes: ParseTreeProperty[Scope],
                   val callerTypes: ParseTreeProperty[Klass], parser: MiniJavaParser
                 ) extends MiniJavaBaseVisitor[Klass] {

  var currScope: Option[Scope] = None
  implicit val _map = klassMap

  override def visitMainClass(ctx: MainClassContext): Klass = enterScope(ctx)

  override def visitBaseClass(ctx: BaseClassContext): Klass = enterScope(ctx)

  override def visitChildClass(ctx: ChildClassContext): Klass = enterScope(ctx)

  override def visitCaseClassDecl(ctx: CaseClassDeclContext): Klass = enterScope(ctx)

  override def visitParenExpr(ctx: ParenExprContext): Klass = visit(ctx.expr())

  override def visitType(ctx: TypeContext): Klass = {
    Option(ctx.ID) match {
      case None => klassMap get ctx.getText match {
        case None => throw new AssertionError(s"Unresolved symbol ${ctx.getText}")
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
    val sigTypeName = ctx.ID().getText
    klassMap get sigTypeName match {
      case Some(sigType) =>
    }

  }

  override def visitVarDefinition(ctx: VarDefinitionContext): Klass = {
    currScope match {
      case None => throw new AssertionError("Invalid type checker state.")
      case Some(scope) =>
        val varName = ctx.ID().getSymbol.getText
        scope.deepFind(varName) match {
          case None => throw new AssertionError(s"Unresolved symbol $varName")
          case Some(leftSymbol) =>
            val leftType = leftSymbol.kType
            visit(ctx.expr()) match {
              case Klass(leftType.name, _) => null
              case Klass(rightName, _) => throw new AssertionError(s"Incompatible types: ${leftType.name} and $rightName")
            }
        }
    }
  }

  override def visitMultiplyExpression(ctx: MultiplyExpressionContext): Klass = {
    val leftType = visit(ctx.expr(0))
    val rightType = visit(ctx.expr(1))
    TypeChecker.binaryOperatorCheck(leftType, rightType, "*")
  }

  override def visitSubtractExpression(ctx: SubtractExpressionContext): Klass = {
    val leftType = visit(ctx.expr(0))
    val rightType = visit(ctx.expr(1))
    TypeChecker.binaryOperatorCheck(leftType, rightType, "-")
  }

  override def visitPlusExpression(ctx: PlusExpressionContext): Klass = {
    val leftType = visit(ctx.expr(0))
    val rightType = visit(ctx.expr(1))
    TypeChecker.binaryOperatorCheck(leftType, rightType, "+")
  }

  override def visitLessThanExpr(ctx: LessThanExprContext): Klass = {
    val leftType = visit(ctx.expr(0))
    val rightType = visit(ctx.expr(1))
    TypeChecker.binaryOperatorCheck(leftType, rightType, "<")
  }

  override def visitGreaterThanExpr(ctx: GreaterThanExprContext): Klass = {
    val leftType = visit(ctx.expr(0))
    val rightType = visit(ctx.expr(1))
    TypeChecker.binaryOperatorCheck(leftType, rightType, ">")
  }

  override def visitArrayAccessExpression(ctx: ArrayAccessExpressionContext): Klass = {
    val arrType = visit(ctx.atom())
    val indexType = visit(ctx.expr())

    arrType match {
      case Klass("int[]", _) => indexType match {
        case Klass("int", _) => klassMap get "int" get
        case Klass(actual, _) => throw new AssertionError(s"Required an int but found an $actual")
      }
      case Klass(actual, _) => throw new AssertionError(s"Invalid array type $actual")
    }

  }

  override def visitArrLenExpression(ctx: ArrLenExpressionContext): Klass = {
    val intArr = visit(ctx.expr())
    intArr match {
      case Klass("int[]", _) => klassMap get "int" get
      case Klass(actual, _) => throw new AssertionError(s"Invalid operand type $actual for .length")
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
    visitChildren(ctx)
    val symbolName = ctx.ID().getSymbol.getText
    currScope match {
      case None => throw new AssertionError("Invalid type checker state.")
      case Some(scope) => scope.deepFind(symbolName) match {
        case None => throw new AssertionError(s"Symbol $symbolName not found.")
        case Some(symbol) => symbol.kType
      }
    }
  }

  override def visitIntegerArr(ctx: IntegerArrContext): Klass = {
    val sizeType = visit(ctx.expr())

    sizeType match {
      case Klass("int", _) => klassMap get "int[]" get
      case Klass(actual, _) => throw new AssertionError(s"Array size must be an int, found $actual")
    }
  }

  override def visitConstructorCall(ctx: ConstructorCallContext): Klass = {
    val klassType = klassMap get ctx.ID().getText

    klassType match {
      case None => throw new AssertionError(s"Symbol ${ctx.ID().getText} not found.")
      case Some(klass) => klass
    }
  }

  override def visitNotExpr(ctx: NotExprContext): Klass = {
    val bool: Klass = visit(ctx.expr())
    bool match {
      case Klass("boolean", _) => bool
      case _ => throw new AssertionError(s"Invalid type: ${bool.name} for unary operator '!'.")
    }
  }

  private def getEnclosingKlass(scope: Option[Scope]): Klass = {
    scope match {
      case block: Some[Block] =>
        val blockScope: Block = block.get
        getEnclosingKlass(blockScope.parentScope)
      case method: Some[Method] =>
        val methodScope: Method = method.get
        getEnclosingKlass(methodScope.parentScope)
      case klass: Some[Klass] => klass.get
      case _ => throw new AssertionError("Invalid type checker state.")
    }
  }

  private def enterScope(ctx: ParserRuleContext) = {
    currScope = Option(scopes get ctx)

    currScope = currScope match {
      case None => throw new AssertionError("Invalid type checker state.")
      case Some(scope) =>
        visitChildren(ctx) //run type check visitor on children
        scope.parentScope
    }
    null
  }
}

object TypeChecker {
  def binaryOperatorCheck(leftType: Klass, rightType: Klass, operand: String)(implicit klassMap: KlassMap): Klass = {
    (leftType, rightType) match {
      case (Klass("int", _), Klass("int", _)) => klassMap get "int" get
      case _ => throw new AssertionError(s"Invalid types (${leftType.name}, ${rightType.name}) for operator $operand")
    }
  }
}
