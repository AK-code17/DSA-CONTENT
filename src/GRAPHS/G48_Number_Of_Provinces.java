package GRAPHS;

public class G48_Number_Of_Provinces {
    /*
 * ============================================================
 * PROBLEM: Number of Provinces (Leetcode 547)
 * ============================================================
 * INTUITION:
 * A "province" = a group of cities all connected to each other
 * directly or indirectly. We need to count how many such
 * completely isolated groups exist.
 *
 * KEY INSIGHT:
 * Think of each city as a node. A single connection between
 * two cities (or groups) merges them into ONE province.
 * So the question becomes: how many independent components
 * exist in this graph?
 *
 * WHY DSU?
 * DSU is built exactly for this — it tracks which nodes belong
 * to the same component and merges components efficiently.
 *
 * CORE FORMULA:
 * provinces = total_nodes - successful_merges
 *
 * Because every successful merge reduces the province count by 1.
 * Start with n provinces (everyone isolated), merge k times → n-k provinces.
 *
 * COMPLEXITY:
 * Time  → O(n²  * α(n)) ≈ O(n²)  — α(n) is inverse Ackermann, nearly O(1)
 * Space → O(n)  — parent[] and size[] arrays
 * ============================================================
 */


/*
 * ============================================================
 * DSU (Disjoint Set Union) — also called Union-Find
 * ============================================================
 * WHAT IT DOES:
 * Maintains a forest of trees where each tree = one component.
 * Every node points to its parent. The root of a tree = the
 * "representative" of that component.
 *
 * TWO OPTIMIZATIONS used here:
 * 1. Union by Size  → always attach smaller tree under larger tree
 *                  → keeps trees shallow → faster find_root
 * 2. Path Compression → while finding root, flatten the tree
 *                     → future find_root calls are even faster
 * ============================================================
 */
class DisjointSetUnion {
    int[] parent; // parent[i] = parent of node i. If parent[i]==i, i is a root.
    int[] size;   // size[i] = size of the component rooted at i (only meaningful at root)

    /*
     * INITIALIZATION:
     * Every node starts as its own parent (its own component).
     * Every component has size 1 (just itself).
     */
    protected DisjointSetUnion(int n) {
        parent = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i; // each node is its own root initially
            size[i] = 1;   // each component has just 1 node
        }
    }

    /*
     * FIND ROOT (Iterative — avoids Stack Overflow on large inputs)
     * ----------------------------------------------------------
     * Walks up the parent pointers until we reach the root.
     * Root is identified by: parent[node] == node
     *
     * PATH COMPRESSION (the line: parent[node] = parent[parent[node]])
     * → "Path halving" technique: every node on the path skips one
     *   level and points to its grandparent instead of its parent.
     * → This flattens the tree progressively, making future
     *   find_root calls faster.
     *
     * WHY ITERATIVE?
     * Recursive version risks StackOverflow for n = 20000+
     * since Java's default stack depth is limited (~500-1000 frames).
     */
    protected int find_root(int node) {
        while (node != parent[node]) {
            parent[node] = parent[parent[node]]; // path halving compression
            node = parent[node];                 // move up towards root
        }
        return node; // node == parent[node] means this IS the root
    }

    /*
     * UNION BY SIZE
     * ----------------------------------------------------------
     * Merges the components containing u and v.
     *
     * STRATEGY:
     * Always attach the smaller component's root under the
     * larger component's root. This keeps the tree height
     * bounded at O(log n), ensuring efficient find_root.
     *
     * RETURN VALUE:
     * true  → successfully merged two DIFFERENT components
     * false → u and v are ALREADY in the same component (no merge needed)
     *
     * WHY THIS RETURN VALUE MATTERS:
     * The caller uses this to count merges, which directly
     * gives us the number of provinces via: provinces = n - merges
     */
    protected boolean UnionbySize(int u, int v) {
        int root_u = find_root(u);
        int root_v = find_root(v);

        // already in the same component — no merge needed
        if (root_u == root_v) return false;

        // attach smaller tree under larger tree (union by size)
        if (size[root_u] > size[root_v]) {
            parent[root_v] = root_u;       // root_v now points to root_u
            size[root_u] += size[root_v];  // update size of the new root
        } else {
            parent[root_u] = root_v;       // root_u now points to root_v
            size[root_v] += size[root_u];  // update size of the new root
        }

        return true; // merge was successful
    }
}


class Solution {

    public int findCircleNum(int[][] isConnected) {

        int nodes = isConnected.length;
        DisjointSetUnion dsu = new DisjointSetUnion(nodes);
        int merged = 0; // counts successful merges (= province reductions)

        /*
         * TRIANGLE SCAN (c = r+1) — not full matrix scan
         * ----------------------------------------------------------
         * isConnected is symmetric: isConnected[r][c] == isConnected[c][r]
         * So scanning the full matrix processes every edge TWICE.
         *
         * By starting c at r+1, we only scan the upper triangle:
         * → each edge processed exactly once
         * → half the union calls
         * → diagonal (self-loops) naturally skipped since c > r always
         *
         * Visual:
         *   c: 0 1 2 3
         * r:
         * 0  [- X X X]   ← we scan only X's (upper triangle)
         * 1  [- - X X]
         * 2  [- - - X]
         * 3  [- - - -]
         */
        for (int r = 0; r < nodes; r++) {
            for (int c = r + 1; c < nodes; c++) {
                if (isConnected[r][c] == 1) {
                    // if merge succeeds, two components became one → province count drops
                    if (dsu.UnionbySize(r, c)) merged++;
                }
            }
        }

        /*
         * FINAL ANSWER:
         * Start with n isolated provinces.
         * Every successful merge reduces province count by exactly 1.
         * Therefore: provinces = nodes - merged
         *
         * Example:
         * 4 nodes, edges: (0-1), (2-3)
         * merged = 2 → provinces = 4 - 2 = 2 ✅
         *
         * 3 nodes, all connected: (0-1), (1-2)
         * merged = 2 → provinces = 3 - 2 = 1 ✅
         */
        return nodes - merged;
    }
}

}
