package compiler.scope
import compiler.Symbol
import Scope.SymbolMap
/**
  * A class representing a block scope.
  */
class Block(_parentScope: Scope) extends Scope {
  override val name: String = ""
  override val parentScope: Option[Scope] = Some(_parentScope)

  val locals = new SymbolMap()
  val initializedVariables = new SymbolMap()

  /**
    * Define a symbol within this scope.
    *
    * @param symbol
    * The symbol to be defined.
    */
  override def define(symbol: Symbol): Unit = locals += symbol.name -> symbol

  /**
    * Initializes a symbol within this scope.
    *
    * @param symbol
    * The symbol to be initialized.
    */
  override def initialize(symbol: Symbol): Unit = initializedVariables += symbol.name -> symbol

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
    locals.get(name) match {
      case None => parentScope.get.deepFind(name)
      case Some(symbol) => Some(symbol)
    }
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
  override def shallowFind(name: String): Option[Symbol] = locals get name

  /**
    * Returns true if and only if their exists a scope whose name is equal to the name parameter.
    *
    * @param name
    * The name of some scope.
    * @return
    * True if and only if their exists a scope whose name is equal to the name of the name parameter.
    */
  override def isInitialized(name: String): Boolean = {
    locals.get(name) match {
      case Some(symbol) => true
      case _ => parentScope.get.isInitialized(name)
    }
  }

  /**
    * The set of all variables initialized in this scope.
    *
    * @return
    * A set of symbols initialized in this scope.
    */
  override def initVars: Set[Symbol] = locals.values.toSet
}
