package sdb.cs.algo;

public class PercolationStats {
	private int n;
	private int t;
	private double[] openSitesFractions;
	
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
		n = N;
		t = T;
		openSitesFractions = new double[T];
		runSimulation();
	}
	
	
	public double mean() {
		double sum = 0;
		for (int i = 0; i < openSitesFractions.length; i++) {
			sum += openSitesFractions[i];
		}
		return sum / openSitesFractions.length;
	}
	
	/*public double stddev() {
		
	}
	public double confidenceLo() {
		
	}
	public double confidenceHi() {
		
	}*/
	
	private void runSimulation() {
		int runs = 0;
		double totalSites = (double) n * n;
		while (runs < t) {
			int openSites = openSitesForNewSimulation(n);
			openSitesFractions[runs] = openSites / totalSites;
			runs++;
		}
	}
	
	private int openSitesForNewSimulation(int N) {
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
		return openCount;
	}


	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		System.out.println("N: " + N + " T: " + T);
		PercolationStats ps = new PercolationStats(N, T);
		System.out.println(ps.mean());
	}
}
