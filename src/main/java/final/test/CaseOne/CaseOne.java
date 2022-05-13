class CaseOneParent {

  public CaseOneTest Proxy(CaseOneTest j) {
    return j;
  }
}

public class CaseOne extends CaseOneParent {
// Expected aliasing alert between the following groups:
// variableOne - variableThree 
// variableTwo - variableFour  - variableSix
// variableFive - variableFour  - variableSix
  public int CaseOneMethod(int k) {
    CaseOneTest variableOne = new CaseOneTest();
    CaseOneTest variableTwo = new CaseOneTest();
    CaseOneTest variableThree = variableOne;
    CaseOneTest variableFour = Proxy(variableTwo);
    CaseOneTest variableFive = new CaseOneTest();
    CaseOneTest variableSix = Proxy(variableFive);
    return 0;
  }
}

class CaseOneTest {

  public int testsMethod(int k) {
    return k;
  }
}
