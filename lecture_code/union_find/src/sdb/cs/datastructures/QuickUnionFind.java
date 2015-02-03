/*************************************************************************
 *  
 *  Union is O(n) because it includes cost of finding roots, find is O(n)
 *
 *************************************************************************/

package sdb.cs.datastructures;

public class QuickUnionFind {
	private int[] id;
	
	public QuickUnionFind(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
	}
	
	public int find(int p) {
		while (id[p] != p) {
			p = id[p];
		}
		return p;
	}
	
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	public void union(int p, int q) {
		int pid = id[p];
		int qid = id[q];
		id[pid] = qid;
	}
}
