import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ArrayList;



public class Percolation {

    private int[][] grid;
    private int openSites;
    private WeightedQuickUnionUF uf;
    private int top;
    private int bot;
    private boolean percolated;

    public  Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        grid = new int[n][n];
        int len = (n * n) + 2;
        uf = new WeightedQuickUnionUF(len);
        top = len - 1;
        bot = len - 2;
        // connect top and bottom rows to virtual top and bot sites
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (!isOpen(row, col)) {
            // System.out.println("opening row:" + row + " col: " + col);
            int current = coordToValue(row, col);
            if(row == 1) union(current, top);
            ArrayList<Integer> open = getAdjacentOpen(row, col);
            int i = row - 1;
            int j = col - 1;
            openSites += 1;
            grid[i][j] = 1;
            for (int site : open) {
                union(site, current);
            }
            if (row == grid.length && !percolated && connected(top, current)) {
                union(current, bot);
            }
        }

    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        int i = row - 1;
        int j = col - 1;
        if(!inBounds(i,j)) throw new IllegalArgumentException();
        return grid[i][j] > 0;
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        int site = coordToValue(row, col);
        if(!inBounds(row - 1,col - 1)) throw new IllegalArgumentException();
        return connected(top, site);
    }

    public int numberOfOpenSites() {
        // number of open sites
        return openSites;
    }

    public boolean percolates() {
        // does the system percolate?

        percolated = connected(top, bot);
        return percolated;
    }

    public static void main(String[] args) {
        // test client (optional)
        Percolation test = new Percolation(6);
        int val = test.coordToValue(6,0);
        System.out.println(val);
    }

    private int coordToValue(int row, int col) {
        // return UF value
        // this is COORD not INDEX to value

        int val = col + (grid[0].length * (row - 1)) - 1;
        return val;
    }

    private ArrayList<Integer> getAdjacentOpen(int row, int col) {
        int i = row - 1;
        int j = col - 1;
        ArrayList<Integer> open = new ArrayList<Integer>();
        if (inBounds(i + 1, j) && grid[i + 1][j] > 0) {
            open.add(coordToValue(row + 1, col));
        }
        if (inBounds(i - 1, j) && grid[i - 1][j] > 0) {
            open.add(coordToValue(row - 1, col));
        }
        if (inBounds(i, j + 1) && grid[i][j + 1] > 0) {
            open.add(coordToValue(row, col + 1));
        }
        if (inBounds(i, j - 1) && grid[i][j - 1] > 0) {
            open.add(coordToValue(row, col - 1));
        }
        return open;
    }

    private boolean inBounds(int i, int j) {
        if (i < 0 || i >= grid.length ) return false;
        if (j < 0 || j >= grid.length ) return false;
        return true;
    }

    private void union(int p, int q) {
        uf.union(p, q);
    }

    private boolean connected(int p, int q) {
        return uf.connected(p, q);
    }
}