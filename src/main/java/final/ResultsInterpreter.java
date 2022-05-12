package tp_final;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Scanner;

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
        System.out.print("\n");
      }

      return;
    } catch (Exception e) {
      System.out.println("ERRRORRR");
    }
  }
}
