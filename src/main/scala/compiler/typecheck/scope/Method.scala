package compiler.typecheck.scope

import antlr4.MiniJavaParser.{CaseClassDeclContext, MethodDeclContext}
import compiler.typecheck.scope.Scope.{LinkedSymbolMap, SymbolMap}
import compiler.typecheck.symbol.{SubSymbol, Symbol}

/**
  * The class associated with methods of type scope.
  */
class Method(override val name: String, override val kType: Klass, val ownerKlass: Scope, val paramDefs: LinkedSymbolMap) extends Scope with SubSymbol {
  val locals = new SymbolMap()
  val initializedVariables = new SymbolMap()

  override val parentScope:Option[Scope] = Some(ownerKlass)

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
    paramDefs get name match {
      case None => locals get name match {
        case None => parentScope.get.deepFind(name)
        case s: Some[Symbol] => s
      }
      case s: Some[Symbol] => s
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
  override def shallowFind(name: String): Option[Symbol] = {
    paramDefs get name match {
      case symbol: Some[Symbol] => symbol
      case None => locals get name
    }
  }

  /**
    * Returns true if and only if their exists a scope whose name is equal to the name parameter.
    *
    * @param name
    * The name of some scope.
    * @return
    * True if and only if their exists a scope whose name is equal to the name of the name parameter.
    */
  override def isInitialized(name: String): Boolean = {
    val existsInLocalScop = exists(name)
    existsInLocalScop match {
      case true => existsInLocalScop
      case false => parentScope.get.isInitialized(name)
    }
  }

  private def exists(name: String): Boolean = initVars.exists(_.name == name) || paramDefs.exists(_._2.name == name)

  /**
    * The set of all variables initialized in this scope.
    *
    * @return
    * A set of symbols initialized in this scope.
    */
  override def initVars: Set[Symbol] = initializedVariables.values.toSet

  def argsList: Iterable[Symbol] = paramDefs.values

  def argsDefList: Iterable[Klass] = argsList.map(_.kType)

  override def toString = s"Method($locals, $initializedVariables, $name, $kType, $parentScope, $paramDefs)"

}

object Method {
  def getSignatureSimple(methodDeclContext: MethodDeclContext) = methodDeclContext.ID().getText concat "()"
  def getSignatureSimple(methodDeclContext: CaseClassDeclContext) = methodDeclContext.ID().getText concat "()"

}
