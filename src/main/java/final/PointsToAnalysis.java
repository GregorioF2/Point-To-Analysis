/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package tp_final;
import soot.*;
import soot.Body;
import soot.Local;
import soot.jimple.*;
import soot.jimple.JimpleBody;
import soot.jimple.internal.JIfStmt;
import soot.options.Options;
import soot.toolkits.graph.ClassicCompleteUnitGraph;
import soot.toolkits.graph.UnitGraph;
import java.util.*;
import tp.utils.infoLogger;
import java.util.*;  
import java.util.stream.Collectors;

import java.io.File;

public class PointsToAnalysis {
    public static String sourceDirectory = System.getProperty("user.dir") + File.separator + "src/main/java/tutorial/demo";
    public static String clsName = "FizzBuzz";
    public static String methodName = "printFizzBuzz";
    public static String pckg = "FizzBuzz";

    public static void setupSoot() {
        G.reset();
        Options.v().set_prepend_classpath(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_soot_classpath(sourceDirectory);
        SootClass sc = Scene.v().loadClassAndSupport(clsName);
        sc.setApplicationClass();
        Scene.v().loadNecessaryClasses();
    }

    public static void main(String[] args) {
        setupSoot();
        
        DatalogIntegrator integrator = new DatalogIntegrator();

        SootClass mainClass = Scene.v().getSootClass(clsName);
        List<SootMethod> methods = mainClass.getMethods();
        SootMethod sm = mainClass.getMethodByName(methodName);
        List<Body> bodys = methods.stream()
        .map(method -> method.retrieveActiveBody())
        .collect(Collectors.toList());;

        for (Body body: bodys) {
            PointsToAnalysis.AnalyzeBody(body, body.getMethod().getName(), integrator);
        }
        integrator.CloseWriters();
    }

    public static void AnalyzeBody(soot.Body body, String methodName, DatalogIntegrator integrator) {
        integrator.WriteReachableFact(methodName);
        System.out.println("Method: " + methodName);
        int c = 1;
        for (Unit u : body.getUnits()) {
            System.out.println("----> u: " + u.toString());
            infoLogger.loggStmtType(u);
            boolean isDefinitionStmt = u instanceof soot.jimple.AssignStmt;
            if (!isDefinitionStmt) {
                continue;
            }
            integrator.WriteStmtFact((soot.jimple.AssignStmt) u, methodName, c);

            c++;
        }

        for (int i = 0; i < body.getMethod().getParameterCount(); i++) {
            integrator.WriteFormalArgFact(body.getMethod().getName(), i, body.getParameterLocal(i));
        }
    }
}