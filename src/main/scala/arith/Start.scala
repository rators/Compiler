package arith

/**
  * Created by rtorres on 9/16/16.
  */
object Start extends App {
  val p = new Parser(new Lex())
  val result = p.parse
  println(s"\nResult + $result")
}
