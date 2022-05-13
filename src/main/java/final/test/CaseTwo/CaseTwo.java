class CaseTwoParent {

  public CaseTwoTest Proxy(CaseTwoTest j) {
    return j;
  }
}

public class CaseTwo extends CaseTwoParent {
// Expected aliasing alert between the following groups:
// variableFour - variableThree - variableOne.fieldOne 
  public int CaseTwoMethod() {
    CaseTwoTest variableOne = new CaseTwoTest();
    CaseTwoTest variableTwo = new CaseTwoTest();
    CaseTwoTest2 variableThree = new CaseTwoTest2();
    variableOne.fieldOne = variableThree;
    CaseTwoTest2 variableFour = variableThree;
    return 0;
  }
}

class CaseTwoTest {

  CaseTwoTest2 fieldOne;

  public int testMethod(int k) {
    return k;
  }
}

class CaseTwoTest2 {

  public int testMethod(int k) {
    return k;
  }
}
