package hw06.models;

import java.util.ArrayList;
import hw06.components.ParDictionary;
import hw06.components.ParValidator;
import hw06.models.interfaces.MoneyStorageInterface;

public class Atm implements MoneyStorageInterface {
  private ArrayList<NotesBank> notesBanks;

  public Atm(ArrayList<NotesBank> notesBanks) {
    this.notesBanks = notesBanks;
  }

  public void releaseMoney(int sum) throws Exception {
    validateSumRequested(sum);

    if(getIsReleasableSum(sum)) {
      for(var par : ParDictionary.getParsListReverseOrdered()) {
        sum -= getNotesBankByPar(par).releaseMaxAvailableSum(sum);

        if(sum == 0) {
          break;
        }
      }
    } else {
      throw new Exception("Cannot release such sum ("+sum+")");
    }
  }

  public int getRemain() {
    return this.notesBanks.stream()
                .mapToInt(notesBank -> notesBank.getSum())
                .reduce((sum1,sum2) -> sum1 + sum2)
                .getAsInt();
  }

  public void addMoneyItemByPar(int par) throws Exception {
    ParValidator.checkPar(par);
    var notesBank = getNotesBankByPar(par);

    if(notesBank == null) {
      throw new Exception("failed to access bank of par " + par);
    }

    notesBank.increaseAmount();
  }

  private void validateSumRequested(int sum) throws Exception {
    if(sum % 100 != 0) {
      throw new Exception("bad sum requested ("+sum+")");
    }

    int remain = getRemain();

    if(sum > remain) {
      throw new Exception("Cannot release "+sum+", only "+remain+" remain");
    }
  }

  private boolean getIsReleasableSum(int sum) {
    for(var par : ParDictionary.getParsListReverseOrdered()) {
      var notesBank = getNotesBankByPar(par);

      if(notesBank != null) {
        sum -= notesBank.getMaxAvailableForReleaseSum(sum);
      }
    }

    return sum == 0;
  }

  private NotesBank getNotesBankByPar(int par) {
    return notesBanks.stream()
            .filter(notesBank -> notesBank.getPar() == par)
            .findFirst()
            .orElse(null);
  }
}
