package hw07.models;

import java.util.ArrayList;
import java.util.List;
import hw07.components.ParDictionary;
import hw07.components.ParValidator;
import hw07.models.interfaces.MoneyStorageInterface;

public class Atm implements MoneyStorageInterface {
  protected final List<NotesBank> notesBanks;

  public Atm(List<NotesBank> notesBanks) {
    this.notesBanks = new ArrayList<NotesBank>(notesBanks);
  }

  public List<NotesBank> getNotesBanks() {
    var copy = new ArrayList<NotesBank>();
    notesBanks.forEach(notesBank -> copy.add(notesBank.clone()));
    return copy;
  }

  @Override
  public void releaseMoney(int sum) {
    validateSumRequested(sum);

    if(getIsReleasableSum(sum)) {
      for(var par : ParDictionary.getParsListReverseOrdered()) {
        sum -= getNotesBankByPar(par).releaseMaxAvailableSum(sum);

        if(sum == 0) {
          break;
        }
      }
    } else {
      throw new RuntimeException("Cannot release such sum ("+sum+")");
    }
  }

  @Override
  public int getRemain() {
    return notesBanks.stream()
                .mapToInt(notesBank -> notesBank.getSum())
                .reduce((sum1,sum2) -> sum1 + sum2)
                .orElse(0);
  }

  @Override
  public void addMoneyItemByPar(int par) {
    ParValidator.checkPar(par);
    var notesBank = getNotesBankByPar(par);

    if(notesBank == null) {
      throw new RuntimeException("failed to access bank of par " + par);
    }

    notesBank.increaseAmount();
  }

  private void validateSumRequested(int sum) {
    boolean hasBankWithNeededPars = false;

    for(var notesBank : notesBanks) {
      if(sum % notesBank.getPar().getValue() == 0) {
        hasBankWithNeededPars = true;
        break;
      }
    }

    if(!hasBankWithNeededPars) {
      throw new RuntimeException("bad sum requested ("+sum+")");
    }

    int remain = getRemain();

    if(sum > remain) {
      throw new RuntimeException("Cannot release "+sum+", only "+remain+" remain");
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
            .filter(notesBank -> notesBank.getPar().getValue() == par)
            .findFirst()
            .orElse(null);
  }
}
