package compiler.typecheck.error

import compiler.typecheck.scope.{Klass, Method}
import org.antlr.v4.runtime.Token

trait TypeError

case class BinaryOperatorError(leftType: Klass, rightType: Klass, offender: Token, operator: Error.Operator)
  extends AssertionError(Error.binaryOperatorMessage(leftType, rightType, offender, operator))

case class InvalidReturnType(expectedType: Klass, actualType: Klass, offender: Token)
extends AssertionError(Error.invalidReturnType(expectedType, actualType, offender))

case class InvalidVarDefinition(expected: String, actual: String, offender: Token)
  extends AssertionError(Error.invalidVarDefinition(actual, expected, offender))

case class InvalidMethodOverload(parentMethod: Method, parentKlass: Klass, offendingKlass: Klass, offender: Token)
  extends AssertionError(Error.invalidMethodOverloadMessage(parentMethod, parentKlass, offendingKlass, offender))

case class SymbolAlreadyDefinedError(symbol: String, offender: Token)

case class UnresolvedSymbolError(symbol: String, offender: Token)
  extends AssertionError(Error.unresolvedSymbolMessage(symbol, offender))

case class InvalidArrayIndexType(actualType: String, offender: Token)
  extends AssertionError(Error.invalidArrIndexType(actualType, offender))

case class InvalidArgumentsList(method: Method, expected: List[Klass], actual: List[Klass], offender: Token)
  extends AssertionError(Error.invalidMethodCall(method, expected, actual, offender))

case class UnaryOperatorError(actual: Klass, offender: Token)
  extends AssertionError(Error.invalidUnaryOperator(actual, offender))


case class InvalidType(actual: Klass, expected: Klass, offender: Token)
  extends AssertionError(Error.invalidType(actual, expected, offender))

/**
  *
  */
object Error {
  def invalidReturnType(expectedType: Klass, actualType: Klass, offender: Token): String = {
    s"${errorPosit(offender)} Invalid return type: method return type [${actualType.name}] not <| with signature type [${expectedType.name}]"
  }

  type Operator = String

  def invalidType(actual: Klass, expected: Klass, offender: Token): String = {
    s"${errorPosit(offender)} Invalid type: found a [${actual.name}], but expected a [${expected.name}]."
  }

  def invalidMethodCall(method: Method, expected: List[Klass], actual: List[Klass], offender: Token): String = {
    s"${errorPosit(offender)} Invalid call to method ${method.name}: found argument types ${actual.map(_.name).mkString("<", ",", ">")}, expected ${expected.mkString("<", ",", ">")}."
  }

  def binaryOperatorMessage(leftType: Klass, rightType: Klass, offender: Token, operator: Operator): String = {
    s"${errorPosit(offender)} Invalid types [${leftType.name}, ${rightType.name}] for operator '$operator'."
  }

  def invalidMethodOverloadMessage(parentMethod: Method, parentKlass: Klass, offendingKlass: Klass, offender: Token): String = {
    s"${errorPosit(offender)} Invalid overload for method [${parentMethod.name}] in class [${offendingKlass.name}] extending [${parentKlass.name}]."
  }

  def invalidUnaryOperator(actual: Klass, offender: Token): String = {
    s"${errorPosit(offender)} Invalid type for unary operator. Expected [boolean] but found ${actual.name}."
  }

  def unresolvedSymbolMessage(symbol: String, offender: Token): String = {
    s"${errorPosit(offender)} Unresolved symbol : $symbol."
  }

  def errorPosit(offender: Token): String = {
    s"Error:${offender.getLine -> offender.getCharPositionInLine}."
  }

  def invalidVarDefinition(actualType: String, expectedType: String, offender: Token) = {
    s"${errorPosit(offender)} Invalid definition: variable of type: [$expectedType] incompatible with type [$actualType]."
  }

  def invalidArrIndexType(actualType: String, offender: Token) = {
    s"${errorPosit(offender)} Invalid array index of type [$actualType], must be of type [int]."
  }
}
