package bool

import scala.collection.mutable

/**
  * Convenience class for holding the values of ids.
  */
class History() {
  val iDValues: mutable.ArrayBuffer[Boolean] = mutable.ArrayBuffer.fill(26)(false)

  /**
    * Retrieves the value associated with the id.
    * @param id
    *           The id associated with a value.
    * @return
    *         The boolean value associated with an id.
    */
  def get(id: Char): Boolean = {
    checkValid(id, () => iDValues(toInd(id)))
  }

  /**
    * Sets the value associated with an id.
    * @param id
    *           The id associated with a value.
    * @param value
    *           The value associated with an id.
    * @return
    *         The boolean value associated with an id.
    */
  def set(id: Char, value: Boolean): Boolean = {
    val setId: () => Boolean = () => {
      iDValues(toInd(id)) = value
      iDValues(toInd(id))
    }
    checkValid(id, setId)
  }

  def set(p: (Char, Boolean)): Boolean = set(p._1, p._2)

  private def checkValid(id: Char, action: () => Boolean): Boolean =
    id.toInt >= 'a' && id.toInt <= 'z' match {
      case true => action()
      case false => throw new AssertionError(s"$id is not a lower case letter from a-z")
    }

  implicit def toInd(id: Char): Int = id.toInt - 97
}

object History {
  def validateId(id: Char) = id.toInt >= 'a' && id.toInt <= 'z'
  def apply() = new History()
}

object HistoryTest extends App {
  val history = History()

  println(history get 'a')

  println(history set 'a' -> true)

  println(history get 'a')
}
