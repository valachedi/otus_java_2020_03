package hw07.components;

public class ParValidator {
  public static void checkPar(int par) {
    boolean isValidPar = ParDictionary.ValidPar.getByPar(par) != null;

    if(!isValidPar) {
      throw new RuntimeException("Invalid par: " + par);
    }
  }
}
