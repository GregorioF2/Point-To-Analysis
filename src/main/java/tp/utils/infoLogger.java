package tp.utils;

import soot.Immediate;
import soot.Local;
import soot.Unit;
import soot.Value;
import soot.grimp.NewInvokeExpr;
import soot.jimple.*;
import soot.shimple.PhiExpr;
import soot.shimple.PiExpr;
import soot.shimple.ShimpleExpr;

public class infoLogger {
    public static void loggExpType (Value value) {
        if (value instanceof AddExpr) System.out.println("Value instance of AddExpr");
        if (value instanceof AndExpr) System.out.println("Value instance of AndExpr");
        if (value instanceof AnyNewExpr) System.out.println("Value instance of AnyNewExpr");
        if (value instanceof ArrayRef) System.out.println("Value instance of ArrayRef");
        if (value instanceof BinopExpr) System.out.println("Value instance of BinopExpr");
        if (value instanceof CastExpr) System.out.println("Value instance of CastExpr");
        if (value instanceof CaughtExceptionRef) System.out.println("Value instance of CaughtExceptionRef");
        if (value instanceof CmpExpr) System.out.println("Value instance of CmpExpr");
        if (value instanceof CmpgExpr) System.out.println("Value instance of CmpgExpr");
        if (value instanceof CmplExpr) System.out.println("Value instance of CmplExpr");
        if (value instanceof ConcreteRef) System.out.println("Value instance of ConcreteRef");
        if (value instanceof ConditionExpr) System.out.println("Value instance of ConditionExpr");
        if (value instanceof DivExpr) System.out.println("Value instance of DivExpr");
        if (value instanceof DynamicInvokeExpr) System.out.println("Value instance of DynamicInvokeExpr");
        if (value instanceof EqExpr) System.out.println("Value instance of EqExpr");
        if (value instanceof Expr) System.out.println("Value instance of Expr");
        if (value instanceof FieldRef) System.out.println("Value instance of FieldRef");
        if (value instanceof GeExpr) System.out.println("Value instance of GeExpr");
        if (value instanceof GtExpr) System.out.println("Value instance of GtExpr");
        if (value instanceof IdentityRef) System.out.println("Value instance of IdentityRef");
        if (value instanceof Immediate) System.out.println("Value instance of Immediate");
        if (value instanceof InstanceFieldRef) System.out.println("Value instance of InstanceFieldRef");
        if (value instanceof InstanceInvokeExpr) System.out.println("Value instance of InstanceInvokeExpr");
        if (value instanceof InstanceOfExpr) System.out.println("Value instance of InstanceOfExpr");
        if (value instanceof InterfaceInvokeExpr) System.out.println("Value instance of InterfaceInvokeExpr");
        if (value instanceof InvokeExpr) System.out.println("Value instance of InvokeExpr");
        if (value instanceof LeExpr) System.out.println("Value instance of LeExpr");
        if (value instanceof LengthExpr) System.out.println("Value instance of LengthExpr");
        if (value instanceof Local) System.out.println("Value instance of Local");
        if (value instanceof LtExpr) System.out.println("Value instance of LtExpr");
        if (value instanceof MulExpr) System.out.println("Value instance of MulExpr");
        if (value instanceof NeExpr) System.out.println("Value instance of NeExpr");
        if (value instanceof NegExpr) System.out.println("Value instance of NegExpr");
        if (value instanceof NewArrayExpr) System.out.println("Value instance of NewArrayExpr");
        if (value instanceof NewExpr) System.out.println("Value instance of NewExpr");
        if (value instanceof NewInvokeExpr) System.out.println("Value instance of NewInvokeExpr");
        if (value instanceof NewMultiArrayExpr) System.out.println("Value instance of NewMultiArrayExpr");
        if (value instanceof OrExpr) System.out.println("Value instance of OrExpr");
        if (value instanceof PhiExpr) System.out.println("Value instance of PhiExpr");
        if (value instanceof PiExpr) System.out.println("Value instance of PiExpr");
        if (value instanceof Ref) System.out.println("Value instance of Ref");
        if (value instanceof RemExpr) System.out.println("Value instance of RemExpr");
        if (value instanceof ShimpleExpr) System.out.println("Value instance of ShimpleExpr");
        if (value instanceof ShlExpr) System.out.println("Value instance of ShlExpr");
        if (value instanceof ShrExpr) System.out.println("Value instance of ShrExpr");
        if (value instanceof SpecialInvokeExpr) System.out.println("Value instance of SpecialInvokeExpr");
        if (value instanceof StaticInvokeExpr) System.out.println("Value instance of StaticInvokeExpr");
        if (value instanceof SubExpr) System.out.println("Value instance of SubExpr");
        if (value instanceof UnopExpr) System.out.println("Value instance of UnopExpr");
        if (value instanceof UshrExpr) System.out.println("Value instance of UshrExpr");
        if (value instanceof VirtualInvokeExpr) System.out.println("Value instance of VirtualInvokeExpr");
        if (value instanceof XorExpr) System.out.println("Value instance of XorExpr");
    }

    public static void loggStmtType (Unit unit) {
        if (unit instanceof AssignStmt) System.out.println("Unit is instanceof AssignStmt");
        if (unit instanceof BreakpointStmt) System.out.println("Unit is instanceof BreakpointStmt");
        if (unit instanceof DefinitionStmt) System.out.println("Unit is instanceof DefinitionStmt");
        if (unit instanceof EnterMonitorStmt) System.out.println("Unit is instanceof EnterMonitorStmt");
        if (unit instanceof ExitMonitorStmt) System.out.println("Unit is instanceof ExitMonitorStmt");
        if (unit instanceof GotoStmt) System.out.println("Unit is instanceof GotoStmt");
        if (unit instanceof IdentityStmt) System.out.println("Unit is instanceof IdentityStmt");
        if (unit instanceof IfStmt) System.out.println("Unit is instanceof IfStmt");
        if (unit instanceof InvokeStmt) System.out.println("Unit is instanceof InvokeStmt");
        if (unit instanceof LookupSwitchStmt) System.out.println("Unit is instanceof LookupSwitchStmt");
        if (unit instanceof MonitorStmt) System.out.println("Unit is instanceof MonitorStmt");
        if (unit instanceof NopStmt) System.out.println("Unit is instanceof NopStmt");
        if (unit instanceof RetStmt) System.out.println("Unit is instanceof RetStmt");
        if (unit instanceof ReturnStmt) System.out.println("Unit is instanceof ReturnStmt");
        if (unit instanceof ReturnVoidStmt) System.out.println("Unit is instanceof ReturnVoidStmt");
        if (unit instanceof TableSwitchStmt) System.out.println("Unit is instanceof TableSwitchStmt");
        if (unit instanceof ThrowStmt) System.out.println("Unit is instanceof ThrowStmt");
    }
}
