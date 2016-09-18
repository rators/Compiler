package arith

import java.io.IOException
import error.ParseException

object Lex {
  val END: Char = '$'
  val LIT: Char = 'L'
}

class Lex () {
  var yytext: Char = 0
  var token: Char = 0

  next()

  private def next() {
    println(s"yytext = $yytext")
    try {
      if (token != Lex.END) {
        val c: Int = System.in.read
        yytext = c.toChar
        if (c == -1) {
          yytext = Lex.END
          token = Lex.END
        }
        else if (yytext == '\n') {
          yytext = Lex.END
          token = Lex.END
        }
        else if (yytext == '+') token = yytext
        else if (yytext == '*') token = yytext
        else if (yytext == '(') token = yytext
        else if (yytext == ')') token = yytext
        else if (yytext >= '0' && yytext <= '9') token = Lex.LIT
        else if (yytext == ' ' || yytext == '\n') next()
        else {
          System.out.println("Unexpected character: " + yytext + "\n")
          next()
        }
      }
    }
    catch {
      case e: IOException => throw new ParseException
    }
  }

  def check(tok: Char): Char = {
    if (tok == token) yytext
    else 0
  }

  def startMatch(tok: Char): Char = {
    val lexval: Char = check(tok)
    if (lexval != 0) next()
    lexval
  }
}