package hw03;

import hw02.MyArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MyArrayListTest {
    private static final int INITITAL_NUMBER_OF_ELEMENTS = 50;
    private static final int MAX_NUMBER_VALUE = 10000;
    private static final int NUMBER_OF_ELEMENTS_TO_ADD_INITIAL = 10;
    private static final int NUMBER_OF_ELEMENTS_TO_ADD = 21;
    private static final Random random = new Random();
    private MyArrayList myList;

    @Before
    public void createMyArrayList() {
        myList = createListWithRandomIntegers(INITITAL_NUMBER_OF_ELEMENTS);
    }

    @Test
    public void runCollectionAddAll() {
        Integer[] elementsToAdd = new Integer[NUMBER_OF_ELEMENTS_TO_ADD];

        for(int i = 0; i < NUMBER_OF_ELEMENTS_TO_ADD; i++) {
            elementsToAdd[i] = getRandomInteger();
        }

        System.out.println("Adding " + NUMBER_OF_ELEMENTS_TO_ADD + " random numbers..");
        Collections.addAll(myList, elementsToAdd);

        myList.printList();
    }

    @Test
    public void runCollectionCopy() {
        MyArrayList<Integer> myListCopy = createListWithRandomIntegers(INITITAL_NUMBER_OF_ELEMENTS);
        Collections.copy(myListCopy, myList);
        System.out.println("copy of myArrayList content:");
        myListCopy.printList();
    }

    @Test
    public void runCollectionSortWithComparator() {
        var integerOrderingComparator = new Comparator<Integer>() {
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

    @Test
    public void runFakeError() {
        myList.set(999999999, 36484);
    }

    @After
    public void removeList() {
        myList = null;
    }

    private Integer getRandomInteger() {
        return (Integer)random.nextInt(MAX_NUMBER_VALUE);
    }

    private MyArrayList<Integer> createListWithRandomIntegers(int numberOfElements) {
        MyArrayList<Integer> myList = new MyArrayList<Integer>();
        
        for(int i = 0; i < numberOfElements; i++) {
            myList.add(getRandomInteger());
        }

        return myList;
    }
}
