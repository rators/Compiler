package compiler.typecheck.visitor

import antlr4.MiniJavaParser._
import antlr4.{MiniJavaBaseVisitor, MiniJavaParser}
import compiler.typecheck.scope.{Klass, Scope}
import compiler.typecheck.utils.KlassMap
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTreeProperty

/**
  *
  */
class TypeChecker(
      val klassMap: KlassMap, val scopes: ParseTreeProperty[Scope],
      val callerTypes: ParseTreeProperty[Klass], parser: MiniJavaParser
      ) extends MiniJavaBaseVisitor[Klass] {

  var currScope: Option[Scope] = None

  override def visitIdLiteral(ctx: IdLiteralContext): Klass = {
    println(ctx.ID())
    klassMap get "boolean" get
  }

  override def visitParenExpr(ctx: ParenExprContext): Klass = visit(ctx.condExpr())

  def enterScope(ctx: ParserRuleContext) = {
    currScope = Option(scopes get ctx)

    currScope = currScope match {
      case None => throw new AssertionError("Invalid type checker state.")
      case Some(scope) =>
        visitChildren(ctx) //run type check visitor on children
        scope.parentScope
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

  override def

}
