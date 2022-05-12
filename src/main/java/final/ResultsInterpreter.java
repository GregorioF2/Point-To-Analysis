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
      File output = new File("./DLOutput/var_points_to.csv");
      if (!output.exists()) {
        System.out.println("Error output not ready");
        return;
      }
      Scanner sc = new Scanner(output);
      HashMap<String, List<String>> heapVars = new HashMap<String, List<String>>();
      sc.useDelimiter("\n");
      while (sc.hasNext()) {
        String[] row = sc.next().split(";");
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
