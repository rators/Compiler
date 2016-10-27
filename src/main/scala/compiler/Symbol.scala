package compiler

sealed trait Identifier {
  val id: Int
}

case class LocalID(id: Int) extends Identifier
case class ParamID(id: Int) extends Identifier

sealed trait Symbol {
  val name: String

  override def toString: String = {
    this match {
      case other => other.toString
      case t: TypedSymbol => s"<${t.name}:${t.name}"
    }
  }
}

sealed trait TypedSymbol extends Symbol {
  val kType: Klass
  def id: Identifier
  def id_=(identifier: Identifier): Unit
}

case class SubSymbol(override val name: String, override val kType: Klass) extends TypedSymbol {
  var _id: Identifier = _
  def id = _id
  def id_=(identifier: Identifier): Unit = {
    identifier match {
      case local: LocalID => _id = local
      case param: ParamID => _id = param
    }
  }
}

case class FieldSymbol(override val name: String, override val kType: Klass) extends TypedSymbol {
  var _id: Identifier = _
  def id = _id
  def id_=(identifier: Identifier): Unit = {
    identifier match {
      case local: LocalID => throw new AssertionError(s"Field $name cannot be assigned a local id: $local")
      case param: ParamID => _id = param
    }
  }
}
