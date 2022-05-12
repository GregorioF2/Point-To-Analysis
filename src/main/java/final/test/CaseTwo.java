class Parent2 {
    public Aux Proxy(Aux j){
        return j;
    }
}

public class CaseTwo extends Parent2 {
    public int CaseTwoMethod(int k){
        Aux t = new Aux();
        Aux t2 = new Aux();
        Aux t3 = t;
        Aux t4 = Proxy(t2);
        Aux t5 = new Aux();
        return 0;
    }
}

class Aux {
    public int testMethod(int k) {
        return k;
    }
}

class Aux2 {
    public int testMethod(int k) {
        return k;
    }
}