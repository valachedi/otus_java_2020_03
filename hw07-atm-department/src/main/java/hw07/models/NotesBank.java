package hw07.models;

import java.lang.Cloneable;
import java.lang.Math;
import java.util.Arrays;
import hw07.components.ParDictionary.ValidPar;

public class NotesBank implements Cloneable {
  private int amount;
  private final ValidPar par;

  public NotesBank(int amount, ValidPar par) {
    this.amount = amount;
    this.par = par;
  }

  public ValidPar getPar() {
    return par;
  }

  public int getSum() {
    return this.amount * this.par.getValue();
  }

  public void increaseAmount() {
    amount++;
  }

  public int getMaxAvailableForReleaseSum(int sum) {
    return par.getValue() * getReleasableAmountBySum(sum);
  }

  public int releaseMaxAvailableSum(int sum) {
    int releasableAmount = getReleasableAmountBySum(sum);
    amount -= releasableAmount;
    return par.getValue() * releasableAmount;
  }

  public NotesBank clone() {
    return new NotesBank(amount, par);
  }

  private int getReleasableAmountBySum(int sum) {
    int parsRequired = (int)Math.floor(sum / par.getValue());
    return Math.min(parsRequired, amount);
  }
}
