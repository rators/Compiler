package arith

import error.ParseException

/**
  * Parser example
  */
class Parser(var lex: Lex) {
  def parse: Int = {
    S
  }

  def S: Int = {
    val r: Int = E
    if (lex.startMatch(Lex.END) == 0) throw new ParseException
    r
  }

  def E: Int = {
    var r: Int = T
    while (lex.startMatch('+') != 0) {
      r += T
    }
    r
  }

  def T: Int = {
    var r: Int = F
    while (lex.startMatch('*') != 0) {
      r *= F
    }
    r
  }

  def F: Int = {
    if (lex.startMatch('(') != 0) {
      val r: Int = E
      if (lex.startMatch(')') != 0) r
      else throw new ParseException
    }
    else {
      val litval: Char = lex.startMatch(Lex.LIT)
      if (litval != 0) {
        litval.toInt - '0'.toInt
      }
      else throw new ParseException
    }
  }
}
