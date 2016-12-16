import scala.io.Source

/**
  *
  */
object DLVerifier extends App {
  verifyDiffs()

  def verifyDiffs(): Unit = {

    List(
      "BinarySearch.txt",
      "BinaryTree.txt",
      "BubbleSort.txt",
      "Factorial.txt",
      "LinearSearch.txt",
      "LinkedList.txt",
      "QuickSort.txt"
    ).foreach((name) => {
      val javaFileName = s"./src/main/resources/sources/java_gen/$name"
      val ravaFileName = s"./src/main/resources/sources/rava_gen/$name"
      val sootFileName = s"./src/main/resources/sources/soot_gen/$name"

      val javaOutput = fileToString(javaFileName)
      val ravaOutput = fileToString(ravaFileName)
      val sootOutput = fileToString(sootFileName)

      assert(ravaOutput == sootOutput)
      assert(ravaOutput == javaOutput)
    })
  }

  def fileToString(filePath: String): String = Source.fromFile(filePath).getLines().mkString("")

}
