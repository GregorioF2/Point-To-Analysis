
class Parent {
    int ParentMethod() {
        return 123;
    }
}


public class FizzBuzz extends Parent {

    public int fizzProp;
    public int printFizzBuzz(int k){
        Test2 t = new Test2();
        Test2 t2 = new Test2();
        Test2 t3 = t;
        Test2 t4 = proxy(t2);
        return ParentMethod();
    }

    public Test2 proxy(Test2 j){
        return j;
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