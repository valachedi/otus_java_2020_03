package hw06.models.interfaces;

public interface MoneyStorageInterface {
  void addMoneyItemByPar(int par) throws Exception;
  void releaseMoney(int sum) throws Exception;
  int getRemain();
}
