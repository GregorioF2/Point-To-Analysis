package tp_final;

import java.io.File;
import java.util.*;
import java.util.*;
import java.util.stream.Collectors;
import soot.*;
import soot.Body;
import soot.Local;
import soot.jimple.*;
import soot.jimple.JimpleBody;
import soot.jimple.internal.JIfStmt;
import soot.options.Options;
import soot.toolkits.graph.ClassicCompleteUnitGraph;
import soot.toolkits.graph.UnitGraph;
import tp.utils.infoLogger;

public class PointsToAnalysis {

  public static String sourceDirectory =
    System.getProperty("user.dir") +
    File.separator +
    "src/main/java/final/test";
  public static int c = 1;

  public static void setupSoot(String testCase) {
    G.reset();
    Options.v().set_prepend_classpath(true);
    Options.v().set_allow_phantom_refs(true);
    Options.v().set_soot_classpath(sourceDirectory + File.separator + testCase);
    SootClass sc = Scene.v().loadClassAndSupport(testCase);
    sc.setApplicationClass();
    Scene.v().loadNecessaryClasses();
  }

  public static void main(String[] args) {
    String testCase = args.length > 0 ? args[0] : "CaseOne";
    setupSoot(testCase);
    DatalogIntegrator integrator = new DatalogIntegrator();

    List<SootClass> classes = Scene
      .v()
      .getClasses()
      .stream()
      .filter(c -> c.getPackageName() == "")
      .collect(Collectors.toList());
    for (SootClass c : classes) {
      System.out.println(
        "Class: " + c.getName() + ". package: " + c.getPackageName()
      );
    }

    for (SootClass c : classes) {
      List<SootMethod> methods = c.getMethods();
      List<Body> bodys = methods
        .stream()
        .map(method -> method.retrieveActiveBody())
        .collect(Collectors.toList());
      for (Body body : bodys) {
        PointsToAnalysis.AnalyzeBody(body, body.getMethod(), integrator);
      }
    }

    integrator.CloseWriters();
  }

  public static void AnalyzeBody(
    soot.Body body,
    SootMethod method,
    DatalogIntegrator integrator
  ) {
    System.out.println(
      "\n\n New method: " +
      method.getName() +
      ". Signature: " +
      method.getSignature() +
      ". Subsignature: " +
      method.getSubSignature() +
      "\n\n"
    );
    integrator.WriteReachableFact(method);
    for (Unit u : body.getUnits()) {
      String unitString = u.toString();
      int margin = 90 - unitString.length();
      String marginString = new String(new char[margin]).replace("\0", " ");
      System.out.println(
        String.valueOf(c) +
        "----> " +
        u.toString() +
        marginString +
        "  // " +
        String.join(", ", infoLogger.stmtTypes(u))
      );
      boolean isStmt = u instanceof Stmt;
      if (!isStmt) {
        continue;
      }
      integrator.WriteStmtFact((Stmt) u, method, c);

      c++;
    }

    for (int i = 0; i < body.getMethod().getParameterCount(); i++) {
      integrator.WriteFormalArgFact(
        body.getMethod(),
        i,
        body.getParameterLocal(i)
      );
    }
  }
}
