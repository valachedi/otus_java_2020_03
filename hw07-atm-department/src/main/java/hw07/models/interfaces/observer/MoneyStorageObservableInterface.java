package hw07.models.interfaces.observer;

import hw06.models.interfaces.MoneyStorageInterface;

public interface MoneyStorageObservableInterface extends MoneyStorageInterface {
  void addListener(StorageListenerInterface listener);
  void removeListener(StorageListenerInterface listener);
}
