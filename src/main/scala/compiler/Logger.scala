package compiler

object Logger {
  def logErr(errMsg: String, column: Int, row: Int): String = {
    s"${27.toChar}[31mSyntax Error [$row,$column] - $errMsg${27.toChar}[0m"
  }
}
