package compiler

import java.io.{File, InputStream, PrintWriter}
import java.util.Scanner

import scala.sys.process

/**
  *
  */
object OutputConsumer extends App {
  val files = new File("./sootOutput")

  for {
    in <- files.listFiles()
    i <- in.listFiles()
  } {
    val process = Runtime.getRuntime.exec(Array[String]("java", "-cp", i.getAbsolutePath.replace(i.getName, ""), i.getName.replace(".class", "")))
    val outputBuilder: StringBuilder = new StringBuilder()

    val stream: InputStream = process.getInputStream()
    val sc = new Scanner(stream)

    while(sc.hasNext) {
      val o = sc.nextLine()
      outputBuilder.append(o).append(System.lineSeparator())
      println(o)

      val out = new PrintWriter("src/main/resources/sources/soot_gen/" + i.getName.replace(".class", "") + ".txt")
      out.println(outputBuilder.toString)
      out.close()
    }
  }

}
