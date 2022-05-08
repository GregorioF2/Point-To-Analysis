
class Parent {
    int ParentMethod() {
        return 123;
    }
}


public class FizzBuzz extends Parent {

    public int fizzProp;
    public int printFizzBuzz(int k){
        this.fizzProp = 2;
        int test;
        test = 2;
        int intParameter = 4;
        Test2 t = new Test2();
        int fparam = t.testMethod(intParameter);
        int gparam = Test.TestFunc() + fparam;
        return ParentMethod();
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