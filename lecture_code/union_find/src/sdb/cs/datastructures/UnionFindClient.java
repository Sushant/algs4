package sdb.cs.datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UnionFindClient {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		
		try {
			int N = Integer.parseInt(br.readLine());
			// UnionFind uf = new UnionFind(N);
			// QuickUnionFind uf = new QuickUnionFind(N);
			// WQUF uf = new WQUF(N);
			WQUFPC uf = new WQUFPC(N);
			while ((input = br.readLine()) != null)
			{
				String[] numbers = input.split(" ");
				int p = Integer.parseInt(numbers[0]);
				int q = Integer.parseInt(numbers[1]);
				if (!uf.connected(p, q)) {
					uf.union(p, q);
					System.out.println(p + " " + q);
				} else {
					System.out.println(p + " and " + q + " are already connected");
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
