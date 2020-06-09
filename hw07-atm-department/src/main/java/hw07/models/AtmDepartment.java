package hw07.models;

import java.util.ArrayList;
import java.util.List;
import hw07.models.interfaces.memento.OriginatorInterface;
import hw07.models.interfaces.observer.MoneyStorageObservableInterface;
import hw07.models.interfaces.observer.StorageListenerInterface;

public class AtmDepartment implements StorageListenerInterface {
  private static final int WARNING_BIG_WITHDRAW_MORE_THEN = 10000;
  private final List<MoneyStorageObservableInterface> storages;
  private int totalStoragesSum = 0;

  public AtmDepartment() {
    storages = new ArrayList<MoneyStorageObservableInterface>();
  }

  public void addStorageUnit(MoneyStorageObservableInterface storage) {
    storages.add(storage);
    storage.addListener(this);
    totalStoragesSum += storage.getRemain();
  }

  public <T extends MoneyStorageObservableInterface & OriginatorInterface> T getStorageByIndex(int index) {
    return (T)storages.get(index);
  }

  @Override
  public void onStorageDeposit(MoneyStorageObservableInterface storage, int amount) {
    totalStoragesSum += amount;
  }

  @Override
  public void onStorageWithdraw(MoneyStorageObservableInterface storage, int sum) {
    if(totalStoragesSum > WARNING_BIG_WITHDRAW_MORE_THEN) {
      System.out.println("too big withdrow at storage "+storage+" ("+sum+")");
    }

    totalStoragesSum -= sum;
  }
}
