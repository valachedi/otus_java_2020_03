package hw07.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

public class ParDictionary {
  private static TreeSet<Integer> parsList;

  private enum ValidPar {
    Par100(100),
    Par500(500),
    Par1000(1000),
    Par2000(2000),
    Par5000(5000);

    private final int value;

    ValidPar(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
  };

  public static TreeSet<Integer> getParsList() {
    if(parsList == null) {
      parsList = new TreeSet<Integer>();

      for(ValidPar par : ValidPar.values()) {
        parsList.add((int)par.getValue());
      }
    }

    return parsList;
  }

  public static TreeSet<Integer> getParsListReverseOrdered() {
    return (TreeSet<Integer>)getParsList().descendingSet();
  }
}
