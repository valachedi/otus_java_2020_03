package hw02;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MyArrayListRunner {
    private static final int NUMBER_OF_ELEMENTS_TO_ADD_INITIAL = 10;
    private static final int MAX_NUMBER_VALUE = 55555;
    private static final int NUMBER_OF_ELEMENTS_TO_ADD = 21;
    private static final Random random = new Random();

    private MyArrayList<Integer> myList;

    public MyArrayListRunner() {
        initList();
    }

    public void runWithCollections() {
    	runCollectionAddAll();
    	runCollectionCopy();
    	runCollectionSortWithComparator();
    }

    private void initList() {
        myList = createListWithRandomIntegers(NUMBER_OF_ELEMENTS_TO_ADD_INITIAL);
        System.out.println("List initiated with " + NUMBER_OF_ELEMENTS_TO_ADD_INITIAL + " random numbers:");
        myList.printList();
    }

    private void runCollectionAddAll() {
    	Integer[] elementsToAdd = new Integer[NUMBER_OF_ELEMENTS_TO_ADD];

    	for(int i = 0; i < NUMBER_OF_ELEMENTS_TO_ADD; i++) {
            elementsToAdd[i] = getRandomInteger();
        }

        System.out.println("Adding " + NUMBER_OF_ELEMENTS_TO_ADD + " random numbers..");
        Collections.addAll(myList, elementsToAdd);

        myList.printList();
    }

    private void runCollectionCopy() {
    	MyArrayList<Integer> myListCopy = createListWithRandomIntegers(NUMBER_OF_ELEMENTS_TO_ADD_INITIAL + NUMBER_OF_ELEMENTS_TO_ADD);
        Collections.copy(myListCopy, myList);
        System.out.println("copy of myArrayList content:");
        myListCopy.printList();
    }

    private void runCollectionSortWithComparator() {
        var integerOrderingComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer int1, Integer int2) {
                var result = 0;

                if(int1 == null) {
                    result = 1;
                } else if(int2 == null) {
                    result = -1;
                } else {
                    return int1 - int2;
                }

                return result;
            }
        };

        Collections.sort(myList, integerOrderingComparator);
        System.out.println("sorted myList:");
        myList.printList();
    }

    private MyArrayList<Integer> createListWithRandomIntegers(int numberOfElements) {
        MyArrayList<Integer> myList = new MyArrayList<Integer>();
        
        for(int i = 0; i < numberOfElements; i++) {
            myList.add(getRandomInteger());
        }

        return myList;
    }


    private Integer getRandomInteger() {
    	return (Integer)random.nextInt(MAX_NUMBER_VALUE);
    }
}
