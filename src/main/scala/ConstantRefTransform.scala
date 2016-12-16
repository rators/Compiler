/**
  */
import java.util

import soot.jimple.IntConstant
import soot.jimple.internal._
import soot.{Body, BodyTransformer, SootMethod, Value, ValueBox}

import scala.collection.JavaConverters._

trait RBodyTransformer extends BodyTransformer {
  override def internalTransform(body: Body, s: String, map: util.Map[String, String]): Unit = {
    this.internalTransform(body, s, map.asScala.toMap)
  }

  def internalTransform(body: Body, s: String, map: Map[String, String])
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

  override def internalTransform(body: Body, s: String, map: Map[String, String]): Unit = {

  }

  def handleMethod(sootMethod: SootMethod): Unit = if (!sootMethod.isConcrete) {} else {
    val statements = sootMethod.getActiveBody.getUnits
    statements.asScala
  }

  def opsNotDefined(binOpExp: BinOpExp)(values: Map[String, Int]): Boolean = {
    (binOpExp.left, binOpExp.right) match {
      case (op1: JimpleLocal, op2: JimpleLocal) => (values.get(op1.getName), values.get(op2.getName)) match {
        case (Some(_), Some(_)) => true
        case _ => false
      }
      case _ => false
    }
  }

  def isIntExpression(valueBox: ValueBox)(values: Map[String, Int]): Boolean = {
    valueBox.getValue match {
      case addExpr: JAddExpr => opsNotDefined(addExpr)(values)
      case subExpr: JSubExpr => opsNotDefined(subExpr)(values)
      case mulExpr: JMulExpr => opsNotDefined(mulExpr)(values)
      case _ => false
    }
  }

  def reduceBox(valueBox: ValueBox)(values: Map[String, Integer]): Int = {
    valueBox.getValue match {
      case addExpr: JAddExpr => evaluateOp(addExpr, values, _ + _)
      case subExpr: JSubExpr => evaluateOp(subExpr, values, _ - _)
      case mulExpr: JMulExpr => evaluateOp(mulExpr, values, _ * _)
      case _ => 0
    }
  }

  def evaluateOp(rightBox: BinOpExp, values: Map[String, Integer], op: (Int, Int) => Int): Int = {
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
    }
  }
}

object SootImpl {
}