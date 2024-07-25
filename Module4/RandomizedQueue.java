package Module4;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] rQ;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue(){
        rQ = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return n;
    }

    // add the item
    public void enqueue(Item item){
        if (item == null) throw new IllegalArgumentException("null is an illegal argument");
        if (n == rQ.length) resize(2* rQ.length);
        rQ[n++] = item;
    }

    private void resize(int newSize){
        Item [] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < n; i++)
            copy[i] = rQ[i];
        rQ = copy;
        //System.out.println(rQ.length);
    }

    // remove and return a random item
    public Item dequeue(){
        if (n == 0) throw new NoSuchElementException("Randomised queue is empty");
        int randomInt = StdRandom.uniformInt(0, n);
        //StdOut.println("randomInt : " + randomInt);
        Item item = rQ[randomInt];
        rQ[randomInt] = rQ[--n];
        rQ[n] = null;
        if (n > 0 && n == rQ.length/4) resize(rQ.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (n == 0) throw new NoSuchElementException("Randomised queue is empty");
        return rQ[StdRandom.uniformInt(0, n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{

        private final Item[] copy;
        private int i = 0;

        public ListIterator(){
            copy = (Item[]) new Object[n];
            for (int i = 0; i < n ; i++){
                copy[i] = rQ[i];
            }
            StdRandom.shuffle(copy);
        }

        public boolean hasNext() {
            return i  < copy.length;
        }

        public Item next() {
            if (i  >= copy.length) throw new NoSuchElementException("You have reached the end of the queue");
            return copy[i++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }

    private static void print(RandomizedQueue<String> randomizedQueue){
        for (String s: randomizedQueue){
            System.out.print(s + " ");
        }
        StdOut.println();
    }
    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        System.out.println(randomizedQueue.isEmpty());

        randomizedQueue.enqueue("it");
        print(randomizedQueue);


        randomizedQueue.enqueue("was");
        print(randomizedQueue);

        randomizedQueue.enqueue("the");
        print(randomizedQueue);

        randomizedQueue.enqueue("best");
        print(randomizedQueue);

        randomizedQueue.dequeue();
        print(randomizedQueue);

        randomizedQueue.enqueue("of");
        print(randomizedQueue);

        randomizedQueue.enqueue("times");
        print(randomizedQueue);

        randomizedQueue.dequeue();
        print(randomizedQueue);
        System.out.println(randomizedQueue.sample());

        randomizedQueue.dequeue();
        print(randomizedQueue);

        randomizedQueue.dequeue();
        print(randomizedQueue);

        randomizedQueue.dequeue();
        print(randomizedQueue);

    }

}
