package compiler.typecheck.scope

import compiler.typecheck.error.InvalidMethodOverload
import compiler.typecheck.scope.Scope.SymbolMap
import compiler.typecheck.symbol.Symbol
import org.antlr.v4.runtime.Token

import scala.util.{Failure, Success, Try}

/**
  * The object representing all classes during semantic analysis and synthesis.
  */
case class Klass(override val name: String, var _superKlass: Option[Klass] = None) extends Scope {
  val symbolTable = new SymbolMap()

  override def parentScope: Option[Klass] = _superKlass

  def superKlass_=(klass: Option[Klass]) = {
    _superKlass match {
      case None => _superKlass = klass
      case Some(_) => throw new RuntimeException("Cannot set super klass more than once.")
    }
  }

  def superKlass = _superKlass

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
  override def initVars: Set[Symbol] = symbolTable.values.toSet

  override def toString = s"Klass($name, $parentScope)"

  /**
    * Covariance check method.
    *
    * @param other
    * The class being compared to.
    * @return
    * True if this class is covariant with the other class
    */
  def <|(other: Klass): Boolean = parents contains other

  /**
    * Gets the parents for this class.
    *
    * @return
    * The list parents.
    */
  private def parents: List[Klass] = getInheritors(Some(this))

  /**
    * Gets the inheritors for a class
    *
    * @param k
    * Klass being compared to.
    * @return
    *
    */
  private def getInheritors(k: Option[Klass]): List[Klass] = {
    k match {
      case None => Nil
      case Some(klass) => klass :: getInheritors(klass.parentScope)
    }
  }

  def methods: List[Method] = getMethods(Some(this))

  /**
    * The methods contained in this class.
    * @return
    *         The methods.
    */
  def localMethods: List[Method] = symbolTable.values.filter((v) => v match {
    case m: Method => true
    case _ => false
  }).map(_.asInstanceOf[Method]).toList

  def getMethods(d: Option[Klass]): List[Method] = {
    d match {
      case None => Nil
      case Some(k: Klass) => k.localMethods ::: getMethods(k.parentScope)
    }
  }

}

object Klass {
  def apply(name: String) = new Klass(name, None)

  def apply(name: String, parent: Klass) = new Klass(name, Some(parent))

  def validMethodOverloading(child: Klass, parent: Klass, offender: Token): Try[Unit] = {
    val childMethods = child.methods
    val parentMethods = parent.methods

    val overriddenMethods: Iterable[Method] = childMethods.filter((childMethod) => parentMethods.exists(_.name == childMethod.name))

    val methodPairs = overriddenMethods.map((method) => method -> parentMethods.find(_.name == method.name).get)

    val methodMatches = methodPairs.map((pair) => ((pair._1, pair._2), pair._1.signature == pair._2.signature))


    val offenderOpt = methodMatches.find((v) => !v._2)

    offenderOpt match {
      case Some(((childMethod, parentMethod), false)) =>
        Failure(InvalidMethodOverload(parentMethod, parentMethod.ownerKlass.asInstanceOf[Klass], childMethod.ownerKlass.asInstanceOf[Klass], offender))
      case None => Success()
    }
  }
}
