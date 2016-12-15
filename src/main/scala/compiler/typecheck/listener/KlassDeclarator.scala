package compiler.typecheck.listener

import antlr4.MiniJavaBaseListener
import antlr4.MiniJavaParser._
import compiler.typecheck.scope.Klass
import compiler.typecheck.symbol.{PropertySymbol, VarSymbol}
import compiler.typecheck.utils.{DuplicateClassErr, KlassMap}

import scala.util.{Failure, Success}

/**
  * Visitor class used to collect the names of Klasses declared within the source program.
  *
  * See overridden method documentation for implementation details.
  */
class KlassDeclarator extends MiniJavaBaseListener {
  val klassMap = new KlassMap()

  override def enterBaseClass(ctx: BaseClassContext): Unit = {
    val name = ctx.ID.getText
    klassMap += Klass(name) match {
      case Failure(e: DuplicateClassErr) => throw new AssertionError(s"Duplicate class $name")
      case Success(s) =>
        addPrimitives(s)
        println(s"Class $name successfully added")
    }
  }

  override def enterChildClass(ctx: ChildClassContext): Unit = {
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
    val name = ctx.ID.getText
    klassMap += Klass(name) match {
      case Failure(e: DuplicateClassErr) => throw new AssertionError(s"Duplicate class $name")
      case Success(s) => println(s"Class $name successfully added")
    }
  }

  private def addPrimitives(klass: Klass): Unit = {
    List("true", "false")
      .map((name) => VarSymbol(name, klassMap get "boolean" get))
      .foreach((symbol) => {
        klass.initialize(symbol)
        klass.define(symbol)
      })

//    val intSymbol = PropertySymbol("int", klassMap get "int" get)
//    val intArrSymbol = PropertySymbol("int[]", klassMap get "int[]" get)
//
//    klass initialize PropertySymbol("int", klassMap get "int" get)
//    klass define
//    PropertySymbol("int", klassMap get "int[]" get)

  }
}