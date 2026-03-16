package GRAPHS;

import java.util.Arrays;

public class G47_Kruskals_Algorithm {
    
    static int kruskalsMST(int V, int[][] edges) {
        // Sort all the edges by their weights..
        
        Arrays.sort(edges , (a,b) -> a[2]-b[2]);
        
        int MST_Sum = 0;
        DisjointSetUnion dsu = new DisjointSetUnion(V);
        
        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];
            
            
            // a single unionby rank/size is needed since ,we are checking
            // again for their root , so best is to return a boolean in union method
            // if they are conneted return a true ,else return a false..
            
            // if they are not connected add the wt , connect them..
            // we are ignoring the connected componenets , because
            // they are already connected then why to complicate
            // by again having an edge between them..
            
            if(!dsu.UnionbyRank(u,v)){
                MST_Sum += wt;
            }
        }
        
    
        return MST_Sum;
    }
}


class DisjointSetUnion {
    
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
    protected boolean UnionbyRank(int u, int v) {
        int root_u = find_root(u);
        int root_v = find_root(v);

        if (root_u == root_v) return true;

        if (rank[root_u] > rank[root_v]) {
            parent[root_v] = root_u;
        } else if (rank[root_u] < rank[root_v]) {
            parent[root_u] = root_v;
        } else {
            // If heights are equal, pick one as boss and increment its height
            parent[root_v] = root_u;
            rank[root_u]++;
        }
        
        return false;
    }

    /**
     * ⚖️ UNION BY SIZE
     * Logic: Attach the group with fewer nodes to the group with more nodes.
     */
    protected boolean UnionbySize(int u, int v) {
        int root_u = find_root(u);
        int root_v = find_root(v);

        if (root_u == root_v) return true;

        if (size[root_u] > size[root_v]) {
            parent[root_v] = root_u;
            size[root_u] += size[root_v];
        } else {
            parent[root_u] = root_v;
            size[root_v] += size[root_u];
        }
        
        return false;
    }

    protected boolean Is_Connected(int u, int v) {
        return find_root(u) == find_root(v);
    }

}
