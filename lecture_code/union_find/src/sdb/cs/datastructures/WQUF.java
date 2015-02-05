/*************************************************************************
 *  
 *  Weighted Quick Union Find
 *  Union: O(log n)
 *  Find : O(log n)
 *
 *************************************************************************/
package sdb.cs.datastructures;

public class WQUF {
	private int[] id;
	private int[] sz;
	
	public WQUF(int N) {
		id = new int[N];
		sz = new int[N];
		
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}
	
	public int find(int p) {
		while (p != id[p]) {
			p = id[p];
		}
		return p;
	}
	
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	public void union(int p, int q) {
		int pid = find(p);
		int qid = find(q);
		if (pid == qid) return;
		if (sz[pid] > sz[qid]) {
			id[qid] = pid;
			sz[pid] += sz[qid];
		} else {
			id[pid] = qid;
			sz[qid] += sz[pid];
		}
	}

}
