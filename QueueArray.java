/****************************
 * QueueArray.java
 *
 * Class for Queue using
 * Array list implementing myIterator
 ****************************/

import java.util.ArrayList;

public class QueueArray<E> implements MyQueue<E> {
    private ArrayList<E> content;

    //Iteratorklasse
    private class QIterator implements MyIterator<E> {
        private int current = 0;        // points at position of next element

        @Override
        public boolean hasNext() {
            return current < size();
        }

        @Override
        public E getNext() {
            E val = content.get(current);
            current++;
            return val;
        }
    }

    //default constructor for creating an empty queue
    public QueueArray () {
        content = new ArrayList<E>();
    }

    //inserts item at the end of the queue
    public void enqueue (E item) {
        content.add(item);
    }

    //returns the head of the queue and deletes it from the queue;
    //returns null if queue is empty
    public E dequeue () {
        if(!isEmpty()){
            E helper = content.get(0);
            content.remove(0);
            return helper;
        } else {
            return null;
        }
    }

    //test if queue is empty
    public boolean isEmpty() {
        return content.isEmpty();
    }

    //returns the number of elements in the queue
    public int size () {
        return content.size();
    }

    //returns a String-representation of this queue as
    //[item1, item2, ..., itemn] with item1 as the top item
    //and itemn as the last inserted item
    public String toString () {
        return content.toString();
    }

    //returns an iterator for traversing the queue
    public MyIterator<E> getIterator() {
        return new QIterator();
    }
}
