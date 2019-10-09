
import soot.Unit;
import soot.Body;
import soot.BodyTransformer;
import soot.PackManager;
import soot.Transform;
import soot.toolkits.graph.ExceptionalUnitGraph;
import tp.SensibleAnalysis;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Main analysis launcher class
 */
public class Launcher {


    public static void main(String[] args) {

        PackManager.v().getPack("jtp").add(new Transform("jtp.SensibleAnalysis", new BodyTransformer() {


            @Override
            protected void internalTransform(Body body, String phaseName, Map<String, String> options) {
                SensibleAnalysis analysis = SensibleAnalysis.analyse(body);
            }
        }));
        soot.Main.main(args);
    }


}
