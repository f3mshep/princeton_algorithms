import edu.princeton.cs.algs4.UF;

import java.util.ArrayList;

public class Percolation {

    private int[][] grid;
    private int openSites;
    private UF uf;
    private int top;
    private int bot;
    private boolean percolated;

    public  Percolation(int n) {
        // create n-by-n grid, with all sites blocked

        // create an array of arrays to reprsent a grid.
        // elements will be integers 0,1 or 2
        // 0 indicates closed, 1 indicates open, 2 indicates full

        grid = new int[n][n];
        int len = (n * n) + 2;
        uf = new UF(len);
        top = len - 1;
        bot = len - 2;
        // connect top and bottom rows to virtual top and bot sites
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        // REMBMER: row and col ARE NOT INDEX VALUES! add 1 to each to access proper arr coord
        // CHANGE OPEN TO ALSO UNION IF SITE IS IN TOP OR BOT ROW

        if (!isOpen(row, col)) {
            // System.out.println("opening row:" + row + " col: " + col);
            int current = coordToValue(row, col);
            if(row == 1) union(current, top);
            if(row == grid.length) union(current, bot);

            ArrayList<Integer> open = getAdjacentOpen(row, col);

            int i = row - 1;
            int j = col - 1;
            openSites += 1;
            grid[i][j] = 1;
            for (int site : open) {
                union(site, current);
            }

        }

    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        // REMBMER: row and col ARE NOT INDEX VALUES! add 1 to each to access proper arr coord
        // return true if site is over 0
        int i = row - 1;
        int j = col - 1;
        return grid[i][j] > 0;
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?

        // REMBMER: row and col ARE NOT INDEX VALUES! add 1 to each to access proper arr coord
        // returns true if UF site is connected to top
        int site = coordToValue(row, col);
        return connected(top, site);
    }

    public int numberOfOpenSites() {
        // number of open sites

        // update this whenever a site is opened
        // return whatever that variable is
        return openSites;
    }

    public boolean percolates() {
        // does the system percolate?

        // this will incorporate the UF class
        // Two possible approaches:
        // represent the whole grid as one UF class
        // or
        // represent each row as one UF class
        // either way, top and bottom of each UF class will point to
        // "virtual" top and bottom sites, which point to every site on top and
        // bottom

        return connected(top, bot);
    }

    public static void main(String[] args) {
        // test client (optional)
        Percolation test = new Percolation(6);
        int val = test.coordToValue(6,0);
        System.out.println(val);
    }

    public int coordToValue(int row, int col) {
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