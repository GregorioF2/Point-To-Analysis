package tp_final;
import java.io.File;  // Import the File class
import java.io.FileWriter;

import java.io.IOException;  // Import the IOException class to handle errors
import java.text.SimpleDateFormat;  
import java.util.Date; 
import soot.jimple.*;
import java.util.HashMap;
import tp.utils.infoLogger;

public class DatalogIntegrator {
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_mm_ss");
    private HashMap <String, FileWriter> fwMap = new HashMap <String, FileWriter> ();
    private String dirPath;

    public DatalogIntegrator() {
        String currentTimeStamp = DatalogIntegrator.formatter.format(new Date());
        String currentDir = System.getProperty("user.dir");
        String path = currentDir + "/" + currentTimeStamp;
        this.dirPath = path;
        File file = new File(path);
        file.mkdirs();

        this.createFW("alloc", ".facts");
        this.createFW("move", ".facts");
        this.createFW("load", ".facts");
        this.createFW("store", ".facts");
        this.createFW("vcall", ".facts");
        this.createFW("reachable", ".facts");
        this.createFW("formal_arg", ".facts");
    }

    private void createFW(String key, String extension) {
        try {
            String filePath = this.dirPath + "/" + key + extension;
            File allocFacts = new File(filePath);
            allocFacts.createNewFile();
            FileWriter fw = new FileWriter(filePath,true);
            this.fwMap.put(key, fw);
        } catch (IOException e) {
            System.out.println("Error creating " + key + " facts file: " + e.toString());
            e.printStackTrace();
        }
    }

    private void wirteAlloc(String inMeth, soot.Local leftOp, soot.jimple.NewExpr rightOp) {
        try {
            FileWriter fw = this.fwMap.get("alloc");
            System.out.println("New expr: " + rightOp.toString());
            fw.write(Utils.writeFact(Utils.varName(inMeth, leftOp.getName()), String.valueOf(rightOp.getType().getNumber()) ));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeMove(String inMeth, soot.Local l, soot.Local r) {
        try {
            FileWriter fw = this.fwMap.get("move");
            fw.write(Utils.writeFact(Utils.varName(inMeth, l.getName()), Utils.varName(inMeth, r.getName())));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeLoad(String inMeth, soot.Local l, soot.jimple.InstanceFieldRef r) {
        try {
            FileWriter fw = this.fwMap.get("load");
            soot.Value base = r.getBase();
            if (!(base instanceof soot.Local)) {
                System.out.println("Error write Load, base not local");
                return;
            }
            fw.write(Utils.writeFact(Utils.varName(inMeth, l.getName()), Utils.varName(inMeth, ((soot.Local)base).getName()), r.getField().getName()));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }
    
    private void writeStore(String inMeth, soot.jimple.InstanceFieldRef l, soot.Local r) {
        try {
            FileWriter fw = this.fwMap.get("store");
            soot.Value base = l.getBase();
            if (!(base instanceof soot.Local)) {
                System.out.println("Error write Store, base not local");
                return;
            }
            fw.write(Utils.writeFact(Utils.varName(inMeth, ((soot.Local)base).getName()), l.getField().getName(), Utils.varName(inMeth, r.getName()))
            );
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeVCall(String base, String sig, int line, String inMeth) {
        try {
            FileWriter fw = this.fwMap.get("vcall");
            fw.write(Utils.writeFact(Utils.varName(inMeth, base), sig, String.valueOf(line), inMeth));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private static void LogInfo(soot.jimple.AssignStmt u) {
        // System.out.println("----> left:");
        // infoLogger.loggExpType(u.getLeftOp());
        // System.out.println("----> right:");
        // infoLogger.loggExpType(u.getRightOp());
        // System.out.println("======");
    }

    public void WriteStmtFact(soot.jimple.AssignStmt expr, String inMeth, int line) {
        DatalogIntegrator.LogInfo(expr);

        soot.Value leftOp = expr.getLeftOp();
        soot.Value rightOp = expr.getRightOp();

        if (rightOp instanceof soot.jimple.NewExpr) {
            if (!(leftOp instanceof soot.Local)) {
                System.out.println("Left op on assignmt is not local:  " + leftOp);
                infoLogger.loggExpType(leftOp);
            } else {
                this.wirteAlloc(inMeth, (soot.Local)leftOp, (soot.jimple.NewExpr)rightOp);
            }
        } else if (rightOp instanceof soot.Local && leftOp instanceof soot.Local) {
            this.writeMove(inMeth, (soot.Local)leftOp, (soot.Local) rightOp);
        } else if (leftOp instanceof soot.jimple.InstanceFieldRef && rightOp instanceof soot.Local) {
            this.writeStore(inMeth, (soot.jimple.InstanceFieldRef)leftOp, (soot.Local)rightOp);
        } else if (leftOp instanceof soot.Local && rightOp instanceof soot.jimple.InstanceFieldRef) {
            this.writeLoad(inMeth, (soot.Local)leftOp, (soot.jimple.InstanceFieldRef)rightOp);
        } else if (rightOp instanceof soot.jimple.InvokeExpr) {
            soot.jimple.InvokeExpr ie = (soot.jimple.InvokeExpr) rightOp;
            String base;
            if  (ie instanceof soot.jimple.StaticInvokeExpr) {
                base = "";
            } else {
                soot.Local l = (soot.Local) ((soot.jimple.InstanceInvokeExpr)ie).getBase();
                base = l.getName();
            }
            this.writeVCall(base, ie.getMethod().getName(), line, inMeth);
        }
        return;
    } 

    public void WriteReachableFact(String methodName) {
        try {
            FileWriter fw = this.fwMap.get("reachable");
            fw.write(Utils.writeFact(methodName));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    public void WriteFormalArgFact(String methodName, int index, soot.Local var) {
        try {
            FileWriter fw = this.fwMap.get("formal_arg");
            fw.write(Utils.writeFact(methodName, String.valueOf(index), Utils.varName(methodName, var.getName())));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    //public void WriteFormalArgument(String methodName)

    public void CloseWriters() {
        try {
            for (FileWriter fw : this.fwMap.values()) {
                fw.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

}

class Utils {
    static String varName(String method, String var) {
        if (var == "") {
            return "";
        }
        return method + "::" + var;
    }

    static String writeFact(String... args) {
        String res = "";
        for (String s: args) {
            res += " " + s;
        }
        return res.trim() + "\n";
    }
}