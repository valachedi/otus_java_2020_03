package hw06.models.interfaces;

public interface MoneyStorageInterface {
  public void addMoneyItemByPar(int par) throws Exception;
  public void releaseMoney(int sum) throws Exception;
  public int getRemain();
}
