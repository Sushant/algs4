package sdb.cs.algo;

public class Percolation {
	private WeightedQuickUnionUF uf;
	private int[][] siteStates;
	private int gridBounds;
	private boolean percolates;
	private int virtualTopSite;
	private int virtualBottomSite;
	
	public Percolation(int N) {
		if (N <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
		percolates = false;
		siteStates = new int[N][N];
		gridBounds = N;
		virtualTopSite = 0;
		virtualBottomSite = N * N - 1;
		
		uf = new WeightedQuickUnionUF(N * N);
		initSiteStates();
		connectTopRowSites();
		connectBottomRowSites();
	}
	
	/**
     * 1 = blocked
     * 0 = open
     * Initialize all sites to be blocked.
     */
	private void initSiteStates() {
		for (int i = 0; i < gridBounds; i++) {
			for (int j = 0; j < gridBounds; j++) {
				siteStates[i][j] = 1;
			}
		}
	}
	
	private void connectTopRowSites() {
		for (int col = 0; col < gridBounds; col++) {
			uf.union(virtualTopSite, col);
		}
	}
	
	private void connectBottomRowSites() {
		int start = gridBounds * (gridBounds - 1);
		for (int col = start; col < start + gridBounds; col++) {
			uf.union(virtualBottomSite, col);
		}
	}

    /**
     * Opens a site if it isn't already, also connects
     * the newly opened site to its neighboring open sites.
     * Will perform at most 4 union operations, hence
     * time complexity is O(1).
     */
	public void open(int i, int j) {
		if (i < 1 || i > gridBounds || j < 1 || j > gridBounds) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		if (isOpen(i, j)) return;
		siteStates[i - 1][j - 1] = 0;
		connectNewlyOpenedSite(i - 1, j - 1);
	}
	
	/**
     * Connects the current site to its neighbors if they are open.
     * If as a result of connecting the sites, we notice that the
     * filled site(s) are connected to the bottom site(s), we can say
     * that the system percolates.
     */
	private void connectNewlyOpenedSite(int i, int j) {
	  for (int di = -1; di <= 1; di++) {
		  for (int dj = -1; dj <= 1; dj++) {
			  if (isNeighboringSite(i, j, di, dj)) {
				  int row = i + di;
				  int col = j + dj;
				  if (isOpen(row + 1, col + 1)) {
					  if (isFull(i + 1, j + 1) && siteCanPercolate(row + 1, col + 1) 
							  || isFull(row + 1, col + 1) && siteCanPercolate(i + 1, j + 1)) {
						  percolates = true;
					  }
					  uf.union(row * gridBounds + col, i * gridBounds + j);
				  }
			  }
		  }
	  }
	}
	
	
	private boolean isNeighboringSite(int i, int j, int di, int dj) {
		if (i + di >= 0 && i + di < gridBounds)
			if (j + dj >= 0 && j + dj < gridBounds)
				if ((di * di) + (dj * dj) == 1)
					return true;
		return false;
	}
	
	/**
     * Returns state of the site, O(1)
     */
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > gridBounds || j < 1 || j > gridBounds) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		return siteStates[i - 1][j - 1] == 0;
	}
	
	/**
     * Performs at most 1 connected operation, O(1).
     */
	public boolean isFull(int i, int j) {
		if (isOpen(i, j)) {
			i -= i;
			j -= j;
			int site = i * gridBounds + j;
			return uf.connected(virtualTopSite, site);
		}
		return false;
	}
	
	/**
     * Similar to isFull, but checks if its connected to bottom sites.
     */
	private boolean siteCanPercolate(int i, int j) {
		if (isOpen(i, j)) {
			i -= i;
			j -= j;
			int site = i * gridBounds + j;
			return uf.connected(virtualBottomSite, site);
		}
		return false;
	}
	
	/**
     * Percolation is determined at the time of connecting newly opened
     * sites. Thus, we only need to return the boolean value stored, O(1).
     */
	public boolean percolates() {
		return percolates;
	}
	
	public static void main(String [] args) {
		int N = 20;
		Percolation p = new Percolation(N);
		int openCount = 0;
		while(true) {
			int random = StdRandom.uniform(N * N);
			int i = (random / N) + 1;
			int j = (random % N) + 1;
			if (!p.isOpen(i, j)) {
				p.open(i, j);
				openCount += 1;
			}
			if (p.percolates())
				break;
		}
		System.out.println(openCount);
	}
}
