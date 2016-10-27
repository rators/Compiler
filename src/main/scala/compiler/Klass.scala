package compiler

import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * The object representing all classes during semantic analysis and synthesis.
  */
class Klass(name: String, superClass: Option[Klass]) extends Scope {
  type SymbolMap = collection.mutable.HashMap[String, Symbol]

  val symbolTable = new SymbolMap()
  override val scopeName: String = _


  override val parentScope: Option[Scope] = None

  //utility method to make things less ugly :( ...
  def defSymbol(e: (String, Symbol)): Option[Symbol] = symbolTable.put(e._1, e._2)

  /**
    * Define a symbol within this scope.
    *
    * @param symbol
    * The symbol to be defined.
    */
  override def define(symbol: Symbol): Unit = defSymbol(symbol.name -> symbol)

  /**
    * Initializes a symbol within this scope.
    *
    * @param symbol
    * The symbol to be initialized.
    */
  override def initialize(symbol: Symbol): Unit = {
    throw new AssertionError("Class symbols cannot be re-initialized after definition.")
  }

  /**
    * Searches for the symbol associated with the name parameter
    * in this scope and all scopes enclosed within this scope.
    *
    * @param name
    * The name of some symbol.
    * @return
    * The symbol if one exists.
    */
  override def deepFind(name: String): Option[Symbol] = {
    var currKlass = Option(this)
    var result: Option[Symbol] = None

    while (result.isEmpty && currKlass.isDefined) {
      result = currKlass.get.symbolTable.get(name)
      currKlass = currKlass.get.superClass
    }

    result
  }

  /**
    * Searches for the symbol associated with the name parameter
    * in top-level of this scope.
    *
    * @param name
    * The name of some symbol.
    * @return
    * The symbol if one exists.
    */
  override def shallowFind(name: String): Option[Symbol] = symbolTable.get(name)

  /**
    * Returns true if and only if their exists a scope whose name is equal to the name parameter.
    *
    * @param name
    * The name of some scope.
    * @return
    * True if and only if their exists a scope whose name is equal to the name of the name parameter.
    */
  override def isInitialized(name: String): Boolean = symbolTable.get(name).isDefined

  /**
    * The set of all variables initialized in this scope.
    *
    * @return
    * A set of symbols initialized in this scope.
    */
  override def initVars: Set[Symbol] =
  throw new AssertionError("Initialized variables are not the responsibility of the Klass object")
}
