package GRAPHS;

import java.util.ArrayList;
import java.util.List;

public class G51_Number_of_Islands_II {
    /*
 * ============================================================
 * PROBLEM: Number of Islands 2 (GFG / LC 305)
 * ============================================================
 * INTUITION:
 * We are given an empty grid initially. Land cells are added
 * one by one via operators. After each addition, we need to
 * report the current number of islands.
 *
 * KEY INSIGHT — WHY DSU?
 * Every time we add a land cell:
 * → It starts as its own island (+1)
 * → If it touches an existing island → they merge (-1 per merge)
 * DSU handles this merge tracking naturally and efficiently.
 *
 * SIGNAL TO USE DSU HERE:
 * "merge islands that share a side" = classic union operation
 * "count components after each merge" = DSU's core strength
 *
 * THE INVARIANT:
 * islands = islands + 1 (new cell added)
 *         - number of successful merges with neighbors
 * Every successful union = two islands becoming one = islands--
 *
 * DUPLICATE CELL HANDLING (the critical guard):
 * If the same cell is added twice:
 * → Second time: is_present() returns true → skip Islands++
 * → mark_presence() must NOT be called again on duplicates
 * → Calling it again resets parent[node]=node, corrupting the
 *   DSU tree (node detaches from its merged component)
 * → Guard: only mark + increment if cell is NOT already present
 *
 * 2D → 1D MAPPING:
 * DSU works on integer indexes, not (row,col) pairs.
 * cell = row * cols + col
 * Maps every (row,col) to a unique integer 0 to rows*cols-1
 * This lets us use a flat array for parent[] and size[]
 *
 * COMPLEXITY:
 * Time  → O(Q * α(N)) ≈ O(Q)  — Q operators, α nearly O(1)
 *          α(N) = inverse Ackermann, never exceeds 4 in practice
 * Space → O(rows * cols)       — DSU arrays
 *          O(Q)                — answer list
 * ============================================================
 */


/*
 * ============================================================
 * DSU (Disjoint Set Union) — Modified For Sparse Grid
 * ============================================================
 * KEY MODIFICATION vs standard DSU:
 * Standard DSU initializes ALL nodes as active (parent[i]=i).
 * Here, cells start as INACTIVE (parent[i]=-1) because the
 * grid is empty initially — land cells are added one by one.
 *
 * So we need two extra methods:
 * mark_presence() → activates a cell when land is added
 * is_present()    → checks if a cell is active land
 *
 * WHY -1 AS SENTINEL?
 * Valid node indexes are 0 to rows*cols-1 — all non-negative.
 * So -1 is safely outside the valid range → perfect sentinel.
 * No valid parent pointer will ever be -1 after activation.
 *
 * OPTIMIZATIONS:
 * 1. Union by Size  → attach smaller tree under larger
 *                   → keeps tree shallow → faster find_root
 * 2. Path Halving   → during find_root, each node skips one
 *                   → level and points to grandparent instead
 *                   → flattens tree in single pass (no 2nd pass
 *                   → needed unlike full path compression)
 * ============================================================
 */
class DisjointSetUnion {
    int[] parent; // parent[i] = parent of cell i. -1 means inactive (water)
    int[] size;   // size[i]   = size of component rooted at i

    /*
     * Initialize all cells as INACTIVE (water).
     * parent[i] = -1 means this cell has not been added as land yet.
     * size[i] = 1 so that when a cell IS activated, its size is ready.
     */
    DisjointSetUnion(int nodes) {
        parent = new int[nodes];
        size = new int[nodes];

        for (int i = 0; i < nodes; i++) {
            parent[i] = -1; // -1 = inactive/water, not yet added as land
            size[i] = 1;    // default size ready for when cell gets activated
        }
    }

    /*
     * MARK PRESENCE — Activate a cell as land
     * ----------------------------------------------------------
     * Sets parent[node] = node, making it its own root.
     * This is called ONLY when a cell is added for the FIRST time.
     * Calling this on an already active cell would corrupt the DSU
     * tree by detaching the node from its merged component.
     * The guard in numOfIslands() ensures this never happens.
     */
    public void mark_presence(int node) {
        parent[node] = node; // node is now active, starts as its own root
    }

    /*
     * IS PRESENT — Check if a cell is active land
     * ----------------------------------------------------------
     * Returns true if cell has been added as land (parent != -1)
     * Returns false if cell is still water (parent == -1)
     * Used to:
     * 1. Guard against duplicate additions
     * 2. Check neighbors before attempting union
     */
    public boolean is_present(int node) {
        return parent[node] != -1;
    }

    /*
     * FIND ROOT — Iterative with Path Halving
     * ----------------------------------------------------------
     * Walks up parent pointers to find the root (representative)
     * of this cell's island component.
     *
     * PATH HALVING (parent[node] = parent[parent[node]]):
     * Each node skips one level and points to its grandparent.
     * Flattens the tree in a SINGLE pass — no second pass needed.
     * Future find_root calls on same path will be faster.
     *
     * WHY ITERATIVE?
     * Recursive version risks StackOverflow for large grids.
     * Iterative is always safe regardless of tree depth.
     */
    public int find_root(int node) {
        while (node != parent[node]) {
            parent[node] = parent[parent[node]]; // path halving — skip to grandparent
            node = parent[node];                 // move up towards root
        }
        return node; // parent[node]==node means this IS the root
    }

    /*
     * UNION BY SIZE — Merge two island components
     * ----------------------------------------------------------
     * Merges the islands containing cells u and v.
     * Always attaches smaller component under larger component.
     * Keeps tree height bounded → find_root stays fast.
     *
     * RETURN VALUE:
     * true  → successfully merged two DIFFERENT islands
     * false → u and v already in the same island (no merge)
     *
     * WHY RETURN VALUE MATTERS:
     * Caller uses true/false to decide whether to decrement
     * island count. Only a real merge reduces island count.
     */
    public boolean UnionbySize(int u, int v) {
        int root_u = find_root(u);
        int root_v = find_root(v);

        if (root_u == root_v) return false; // same island, no merge needed

        // attach smaller tree under larger tree
        if (size[root_u] > size[root_v]) {
            parent[root_v] = root_u;
            size[root_u] += size[root_v];
        } else {
            parent[root_u] = root_v;
            size[root_v] += size[root_u];
        }

        return true; // merge successful, two islands became one
    }
}


class Solution {
    public List<Integer> numOfIslands(int rows, int cols, int[][] operators) {

        List<Integer> ans = new ArrayList<>();
        if (operators == null || operators.length == 0) return ans;

        DisjointSetUnion dsu = new DisjointSetUnion(rows * cols);
        int islands = 0;

        /*
         * DIRECTIONS — Four cardinal sides
         * ----------------------------------------------------------
         * Islands are connected via shared SIDES only (not diagonals).
         * These four directions cover: LEFT, RIGHT, UP, DOWN.
         * Static final because this never changes — it's a constant.
         */
        int[][] directions = {
            {0, -1},  // left
            {0,  1},  // right
            {-1, 0},  // up
            {1,  0}   // down
        };

        for (int[] operator : operators) {

            int row = operator[0];
            int col = operator[1];

            /*
             * 2D → 1D CELL INDEX MAPPING:
             * DSU operates on flat integer indexes.
             * (row, col) → row * cols + col gives a unique index
             * for every cell in the grid, ranging 0 to rows*cols-1.
             *
             * Example: 3x3 grid
             * (0,0)=0  (0,1)=1  (0,2)=2
             * (1,0)=3  (1,1)=4  (1,2)=5
             * (2,0)=6  (2,1)=7  (2,2)=8
             */
            int cell = row * cols + col;

            /*
             * DUPLICATE GUARD — The critical check:
             * ----------------------------------------------------------
             * If cell is already active land → skip everything.
             * DO NOT increment islands (not a new island).
             * DO NOT call mark_presence() again — this would reset
             * parent[cell]=cell, detaching it from its merged component
             * and corrupting the entire DSU tree structure.
             *
             * Only if cell is NEW:
             * → mark it as active land
             * → increment island count (new isolated island)
             */
            if (!dsu.is_present(cell)) {
                dsu.mark_presence(cell); // activate cell BEFORE union checks
                islands++;               // new cell = new island initially
            }

            /*
             * NEIGHBOR CHECK — Merge with adjacent islands:
             * ----------------------------------------------------------
             * For each of the 4 directions:
             * 1. Compute neighbor coordinates (x, y)
             * 2. Bounds check → must be within grid
             * 3. Presence check → neighbor must be active land
             * 4. Union → if merge succeeds (different islands) → islands--
             *
             * WHY mark_presence BEFORE this loop?
             * union() calls find_root() on the current cell.
             * find_root() needs parent[cell] != -1 to work correctly.
             * If we mark after, find_root() would follow -1 → crash.
             *
             * WHY islands-- only on successful union?
             * Two cells already in same island → union returns false
             * → they were already counted as one island → no change.
             * Only a NEW merge actually reduces the island count.
             */
            for (int[] side : directions) {
                int x = row + side[0];
                int y = col + side[1];
                int adjCell = x * cols + y;

                // bounds check + presence check before attempting union
                if (x >= 0 && x < rows && y >= 0 && y < cols && dsu.is_present(adjCell)) {
                    // everytime a merge happens then the islands gets reduced..
                    if (dsu.UnionbySize(cell, adjCell)) islands--;
                }
            }

            // snapshot current island count after this operator
            ans.add(islands);
        }

        return ans;
    }
}
}
