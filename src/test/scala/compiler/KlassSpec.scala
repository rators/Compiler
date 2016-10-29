package compiler

import compiler.scope.Klass

import collection.mutable.Stack
import org.scalatest._
/**
  * Test class for Klass.
  */
class KlassSpec extends FlatSpec with Matchers {
  "A Klass should" should "contain the symbols pushed into it"in {
    val klass = new Klass("Test", None)
    val symbols = Set(FieldSymbol("A", klass), FieldSymbol("B", klass))

    // def all symbols.
    symbols.foreach((s) => klass.defSymbol(s.name -> s))

    val actualSymbols = klass.symbolTable

    // compare the symbol size and values.
    actualSymbols.values.toSet shouldEqual symbols
  }

  "A Klass should not be called to initialize variables to its top leveldireclty"


}
