package hw02;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MyArrayListRunner {
    private static final int NUMBER_OF_GENERATED_ELEMENTS = 10;
    private static final int MAX_NUMBER_VALUE = 55555;
    private static final int MAX_LENGTH_ADDALL = 20;
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
        myList = new MyArrayList<Integer>();
        
        for(int i = 0; i < NUMBER_OF_GENERATED_ELEMENTS; i++) {
            myList.add(getRandomInteger());
        }

        System.out.println("List initiated with " + NUMBER_OF_GENERATED_ELEMENTS + " random numbers:");
        myList.printList();
    }

    private void runCollectionAddAll() {
    	Integer[] elementsToAdd = new Integer[MAX_LENGTH_ADDALL];

    	for(int i = 0; i < MAX_LENGTH_ADDALL; i++) {
            elementsToAdd[i] = getRandomInteger();
        }

        System.out.println("Adding " + MAX_LENGTH_ADDALL + " random numbers..");
        Collections.addAll(myList, elementsToAdd);

        myList.printList();
    }

    private void runCollectionCopy() {
    	var myListCopy = new MyArrayList<Integer>();
    	myListCopy.growToSize(myList.size());
        Collections.copy(myListCopy, myList);
        System.out.println("copy of myList content:");
        myListCopy.printList();
    }

    private void runCollectionSortWithComparator() {
        var integerOrderingComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer int1, Integer int2) {
                return int1 - int2;
            }
        };

        Collections.sort(myList, integerOrderingComparator);
        System.out.println("sorted myList:");
        myList.printList();
    }


    private Integer getRandomInteger() {
    	return (Integer)random.nextInt(MAX_NUMBER_VALUE);
    }
}
