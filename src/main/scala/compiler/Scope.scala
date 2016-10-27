package compiler

/**
  * A trait representing a scope.
  */
trait Scope {
  /**
    * The name of this scope.
    */
  val scopeName: String

  /**
    * Some enclosing scope for this scope if one exists.
    */
  val parentScope: Option[Scope]

  /**
    * Define a symbol within this scope.
    *
    * @param symbol
    * The symbol to be defined.
    */
  def define(symbol: Symbol): Unit

  /**
    * Initializes a symbol within this scope.
    *
    * @param symbol
    * The symbol to be initialized.
    */
  def initialize(symbol: Symbol): Unit

  /**
    * Searches for the symbol associated with the name parameter
    * in this scope and all scopes enclosed within this scope.
    *
    * @param name
    * The name of some symbol.
    * @return
    * The symbol if one exists.
    */
  def deepFind(name: String): Option[Symbol]

  /**
    * Searches for the symbol associated with the name parameter
    * in top-level of this scope.
    *
    * @param name
    * The name of some symbol.
    * @return
    * The symbol if one exists.
    */
  def shallowFind(name: String): Option[Symbol]

  /**
    * Returns true if and only if their exists a scope whose name is equal to the name parameter.
    *
    * @param name
    * The name of some scope.
    * @return
    * True if and only if their exists a scope whose name is equal to the name of the name parameter.
    */
  def isInitialized(name: String): Boolean

  /**
    * The set of all variables initialized in this scope.
    *
    * @return
    * A set of symbols initialized in this scope.
    */
  def initVars: Set[Symbol]
}

object Scope {

}
