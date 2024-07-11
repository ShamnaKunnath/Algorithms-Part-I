package Module4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private final Item item;
        private Node next;
        private Node prev;

        public Node(Item item){
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque(){
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return ((first == null) && (last == null));
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null){
            throw new IllegalArgumentException("Invalid argument");
        }
        size+=1;
        Node newNode = new Node(item);
        //no element
        if (this.first == null){
            this.first = newNode;
            this.last = newNode;
        }
        else {
            newNode.next = this.first;
            this.first.prev = newNode;
            this.first = newNode;
        }
    }

    // add the item to the back
    public void addLast(Item item){
        if (item == null){
            throw new IllegalArgumentException("Invalid argument");
        }
        size+=1;
        Node newNode = new Node(item);
        //no element
        if (this.last == null){
            this.last = newNode;
            this.first = newNode;
        }
        //single elements
        else {
            newNode.prev = this.last;
            this.last.next = newNode;
            this.last = newNode;
        }
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (this.first == null){
            throw new NoSuchElementException("Deque is empty");
        }
        size-=1;
        Node oldFirst = this.first;
        this.first = this.first.next;
        if (this.first != null){
            this.first.prev = null;
        }
        else{
            this.last = null;
        }
        oldFirst.next = null;
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if (this.last == null){
            throw new NoSuchElementException("Deque is empty");
        }
        size-=1;
        Node oldLast = this.last;
        this.last = oldLast.prev;
        oldLast.prev = null;
        if (this.last != null) {
            this.last.next = null;
        }
        else{
            this.first = null;
        }
        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private Node current = first;

        public boolean hasNext(){
            return (current != null);
        }
        public Item next() {
            if (current == null){
                throw new NoSuchElementException("There are no more elements");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove(){
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

    private static void print(Deque<String> deque){
        for (String s: deque){
            System.out.print(s + " ");
        }
        System.out.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.print(deque.isEmpty());
        deque.addFirst("One");
        //System.out.print(" : one");
        print(deque);
        deque.addLast("Two");
        //System.out.print("one two : ");
        print(deque);
        deque.addLast("Three");
        //System.out.print("one two three : ");
        print(deque);
        deque.addFirst("Zero");
        //System.out.print("one two three zero : ");
        print(deque);
        System.out.println("4 : " + deque.size());

        System.out.println("zero : " + deque.removeFirst());
        //System.out.print("one two three : ");
        print(deque);
        System.out.println("three : " + deque.removeLast());
        //System.out.print("one two : ");
        print(deque);
        System.out.println("2 : " + deque.size());

        System.out.println("one : " + deque.removeFirst());
        System.out.println("two : " + deque.removeLast());
        print(deque);

        deque.removeFirst();
        deque.removeLast();
        deque.addLast(null);
        deque.addFirst(null);

    }
}
