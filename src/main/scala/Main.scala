import scala.collection.mutable.ListBuffer

/**
  *
  */
object Main extends App {



  def map[T, R](list: List[T], f: T => R): List[R] = {
    list match {
      case Nil => Nil
      case head :: tail => f(head) :: map(tail, f)
    }
  }

}
