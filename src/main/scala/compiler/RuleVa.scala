package compiler

import org.antlr.v4.runtime.{RecognitionException, Recognizer, RuleContext}
import Logger._

import scala.collection.generic.SeqFactory

/**
  * Base trait for rules.
  */
sealed trait RuleVa {
  def handleErr(rulesList: List[RuleVa], expectedTokens: List[String], offendingToken: String, e: RecognitionException, col: Int, row: Int): Unit = {
    println(s"Offending symbol: $offendingToken")
  }
}

object CompilerImpl {
  def strToRule(str: String): RuleVa = {
    str match {
      case "prog" => Prog
      case "mainClass" => MainClass
      case "classDecl" => ClassDecl
      case "varDecl" => VarDecl
      case "methodDecl" => MethodDecl
      case "caseClassDecl" => CaseClassDecl
      case "formalList" => FormatList
      case "type" => Type
      case "expr" => Expr
      case "statement" => Statement
      case "condExpr" => CondExpr
      case "atom" => Atom
      case _ => throw new AssertionError(s"Invalid rule : $str")
    }
  }

  implicit class RuleVaCtx(ctx: RuleContext) {
    def ruleList()(implicit recognizer: Recognizer[_, _]): List[RuleVa] = {
      val rulesStr = ctx.toString(recognizer)
      val brakOff = (s: Char) => s == '[' || s == ']'
      rulesStr
        .split(' ')
        .map(_.filterNot(brakOff))
        .map(strToRule)
        .toList
    }
  }

}

case object Prog extends RuleVa

case object MainClass extends RuleVa

case object ClassDecl extends RuleVa

case object VarDecl extends RuleVa

case object MethodDecl extends RuleVa

case object CaseClassDecl extends RuleVa {

  override def handleErr(rulesList: List[RuleVa], expectedTokens: List[String], offendingToken: String, e: RecognitionException, column: Int, row: Int): Unit = {
    println(s"Expected tokens $expectedTokens")
    expectedTokens match {
      case List("')'") => throw SyntaxError("case class constructor missing closing token right 'right paren': ')'", column, row)
      case List("')'", "','") => throw SyntaxError("case class constructor argument list syntax invalid. Expected closing token right 'right paren': ')' or ',' separator", column, row)
    }
  }
}

case object FormatList extends RuleVa

case object Type extends RuleVa

case object Expr extends RuleVa {
  override def handleErr(rulesList: List[RuleVa], expectedTokens: List[String], offendingToken: String, e: RecognitionException, column: Int, row: Int): Unit = {
    expectedTokens match {
      case List("';'", _*) => throw SyntaxError("expected end of expression token ';'", column, row)
    }
  }
}

case object Statement extends RuleVa {
  override def handleErr(rulesList: List[RuleVa], expectedTokens: List[String], offendingToken: String, e: RecognitionException, col: Int, row: Int): Unit = {
    println(s"Expected tokens $expectedTokens")
    expectedTokens match {
      case List("'{'", _*) => throw SyntaxError("invalid statement, probable cause: '{' imbalance", col, row)
    }
  }
}

case object CondExpr extends RuleVa

case object Atom extends RuleVa