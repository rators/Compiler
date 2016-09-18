package bool

object Lex {
  val END: Char = '$'
  val LIT: Char = 'L'
  val ID: Char = 'I'
}

/**
  * Lexer for the boolean expression REPL.
  */
class Lex() {
  var yytext: Char = 0
  var token: Char = 0

  next()

  def reset() = {
    yytext = 0
    token = 0
    next()
  }

  private def next(): Unit = {
      token != Lex.END match {
        case true =>
          val c = consume
          yytext = c.toChar
          c == -1 match { // -1 is a char unsupported by the jvm codec.
            case true =>
              yytext = Lex.END
              token = Lex.END
            case false => parseYYText()
          }
        case false => println("Lexer finished")
      }
  }

  private def parseYYText(): Unit = {
    yytext match {
      case '\n' =>
        yytext = Lex.END
        token = Lex.END
      case '&' => token = yytext
      case '|' => token = yytext
      case '^' => token = yytext
      case '~' => token = yytext
      case '=' => token = yytext
      case '?' => token = yytext
      case '(' => token = yytext
      case ')' => token = yytext
      case _ if yytext >= 'a' && yytext <= 'z' => token = Lex.ID
      case _ if yytext >= '0' && yytext <= '1' => token = Lex.LIT
      case _ if yytext == ' ' || yytext == '\n' => next()
      case _ =>
        println(s"Unexpected character $yytext \n")
        next()
    }
  }

  private def consume: Int = {
    System.in.read
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