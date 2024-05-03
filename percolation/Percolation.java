import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid;
    private int size;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int countOfOpenSites;
    private WeightedQuickUnionUF quickUnionUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw  new IllegalArgumentException("n should be greater than 0");
        }
        size = n;
        grid = new boolean[n * n + 2];
        grid[0] = true;
        for (int i = 1; i < n * n + 2; i++) {
            grid[i] = false;
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 1);
        quickUnionUF = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size) {
            throw new IllegalArgumentException("row indices should be between 1 and n");
        }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException("column indices should be between 1 and n");
        }
        if (!isOpen(row, col)) {
            countOfOpenSites++;
            grid[this.serializedIndex(row, col)] = true;

            if (row < size && this.isOpen(row + 1, col)) {
                weightedQuickUnionUF.union(this.serializedIndex(row, col), this.serializedIndex(row +  1, col));
                quickUnionUF.union(this.serializedIndex(row, col), this.serializedIndex(row +  1, col));

            }
            if (row > 1 && this.isOpen(row - 1, col)) {
                weightedQuickUnionUF.union(this.serializedIndex(row, col), this.serializedIndex(row -  1, col));
                quickUnionUF.union(this.serializedIndex(row, col), this.serializedIndex(row -  1, col));
            }
            if (col < size && this.isOpen(row, col + 1)) {
                weightedQuickUnionUF.union(this.serializedIndex(row, col), this.serializedIndex(row, col + 1));
                quickUnionUF.union(this.serializedIndex(row, col), this.serializedIndex(row, col + 1));
            }
            if (col > 1 && this.isOpen(row, col - 1)) {
                weightedQuickUnionUF.union(this.serializedIndex(row, col), this.serializedIndex(row, col - 1));
                quickUnionUF.union(this.serializedIndex(row, col), this.serializedIndex(row, col - 1));
            }

            // connects the top to arbitrary object at 0
            if (row == 1) {
                weightedQuickUnionUF.union(this.serializedIndex(row, col), 0);
                quickUnionUF.union(this.serializedIndex(row, col), 0);
            }

            // connects the bottom to arbitrary object at n*n+1
            if (row == size) {
                // weightedQuickUnionUF.union(size * size + 1, this.serializedIndex(row, col));
                quickUnionUF.union(this.serializedIndex(row, col), size * size + 1);

            }
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
        return grid[this.serializedIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size) {
            throw new IllegalArgumentException("row indices should be between 1 and n");
        }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException("column indices should be between 1 and n");
        }
        return weightedQuickUnionUF.connected(this.serializedIndex(row, col), 0);
        // return weightedQuickUnionUF.find(this.serializedIndex(row, col)) == 0;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.countOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.connected(0, size * size + 1);
        // return quickUnionUF.find(size * size + 1) == 0;
        // return weightedQuickUnionUF.connected(0,size*size+1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
