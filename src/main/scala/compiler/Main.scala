package compiler

import java.io.{File, FileInputStream}

import antlr4.MiniJavaParser.ProgContext
import antlr4.{MiniJavaBaseListener, MiniJavaLexer, MiniJavaParser}
import compiler.CompilerImpl._
import compiler.typecheck.listener.{CodeGenerator, KlassDeclarator, SymbolDeclarator}
import compiler.typecheck.scope.{Klass, Scope}
import compiler.typecheck.utils.KlassMap
import compiler.typecheck.visitor.TypeChecker
import org.antlr.v4.runtime._
import org.antlr.v4.runtime.tree.{ParseTreeProperty, ParseTreeWalker}

import scala.collection.JavaConversions._
import scala.util.{Failure, Success, Try}

/**
  * Main class for Phase 1.
  */
object Main extends App {
      List("bubble").foreach(parseFile)
//  List("test").foreach(parseFile)

  def parseFile(fileName: String): Unit = {
    //create input stream from input file
    val fileStream = fileToStream(s"/sources/$fileName.rava")
    val lexer = new MiniJavaLexer(fileStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new MiniJavaParser(tokenStream)

    //remove error listeners to ensure error messages are not repeated.
    parser.removeErrorListeners()
    parser.addErrorListener(new MiniJavaErrorListener())

    //run the parser starting at the root rule `prog`
    Try(parser.prog()) match {
      case Success(progContext) =>
        walkThrough(progContext)

        val klassMap = klassDeclWalk(progContext)
        val scope = new ParseTreeProperty[Scope]()
        val callerTypes = new ParseTreeProperty[Klass]()
        symbolDeclWalk(klassMap, scope, progContext)
        typeCheckWalk(klassMap, scope, progContext, callerTypes)
        codeGenWalk(klassMap, scope, progContext, callerTypes)
      case Failure(e) => System.err.println(s"COMPILER ERR -- $e")
    }
  }

  def walkThrough(parseTree: ProgContext): Unit = {
    val walker = new ParseTreeWalker()

    walker.walk(new MiniJavaBaseListener(), parseTree)
  }

  def klassDeclWalk(parseTree: ProgContext): KlassMap = {
    val walker = new ParseTreeWalker()

    val klassDeclarator = new KlassDeclarator()
    walker.walk(klassDeclarator, parseTree)
    klassDeclarator.klassMap
  }

  def symbolDeclWalk(klassMap: KlassMap, scopes: ParseTreeProperty[Scope], parseTree: ProgContext): Unit = {
    val walker = new ParseTreeWalker()

    val klassDeclarator = new SymbolDeclarator(klassMap, scopes, null)

    val tryKlassDeclWalk = Try {
      walker.walk(klassDeclarator, parseTree)
    }

    tryKlassDeclWalk match {
      case Failure(e) =>
        System.err.println(e.toString)
        System.exit(1)
      case Success(_) => println("Klasses declared successfully!")
    }
  }

  def typeCheckWalk(klassMap: KlassMap, scopes: ParseTreeProperty[Scope], parseTree: ProgContext, callerTypes: ParseTreeProperty[Klass]): Unit = {
    val typeChecker = new TypeChecker(klassMap, scopes, callerTypes, null)

    val tryTypeCheck = Try {
      typeChecker.visit(parseTree)
    }

    tryTypeCheck match {
      case Failure(e) =>
        System.err.println(e.toString)
      case Success(_) => println("Type check successful!")
    }
  }

  def codeGenWalk(klassMap: KlassMap, scopes: ParseTreeProperty[Scope], parseTree: ProgContext, callerTypes: ParseTreeProperty[Klass]): Unit = {
    val codeGen = new CodeGenerator(klassMap, scopes, callerTypes)

    ParseTreeWalker.DEFAULT.walk(codeGen, parseTree)
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
        val r = "\\{(.*?)\\}".r
        val splitStr = msg.split(' ')
        r.findFirstMatchIn(msg) match {
          case Some(expected) => MissingData(expected.group(0), splitStr(2))
          case None =>
            val splitMsg = msg.split(' ')
            val actual = splitMsg(2)
            val expected = splitMsg(4)
            MissingData(expected, actual)
        }
    }
  }

  override def syntaxError(recognizer: Recognizer[_, _], offendingSymbol: scala.Any, line: Int, charPositionInLine: Int, msg: String, e: RecognitionException): Unit = {
    Option(e) match {
      case Some(_) =>
        implicit val rec = recognizer
        val rulesList = e.getCtx.ruleList // get the rules list
      val expectedTokens = e.getExpectedTokens.toList.map((s) => MiniJavaParser.VOCABULARY.getDisplayName(s)).toList // get the epected tokens
      val leafRule = rulesList.head // get the leaf rule that fails
        leafRule.handleErr(rulesList, expectedTokens, e.getOffendingToken.getText, e, charPositionInLine, line) // call the handler method on that
      case None =>
        msg.contains("missing") match {
          case true =>
            val missingData = toMissingData(msg, "missing")
            throw SyntaxError(s"expected ${missingData.expected} symbol(s), but found: ${missingData.actual}", line, charPositionInLine)
          case false =>
            val missingData = toMissingData(msg, "extraneous")
            missingData.actual.equals("'else'") match {
              case true => throw SyntaxError(s"Else without an if", line, charPositionInLine)
              case false => throw SyntaxError(s"expected ${missingData.expected} symbol(s), but found: ${missingData.actual}", line, charPositionInLine)
            }
        }
    }
  }


}
