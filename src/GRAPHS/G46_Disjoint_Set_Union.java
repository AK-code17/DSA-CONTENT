package GRAPHS;

import java.util.*;

/**
 * 🛰️ INTUITION: 
 * Disjoint Set Union (DSU) is a data structure used to track "connected components" 
 * or "groups" in a graph. It answers two questions very quickly:
 * 1. Are these two nodes in the same group? (find)
 * 2. Can we merge these two groups into one? (union)
 *
 * 🏗️ APPROACH (The Two Pillars of Efficiency):
 * - Path Compression (Find): Flattening the tree structure every time we look for a root.
 * - Union by Rank/Size: Always attaching the smaller "team" to the leader of the bigger team.
 */
public class G46_Disjoint_Set_Union {
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter total_nodes");
        int nodes = sc.nextInt();
        DisjointSetUnion dsu = new DisjointSetUnion(nodes);
        
        // Example Operations: Building "islands" of nodes
        dsu.UnionbyRank(1, 2);
        dsu.UnionbyRank(2, 3); // Group A: {1, 2, 3}
        dsu.UnionbyRank(4, 5);
        dsu.UnionbyRank(6, 7);
        dsu.UnionbyRank(5, 6); // Group B: {4, 5, 6, 7}

        dsu.Is_Same_Group(3, 7); // Should be different

        dsu.UnionbyRank(3, 7);   // Bridging Group A and Group B

        dsu.Is_Same_Group(3, 7); // Should now be same
    }

    static class DisjointSetUnion {
        int[] parent; // parent[i] stores the immediate boss of node i
        int[] rank;   // rank[i] approximates the height of the tree rooted at i
        int[] size;   // size[i] stores the total number of nodes in the group rooted at i

        protected DisjointSetUnion(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            size = new int[n + 1];

            for (int i = 0; i <= n; i++) {
                parent[i] = i; // Every node is its own boss initially 👑
                rank[i] = 0;   // Height starts at 0
                size[i] = 1;   // Every group starts with 1 node
            }
        }

        /**
         * 🕵️‍♂️ FIND (with Path Compression)
         * Time Complexity: O(α(N)), basically constant time.
         */
        protected int find_root(int node) {
            if (node == parent[node]) return node;
            
            // Path Compression: Pointing the node directly to the ultimate root
            // while backtracking to make future searches instant. ⚡
            return parent[node] = find_root(parent[node]);
        }

        /**
         * 🤝 UNION BY RANK
         * Logic: Attach shorter tree under taller tree to keep the overall height minimal.
         */
        protected void UnionbyRank(int u, int v) {
            int root_u = find_root(u);
            int root_v = find_root(v);

            if (root_u == root_v) return;

            if (rank[root_u] > rank[root_v]) {
                parent[root_v] = root_u;
            } else if (rank[root_u] < rank[root_v]) {
                parent[root_u] = root_v;
            } else {
                // If heights are equal, pick one as boss and increment its height
                parent[root_v] = root_u;
                rank[root_u]++;
            }
        }

        /**
         * ⚖️ UNION BY SIZE
         * Logic: Attach the group with fewer nodes to the group with more nodes.
         */
        protected void UnionbySize(int u, int v) {
            int root_u = find_root(u);
            int root_v = find_root(v);

            if (root_u == root_v) return;

            if (size[root_u] > size[root_v]) {
                parent[root_v] = root_u;
                size[root_u] += size[root_v];
            } else {
                parent[root_u] = root_v;
                size[root_v] += size[root_u];
            }
        }

        protected void Is_Same_Group(int u, int v) {
            if (find_root(u) == find_root(v)) 
                System.out.println(u + " " + v + " Same group");
            else 
                System.out.println(u + " " + v + " Differ group");
        }
    }
}