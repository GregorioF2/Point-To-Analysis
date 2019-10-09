package tp.Visitors;

import soot.Local;
import soot.SootMethod;
import soot.Value;
import soot.jimple.BinopExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.ParameterRef;
import tp.SensibleAnalysis;
import tp.abs.SensibleLattice;

import java.util.HashMap;
import java.util.List;

public class ValueVisitor {
    private HashMap<String, SensibleLattice> localsLatticeMap;
    private HashMap<Integer, SensibleLattice> methodParams;
    private Boolean possibleLeak = false;

    public ValueVisitor(HashMap<String, SensibleLattice> locals,
                        HashMap<Integer, SensibleLattice> params) {
        localsLatticeMap = locals;
        methodParams = params;
    }

    public Boolean thereIsPossibleLeak () {
        return this.possibleLeak;
    }

    public boolean visit(Value value) {
        if (value instanceof BinopExpr) {
            return visitBinOpExp((BinopExpr) value);
        }
        if (value instanceof InvokeExpr) {
            return visitInvokeExp((InvokeExpr) value);
        }
        if (value instanceof Local) {
            return visitLocal((Local) value);
        }
        if (value instanceof ParameterRef) {
            return visitRef((ParameterRef) value);
        }
        return false;
    }

    private boolean visitRef(ParameterRef value) {
        Integer paramIndex = value.getIndex();
        return SensibleLattice.isSensible(
                methodParams.getOrDefault(paramIndex, SensibleLattice.BOTTOM)
        );
    }

    private boolean visitLocal(Local value) {
        return SensibleLattice.isSensible(
                localsLatticeMap.getOrDefault(value.getName(), SensibleLattice.BOTTOM)
        );
    }

    private boolean visitBinOpExp(BinopExpr value) {
        Value op1 = value.getOp1();
        Value op2 = value.getOp2();

        return this.visit(op1) || this.visit(op2);
    }

    private boolean visitInvokeExp(InvokeExpr expr) {
        SootMethod method = expr.getMethod();

        if (method.getName().equals("sensible") &&
            method.getDeclaringClass().getName().equals("tp.utils.Is")) {
            return handleSensibleMethod(expr);
        }

        if (method.getName().equals("sanitize") &&
                method.getDeclaringClass().getName().equals("tp.utils.Is")) {
            return handleSanitize(expr);
        }

        if (method.getName().equals("println") &&
            method.getDeclaringClass().getName().equals("java.io.PrintStream")) {
            return handlePrintln(expr);
        }

        return SensibleAnalysis.analyse(
                expr.getMethod().getActiveBody(),
                this.parametersAsLattice(expr)
            )
                .hasPossibleLeak();
    }

    private boolean handleSanitize(InvokeExpr expr) {
        List<Value> args = expr.getArgs();
        for (int i = 0 ; i < args.size() ; i ++) {
            if (args.get(i) instanceof Local) {
                localsLatticeMap.put(
                        ((Local) args.get(i)).getName(),
                        SensibleLattice.LOW);
            }
        }
        return true;
    }

    private boolean handlePrintln(InvokeExpr expr) {
        boolean sensibleArgument = false;
        List<Value> args = expr.getArgs();
        for (Value val: args) {
            sensibleArgument = sensibleArgument || this.visit(val);
        }
        if (sensibleArgument) {
            this.possibleLeak = true;
        }
        return false;
    }

    private boolean handleSensibleMethod(InvokeExpr expr) {
        List<Value> args = expr.getArgs();
        for (int i = 0 ; i < args.size() ; i ++) {
            if (args.get(i) instanceof Local) {
                localsLatticeMap.put(
                        ((Local) args.get(i)).getName(),
                        SensibleLattice.TOP);
            }
        }
        return true;
    }


    public HashMap<Integer, SensibleLattice> parametersAsLattice(InvokeExpr expr) {
        HashMap<Integer, SensibleLattice> parametersAsLattice = new HashMap<>();
        List<Value> args = expr.getArgs();

        for (int index = 0; index < args.size(); index ++) {
            parametersAsLattice.put(index, this.localsLatticeMap.getOrDefault(
                    args.get(index), SensibleLattice.BOTTOM)
            );
        }

        return parametersAsLattice;
    }
}
