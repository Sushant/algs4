/*************************************************************************
 *  
 *  This is an eager, O(n^2) algorithm.
 *
 *************************************************************************/
package sdb.cs.datastructures;

public class UnionFind {
	private int[] id;
	
	public UnionFind(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++)
			id[i] = i;
	}
	
	public boolean connected(int p, int q) {
		return id[p] == id[q];
	}
	
	public int find(int p) {
		return id[p];
	}
	
	public void union(int p, int q) {
		int pid = id[p];
		int qid = id[q];
		for (int i = 0; i < id.length; i++) {
			if (id[i] == pid)
				id[i] = qid;
		}
	}
}
