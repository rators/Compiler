package compiler.typecheck.utils

import compiler.typecheck.scope.Klass

import scala.util.{Failure, Success, Try}

case class DuplicateClassErr(klass: Klass) extends Throwable

/**
  * A collection of klasses with safe guard accessors.
  */
class KlassMap {
  private val klassMap = scala.collection.mutable.HashMap[String, Klass]()

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

  def get(name: String) = klassMap get name

  private def values: Iterable[Klass] = klassMap.values

  private def keys: Set[String] = klassMap.keySet.toSet

  override def toString = klassMap.toString
}
