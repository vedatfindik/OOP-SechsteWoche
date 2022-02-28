/****************************
 * QueueLinked.java
 *
 * Class for Queue using
 * linked list implementing myIterator
 ****************************/

public class QueueLinked<E> implements MyQueue<E> {
    private Node<E> head;
    private Node<E> tail;
    private int sizeCounter;

    //Iteratorklasse
    private class QIterator implements MyIterator<E> {
        private Node<E> currenthead = head;

        @Override
        public boolean hasNext() {
            return currenthead.next != null;
        }

        @Override
        public E getNext() {
            E val = currenthead.next.data;
            currenthead = currenthead.next;
            return val;
        }
    }

    //default constructor for creating an empty queue
    public QueueLinked () {
        tail = new Node<E>(null);
        head = tail;
        sizeCounter = 0;
    }

    //inserts item at the end of the queue
    public void enqueue (E item) {
        Node<E> itemnode = new Node<>(item);
        tail.next = itemnode;
        tail = itemnode;
        sizeCounter++;
    }

    //returns the head of the queue and deletes it from the queue;
    //returns null if queue is empty
    public E dequeue () {
        if(!isEmpty()){
            E current = head.next.data;
            head = head.next;
            sizeCounter--;
            return current;
        } else {
            return null;
        }
    }

    //test if queue is empty
    public boolean isEmpty() {
        return (size() == 0);
    }

    //returns the number of elements in the queue
    public int size () {
        return sizeCounter;
    }

    //returns a String-representation of this queue as
    //[item1, item2, ..., itemn] with item1 as the top item
    //and itemn as the last inserted item
    public String toString () {
        String ret = "[";
        String sep = "";
        MyIterator it = getIterator();
        while (it.hasNext()) {
            ret = ret + sep + it.getNext();
            sep = ", ";
        }

        return ret + "]";
    }

    //returns an iterator for traversing the queue
    public MyIterator<E> getIterator() {
        return new QIterator();
    }
}
