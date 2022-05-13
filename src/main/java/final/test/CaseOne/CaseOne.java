class CaseOneParent {

  public CaseOneTest Proxy(CaseOneTest j) {
    return j;
  }
}

public class CaseOne extends CaseOneParent {

  public int CaseOneMethod(int k) {
    CaseOneTest t = new CaseOneTest();
    CaseOneTest t2 = new CaseOneTest();
    CaseOneTest t3 = t;
    CaseOneTest t4 = Proxy(t2);
    CaseOneTest t5 = new CaseOneTest();
    CaseOneTest t6 = Proxy(t5);
    return 0;
  }
}

class CaseOneTest {

  public int testsMethod(int k) {
    return k;
  }
}
