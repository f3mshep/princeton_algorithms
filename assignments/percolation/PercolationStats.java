import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.util.Arrays;

public class PercolationStats {

    private static final double PERCENTILE = 1.96;
    private double [] results;
    private int t;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n < 1 || trials < 1) throw new IllegalArgumentException();
        results = new double[trials];
        int count = 0;
        t = trials;
        while (count < trials) {
            results[count] = performTrial(n);
            count++;
        }

    }
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(results);
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(results);
    }
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean() - (PERCENTILE * stddev()) / Math.sqrt(t);
    }
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() - (PERCENTILE * stddev()) / Math.sqrt(t);
    }

    private String printConf() {
        return "[" + confidenceLo() +","+ confidenceHi() + "]";
    }

    public static void main(String[] args) {
        PercolationStats client = new PercolationStats(Integer.parseInt(args[0]),
                                                       Integer.parseInt(args[1]));
        System.out.println("mean                    = " + client.mean());
        System.out.println("stddev                  = " + client.stddev());
        System.out.println("95% confidence interval = " + client.printConf());

    }

    private double performTrial(int n) {
        Percolation client = new Percolation(n);
        while (!client.percolates()) {
            int i = StdRandom.uniform(1, n+ 1);
            int j = StdRandom.uniform(1, n+ 1);
            client.open(i,j);
        }
        return (double) client.numberOfOpenSites() / (n*n);
    }

    public void printResults() {
        System.out.println(Arrays.toString(results));
    }
}