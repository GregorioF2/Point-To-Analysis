package tp_final;
import java.io.File;  // Import the File class
import java.io.FileWriter;

import java.io.IOException;  // Import the IOException class to handle errors
import java.text.SimpleDateFormat;  
import java.util.Date; 
import soot.jimple.*;
import java.util.HashMap;
import tp.utils.infoLogger;
import soot.*;

public class DatalogIntegrator {
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_mm_ss");
    private HashMap <String, FileWriter> fwMap = new HashMap <String, FileWriter> ();
    private String dirPath;
    private int heap;

    public DatalogIntegrator() {
        String currentTimeStamp = DatalogIntegrator.formatter.format(new Date());
        String currentDir = System.getProperty("user.dir");
        // String path = currentDir + "/" + currentTimeStamp;
        String path = currentDir + "/DLInput";
        this.dirPath = path;
        this.heap = 0;
        File file = new File(path);
        file.mkdirs();

        this.createFW("alloc", ".facts");
        this.createFW("move", ".facts");
        this.createFW("load", ".facts");
        this.createFW("store", ".facts");
        this.createFW("vcall", ".facts");
        this.createFW("reachable", ".facts");
        this.createFW("formal_arg", ".facts");
        this.createFW("formal_ret", ".facts");
        this.createFW("actual_arg", ".facts");
        this.createFW("actual_ret", ".facts");
    }

    ////////
    // PRIVATE FUNCTIONS
    ////////
    private String uName(SootMethod method) {
        return String.join("__", method.getSignature().split(" "));
    }

    private String heapType(){
        return String.valueOf(this.heap++);
    }

    private String varName(SootMethod method, soot.Local local) {
        return uName(method) + "::" + local.getName();
    }

    private String varName(SootMethod method, String local) {
        if (local == "") {
            return "";
        }
        return uName(method) + "::" + local;
    }

    private String writeFact(String... args) {
        return String.join(";", args) + "\n";
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

    // RELATIONS STATEMENTS

    private void writeAlloc(SootMethod inMeth, soot.Local leftOp, soot.jimple.NewExpr rightOp) {
        try {
            FileWriter fw = this.fwMap.get("alloc");
            fw.write(writeFact(varName(inMeth, leftOp), heapType(), uName(inMeth)));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeMove(SootMethod inMeth, soot.Local l, soot.Local r) {
        try {
            FileWriter fw = this.fwMap.get("move");
            fw.write(writeFact(varName(inMeth, l), varName(inMeth, r)));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeLoad(SootMethod inMeth, soot.Local l, soot.jimple.InstanceFieldRef r) {
        try {
            FileWriter fw = this.fwMap.get("load");
            soot.Value base = r.getBase();
            if (!(base instanceof soot.Local)) {
                System.out.println("Error write Load, base not local");
                return;
            }
            fw.write(writeFact(varName(inMeth, l), varName(inMeth, (soot.Local)base), r.getField().getName()));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }
    
    private void writeStore(SootMethod inMeth, soot.jimple.InstanceFieldRef l, soot.Local r) {
        try {
            FileWriter fw = this.fwMap.get("store");
            soot.Value base = l.getBase();
            if (!(base instanceof soot.Local)) {
                System.out.println("Error write Store, base not local");
                return;
            }
            fw.write(writeFact(varName(inMeth, (soot.Local)base), l.getField().getName(), varName(inMeth, r)));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeVCall(String base, SootMethod sig, int line, SootMethod inMeth) {
        try {
            FileWriter fw = this.fwMap.get("vcall");
            fw.write(writeFact(varName(inMeth, base), uName(sig), String.valueOf(line), uName(inMeth)));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeActualArg(int invoSite, int argNumber, Local arg, SootMethod method) {
        try {
            FileWriter fw = this.fwMap.get("actual_arg");
            fw.write(writeFact(String.valueOf(invoSite), String.valueOf(argNumber), varName(method, arg)));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeActualRet(int invoSite, Local arg, SootMethod method) {
        try {
            FileWriter fw = this.fwMap.get("actual_ret");
            fw.write(writeFact(String.valueOf(invoSite), varName(method, arg)));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeFormalRet(SootMethod method, Local var) {
        try {
            FileWriter fw = this.fwMap.get("formal_ret");
            fw.write(writeFact(uName(method), varName(method, var)));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }



    ////////
    // PUBLIC FUNCTIONS
    ////////
    public void WriteStmtFact(Stmt stmt, SootMethod inMeth, int line) {
        //DatalogIntegrator.LogInfo(expr);
        if (stmt instanceof AssignStmt) {
            AssignStmt expr = (AssignStmt) stmt;
            soot.Value leftOp = expr.getLeftOp();
            soot.Value rightOp = expr.getRightOp();

            if (rightOp instanceof soot.jimple.NewExpr) {
                if (!(leftOp instanceof soot.Local)) {
                    System.out.println("Left op on assignmt is not local:  " + leftOp);
                    infoLogger.loggExpType(leftOp);
                } else {
                    writeAlloc(inMeth, (soot.Local)leftOp, (soot.jimple.NewExpr)rightOp);
                }
            } else if (rightOp instanceof soot.Local && leftOp instanceof soot.Local) {
                writeMove(inMeth, (soot.Local)leftOp, (soot.Local) rightOp);
            } else if (leftOp instanceof soot.jimple.InstanceFieldRef && rightOp instanceof soot.Local) {
                writeStore(inMeth, (soot.jimple.InstanceFieldRef)leftOp, (soot.Local)rightOp);
            } else if (leftOp instanceof soot.Local && rightOp instanceof soot.jimple.InstanceFieldRef) {
                writeLoad(inMeth, (soot.Local)leftOp, (soot.jimple.InstanceFieldRef)rightOp);
            } else if (rightOp instanceof soot.jimple.InvokeExpr) {
                soot.jimple.InvokeExpr ie = (soot.jimple.InvokeExpr) rightOp;
                String base;
                if  (ie instanceof soot.jimple.StaticInvokeExpr) {
                    base = "";
                } else {
                    soot.Local l = (soot.Local) ((soot.jimple.InstanceInvokeExpr)ie).getBase();
                    base = l.getName();
                }
                writeVCall(base, ie.getMethod(), line, inMeth);
                int argCount = ie.getArgCount();
                for (int i = 0; i < argCount; i++){
                    Local l = (Local)ie.getArg(i);
                    writeActualArg(line, i, l, inMeth);
                }
                writeActualRet(line, (Local)leftOp, inMeth);
            }
            return;
        } else if (stmt instanceof ReturnStmt) {
            ReturnStmt returnStmt = (ReturnStmt)stmt;
            Local local = (Local)returnStmt.getOp();
            writeFormalRet(inMeth, local);
        }
    } 

    public void WriteReachableFact(SootMethod method) {
        try {
            FileWriter fw = this.fwMap.get("reachable");
            fw.write(writeFact(uName(method)));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    public void WriteFormalArgFact(SootMethod method, int index, soot.Local var) {
        try {
            FileWriter fw = this.fwMap.get("formal_arg");
            fw.write(writeFact(uName(method), String.valueOf(index), varName(method, var)));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

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
