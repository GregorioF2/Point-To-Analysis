
public class CaseThree  {

  public int CaseThreeMethod() {
    CaseThreeTest variableOne = new CaseThreeTest();
    CaseThreeTest variableTwo = new CaseThreeTest();
    variableTwo = variableOne;
    return 0;
  }
}

class CaseThreeTest {
  public int testMethod(int k) {
    return k;
  }
}

