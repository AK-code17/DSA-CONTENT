package GRAPHS;

public class G49_Connecting_the_graphs {
    /*
 * ============================================================
 * PROBLEM: Connecting The Graph (GFG Medium)
 * ============================================================
 * INTUITION:
 * Some edges are "extra" — they connect nodes already in the
 * same component (redundant). These extra edges can be REMOVED
 * and REUSED to connect isolated provinces together.
 *
 * KEY INSIGHT:
 * - Count provinces (isolated components) after all merges
 * - To connect x provinces we need exactly x-1 edges
 * - If total edges < n-1 → impossible upfront (early exit)
 * - Otherwise → answer is always provinces-1
 *
 * WHY WE DON'T NEED TO TRACK extra_edges:
 * The early exit (edge.length < n-1) already guarantees
 * enough edges exist. So impossibility is fully caught there.
 * After that, extra_edges >= provinces-1 is ALWAYS true —
 * making that check redundant.
 *
 * CORE FORMULA:
 * provinces = n - successful_merges
 * answer    = provinces - 1
 *
 * COMPLEXITY:
 * Time  → O(m * α(n)) ≈ O(m)  — beats GFG's expected O(m*n)
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
 * 1. Union by Size     → attach smaller tree under larger tree
 *                      → keeps trees shallow → faster find_root
 * 2. Path Compression  → while finding root, flatten the tree
 *                      → future find_root calls are even faster
 * ============================================================
 */
class DisjointSetUnion {
    int[] parent; // parent[i] = parent of node i. If parent[i]==i, i is a root.
    int[] size;   // size[i] = size of component rooted at i (meaningful only at root)

    /*
     * INITIALIZATION:
     * Every node starts as its own parent (its own component).
     * Every component has size 1 (just itself).
     * n nodes → n isolated provinces initially.
     */
    DisjointSetUnion(int n) {
        parent = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i; // each node is its own root initially
            size[i] = 1;   // each component has just 1 node
        }
    }

    /*
     * FIND ROOT (Iterative — avoids StackOverflow on large inputs)
     * ----------------------------------------------------------
     * Walks up parent pointers until root is found.
     * Root identified by: parent[node] == node
     *
     * PATH HALVING (the line: parent[node] = parent[parent[node]])
     * → Every node on the path skips one level, points to grandparent.
     * → Flattens the tree progressively → future calls faster.
     *
     * WHY ITERATIVE?
     * Recursive version risks StackOverflow for n = 20000+
     * since Java's default stack depth is limited (~500-1000 frames).
     */
    public int find_root(int node) {
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
     * Always attach smaller component's root under larger component's
     * root. Keeps tree height bounded at O(log n).
     *
     * RETURN VALUE:
     * true  → successfully merged two DIFFERENT components
     * false → u and v ALREADY in same component (redundant edge)
     */
    public boolean UnionbySize(int u, int v) {
        int root_u = find_root(u);
        int root_v = find_root(v);

        // already in same component — redundant edge, no merge needed
        if (root_u == root_v) return false;

        // attach smaller tree under larger tree (union by size)
        if (size[root_u] > size[root_v]) {
            parent[root_v] = root_u;      // root_v now points to root_u
            size[root_u] += size[root_v]; // update size of new root
        } else {
            parent[root_u] = root_v;      // root_u now points to root_v
            size[root_v] += size[root_u]; // update size of new root
        }

        return true; // merge successful, province count drops by 1
    }
}


class Solution {
    public int Solve(int n, int[][] edge) {

        /*
         * EARLY EXIT:
         * To connect n nodes, minimum n-1 edges are required.
         * If we don't even have that many edges, impossible upfront.
         * This also makes tracking extra_edges redundant — if we
         * pass this check, enough edges always exist to connect.
         */
        if (edge.length < n - 1) return -1;

        DisjointSetUnion dsu = new DisjointSetUnion(n);
        int merged = 0; // counts successful merges = province reductions

        /*
         * PROCESS ALL EDGES:
         * Every successful union reduces province count by 1.
         * Redundant edges (same component) are simply skipped —
         * we no longer need to count them since impossibility
         * is already handled by the early exit above.
         */
        for (int[] ed : edge) {
            if (dsu.UnionbySize(ed[0], ed[1])) merged++;
        }

        /*
         * PROVINCE COUNT:
         * Start with n isolated provinces.
         * Every successful merge reduces province count by exactly 1.
         * Therefore: provinces = n - merged
         *
         * Example 1: n=4, edges=[[0,1],[0,2],[1,2]]
         * merged=2 → provinces=2 → return 1 ✅
         *
         * Example 2: n=6, edges=[[0,1],[0,2],[0,3],[1,2],[1,3]]
         * merged=3 → provinces=3 → return 2 ✅
         */
        int provinces = n - merged;
        
        // minimum edges needed to connect x provinces = x-1
        return provinces - 1;
    }
}
}
