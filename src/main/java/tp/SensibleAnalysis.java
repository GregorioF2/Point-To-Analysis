package tp;

import soot.Body;
import soot.SootMethod;
import soot.Unit;
import soot.Local;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;
import tp.Visitors.StatementVisitor;
import tp.abs.SensibleLattice;
import soot.jimple.*;
import tp.utils.UnitUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class SensibleAnalysis extends ForwardFlowAnalysis<Unit, HashMap<String, SensibleLattice>> {
    private HashMap<String, SensibleLattice> localsAsLattice;
    private LinkedList<Unit> posibbleLeaks;
    private SootMethod method;
    private HashMap<Integer, SensibleLattice> methodParams;
    private boolean returnsSensible = false;

    public static SensibleAnalysis analyse(Body body) {
        return new SensibleAnalysis(new ExceptionalUnitGraph(body), new HashMap<>());
    }

    public static SensibleAnalysis analyse(Body body, HashMap<Integer, SensibleLattice> methodParams) {
        return new SensibleAnalysis(new ExceptionalUnitGraph(body), methodParams);
    }

    public SensibleAnalysis(UnitGraph graph, HashMap<Integer, SensibleLattice> params) {
        super(graph);
        localsAsLattice = new HashMap<>();
        posibbleLeaks = new LinkedList<>();
        method = graph.getBody().getMethod().method();
        methodParams = params;

        for (Local local : graph.getBody().getLocals()) {
            localsAsLattice.put(local.getName(), SensibleLattice.BOTTOM);
        }
        doAnalysis();
    }

    @Override
    protected void flowThrough(HashMap<String, SensibleLattice> src,
                               Unit unit,
                               HashMap<String, SensibleLattice> dest) {

        StatementVisitor statementVisitor = new StatementVisitor(src, methodParams);
        boolean thereIsSensibleVariable = statementVisitor.visit((Stmt) unit);

        /*
        if (thereIsSensibleVariable) {
            System.out.println("\n> Sensible variable on method\n" + this.method.getName() +
                    " and instruction " + unit.toString());
        }
        */


        if (statementVisitor.thereIsPossibleLeak()) {
            posibbleLeaks.push(unit);
            int lineNumber = UnitUtils.getLineNumberFromUnit(unit);
            System.out.println("\n> WARNING: possible leak on line number: " + lineNumber + ".\n");
        }

        if (statementVisitor.returnSensible()) {
            this.returnsSensible = true;
        }

        dest.clear();
        dest.putAll(src);
    }

    @Override
    protected HashMap<String, SensibleLattice> newInitialFlow() {
        HashMap<String, SensibleLattice> out = new HashMap<>();
        out.putAll(localsAsLattice);
        return out;
    }

    @Override
    protected void merge(HashMap<String, SensibleLattice> leftBranch,
                         HashMap<String, SensibleLattice> rightBranch,
                         HashMap<String, SensibleLattice> out) {
        out.clear();
        out.putAll(leftBranch);

        for (String variable : rightBranch.keySet()) {
            SensibleLattice currentValue = out.getOrDefault(variable, SensibleLattice.BOTTOM);
            out.put(variable, SensibleLattice.supremeBetween(rightBranch.get(variable), currentValue));
        }
    }

    @Override
    protected void copy(HashMap<String, SensibleLattice> in, HashMap<String, SensibleLattice> out) {
        out.clear();
        out.putAll(in);
    }


    public boolean hasPossibleLeak() {
        return posibbleLeaks.size() > 0;
    }

    public boolean returnsSensible() {
        return this.returnsSensible;
    }

    public List<Unit> getPosibleLeaksUnits() {
        return this.posibbleLeaks;
    }

    public boolean unitIsOffending(Unit unit) {
        return false;
    }
}
