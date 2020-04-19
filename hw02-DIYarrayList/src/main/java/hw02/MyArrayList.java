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

    private Object[] _array;
    private int numberOfElements = 0;

    public MyArrayList() {
        _array = new Object[INITIAL_CAPACITY];
    }

    public T set(int index, T element) {
        if(index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }

        T prevValue = (T)_array[index];
        _array[index] = element;

        return prevValue;
    }

    public T get(int index) {
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        return (T)_array[index];
    }

    public boolean add(T e) {
        if(isFull()) {
            grow();
        }

        _array[numberOfElements++] = e;

        return true;
    }

    public Object[] toArray() {
        return Arrays.copyOf(_array, size());
    }

    public Iterator<T> iterator() {
        return new Itr();
    }

    public boolean isEmpty() {
        return _array.length == 0;
    }

    public int size() {
        return _array.length;
    }

    public void printList() {
        for(int i = 0; i < size(); i++) {
            if(i != 0) {
                System.out.print(",");
            }

            System.out.print(get(i));
        }

        System.out.println();
    }

    public void growToSize(int requiredSize) {
        if(requiredSize > size()) {
            _array = Arrays.copyOf(_array, requiredSize);
        }
    }

    @Override
    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[])_array, c);
    }

    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    public MyArrayList<T> subList(int from, int to) {
        throwMethodIsNotImplemented();
        return null;
    }

    public ListIterator<T> listIterator(int index) {
        throwMethodIsNotImplemented();
        return null;
    }

    public int indexOf(Object o) {
        throwMethodIsNotImplemented();
        return 0;
    }

    public boolean retainAll(Collection<?> c) {
        throwMethodIsNotImplemented();
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        throwMethodIsNotImplemented();
        return false;
    }

    public boolean addAll(Collection<? extends T> c) {
        throwMethodIsNotImplemented();
        return false;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throwMethodIsNotImplemented();
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        throwMethodIsNotImplemented();
        return false;
    }

    public boolean remove(Object o) {
        throwMethodIsNotImplemented();
        return false;
    }

    public int lastIndexOf(Object o) {
        throwMethodIsNotImplemented();
        return 0;
    }

    public T remove(int index) {
        throwMethodIsNotImplemented();
        return null;
    }

    public void add(int index, T element) {
        throwMethodIsNotImplemented();
    }

    public void clear() {
        throwMethodIsNotImplemented();
    }

    public <T> T[] toArray(T[] a) {
        throwMethodIsNotImplemented();
        return null;
    }

    public boolean contains(Object o) {
        throwMethodIsNotImplemented();
        return false;
    }

    private boolean isFull() {
        return numberOfElements == _array.length;
    }

    private void grow() {
        var newCapacityDoubled = _array.length * 2;
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

    private void throwMethodIsNotImplemented()
    {
        throw new UnsupportedOperationException("method not implemented");
    }
}
