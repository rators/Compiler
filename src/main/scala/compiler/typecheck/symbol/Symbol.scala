package compiler.typecheck.symbol

import compiler.typecheck.scope.Klass

trait Symbol {
  val name: String
  val kType: Klass

  private var _id: Option[Int] = None

  def id: Int = _id.get

  def id_=(i: Int) = _id = Some(i)

  def hasId: Boolean = _id.isDefined

  override def toString = s"<$name:$name>"
}

trait SubSymbol extends Symbol {
  var _paramID: Int = _
  var _localID: Int = _

  def paramID = _paramID

  def paramID_=(identifier: Int): Unit = _paramID = identifier
}

case class PropertySymbol(override val name: String, override val kType: Klass) extends Symbol

case class VarSymbol(override val name: String, override val kType: Klass) extends Symbol

case class ParamSymbol(override val name: String, override val kType: Klass) extends Symbol