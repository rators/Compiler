package bool

/**
  * Start for the boolean parser.
  */
object Start extends App {
  implicit val history = History()

  val parser = new Parser(new Lex())

  while(true) {
    val r = parser.parse
    println(r)
    parser.reset()
  }
}
