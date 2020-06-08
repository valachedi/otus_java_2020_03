package hw06;

import hw06.models.BankClient;
import hw06.models.AtmFactory;
import hw06.models.interfaces.CashCustomerInterface;
import hw06.models.interfaces.MoneyStorageInterface;

import java.util.ArrayList;
import hw06.models.Atm;
import hw06.models.NotesBank;

public class Demo {
  MoneyStorageInterface storage;
  CashCustomerInterface cashCustomer;

  public Demo() throws Exception {
    storage = AtmFactory.createAtmWithRandomNoteBanksContent();
    cashCustomer = new BankClient(storage);
    System.out.println("Storage initial sum: "+storage.getRemain());
  }

  public void run() {
    testPutPar(100);
    testPutPar(150);
    testPutPar(1100);
    testPutPar(1000);
    testPutPar(2000);
    testPutPar(5000);
    testPutPar(5500);

    testReleaseSum(100);
    testReleaseSum(900);
    testReleaseSum(900);
    testReleaseSum(900);
    testReleaseSum(9500);
    testReleaseSum(1500);
    testReleaseSum(2500);
    testReleaseSum(9550);
    testReleaseSum(25000);
    testReleaseSum(1_000_000);
  }

  private void testPutPar(int par) {
    System.out.println("Client importing money of par " + par);
    cashCustomer.putMoneyOfPar(par);
    System.out.println("Sum of storage: "+storage.getRemain());
    System.out.println("");
  }

  private void testReleaseSum(int sum) {
    System.out.println("Client request to withdraw sum " + sum);
    cashCustomer.withdrawMoney(sum);
    System.out.println("Remain in storage: "+storage.getRemain());
    System.out.println("");
  }
}
