package hw07.models.interfaces;

public interface MoneyStorageInterface {
  void addMoneyItemByPar(int par);
  void releaseMoney(int sum);
  int getRemain();
}
