import java.util.Arrays;

public class PercolationStats {

    double [] results;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        results = new double[trials];
        int count = 0;
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
        return 0.0;
    }
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return 0.0;
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(72, 100);
        System.out.println("Mean: " + test.mean());
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