package hw06.models;

import hw06.models.interfaces.MoneyStorageInterface;
import hw06.models.interfaces.CashCustomerInterface;

public class BankClient implements CashCustomerInterface {
  private MoneyStorageInterface moneyStorage;

  public BankClient(MoneyStorageInterface moneyStorage) {
    this.moneyStorage = moneyStorage;
  }

  public void withdrawMoney(int money) {
    try {
      moneyStorage.releaseMoney(money);
    } catch(Exception e) {
      System.out.println("Getting money failed: "+e.getMessage());
    }
  }

  public void putMoneyOfPar(int par) {
    try {
      moneyStorage.addMoneyItemByPar(par);
    } catch(Exception e) {
      System.out.println("Money rejected: "+e.getMessage());
    }
  }
}
