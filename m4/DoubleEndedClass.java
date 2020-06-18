import java.util.Iterator;
import java.util.Random;
import java.util.NoSuchElementException;

public class DoubleEndedClass<T> implements DoubleEndedList<T> {

    private Node first;
    private int size;
    private Node last;

    public DoubleEndedClass() {

        first = null;
        size = 0;

    }

    public int size() {

        return size;
    }

    private class Node {

        private T element;
        private Node next;

        public Node(T val) {
            element = val;
        }

    }

    public void addFirst(T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        Node n = new Node(element);


        if (size == 0) {
            size++;
            first = n;
            last = n;
        } else {
            size++;
            n.next = first;
            first = n;
        }

    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void addLast(T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        Node n = new Node(element);

        if (size == 0) {
            size++;
            first = n;
            last = n;
        } else {
            size++;
            last.next = n;
            last = n;
        }

    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        T deleted = first.element;
        first = first.next;
        size--;
        return deleted;

    }

    public Iterator<T> iterator() {
        Iterate itr = new Iterate();
        return itr;
    }

    private class Iterate implements Iterator<T> {
        private Node index = first;

        public T next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T selected = index.element;
            index = index.next;
            return selected;
        }

        public boolean hasNext() {
            if (index == null) {
                return false;
            } else {
                return true;
            }
        }

    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            T temp = first.element;
            first = null;
            last = null;
            size--;
            return temp;
        } else {
            Node n = first;
            int counter = 0;
            while (counter < size - 2) {
                n = n.next;
                counter++;
            }

            T removed = n.next.element;
            n.next = null;
            last = n;
            size--;
            return removed;
        }

    }

}