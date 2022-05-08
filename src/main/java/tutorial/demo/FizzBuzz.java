public class FizzBuzz {

    public int fizzProp;
    public void printFizzBuzz(int k){
        this.fizzProp = 2;

        Test2 t = new Test2();
    }

    public void fizzBuzz(int n){
        for (int i=1; i<=n; i++)
            printFizzBuzz(i);
    }
}
class Test {
    public Test2 prop;
    public int testNumber;
    public Test(int k) {
        this.testNumber = k;
    }
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