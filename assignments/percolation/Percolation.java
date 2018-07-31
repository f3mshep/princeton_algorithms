import edu.princeton.cs.algs4.UF;

public class Percolation {

    public int[][] grid;
    private int openSites;
    private UF uf;
    private int top;
    private int bot;

    public  Percolation(int n) {
        // create n-by-n grid, with all sites blocked

        // create an array of arrays to reprsent a grid.
        // elements will be integers 0,1 or 2
        // 0 indicates closed, 1 indicates open, 2 indicates full

        grid = new int[n][n];
        int len = (n * n) + 2;
        uf = new UF(len);
        top = len - 1;
        bot = len;
        // connect top and bottom rows to virtual top and bot sites
        for (int i = 0; i < grid[0].length; i++)
        {
            int site = coordToValue(1, i + 1);
            union(site, top);
            site = coordToValue(grid.length, i + 1);
            union(site, bot);
        }
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        // REMBMER: row and col ARE NOT INDEX VALUES! add 1 to each to access proper arr coord
        // if site is 0, make it 1
        row -= 1;
        col -= 1;
        openSites +=1;
        if(grid[row][col] == 0) grid[row][col] = 1;
    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        // REMBMER: row and col ARE NOT INDEX VALUES! add 1 to each to access proper arr coord
        // return true if site is over 0
        row -= 1;
        col -= 1;
        return grid[col][row] > 0;
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        // REMBMER: row and col ARE NOT INDEX VALUES! add 1 to each to access proper arr coord
        // returns true if UF site is connected to top
        int site = coordToValue(row, col);
        return uf.connected(top, site);
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
        return true;
    }

    public static void main(String[] args) {
        // test client (optional)
        Percolation test = new Percolation(3);
        System.out.println(test.grid);
    }

    private int coordToValue(int row, int col)
    {
        // return UF value
        // this is COORD not INDEX to value
        return col + (grid[0].length * (row - 1));
    }

    private void union(int p, int q)
    {
        uf.union(p, q);
    }
}