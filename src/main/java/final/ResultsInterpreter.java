package tp_final;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Scanner;

class HeapField {

  String heap;
  String field;

  HeapField(String heap, String field) {
    this.heap = heap;
    this.field = field;
  }
}

public class ResultsInterpreter {

  public static void main(String[] args) {
    System.out.println("\n\n\n////////////////////////////");
    System.out.println("/// RESULTS");
    System.out.println("////////////////////////////\n\n\n");
    try {
      File varPointsTo = new File("./DLOutput/var_points_to.csv");
      if (!varPointsTo.exists()) {
        System.out.println("Error varPointsTo not ready");
        return;
      }

      File fieldPointsTo = new File("./DLOutput/field_points_to.csv");
      if (!fieldPointsTo.exists()) {
        System.out.println("Error fieldPointsTo not ready");
        return;
      }
      Scanner scannerVarPointsTo = new Scanner(varPointsTo);
      scannerVarPointsTo.useDelimiter("\n");
      Scanner scannerFieldPointsTO = new Scanner(fieldPointsTo);
      scannerFieldPointsTO.useDelimiter("\n");

      HashMap<String, List<String>> heapVars = new HashMap<String, List<String>>();
      HashMap<String, List<HeapField>> heapFields = new HashMap<String, List<HeapField>>();

      while (scannerVarPointsTo.hasNext()) {
        String[] row = scannerVarPointsTo.next().split(";");
        String heap = row[1];
        String varMethod = row[0];

        if (!heapVars.containsKey(heap)) {
          heapVars.put(heap, new LinkedList<String>());
        }
        if (varMethod.contains("$")) {
          continue;
        }
        List<String> heapList = heapVars.get(heap);
        heapList.add(varMethod);
      }
      while (scannerFieldPointsTO.hasNext()) {
        String[] row = scannerFieldPointsTO.next().split(";");
        String heapBase = row[0];
        String field = row[1];
        String heapTo = row[2];
        if (!heapFields.containsKey(heapTo)) {
          heapFields.put(heapTo, new LinkedList<HeapField>());
        }
        List<HeapField> heapList = heapFields.get(heapTo);
        heapList.add(new HeapField(heapBase, field));
      }

      for (String key : heapVars.keySet()) {
        List<String> heapVar = heapVars.get(key);
        if (heapVar.size() <= 1) {
          continue;
        }
        System.out.println("\n\nVariables that might have aliasing: ");
        for (String hv : heapVar) {
          String[] varMethod = hv.split("::");
          String method = varMethod[0];
          String var = varMethod[1];
          System.out.println("---> Variable: " + var + ". On method " + method);
        }
        if (heapFields.containsKey(key)) {
          List<HeapField> hflds = heapFields.get(key);
          System.out.println("Together with the fields: ");
          for (HeapField hf : hflds) {
            String h = hf.heap;
            String fld = hf.field;
            List<String> varsOfFields = heapVars.get(h);
            for (String hv : varsOfFields) {
              String[] vm = hv.split("::");
              String m = vm[0];
              String v = vm[1];
              System.out.println(
                "---> Field: " + fld + " of variable: " + v + ". On method " + m
              );
            }
          }
        }
        System.out.print("\n");
      }

      return;
    } catch (Exception e) {
      System.out.println("ERRRORRR");
    }
  }
}
