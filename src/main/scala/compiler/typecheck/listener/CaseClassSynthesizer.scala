package compiler.typecheck.listener

import java.io._

import antlr4.MiniJavaBaseListener
import antlr4.MiniJavaParser.CaseClassDeclContext

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * The case class synthesizer method.
  */
class CaseClassSynthesizer extends MiniJavaBaseListener {
  val caseMap = mutable.HashMap[String, String]()

  override def enterCaseClassDecl(ctx: CaseClassDeclContext): Unit = {
    val properties = ctx.caseProperty.asScala
    val methodArgs = properties.map((p) => s"${p.`type`().getText} _${p.ID().getText}").mkString("(", ",", ")")
    val setArgs = properties.map((p) => s"${p.ID().getText} = _${p.ID().getText};").mkString("", "\n", "")
    val fields = properties.map((p) => s"${p.`type`().getText} ${p.ID().getText};").mkString("", "\n", "")
    val getters = properties.map((p) => s"public ${p.`type`().getText} get${p.ID().getText}(){ return ${p.ID()}; }")

    val pw = new PrintWriter(new File(s"${ctx.ID().getText}.rava"))

    val builder = new java.lang.StringBuilder()

    builder.append(s"class ${ctx.ID().getText} {\n")
    builder.append(fields)
    builder.append(s"public ${ctx.ID().getText} make${ctx.ID().getText}$methodArgs{\n")
    builder.append(setArgs)
    builder.append("return this;\n")
    builder.append(s"}\n")
    builder.append(getters.mkString("\n", "\n", "\n"))
    builder.append("}\n")

    caseMap += ctx.ID().getText -> builder.toString

    pw.close()
  }

}
