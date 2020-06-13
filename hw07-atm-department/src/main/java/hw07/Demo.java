package hw07;

import hw07.models.AtmControllable;
import hw07.models.AtmDepartment;
import hw07.models.AtmFactory;
import hw07.models.interfaces.observer.MoneyStorageObservableInterface;

public class Demo {
  private static final int NUMBER_OF_DEPARTMENT_UNITS = 9;
  private static final int TEST_STORAGE_INDEX = 3;

  AtmDepartment atmDepartment;

  public Demo() throws Exception {
    initiateAtmDepartment();
  }

  public void run() throws Exception {
    var atm = atmDepartment.getStorageByIndex(TEST_STORAGE_INDEX);
    System.out.println("initial atm " + atm + " sum: " + atm.getRemain());
    var atmInitialState = atm.saveState();

    atm.addMoneyItemByPar(100);
    atm.addMoneyItemByPar(1000);
    atm.releaseMoney(30000);

    System.out.println("atm remain: " + atm.getRemain());

    System.out.println("restoring initial state of atm " + atm);
    atm.restoreState(atmInitialState);
    System.out.println("atm " + atm + " sum remain: " + atm.getRemain());
  }

  private void initiateAtmDepartment() throws Exception {
    atmDepartment = new AtmDepartment();

    for(int i = 0; i < NUMBER_OF_DEPARTMENT_UNITS; i++) {
      atmDepartment.addStorageUnit(generateNewStorageItem());
    }
  }

  private MoneyStorageObservableInterface generateNewStorageItem() throws Exception {
    return new AtmControllable(AtmFactory.createAtmWithRandomNoteBanksContent());
  }
}
