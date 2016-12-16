package optimization;

import soot.*;
import soot.jimple.*;
import soot.jimple.internal.*;
import soot.shimple.ShimpleBody;
import java.util.*;

/**
 *
 */
public class BranchTransform extends BodyTransformer {
    private Map<String, Integer> valueBoxes;

    @Override
    protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
        valueBoxes = new HashMap<>();
        ShimpleBody body = (ShimpleBody) b;
        handleMethod(body.getMethod());
    }

    private void handleMethod(SootMethod m) {
        if (!m.isConcrete()) return;
        for (Unit unit : m.getActiveBody().getUnits()) {
            final Stmt s = (Stmt) unit;


            if (s instanceof JAssignStmt) {
                JAssignStmt assignStmt = (JAssignStmt) s;
                Value rightValue = (assignStmt).rightBox.getValue();
                Value leftValue = (assignStmt).leftBox.getValue();

                if (rightValue instanceof IntConstant && leftValue instanceof Local) {
                    String leftName = ((JimpleLocal) leftValue).getName();
                    int rightIntValue = ((IntConstant) rightValue).value;
                    valueBoxes.put(leftName, rightIntValue);

                    assignStmt.setRightOp(IntConstant.v(valueBoxes.get(((JimpleLocal) leftValue).getName())));

                } else {
                    if (valueBoxes.get(assignStmt.rightBox) != null) {

                        valueBoxes.put(((JimpleLocal) leftValue).getName(), valueBoxes.get(((JAssignStmt) s).rightBox));
                        ((JAssignStmt) s).setRightOp(IntConstant.v(valueBoxes.get(((JimpleLocal) ((JAssignStmt) s).leftBox.getValue()).getName())));
                    } else if (isRetrieveable(((JAssignStmt) s).rightBox)) {
                        System.err.println(((JAssignStmt) s).rightBox);
                        valueBoxes.put(((JimpleLocal) ((JAssignStmt) s).leftBox.getValue()).getName(), retrieve(((JAssignStmt) s).rightBox));
                        ((JAssignStmt) s).setRightOp(IntConstant.v(valueBoxes.get(((JimpleLocal) ((JAssignStmt) s).leftBox.getValue()).getName())));
                    }
                }

            }
        }
    }

    private boolean isRetrieveable(ValueBox valueBox) {
        if (valueBox.getValue() instanceof JAddExpr) {
            if (((JAddExpr) valueBox.getValue()).getOp1() instanceof JimpleLocal && valueBoxes.get(((JimpleLocal) ((JAddExpr) valueBox.getValue()).getOp1()).getName()) == null)
                return false;
            if (((JAddExpr) valueBox.getValue()).getOp2() instanceof JimpleLocal && valueBoxes.get(((JimpleLocal) ((JAddExpr) valueBox.getValue()).getOp2()).getName()) == null)
                return false;
            return true;
        } else if (valueBox.getValue() instanceof JSubExpr) {
            if (((JSubExpr) valueBox.getValue()).getOp1() instanceof JimpleLocal && valueBoxes.get(((JimpleLocal) ((JSubExpr) valueBox.getValue()).getOp1()).getName()) == null)
                return false;
            if (((JSubExpr) valueBox.getValue()).getOp2() instanceof JimpleLocal && valueBoxes.get(((JimpleLocal) ((JSubExpr) valueBox.getValue()).getOp2()).getName()) == null)
                return false;
            return true;
        } else if (valueBox.getValue() instanceof JMulExpr) {
            if (((JMulExpr) valueBox.getValue()).getOp1() instanceof JimpleLocal && valueBoxes.get(((JimpleLocal) ((JMulExpr) valueBox.getValue()).getOp1()).getName()) == null)
                return false;
            if (((JMulExpr) valueBox.getValue()).getOp2() instanceof JimpleLocal && valueBoxes.get(((JimpleLocal) ((JMulExpr) valueBox.getValue()).getOp2()).getName()) == null)
                return false;
            return true;
        }
        return false;
    }

    private int retrieve(ValueBox valueBox) {
        if (valueBox.getValue() instanceof JAddExpr) {
            return evalAdd(((JAddExpr) valueBox.getValue()));
        } else if (valueBox.getValue() instanceof JSubExpr) {
            return evalSub(((JSubExpr) valueBox.getValue()));
        } else if (valueBox.getValue() instanceof JMulExpr) {
            return evalMul(((JMulExpr) valueBox.getValue()));
        }
        return 0;
    }

    private int evalSub(JSubExpr rightBox) {
        if (rightBox.getOp1() instanceof IntConstant && rightBox.getOp2() instanceof IntConstant)
            return ((IntConstant) rightBox.getOp1()).value - ((IntConstant) rightBox.getOp2()).value;

        //
        if (rightBox.getOp1() instanceof JimpleLocal) {
            Integer op1 = valueBoxes.get(((JimpleLocal) rightBox.getOp1()).getName());
            if (rightBox.getOp2() instanceof JimpleLocal) {
                if (op1 != null && valueBoxes.get(((JimpleLocal) rightBox.getOp2()).getName()) != null)
                    return op1 - valueBoxes.get(((JimpleLocal) rightBox.getOp2()).getName());
            } else if (rightBox.getOp2() instanceof IntConstant) {
                if (op1 != null)
                    return op1 - ((IntConstant) rightBox.getOp2()).value;
            }
        } else if (rightBox.getOp2() instanceof JimpleLocal) {
            Integer op2 = valueBoxes.get(((JimpleLocal) rightBox.getOp2()).getName());
            if (rightBox.getOp1() instanceof JimpleLocal) {
                if (op2 != null && valueBoxes.get(((JimpleLocal) rightBox.getOp1()).getName()) != null)
                    return valueBoxes.get(((JimpleLocal) rightBox.getOp1()).getName()) - op2;
            } else if (rightBox.getOp1() instanceof IntConstant) {
                if (op2 != null)
                    return ((IntConstant) rightBox.getOp1()).value - op2;
            }
        }

        return 0;
    }

    private int evalMul(JMulExpr rightBox) {
        if (rightBox.getOp1() instanceof IntConstant && rightBox.getOp2() instanceof IntConstant)
            return ((IntConstant) rightBox.getOp1()).value * ((IntConstant) rightBox.getOp2()).value;

        if (rightBox.getOp1() instanceof JimpleLocal) {
            Integer op1 = valueBoxes.get(((JimpleLocal) rightBox.getOp1()).getName());
            if (rightBox.getOp2() instanceof JimpleLocal) {
                if (op1 != null && valueBoxes.get(((JimpleLocal) rightBox.getOp2()).getName()) != null)
                    return op1 * valueBoxes.get(((JimpleLocal) rightBox.getOp2()).getName());
            } else if (rightBox.getOp2() instanceof IntConstant) {
                if (op1 != null)
                    return op1 * ((IntConstant) rightBox.getOp2()).value;
            }
        } else if (rightBox.getOp2() instanceof JimpleLocal) {
            Integer op2 = valueBoxes.get(((JimpleLocal) rightBox.getOp2()).getName());
            if (rightBox.getOp1() instanceof JimpleLocal) {
                if (op2 != null && valueBoxes.get(((JimpleLocal) rightBox.getOp1()).getName()) != null)
                    return valueBoxes.get(((JimpleLocal) rightBox.getOp1()).getName()) * op2;
            } else if (rightBox.getOp1() instanceof IntConstant) {
                if (op2 != null)
                    return ((IntConstant) rightBox.getOp1()).value * op2;
            }
        }

        return 0;
    }

    private int evalAdd(JAddExpr rightBox) {
        if (rightBox.getOp1() instanceof IntConstant && rightBox.getOp2() instanceof IntConstant)
            return ((IntConstant) rightBox.getOp1()).value + ((IntConstant) rightBox.getOp2()).value;
        if (rightBox.getOp1() instanceof JimpleLocal) {
            Integer op1 = valueBoxes.get(((JimpleLocal) rightBox.getOp1()).getName());
            if (rightBox.getOp2() instanceof JimpleLocal) {
                if (op1 != null && valueBoxes.get(((JimpleLocal) rightBox.getOp2()).getName()) != null)
                    return op1 + valueBoxes.get(((JimpleLocal) rightBox.getOp2()).getName());
            } else if (rightBox.getOp2() instanceof IntConstant) {
                if (op1 != null)
                    return op1 + ((IntConstant) rightBox.getOp2()).value;
            }
        } else if (rightBox.getOp2() instanceof JimpleLocal) {
            Integer op2 = valueBoxes.get(((JimpleLocal) rightBox.getOp2()).getName());
            if (rightBox.getOp1() instanceof JimpleLocal) {
                if (op2 != null && valueBoxes.get(((JimpleLocal) rightBox.getOp1()).getName()) != null)
                    return valueBoxes.get(((JimpleLocal) rightBox.getOp1()).getName()) + op2;
            } else if (rightBox.getOp1() instanceof IntConstant) {
                if (op2 != null)
                    return ((IntConstant) rightBox.getOp1()).value + op2;
            }
        }
        return 0;
    }
}
