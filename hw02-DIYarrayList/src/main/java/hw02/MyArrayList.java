package hw02;

import java.lang.IndexOutOfBoundsException;
import java.lang.UnsupportedOperationException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyArrayList<T> implements List<T> {
    private static final int INITIAL_CAPACITY = 30;

    private Object[] innerArray;
    private int numberOfElements = 0;

    public MyArrayList() {
        innerArray = new Object[INITIAL_CAPACITY];
    }

    public T set(int index, T element) {
        if(index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }

        T prevValue = (T)innerArray[index];
        innerArray[index] = element;

        return prevValue;
    }

    public T get(int index) {
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        return (T)innerArray[index];
    }

    public boolean add(T e) {
        if(isFull()) {
            grow();
        }

        innerArray[numberOfElements++] = e;

        return true;
    }

    public Object[] toArray() {
        return Arrays.copyOf(innerArray, size());
    }

    public Iterator<T> iterator() {
        return new Itr();
    }

    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    public int size() {
        return numberOfElements;
    }

    public void printList() {
        for(int i = 0; i < numberOfElements; i++) {
            if(i != 0) {
                System.out.print(",");
            }

            System.out.print(get(i));
        }

        System.out.println();
    }

    private void growToSize(int requiredSize) {
        if(requiredSize > numberOfElements) {
            if(requiredSize > innerArray.length) {
                innerArray = Arrays.copyOf(innerArray, requiredSize);
            }
        }
    }

    @Override
    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[])innerArray, c);
    }

    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    public MyArrayList<T> subList(int from, int to) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public T remove(int index) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public void add(int index, T element) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public void clear() {
        throw new UnsupportedOperationException("method not implemented");
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("method not implemented");
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("method not implemented");
    }

    private boolean isFull() {
        return numberOfElements == innerArray.length;
    }

    private void grow() {
        var newCapacityDoubled = innerArray.length * 2;
        growToSize(newCapacityDoubled);
    }

    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor + 1;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public T previous() {
           return MyArrayList.this.get(cursor - 1);
        }

        public void set(T e) {
            MyArrayList.this.set(cursor - 1, e);
        }

        public void add(T e) {
            MyArrayList.this.add(cursor, e);
        }
    }

    private class Itr implements Iterator<T> {
        protected int cursor;

        Itr() {}

        public boolean hasNext() {
            return cursor != size();
        }

        public T next() {
            return (T) MyArrayList.this.get(cursor++);
        }

        public void remove() {
            MyArrayList.this.remove(cursor);
        }
    }
}
