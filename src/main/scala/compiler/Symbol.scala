package compiler

import compiler.scope.Klass

sealed trait Symbol {
  val name: String
}

sealed trait TypedSymbol extends Symbol {
  val kType: Klass
  def paramID: Int
  def paramID_=(identifier: Int): Unit
  override def toString = s"<$name:$name>"
}

case class SubSymbol(override val name: String, override val kType: Klass) extends TypedSymbol {
  var _paramID: Int = _
  var _localID: Int = _

  def paramID = _paramID
  def paramID_=(identifier: Int): Unit = _paramID = identifier

  def localID = _localID
  def localID_=(identifier: Int): Unit = _localID = identifier
}

case class FieldSymbol(override val name: String, override val kType: Klass) extends TypedSymbol {
  var _id: Int = _
  def paramID = _id
  def paramID_=(identifier: Int): Unit = _id = identifier
}
