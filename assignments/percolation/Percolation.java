public class Percolation {

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked

        // create an array of arrays to reprsent a grid.
        // elements will be integers 0,1 or 2
        // 0 indicates closed, 1 indicates open, 2 indicates full
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already

        // if site is 0, make it 1
    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?

        //return true if site is over 0
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?

        // return true if site == 2
    }

    public int numberOfOpenSites() {
        // number of open sites

        // update this whenever a site is opened
        // return whatever that variable is
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
    }

    public static void main(String[] args) {
        // test client (optional)
    }
}