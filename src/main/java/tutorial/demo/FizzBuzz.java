public class FizzBuzz {

    public void printFizzBuzz(int k){
        int intVal = 0;
        if (k%15==0)
            intVal +=1; // System.out.println("FizzBuzz");
        Test firstTestVar = new Test();
        Test2 secondTestVar = new Test2();

        firstTestVar.prop = secondTestVar;
    }

    public void fizzBuzz(int n){
        for (int i=1; i<=n; i++)
            printFizzBuzz(i);
    }
}
class Test {
    public Test2 prop;
    public static int TestFunc(){
        return 45;
    }

    public int testMethod(int k) {
        return k;
    }
}

class Test2 {
    public int testMethod(int k) {
        return k;
    }
}