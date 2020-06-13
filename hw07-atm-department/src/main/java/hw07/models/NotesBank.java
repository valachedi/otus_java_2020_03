package hw07.models;

import java.lang.Math;
import java.util.Arrays;

public class NotesBank {
  private int amount;
  private int par;

  public NotesBank(int amount, int par) {
    this.amount = amount;
    this.par = par;
  }

  public int getPar() {
    return par;
  }

  public int getSum() {
    return this.amount * this.par;
  }

  public void increaseAmount() {
    amount++;
  }

  public int getMaxAvailableForReleaseSum(int sum) {
    return par * getReleasableAmountBySum(sum);
  }

  public int releaseMaxAvailableSum(int sum) {
    int releasableAmount = getReleasableAmountBySum(sum);
    amount -= releasableAmount;
    return par * releasableAmount;
  }

  public NotesBank clone() {
    return new NotesBank(amount, par);
  }

  private int getReleasableAmountBySum(int sum) {
    int parsRequired = (int)Math.floor(sum / par);
    return Math.min(parsRequired, amount);
  }
}
