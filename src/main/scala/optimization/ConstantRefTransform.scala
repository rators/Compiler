package optimization

import java.util

import soot.jimple.IntConstant
import soot.jimple.internal._
import soot.shimple.ShimpleBody
import soot.{Body, BodyTransformer, Local, SootMethod, Value, ValueBox}

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

trait RBodyTransformer extends BodyTransformer {
  override def internalTransform(body: Body, s: String, map: util.Map[String, String]): Unit = {
    this.internalTransform(body.asInstanceOf[ShimpleBody].getMethod, s, map.asScala.toMap)
  }

  def internalTransform(body: SootMethod, s: String, map: Map[String, String])
}

case class BinOpExp(left: Value, right: Value)

object BinOpExp {
  implicit def addToBing(vb: JAddExpr): BinOpExp = BinOpExp(vb.getOp1, vb.getOp2)

  implicit def mulToBing(vb: JMulExpr): BinOpExp = BinOpExp(vb.getOp1, vb.getOp2)

  implicit def subToBin(vb: JSubExpr): BinOpExp = BinOpExp(vb.getOp1, vb.getOp2)
}

/**
  * A constant reference transformer.
  */
class ConstantRefTransform extends RBodyTransformer {

  import BinOpExp._

  override def internalTransform(body: SootMethod, s: String, map: Map[String, String]): Unit = handleMethod(body)

  def handleMethod(sootMethod: SootMethod): Unit = if (!sootMethod.isConcrete) {} else {
    val statements = sootMethod.getActiveBody.getUnits
    statements.asScala.filter(_.isInstanceOf[JAssignStmt]).map(_.asInstanceOf[JAssignStmt]).foldLeft(Map[String, Int]())((values: Map[String, Int], assignment) => {
      optimizeStatement(assignment)(values) match {
        case Success(entry) =>
          assignment.setRightOp(IntConstant.v(entry._2))
          println(entry._1)
          values + entry
        case Failure(e) => values
      }
    })
  }

  def optimizeStatement(assignStmt: JAssignStmt)(values: Map[String, Int]): Try[(String, Int)] = {
    println(assignStmt.leftBox.getValue.getClass.getSimpleName, assignStmt.rightBox.getValue.getClass.getSimpleName)
    (assignStmt.leftBox.getValue, assignStmt.rightBox.getValue) match {
      case (left: JimpleLocal, right: IntConstant) =>
        Success(assignConstLiteral(left, right)(assignStmt))
      case (left: JimpleLocal, rightValue: JimpleLocal) if values.contains(rightValue.getName) =>
        Success(assignLiteral(left, values(rightValue.getName))(assignStmt))
      case (left: JimpleLocal, rightBox: JAddExpr) =>
        if (isOpOnLocal(rightBox)(values))
          Success(left.getName -> reduceBox(assignStmt.rightBox)(values))
        else Failure(new AssertionError("Fuck it!"))
      case (left: JimpleLocal, rightBox: JMulExpr) =>
        if (isOpOnLocal(rightBox)(values))
          Success(left.getName -> reduceBox(assignStmt.rightBox)(values))
        else Failure(new AssertionError("Fuck it!"))
      case (left: JimpleLocal, rightBox: JSubExpr) =>
        if (isOpOnLocal(rightBox)(values))
          Success(left.getName -> reduceBox(assignStmt.rightBox)(values))
        else Failure(new AssertionError("Fuck it!"))
      case _ => Failure(new AssertionError("No optimization applies here!"))
    }
  }

  def assignConstLiteral(leftValue: Local, rightValue: IntConstant)(jAssignStmt: JAssignStmt): (String, Int) = {
    val leftName = leftValue.getName
    val rightIntValue = rightValue.value

    leftName -> rightIntValue
  }

  def tryReduceAssign(leftValue: JimpleLocal, rightBox: ValueBox)(assignStmt: JAssignStmt, values: Map[String, Int]): Try[(String, Int)] = {
    values get rightBox.toString match {
      case Some(rightIntValue) => Success(assignLiteral(leftValue, rightIntValue)(assignStmt))
      case None if canReduce(rightBox)(values) => Success(assignExpression(leftValue, rightBox)(assignStmt, values))
      case None => Failure(new AssertionError("No optimization applies!"))
    }
  }

  def assignExpression(leftValue: JimpleLocal, rightBox: ValueBox)(assignStmt: JAssignStmt, values: Map[String, Int]): (String, Int) = {
    val rightBoxValue: Int = reduceBox(rightBox)(values)
    leftValue.getName -> rightBoxValue
  }

  def assignLiteral(leftID: JimpleLocal, rightValue: Int)(assignStmt: JAssignStmt): (String, Int) = {
    leftID.getName -> rightValue
  }

  def canReduce(valueBox: ValueBox)(values: Map[String, Int]): Boolean = {
    valueBox.getValue match {
      case addExpr: JAddExpr => isOpOnLocal(addExpr)(values)
      case subExpr: JSubExpr => isOpOnLocal(subExpr)(values)
      case mulExpr: JMulExpr => isOpOnLocal(mulExpr)(values)
      case _ => false
    }
  }

  def isOpOnLocal(binOpExp: BinOpExp)(values: Map[String, Int]): Boolean = {
    (binOpExp.left, binOpExp.right) match {
      case (op1: JimpleLocal, op2: JimpleLocal) =>
        (values.get(op1.getName), values.get(op2.getName)) match {
          case (Some(_), Some(_)) => true
          case _ => false
        }
      case (op1: IntConstant, op2: IntConstant) => true
      case (op1: JimpleLocal, op2: IntConstant) => values.contains(op1.toString)
      case _ =>
        System.err.println(binOpExp.left.getClass.getSimpleName, binOpExp.right.getClass.getSimpleName)
        false
    }
  }

  def reduceBox(valueBox: ValueBox)(values: Map[String, Int]): Int = {
    valueBox.getValue match {
      case addExpr: JAddExpr => evaluateOp(addExpr, values, _ + _)
      case subExpr: JSubExpr => evaluateOp(subExpr, values, _ - _)
      case mulExpr: JMulExpr => evaluateOp(mulExpr, values, _ * _)
    }
  }

  def evaluateOp(rightBox: BinOpExp, values: Map[String, Int], op: (Int, Int) => Int): Int = {
    println(values)
    println(rightBox)
    rightBox match {
      case BinOpExp(left: IntConstant, right: IntConstant) =>
        op(left.value, right.value)
      case BinOpExp(left: JimpleLocal, right: IntConstant) =>
        val leftName = left.getName
        val leftValue = values(leftName)
        op(leftValue, right.value)
      case BinOpExp(left: IntConstant, right: JimpleLocal) =>
        val rightName = right.getName
        val rightValue = values(rightName)
        op(left.value, rightValue)
      case BinOpExp(left: JimpleLocal, right: JimpleLocal) =>
        Try {
          op(values(left.toString), values(right.toString))
        } match {
          case Failure(e) => throw new AssertionError("All is doomed!")
          case Success(s) => s
        }
    }
  }
}

object SootImpl {
}