import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    //private Percolation system;
    private double[] plot;
    private int t;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Constructors should be positive");
        }
        plot = new double[trials];
        t = trials;
        for (int i = 0; i < trials; i++) {
            Percolation system = new Percolation(n);
            while (!system.percolates()) {
                int x = StdRandom.uniformInt(1, n + 1);
                int y = StdRandom.uniformInt(1, n + 1);
                if (!system.isOpen(x, y)) {
                    system.open(x, y);
                }
            }
            plot[i] = (double) system.numberOfOpenSites() / (n * n);

        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(plot);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(plot);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (1.96 * this.stddev() / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev() / Math.sqrt(t));
    }

    // test client (see below)
    public static void main(String[] args) {
        // StdOut.println("Enter n");

        // int N = StdIn.readInt();
        int N = Integer.parseInt(args[0]);
        // StdOut.println("Enter no of trials");
        // int T = StdIn.readInt();
        int T= Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(N, T);
        StdOut.println("mean\t" + "= " + percolationStats.mean() + "\n"
                + "stddev\t" + "= " + percolationStats.stddev() + "\n"
                + "95% confidence interval\t" + "= [" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi() + "]" + "\n"
        );
    }
}
