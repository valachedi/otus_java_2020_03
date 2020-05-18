package hw04.components;

import java.util.ArrayList;

public class BenchmarkOverfillMemory implements BenchmarkOverfillMemoryMBean {
  private static final int INITITAL_ELEMENTS_TO_ADD = 100000;
  private static final int ITERATION_DELAY_SECONDS = 10;
  private static final int LIMIN_ITERATIONS = 100000;
  private static final int MIN_NUMBER_OF_ELEMENTS = 1;

  private int numberOfElementsToAdd = INITITAL_ELEMENTS_TO_ADD;
  private int limit = LIMIN_ITERATIONS;

  @Override
  public int getNumberOfElementsToAdd() {
    return numberOfElementsToAdd;
  }

  @Override
  public void setNumberOfElementsToAdd(int number) {
    if(number < MIN_NUMBER_OF_ELEMENTS) {
      number = MIN_NUMBER_OF_ELEMENTS;
    }

    numberOfElementsToAdd = number;
  }

  public void run() throws Exception {
    ArrayList<Integer> list = new ArrayList<Integer>();
    int listSize;

    while(limit-- > 0) {
      for(int i = numberOfElementsToAdd; i > 0; i--) {
        list.add(new Integer(new String("1")));
      }

      listSize = list.size();

      for(int i = (int)listSize/2; i < listSize; i++) {
        list.set(i, null);
      }
      list.trimToSize();

      Thread.sleep(ITERATION_DELAY_SECONDS);
    }
  }
}
