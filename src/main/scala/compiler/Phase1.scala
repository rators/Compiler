package compiler

import java.io.{File, FileInputStream}

import scala.collection.JavaConversions
import antlr4.MiniJavaParser.ProgContext
import antlr4.{MiniJavaBaseListener, MiniJavaLexer, MiniJavaParser}
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.antlr.v4.runtime._

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import scala.util.{Failure, Success, Try}
import compiler.CompilerImpl._
import org.antlr.v4.automata.ATNPrinter
/**
  * Main class for Phase 1.
  */
object Phase1 extends App {
  List("binarysearch", "binarytree", "bubble", "factorial", "input", "linear", "linked").foreach(parseFile)

  def parseFile(fileName: String): Unit = {
    val fileStream = fileToStream(s"/sources/$fileName.rava")
    val lexer = new MiniJavaLexer(fileStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new MiniJavaParser(tokenStream)
    parser.removeErrorListeners()
    parser.addErrorListener(new MiniJavaErrorListener())

    Try(parser.prog()) match {
      case Success(progContext) => walkThrough(progContext)
      case Failure(e) => println(s"COMPILER ERR -- ${e}")
    }
  }

  def walkThrough(context: ProgContext): Unit = {
    val walker = new ParseTreeWalker()
    walker.walk(new MiniJavaBaseListener(), context)
  }

  def fileToStream(resourcePath: String): CharStream = {
    val sourceUrl = getClass.getResource(resourcePath)
    val file = new File(sourceUrl.getFile)
    val fileInputStream = new FileInputStream(file)
    val input: CharStream = new ANTLRInputStream(fileInputStream)
    input
  }
}

case class SyntaxError(errMsg: String, line: Int, col: Int) extends Throwable {
  override def toString: String = Logger.logErr(errMsg, col, line)
}

case class MissingData(expected: String, actual: String)

class MiniJavaErrorListener extends BaseErrorListener {
  def toMissingData(msg: String, errType: String): MissingData = {
    errType match {
      case "missing" =>
        val splitStr = msg.split(' ')
        MissingData(splitStr(1), splitStr(3))
      case "extraneous" =>
        val splitStr = msg.split(' ')
        MissingData(splitStr(2), splitStr(4))
    }
  }

  override def syntaxError(recognizer: Recognizer[_, _], offendingSymbol: scala.Any, line: Int, charPositionInLine: Int, msg: String, e: RecognitionException): Unit = {
    Option(e) match {
      case Some(_) =>
        implicit val rec = recognizer
        val rulesList = e.getCtx.ruleList
        val expectedTokens = e.getExpectedTokens.toList.map((s) => MiniJavaParser.VOCABULARY.getDisplayName(s)).toList
        val leafRule = rulesList.head
        leafRule.handleErr(rulesList, expectedTokens, e.getOffendingToken.getText, e, charPositionInLine, line)
      case None =>
        msg.contains("missing") match {
          case true =>
            println(s"Contains missing: $msg")
            val missingData = toMissingData(msg, "missing")
            throw SyntaxError(s"expected ${missingData.expected} keyword, but found: ${missingData.actual}", line, charPositionInLine)
          case false =>
            println(s"Contains missing: $msg")
            val missingData = toMissingData(msg, "extraneous")
            throw SyntaxError(s"expected ${missingData.expected} keyword, but found: ${missingData.actual}", line, charPositionInLine)
        }
    }


  }

}
