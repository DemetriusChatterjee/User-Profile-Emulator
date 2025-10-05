package Project1;

import java.util.Iterator;

public class DoublyLinkedList<T> implements Iterable<T> { 
    
    Node<T> head;
    Node<T> tail;
    
    // constructor
    public DoublyLinkedList(){
        head = null;
        tail = head;
    }

    public class Node<E>{
        private E data;
        private Node<E> next;
        private Node<E> prev;

        // constructor
        public Node(E data){
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        // gets the data
        public E getData(){
            return data;
        }

        // gets the next node
        public Node<E> getNext(){
            return next;
        }

        // gets the previous node
        public Node<E> getPrev(){
            return prev;
        }
    }

    // method to get the head
    public Node<T> getHead(){
        return head;
    }

    // adds to the end of the list
    public void addLast(T data){
        Node<T> newNode = new Node<T>(data);
        if(head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // add to the start of the list
    public void addFirst(T data){
        Node<T> newNode = new Node<T>(data);
        if(head == null){
            head = newNode;
            tail = head;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    // add at a specific index
    public void addAtIndex(int index, T data){
        Node<T> newNode = new Node<T>(data);
        Node<T> current = head;
        int i = 0;
        while(i < index - 1 && current != null){
            current = current.next;
            i++;
        }
        newNode.next = current.next;
        if (current.next != null) {
            current.next.prev = newNode;
        }
        current.next = newNode;
        newNode.prev = current;
        if (newNode.next == null) {
            tail = newNode;
        }
    }

    // remove from the end of the list
    public void removeLast(){
        if(head == null){
            return;
        }
        if(head.next == null){
            head = null;
            tail = head;
            return;
        }
        Node<T> current = head;
        while(current.next != tail){
            current = current.next;
        }
        current.next = null;
        tail = current;
    }

    // remove from the start of the list
    public void removeFirst(){
        if(head == null){
            return;
        }
        if(head.next == null){
            head = null;
            tail = head;
            return;
        }
        head = head.next;
        head.prev = null;
    }

    // remove at a specific index
    public void removeAtIndex(int index){
        Node<T> current = head;
        int i = 0;
        while(i < index && current != null){
            current = current.next;
            i++;
        }
        // If the node to be removed is the head
        if (current == head) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null; // List is now empty
            }
        } else if (current == tail) { // If the node to be removed is the tail
            tail = tail.prev;
            tail.next = null;
        } else { // If the node to be removed is in the middle
            Node<T> temp = current.getPrev();
            Node<T> temp2 = current.getNext();  
            temp.next = current.next;
            temp2.prev = current.prev;
        }
    }

    // method to get element at a specific index
    public T get(int index){
        Node<T> current = head;
        int i = 0;
        while(i < index && current != null){
            current = current.next;
            i++;
        }
        return current.getData();
    }

    // method to know if the next element exists
    public boolean hasNext() {
        return head != null && head.next != null;
    }

    // method to print all the elements in the list
    public void printList() {
        Iterator<T> iterator = iterator();
        while(iterator.hasNext()) {
            System.out.print(iterator.next() + ", ");
        }
        System.out.println();
    }

    // method to reverse list order and swap head and tail
    public void reverseList() {
        Node<T> current = head;
        Node<T> temp = null;
        while(current != null){
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;
            current = current.prev;
        }
        if(temp != null){
            temp = head;
            head = tail;
            tail = temp;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new DoublyLinkedListIterator();
    }

    private class DoublyLinkedListIterator implements Iterator<T> {
        private Node<T> temp = head;

        // method to know if the next element exists
        @Override
        public boolean hasNext() {
            return temp != null;
        }

        // method to get the next element if it exists
        @Override
        public T next() {
            if (!hasNext()) {
                System.err.println("No such element");
            }
            T nextData = temp.data;
            temp = temp.next;
            return nextData;
        }
    }
}