package error

/**
  * Parser error thrown when a parser error is encountered.
  */
case class ParseException(msg: String) extends Exception {
  def this() = this("")

  override def toString = msg
}

object ParserError {
  def idLitErr(tok: Any) = s"" +
    s"Invalid id or literal token $tok: " +
    s"expected an id range [a-z] or 1 [true] or 0 [false] or" +
    s"expression statement"

  def startTokenErr(tok: Any) = s"Invalid id, expected and id in range [a-z]"
}