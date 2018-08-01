import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private boolean[][] grid;
    private int openSites;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF backwash;
    private final int VIRTUAL_TOP;
    private final int VIRTUAL_BOT;
    private boolean percolated;

    public  Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        grid = new boolean[n][n];
        int len = (n * n) + 2;
        uf = new WeightedQuickUnionUF(len);
        backwash = new WeightedQuickUnionUF(len);
        VIRTUAL_TOP = len - 1;
        VIRTUAL_BOT = len - 2;
        // connect top and bottom rows to virtual top and bot sites
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (!isOpen(row, col)) {
            // System.out.println("opening row:" + row + " col: " + col);
            int i = row - 1;
            int j = col - 1;
            int current = coordToValue(row, col);
            if(row == 1) union(current, VIRTUAL_TOP);
            openSites += 1;
            grid[i][j] = true;
            connectAdjacent(row, col);
            if (row == grid.length ) {
                unionBackwash(current);
            }
        }

    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        int i = row - 1;
        int j = col - 1;
        if(!inBounds(i,j)) throw new IllegalArgumentException();
        return grid[i][j];
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        int site = coordToValue(row, col);
        if(!inBounds(row - 1,col - 1)) throw new IllegalArgumentException();
        return connected(VIRTUAL_TOP, site);
    }

    public int numberOfOpenSites() {
        // number of open sites
        return openSites;
    }

    public boolean percolates() {
        // does the system percolate?

        percolated = backwash.connected(VIRTUAL_TOP, VIRTUAL_BOT);
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

    private void connectAdjacent(int row, int col) {
        int i = row - 1;
        int j = col - 1;
        int current = coordToValue(row, col);
        if (inBounds(i + 1, j) && grid[i + 1][j]) {
            union(coordToValue(row + 1, col), current);
        }
        if (inBounds(i - 1, j) && grid[i - 1][j]) {
            union(coordToValue(row - 1, col), current);
        }
        if (inBounds(i, j + 1) && grid[i][j + 1]) {
            union(coordToValue(row, col + 1), current);
        }
        if (inBounds(i, j - 1) && grid[i][j - 1]) {
            union(coordToValue(row, col - 1), current);
        }
    }

    private boolean inBounds(int i, int j) {
        if (i < 0 || i >= grid.length ) return false;
        if (j < 0 || j >= grid.length ) return false;
        return true;
    }

    private void union(int p, int q) {
        backwash.union(p, q);
        uf.union(p, q);
    }

    private void unionBackwash(int current) {
        backwash.union(current, VIRTUAL_BOT);
    }

    private boolean connected(int p, int q) {
        return uf.connected(p, q);
    }
}