package hw06.models;

import java.util.Random;

public class NotesBankFactory {
  private static final int MIN_AMOUNT = 5;
  private static final int MAX_AMOUNT = 10;
  private static final Random random = new Random();

  public static NotesBank createWithRandomAmountByPar(int par) throws Exception {
    return new NotesBank(generateRandomAmount(), par);
  }

  private static int generateRandomAmount() {
    return MIN_AMOUNT + random.nextInt(MAX_AMOUNT - MIN_AMOUNT);
  }
}
