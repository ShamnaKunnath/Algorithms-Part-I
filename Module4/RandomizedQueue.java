package Module4;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] rQ;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue(){
        //rQ = new Item[0];
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
        rQ[n++] = item;
    }

    // remove and return a random item
    public Item dequeue(){
        Item item = rQ[--n];
        rQ[n] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        return rQ[n];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{

        public boolean hasNext() {
            return false;
        }

        public Item next() {
            return null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args){

    }

}
