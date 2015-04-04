package impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import adt.Set;

public class ListSet<E> implements Set<E> {

    /**
     * Simple node class for this list.
     */
    private class Node {
        E datum;
        Node next;
        Node(E datum, Node next) {
            this.datum = datum;
            this.next = next;
        }
    }
    
    /**
     * The head node for this list
     */
    private Node head;

    public void add(E item) {
        if (! contains(item))
            head = new Node(item, head);
    }

    public boolean contains(E item) {
        boolean found = false;
        for (Node current = head; ! found && current != null; 
                current = current.next) 
            found |= current.datum.equals(item);
        return found;
    }

    public void remove(E item) {
        if (head == null)
            return;
        else if (head.datum.equals(item))
            head = head.next;
        else { 
            Node current = head;
            while (current.next != null && ! current.next.datum.equals(item)) {
                current = current.next;
            }
            if (current.next != null)
                current.next = current.next.next;
        }
    }

    public int size() {
        int i = 0;
        for (Node current = head; current != null; current = current.next)
            i++;
        return i;
    }

    public boolean isEmpty() {
        return size() == 0;
    }
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node current = head;
            public boolean hasNext() {
                return current != null;
            }

            public E next() {
                if (current == null)
                    throw new NoSuchElementException();
                E toReturn = current.datum;
                current = current.next;
                return toReturn;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
            
        };
    }

}
