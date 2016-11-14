package compiler.typecheck.scope

import Scope.SymbolMap
import compiler.typecheck.symbol.Symbol

/**
  * The object representing all classes during semantic analysis and synthesis.
  */
case class Klass(override val name: String, var _superKlass: Option[Klass] = None) extends Scope {
  val symbolTable = new SymbolMap()

  override def parentScope: Option[Klass] = _superKlass

  def superKlass_=(klass: Option[Klass]) = {
    _superKlass match  {
      case None => _superKlass = klass
      case Some(_) => throw new RuntimeException("Cannot set super klass more than once.")
    }
  }

  def superKlass = _superKlass

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
    var currKlass: Option[Klass] = Option(this)
    var result: Option[Symbol] = None

    while (result.isEmpty && currKlass.isDefined) {
      result = currKlass.get.symbolTable.get(name)
      currKlass = currKlass.get.superKlass
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
  throw new AssertionError("Initialized variables are not the responsibility of theKlass object")

  override def toString = s"Klass($name, $parentScope)"
}

object Klass {
  def apply(name: String) = new Klass(name, None)
  def apply(name: String, parent: Klass) = new Klass(name, Some(parent))
}
