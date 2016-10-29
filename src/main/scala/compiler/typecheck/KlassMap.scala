package compiler.typecheck

import compiler.scope.Klass

import scala.util.{Failure, Success, Try}

case class DuplicateClassErr(klass: Klass) extends Throwable

/**
  * A collection of klasses with safe guard accessors.
  */
class KlassMap extends {
  val klassMap = scala.collection.mutable.HashMap[String, Klass]()

  List(
    "int[]",
    "int",
    "boolean"
  ).foreach((name) => klassMap += name -> Klass(name))

  def +=(klass: Klass): Try[Klass] = {
    klassMap.contains(klass.name) match {
      case true => Failure(DuplicateClassErr(klass))
      case false =>
        klassMap += klass.name -> klass
        Success(klass)
    }
  }

  override def toString = klassMap.toString
}
