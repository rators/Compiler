package compiler.typecheck

import antlr4.MiniJavaBaseListener
import antlr4.MiniJavaParser._
import compiler.Logger
import compiler.scope.Klass

import scala.util.{Failure, Success}

/**
  * Visitor class used to collect the names of Klasses declared within the source program.
  *
  * See overridden method documentation for implementation details.
  */
class KlassDeclarator extends MiniJavaBaseListener {
  val klassMap = new KlassMap()

  override def enterBaseClass(ctx: BaseClassContext): Unit = {
    val test = ctx.getRuleIndex
    println(test)
    val name = ctx.ID.getText
    klassMap += Klass(name) match {
      case Failure(e: DuplicateClassErr) => throw new AssertionError(s"Duplicate class $name")
      case Success(s) => println(s"Class $name successfully added")
    }
  }

  override def enterChildClass(ctx: ChildClassContext): Unit = {
    val test = ctx.getRuleIndex
    println(test)
    val name = ctx.ID(0).getText
    klassMap += Klass(name) match {
      case Failure(e: DuplicateClassErr) => throw new AssertionError(s"Duplicate class $name")
      case Success(s) => println(s"Class $name successfully added")
    }
  }


  override def enterMainClass(ctx: MainClassContext): Unit = {
    val name = ctx.ID(0).getText
    klassMap += Klass(name)
  }

  override def enterCaseClassDecl(ctx: CaseClassDeclContext): Unit = {
    val test = ctx.getRuleIndex
    println(test)
    val name = ctx.ID.getText
    klassMap += Klass(name) match {
      case Failure(e: DuplicateClassErr) => throw new AssertionError(s"Duplicate class $name")
      case Success(s) => println(s"Class $name successfully added")
    }
  }
}
