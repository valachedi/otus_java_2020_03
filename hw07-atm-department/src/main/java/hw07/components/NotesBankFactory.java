package hw07.components;

import java.util.Random;
import hw07.components.ParDictionary.ValidPar;
import hw07.components.ParValidator;
import hw07.models.NotesBank;

public class NotesBankFactory {
  private static final int MIN_AMOUNT = 5;
  private static final int MAX_AMOUNT = 10;
  private static final Random random = new Random();

  public static NotesBank createWithRandomAmountByPar(ValidPar par) {
    return new NotesBank(generateRandomAmount(), par);
  }

  private static int generateRandomAmount() {
    return MIN_AMOUNT + random.nextInt(MAX_AMOUNT - MIN_AMOUNT);
  }
}
