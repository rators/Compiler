package bool

import error.{ParseException, ParserError}
import Parser._

/**
  * Parser for boolean expression REPL.
  * @param lex
  *            The lexer used to turn tokens into lexemes from standard input.
  */
class Parser(lex: Lex)(implicit history: History) {

  def reset() = {
    lex.reset()
  }

  def parse = S

  private def S: Boolean = {
    val id = lex.startMatch(Lex.ID)
    id != 0 match {
      case true => S1(id)
      case false =>
        throw ParseException(ParserError.startTokenErr(id))
    }
  }

  private def S1(id: Char): Boolean = {
    val qMark = lex.startMatch('?')
     qMark != 0 match {
      case true => history get id // Do a query here
      case false =>
        val eQmark = lex.startMatch('=')
        eQmark != 0 match {
          case true =>
            history set id -> EXP
          case false =>
            throw ParseException(s"Unexpected token received, expected a query operator [?] or assignment operator [=]")
        }
    }
  }

  private def EXP: Boolean = {
    var r = A
    while(lex.startMatch('^') != 0) {
      r = r ^ A
    }
    r
  }

  private def A: Boolean = {
    var r = B
    while(lex.startMatch('|') != 0) {
      r = r | B
    }
    r
  }

  private def B: Boolean = {
    var r = C
    while(lex.startMatch('&') != 0) {
      r = r & C
    }
    r
  }

  private def C: Boolean = {
    lex.startMatch('~') != 0 match {
      case true => ! C
      case false => D
    }
  }

  private def D: Boolean = {
    lex.startMatch('(') != 0 match {
      case true =>
        val r: Boolean = EXP
        lex.startMatch(')') != 0 match {
          case true => r
          case false =>
            throw ParseException("Unbalanced parentheses, all open parentheses '(' must have a corresponding ')'")
        }
      case false => E
    }
  }

  private def E: Boolean = {
    val litVal = lex.startMatch(Lex.LIT)
    litVal != 0 match {
      case true => litVal // return the literals boolean value if there is one
      case false =>
        val idVal = lex.startMatch(Lex.ID)
        History.validateId(idVal) match {
          case true => history get idVal // get the ids value
          case false => throw ParseException(ParserError.idLitErr(idVal))
        }
    }
  }
}

object Parser {
  implicit def toBool(boolLit: Char): Boolean = {
    boolLit match {
      case '0' => false
      case '1' => true
      case _ =>
        throw new AssertionError(s"Cannot convert $boolLit to a boolean, must be 1 or 0")
    }
  }
}
