import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid;
    private int size;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int countOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
           throw  new IllegalArgumentException("n should be greater than 0");
        }
        size = n;
        grid = new boolean[n * n + 1];
        grid[0] = true;
        for (int i = 1; i < n * n + 1; i++) {
            grid[i] = false;
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size) {
            throw new IllegalArgumentException("row indices should be between 1 and n");
        }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException("column indices should be between 1 and n");
        }
        countOfOpenSites++;
        grid[serializedIndex(row, col)] = true;

        if (row < size && grid[serializedIndex(row + 1, col)]) {
            weightedQuickUnionUF.union(serializedIndex(row, col), serializedIndex(row +  1, col));
        }
        if (row > 1 && grid[serializedIndex(row - 1, col)]) {
            weightedQuickUnionUF.union(serializedIndex(row, col), serializedIndex(row -  1, col));
        }
        if (col < size && grid[serializedIndex(row, col + 1)]) {
            weightedQuickUnionUF.union(serializedIndex(row, col), serializedIndex(row, col + 1));
        }
        if (col > 1 && grid[serializedIndex(row, col - 1)]) {
            weightedQuickUnionUF.union(serializedIndex(row, col), serializedIndex(row, col - 1));
        }

        //connects the top to arbitrary object at 0
        if (row == 1) {
            weightedQuickUnionUF.union(0, serializedIndex(row, col));
        }

        //connects the bottom to arbitrary object at n*n+1
        if (row == size && this.isFull(row, col)) {
            weightedQuickUnionUF.union(size * size + 1, serializedIndex(row, col));
        }

    }

    private int serializedIndex(int i, int j) {
        return (i - 1) * size + j;
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size) {
            throw new IllegalArgumentException("row indices should be between 1 and n");
        }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException("column indices should be between 1 and n");
        }
        return grid[serializedIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size) {
            throw new IllegalArgumentException("row indices should be between 1 and n");
        }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException("column indices should be between 1 and n");
        }
        //return weightedQuickUnionUF.connected(serializedIndex(row,col),0);
        return weightedQuickUnionUF.find(serializedIndex(row, col)) == 0;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(size * size + 1) == 0;
        //return weightedQuickUnionUF.connected(0,size*size+1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        StdOut.println(percolation.percolates());
        StdOut.println(percolation.isFull(1, 1));
        //StdOut.println(percolation.numberOfOpenSites());
        percolation.open(1, 1);
        //StdOut.println(percolation.percolates());
        //StdOut.println(percolation.numberOfOpenSites());
        StdOut.println(percolation.isFull(1, 1));
        percolation.open(2, 1);
        //StdOut.println(percolation.percolates());
        //StdOut.println(percolation.numberOfOpenSites());
        StdOut.println(percolation.isFull(2, 1));
        StdOut.println(percolation.isFull(3, 1));
        percolation.open(2, 2);
        StdOut.println(percolation.isFull(1,3));
        percolation.open(3, 3);
        StdOut.println(percolation.isFull(3, 3));
        StdOut.println(percolation.percolates());
        //StdOut.println(percolation.numberOfOpenSites());
        //StdOut.println(percolation.percolates());
    }
}
