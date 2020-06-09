package hw07.models.interfaces.observer;

import hw07.models.interfaces.observer.MoneyStorageObservableInterface;

public interface StorageListenerInterface {
  void onStorageDeposit(MoneyStorageObservableInterface storage, int amount);
  void onStorageWithdraw(MoneyStorageObservableInterface storage, int sum);
}
