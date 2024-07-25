package Module4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = StdIn.readInt();
        RandomizedQueue<String> queue =  new RandomizedQueue<>();
        while (!StdIn.isEmpty()){
            queue.enqueue(StdIn.readString());
        }
        while (k-- >0){
            StdOut.println(queue.dequeue());
        }
    }
}
