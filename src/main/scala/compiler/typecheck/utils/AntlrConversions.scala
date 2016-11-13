package compiler.typecheck.utils

import org.antlr.v4.runtime.tree.{ParseTree, ParseTreeProperty}

/**
  * Implicit conversions for antr classes. This is an attempt to make antlr class methods look
  * more like idiomatic scala code
  */
object AntlrConversions {

  implicit class ParseTreePropertyRa[T](val parseTreePropertyRa: ParseTreeProperty[T]){

    def +=(entry: (ParseTree, T)) = parseTreePropertyRa.put(entry._1, entry._2)

    def get(node: ParseTree): T = parseTreePropertyRa.get(node)

    def -=(node: ParseTree): T = parseTreePropertyRa.removeFrom(node)
  }

}
