package hw07.components;

public class ParValidator {
  public static void checkPar(int par) throws Exception {
    boolean isValidPar = ParDictionary.getParsList().contains(par);

    if(!isValidPar) {
      throw new Exception("Invalid par: " + par);
    }
  }
}
