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

        this.createFW("alloc");
        this.createFW("move");
        this.createFW("load");
        this.createFW("store");
    }

    private void createFW(String key) {
        try {
            String filePath = this.dirPath + "/" + key + ".facts";
            File allocFacts = new File(filePath);
            allocFacts.createNewFile();
            FileWriter fw = new FileWriter(filePath,true);
            this.fwMap.put(key, fw);
        } catch (IOException e) {
            System.out.println("Error creating " + key + " facts file: " + e.toString());
            e.printStackTrace();
        }
    }

    private void wirteAlloc(soot.Local leftOp, soot.jimple.NewExpr rightOp) {
        try {
            FileWriter fw = this.fwMap.get("alloc");
            System.out.println("New expr: " + rightOp.toString());
            fw.write(leftOp.getName() + " " + rightOp.getType().getNumber() + "\n");
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeMove(soot.Local l, soot.Local r) {
        try {
            FileWriter fw = this.fwMap.get("move");
            fw.write(l.getName() + " " + r.getName() + "\n");
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeLoad(soot.Local l, soot.jimple.InstanceFieldRef r) {
        try {
            FileWriter fw = this.fwMap.get("load");
            soot.Value base = r.getBase();
            if (!(base instanceof soot.Local)) {
                System.out.println("Error write Load, base not local");
                return;
            }
            fw.write(l.getName() + " " + ((soot.Local)base).getName() + " " + r.getField().getName() + "\n");
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }
    
    private void writeStore(soot.jimple.InstanceFieldRef l, soot.Local r) {
        try {
            FileWriter fw = this.fwMap.get("store");
            soot.Value base = l.getBase();
            if (!(base instanceof soot.Local)) {
                System.out.println("Error write Store, base not local");
                return;
            }
            fw.write(((soot.Local)base).getName() + " " + l.getField().getName() + " " + r.getName() + "\n");
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private static void LogInfo(soot.jimple.AssignStmt u) {
        System.out.println("----> u: " + u.toString());
        infoLogger.loggStmtType(u);
        System.out.println("----> left:");
        infoLogger.loggExpType(u.getLeftOp());
        System.out.println("----> right:");
        infoLogger.loggExpType(u.getRightOp());
        System.out.println("======");
    }

    public void WriteFact(soot.jimple.AssignStmt expr) {
        DatalogIntegrator.LogInfo(expr);

        soot.Value leftOp = expr.getLeftOp();
        soot.Value rightOp = expr.getRightOp();

        if (rightOp instanceof soot.jimple.NewExpr) {
            if (!(leftOp instanceof soot.Local)) {
                System.out.println("Left op on assignmt is not local:  " + leftOp);
                infoLogger.loggExpType(leftOp);
            } else {
                this.wirteAlloc((soot.Local)leftOp, (soot.jimple.NewExpr)rightOp);
            }
        } else if (rightOp instanceof soot.Local && leftOp instanceof soot.Local) {
            this.writeMove((soot.Local)leftOp, (soot.Local) rightOp);
        } else if (leftOp instanceof soot.jimple.InstanceFieldRef && rightOp instanceof soot.Local) {
            this.writeStore((soot.jimple.InstanceFieldRef)leftOp, (soot.Local)rightOp);
        } else if (leftOp instanceof soot.Local && rightOp instanceof soot.jimple.InstanceFieldRef) {
            this.writeLoad((soot.Local)leftOp, (soot.jimple.InstanceFieldRef)rightOp);
        }
        return;
    } 

    public void closeWriters() {
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