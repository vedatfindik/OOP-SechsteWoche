/********************************
 * Node
 *
 * Implementiert einen generischen
 * Knoten
 ********************************/



public class Node<T> {
    T data;
    Node<T> next;

    // Konstruktor
    public Node(T d) {
        data = d;
        next = null;
    }
}
