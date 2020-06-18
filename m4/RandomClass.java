import java.util.Iterator;
import java.util.Random;
import java.util.NoSuchElementException;

// @Author - Matt Koenig (mdk0027@auburn.edu)

public class RandomClass<T> implements RandomizedList<T> {
    private T[] elements;
    private int size;
    private static final int DEFAULT = 1;

    public RandomClass() {
        size = 0;
        elements = (T[]) new Object[DEFAULT];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public T sample() {
        if (size == 0) {
            return null;
        }

        int r = new Random().nextInt(size());
        return elements[r];
    }

    public void add(T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }

        if (size() == elements.length) {
            resize();
        }
        elements[size()] = element;
        size++;

    }

    public Iterator<T> iterator() {
        Iterate itr = new Iterate(elements, size());
        shuffle();
        return itr;
    }

    private class Iterate implements Iterator<T> {
        private T[] iterateArray;
        private int current;

        Iterate(T[] lookFrom, int countTo) {
            iterateArray = lookFrom;
            current = 0;
        }

        public boolean hasNext() {
            if (current < size()) {
                return true;
            } else {
                return false;
            }
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return iterateArray[current++];
        }
    }

    public T remove() {
        if (size() == 0) {
            return null;
        }
        int random = new Random().nextInt(size());

        T removed = elements[random];
        elements[random] = null;
        resizeDown(random);

        size--;
        return removed;

    }

    private void resizeDown(int remove) {
        T[] newArr = (T[]) new Object[size() - 1];
        int counter = 0;
        for (int i = 0; i < size(); i++) {
            if (i != remove) {
                newArr[counter] = elements[i];
                counter++;
            }
        }
        elements = newArr;

    }

    private void resize() {
        T[] newArr = (T[]) new Object[size() + 1];
        for (int i = 0; i < size(); i++) {
            newArr[i] = elements[i];
        }
        elements = newArr;
    }

    private void shuffle() {
        T[] newArr = (T[]) new Object[size()];
        for (int i = 0; i < size(); i++) {
            newArr[i] = elements[i];
            if (i == 1) {
                T temp = newArr[0];
                newArr[0] = newArr[1];
                newArr[1] = temp;
            }
        }
    elements = newArr;
    }
}
