package hw06.components;

import java.util.ArrayList;
import java.util.Collections;

public class ParDictionary {
  private static final int[] VALID_PARS = new int[]{
    100,
    500,
    1000,
    2000,
    5000
  };

  private static ArrayList<Integer> parsList;
  private static ArrayList<Integer> parsListReverseOrdered;

  public static ArrayList<Integer> getParsList() {
    if(parsList == null) {
      parsList = new ArrayList<Integer>(VALID_PARS.length);

      for(int par : VALID_PARS) {
        parsList.add(par);
      }
    }

    return new ArrayList<Integer>(parsList);
  }

  public static ArrayList<Integer> getParsListReverseOrdered() {
    if(parsListReverseOrdered == null) {
      parsListReverseOrdered = getParsList();
      Collections.sort(parsListReverseOrdered, (Integer el1, Integer el2) -> el1 > el2 ? -1 : 1);
    }

    return parsListReverseOrdered;
  }
}
