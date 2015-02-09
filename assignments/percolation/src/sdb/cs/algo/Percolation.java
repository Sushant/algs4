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
		// Remove later
		/*for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(uf.find(i * N + j));
			}
			System.out.println("\n");
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(siteStates[i][j]);
			}
			System.out.println("\n");
		}*/
	}
	
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

	public void open(int i, int j) {
		if (i < 1 || i > gridBounds || j < 1 || j > gridBounds) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		if (isOpen(i, j)) return;
		siteStates[i - 1][j - 1] = 0;
		connectNewlyOpenedSite(i - 1, j - 1);
	}
	
	private void connectNewlyOpenedSite(int i, int j) {
	  for (int di = -1; di <= 1; di++) {
		  for (int dj = -1; dj <= 1; dj++) {
			  int row = i + di;
			  int col = j + dj;
			  if (isNeighboringSite(i, j, di, dj)) {
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
	
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > gridBounds || j < 1 || j > gridBounds) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		return siteStates[i - 1][j - 1] == 0;
	}
	
	public boolean isFull(int i, int j) {
		if (isOpen(i, j)) {
			i -= i;
			j -= j;
			int site = i * gridBounds + j;
			return uf.connected(virtualTopSite, site);
		}
		return false;
	}
	
	private boolean siteCanPercolate(int i, int j) {
		if (isOpen(i, j)) {
			i -= i;
			j -= j;
			int site = i * gridBounds + j;
			return uf.connected(virtualBottomSite, site);
		}
		return false;
	}
	
	public boolean percolates() {
		if (percolates) {
			/*for (int i = 0; i < gridBounds; i++) {
				for (int j = 0; j < gridBounds; j++) {
					System.out.print(siteStates[i][j]);
				}
				System.out.println("\n");
			}*/
			for (int i = 0; i < gridBounds; i++) {
				for (int j = 0; j < gridBounds; j++) {
					System.out.format("%03d ", uf.find(i * gridBounds + j));
				}
				System.out.println("\n");
			}
		}
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
