package optimization

import soot.{PackManager, Transform}

/**
  *
  */
object Main extends App {
  override val args: Array[String] = Array("-via-shimple", "-process-dir", "./src/main/resources/sources/gen")

  if (args.length == 0) {
    System.out.println("Syntax: java Main <classfile> [soot options]")
    System.exit(0)
  }

  PackManager.v.getPack("stp").add(new Transform("stp.MyTransformer", new ConstantRefTransform))
  soot.Main.main(args)
}
