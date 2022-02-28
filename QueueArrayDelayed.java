/**************************************
  * QueueArrayDelayed.java
 *
 * Klasse für Queue mit dynamisch
 * realloziertem array mit verzögerter
 * Löschverschiebung und myIterator
 *
 * Diese Klasse wurde rein aus
 * akademischem Interesse erstellt,
 * um die schlechte Laufzeit der
 * Arraylist beim Löschen zu amortisieren,
 * bietet aber neben enqueue, dequeue,
 * isEmpty und size in amortisiert
 * konstanter Zeit durch die get-Methode
 * Random Access in konstanter Zeit.
 *
 * Diese Klasse basiert im Prinzip,
 * darauf, dass zum Löschen an erster
 * Stelle die verbleibenden Daten nicht
 * sofort an den Anfang verschoben werden
 * müssen, sondern die Operation auf
 * eine der durch Reallozierung notwendigen
 * Arraykopien verschoben werden kann.
 ************************************/

import java.util.Arrays;

public class QueueArrayDelayed<E> implements MyQueue<E> {
    private E[] data;
    private int lpointer; // Points to the first element
    private int rpointer; // Points to the first free space


    //Iteratorklasse
    private class QIterator implements MyIterator<E> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < size();
        }

        @Override
        public E getNext() {
            E val = get(current);
            current++;
            return val;
        }
    }

    //default constructor for creating an empty queue
    public QueueArrayDelayed () {
        data = (E[]) new Object[10];
        lpointer = 0;
        rpointer = 0;
    }

    // doubles Array size if full, halfes it, if filled by less than a quarter.
    private void realloc () {
        if (rpointer == data.length) {
            if (size() < data.length/2) {
                shiftForward();
            } else {
                shiftForwardDoubleSize();
            }
        } else if (size() < data.length/4 && data.length >= 40) {
            shiftForwardHalfSize();
        }
    }

    // moves data to begin of array without altering array size
    private void shiftForward () {
        if (lpointer > 0) {
            for (int i = 0; i < size(); i++) {
                data[i] = data[i + lpointer];
            }
            rpointer = rpointer - lpointer;
            lpointer = 0;
        }
    }

    // moves data to begin of array doubling array size
    private void shiftForwardDoubleSize () {
        E[] ndata = (E[]) new Object[2*data.length];
        for (int i = 0; i < size(); i++) {
            ndata[i] = data[i + lpointer];
        }
        rpointer = rpointer - lpointer;
        lpointer = 0;
        data = ndata;
    }

    // moves data to begin of array halfing array size
    private void shiftForwardHalfSize () {
        E[] ndata = (E[]) new Object[data.length/2];
        for (int i = 0; i < size(); i++) {
            ndata[i] = data[i + lpointer];
        }
        rpointer = rpointer - lpointer;
        lpointer = 0;
        data = ndata;
    }

    // Returns Element at position id of Queue
    public E get (int id) throws IndexOutOfBoundsException {
        if (id < 0 || id >= size()) {
            throw new IndexOutOfBoundsException("Index " + id + "not within range");
        }
        return data[lpointer + id];
    }

    //inserts item at the end of the queue
    public void enqueue (E item) {
        data[rpointer] = item;
        rpointer++;
        realloc();
    }

    //returns the head of the queue and deletes it from the queue;
    //returns null if queue is empty
    public E dequeue () {
        if(!isEmpty()){
            E val = data[lpointer];
            lpointer++;
            realloc();
            return val;
        } else {
            return null;
        }
    }

    //test if queue is empty
    public boolean isEmpty() {
        return rpointer == lpointer;
    }

    //returns the number of elements in the queue
    public int size () {
        return rpointer - lpointer;
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
