package test;

class Parent {
    public Test Proxy(Test j){
        return j;
    }
}

public class CaseOne extends Parent {
    public int fizzProp;
    public int CaseOneMethod(int k){
        Test t = new Test();
        Test t2 = new Test();
        Test t3 = t;
        Test t4 = Proxy(t2);
        return 0;
    }
}

class Test {
    public int testMethod(int k) {
        return k;
    }
}