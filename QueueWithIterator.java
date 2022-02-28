/**************************************
 * QueueWithIterator.java
 *
 * Testing class for the three queues
 * performs basic tests,
 * iterator tests
 * Performance tests
 ************************************/ 

import java.util.Random;

class QueueWithIterator {
    public static void main (String[] args) {
        // Basic tests, testing the correctness of enqueue, dequeue, isEmpty, size, toString especially if Queue is empty.
        System.out.println("Performing some basic tests");

        // Linked
        System.out.println("Testing Linked Queue:");
        basictest(new QueueLinked<Integer>());

        // Array
        System.out.println("Testing Array Queue:");
        basictest(new QueueArray<Integer>());

        // Array delayed
        System.out.println("Testing Delayed Array Queue:");
        basictest(new QueueArrayDelayed<Integer>());


        // IterTest, creating queues of 30000 random integers < 1000000, getting all that are < 10000 by using the MyIterator.
        System.out.println("Performing Iteratortest");
        iteratortest(30000,10000,1000000);
        System.out.println();

        // Timing, performing Tests on queues with a certain prefill level by operating a number of random enqueue and dequeue operations on the Queue.
        System.out.println("Performing Timing Tests");
        perfTest(10000,1000);
        perfTest(10000,10000);
        perfTest(10000,100000);
        perfTest(10000,1000000);
        perfTest(10000,10000000);

        perfTest(1000,10000);
        perfTest(10000,10000);
        perfTest(100000,10000);
        perfTest(1000000,10000);
        perfTest(10000000,10000);
    }

    // Just some basic tests, testing the correctness of enqueue, dequeue, isEmpty, size, toString especially if Queue is empty.
    private static void basictest (MyQueue<Integer> q) {
        System.out.println("Testing Queue, currently: (empty? " + q.isEmpty() + ", size=" + q.size() + ")");
        System.out.println(q);
        System.out.println("Adding 1,2,3,4,5:");
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        System.out.println("(empty? " + q.isEmpty() + ", size=" + q.size() + ")");
        System.out.println(q);
        System.out.println("Getting first: " + q.dequeue());
        System.out.println("Rest:  (empty? " + q.isEmpty() + ", size=" + q.size() + ")");
        System.out.println(q);
        System.out.println("Getting: " + q.dequeue() + ", " + q.dequeue() + ", " + q.dequeue() + ", " + q.dequeue()+", Now:");
        System.out.println("(empty? " + q.isEmpty() + ", size=" + q.size() + ")");
        System.out.println(q);
        System.out.println("Getting: " + q.dequeue() + ", Now:");
        System.out.println("(empty? " + q.isEmpty() + ", size=" + q.size() + ")");
        System.out.println(q);
        System.out.println();
    }

    // Testing the MyIterators
    private static void iteratortest (int size, int limit, int range) {
        // Creating queues
        QueueLinked<Integer> linked = new QueueLinked<>();
        QueueArray<Integer> array = new QueueArray<>();
        QueueArrayDelayed<Integer> arrayD = new QueueArrayDelayed<>();
        // Filling queues
        fill(size, range, linked, array, arrayD);
        
        // Filtering queues
        System.out.println("Trying to get all numbers < " + limit + ":");
        System.out.println("Linked Queue:");
        filterlimit(linked,limit);

        System.out.println("Array Queue:");
        filterlimit(array,limit);

        System.out.println("Delayed Array Queue:");
        filterlimit(arrayD,limit);
    }

    // Uses MyIterator to get all elements < limit
    private static void filterlimit (MyQueue<Integer> q, int limit) {
        MyIterator<Integer> it = q.getIterator();
        String sep = "";
        while (it.hasNext()) {
            Integer val = it.getNext();
            if (val < limit) {
                System.out.print(sep + val);
                sep = ",";
            }
        }
        System.out.println();
    }

    // Returns an array of size @param size of random 0 and 1 values representing enqueue and dequeue operations
    private static int[] getOperations (int size) {
        int[] store = new int[size];
        Random R = new Random();
        for(int i = 0; i < size; i++) {
            store[i] = R.nextInt(2);
        }

        return store;
    }

    // Takes a queue and an array of operations (0 and 1). Performs Operations and return required time.
    private static long timePerform (MyQueue<Integer> q, int[] ops) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < ops.length; i++) {
            if (ops[i] == 0) {
                q.enqueue(0);
            } else {
                q.dequeue();
            }
        }

        return System.currentTimeMillis() - time;
    }

    // Filles three Queues with the same n random integers smaller than range
    private static void fill (int n, int range, MyQueue<Integer> a, MyQueue<Integer> b, MyQueue<Integer> c) {
        Random R = new Random();
        for (int i = 0; i < n; i++){
            Integer random = R.nextInt(range);
            a.enqueue(random);
            b.enqueue(random);
            c.enqueue(random);
        }
    }

    // Creates three Queues filled with prefill values and takes time for operations random operations.
    private static void perfTest (int prefill, int operations) {
        System.out.println("Testing " + operations + " random Operations on a prefill of size " + prefill + ":");
        // Creating operations array
        int[] operationLog = getOperations(operations);

        // Creating queues
        QueueLinked<Integer> linked = new QueueLinked<Integer>();
        QueueArray<Integer> array = new QueueArray<Integer>();
        QueueArrayDelayed<Integer> arrayD = new QueueArrayDelayed<Integer>();

        // Filling queues
        fill(prefill, 1000000, linked, array, arrayD);

        // Timing
        System.out.print("Linked Queue:");
        System.out.println(timePerform(linked, operationLog) + "ms");

        System.out.print("Array Queue:");
        System.out.println(timePerform(array, operationLog) + "ms");

        System.out.print("Delayed Array Queue:");
        System.out.println(timePerform(arrayD, operationLog) + "ms");

        System.out.println();
    }
}

/*
 * Results of the timing tests:
 * [Format: operations|prefill|time_linked|time_array|time_array_delayed]
 *
 *     NOps| prefill|linked|   array|arrayD
 * ========================================
 *     1000|   10000|   1ms|     3ms|   2ms
 *    10000|   10000|   3ms|    29ms|   5ms
 *   100000|   10000|  35ms|   144ms|   8ms
 *  1000000|   10000|  33ms|   832ms|  22ms
 * 10000000|   10000| 237ms| 10014ms| 247ms
 *    10000|    1000|   0ms|     1ms|   0ms
 *    10000|   10000|   0ms|    10ms|   0ms
 *    10000|  100000|   0ms|   115ms|   0ms
 *    10000| 1000000|   0ms|  2873ms|   0ms
 *    10000|10000000|   0ms|308223ms|   1ms
 */
