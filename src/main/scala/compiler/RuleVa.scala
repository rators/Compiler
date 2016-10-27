package compiler

import org.antlr.v4.runtime.{RecognitionException, Recognizer, RuleContext}

/**
  * Base trait for rules.
  *
  * //if see else in bad location => else without if most likely.
  */
sealed trait RuleVa {
  val thisName: String
  def handleErr(rulesList: List[RuleVa], expectedTokens: List[String], offendingToken: String, e: RecognitionException, col: Int, row: Int): Unit = {
    throw SyntaxError(s"invalid $thisName, expected ${expectedTokens.mkString(",")}, but found $offendingToken", col, row)
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

case object Prog extends RuleVa {
  val thisName = "rava program"
}

case object MainClass extends RuleVa {
  val thisName = "main class"
}

case object ClassDecl extends RuleVa {
  val thisName = "class declaration"
}

case object VarDecl extends RuleVa {
  val thisName = "variable declaration"
}

case object MethodDecl extends RuleVa {
  val thisName = "method declaration"
}

case object CaseClassDecl extends RuleVa {
  val thisName = "case class declaration"

  override def handleErr(rulesList: List[RuleVa], expectedTokens: List[String], offendingToken: String, e: RecognitionException, column: Int, row: Int): Unit = {
    println(s"Expected tokens $expectedTokens")
    expectedTokens match {
      case List("')'") => throw SyntaxError(s"case class constructor missing closing token right 'right paren': ')'", column, row)
      case List("')'", "','") => throw SyntaxError(s"case class constructor argument list syntax invalid. Expected closing token right 'right paren': ')' or ',' separator", column, row)
      case l => throw SyntaxError(s"invalid case class declaration. expected $l", column, row)
    }
  }
}

case object FormatList extends RuleVa {
  val thisName = "argument/formal list"
}

case object Type extends RuleVa {
  val thisName = "type"
}


case object Expr extends RuleVa {
  val thisName = "expression"
  override def handleErr(rulesList: List[RuleVa], expectedTokens: List[String], offendingToken: String, e: RecognitionException, column: Int, row: Int): Unit = {
    expectedTokens match {
      case List("';'", _*) => throw SyntaxError("expected end of expression token ';'", column, row)
      case l => throw SyntaxError(s"invalid expression. expected $l", column, row)
    }
  }
}

case object Statement extends RuleVa {
  val thisName = "statement"
  override def handleErr(rulesList: List[RuleVa], expectedTokens: List[String], offendingToken: String, e: RecognitionException, col: Int, row: Int): Unit = {
    println(s"Expected tokens $expectedTokens")
    expectedTokens match {
      case List("'{'", _*) => throw SyntaxError("invalid statement, probable cause: '{' imbalance", col, row)
      case l => throw SyntaxError(s"invalid statement. expected $l", col, row)
    }
  }
}

case object CondExpr extends RuleVa {
  val thisName = "conditional expression"
}

case object Atom extends RuleVa {
  val thisName = "atom"
}