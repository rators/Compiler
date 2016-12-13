package compiler.typecheck.symbol

import compiler.typecheck.scope.Klass

trait Symbol {
  val name: String
  val kType: Klass
}

trait TypedSymbol extends Symbol {
  val kType: Klass
  def paramID: Int
  def paramID_=(identifier: Int): Unit
  override def toString = s"<$name:$name>"
}

trait SubSymbol extends TypedSymbol {
  var _paramID: Int = _
  var _localID: Int = _

  def paramID = _paramID
  def paramID_=(identifier: Int): Unit = _paramID = identifier

}

case class PropertySymbol(override val name: String, override val kType: Klass) extends TypedSymbol {
  var _id: Int = _
  def paramID = _id
  def paramID_=(identifier: Int): Unit = _id = identifier
}

case class VarSymbol(override val name: String, override val kType: Klass) extends TypedSymbol {
  var _id: Int = _
  var _localID: Int = _

  def paramID = _id
  def paramID_=(identifier: Int): Unit = _id = identifier

  def localID = _localID
  def localID_=(identifier: Int): Unit = _localID = identifier
}

case class ParamSymbol(override val name: String, override val kType: Klass) extends TypedSymbol {
  var _id: Int = _
  var _localID: Int = _

  def paramID = _id
  def paramID_=(identifier: Int): Unit = _id = identifier

  def localID = _localID
  def localID_=(identifier: Int): Unit = _localID = identifier
}