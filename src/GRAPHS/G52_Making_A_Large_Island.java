package GRAPHS;

public class G52_Making_A_Large_Island {
    /*
 * ============================================================
 * PROBLEM: Making A Large Island (LC 827)
 * ============================================================
 * INTUITION:
 * Given a binary grid, flip exactly ONE 0 to 1.
 * Find the largest island possible after this flip.
 *
 * TWO-PASS APPROACH:
 * Pass 1 → Build all existing islands using DSU.
 *           Track max island size from existing 1s.
 * Pass 2 → For each 0 cell, simulate flipping it to 1.
 *           Sum sizes of all UNIQUE neighboring islands + 1.
 *           Track max across all flips.
 *
 * EDGE CASES HANDLED:
 * → All 1s  : Pass 2 never runs. max_island from Pass 1 = answer.
 * → [[1]]   : max_island updated inside Pass 1 = 1. ✅
 * → All 0s  : Pass 1 never updates max_island (stays 0).
 *             Every flip in Pass 2 gives 0+1 = 1. ✅
 *
 * COMPLEXITY:
 * Time  → O(N² · α(N²)) ≈ O(N²)
 * Space → O(N²) for DSU arrays
 *         O(1)  for int[4] neighbor dedup (replaces O(k) HashSet)
 * ============================================================
 */

/*
 * ============================================================
 * DSU — Disjoint Set Union (Grid Version)
 * ============================================================
 * All cells known upfront. Only LAND cells are meaningful.
 *
 * ACTIVATION SENTINEL — size[node] != 0:
 * Every node starts with size[i] = 0.
 * When a land cell is activated via mark_land(), size[i] = 1.
 * is_land() checks size[node] != 0 — zero means water, nonzero means land.
 * This is intentional: size[] already tracks component presence.
 * Adding a separate boolean[] active would be a third array
 * solving a problem that doesn't exist.
 *
 * OPTIMIZATIONS:
 * 1. Union by Size  → smaller tree attaches under larger.
 *                     Keeps height bounded at O(log N).
 * 2. Path Halving   → each node points to grandparent on find.
 *                     Flattens tree in a single pass.
 *                     Same O(α(N)) as full compression,
 *                     but only ONE traversal needed (not two).
 * ============================================================
 */
class DisjointSetUnion {
    int[] parent;
    int[] size;

    /*
     * Initialize all nodes as isolated, inactive components.
     * parent[i] = i  → each node is its own root.
     * size[i]   = 0  → all cells start as water (inactive).
     *                   size becomes 1 only when mark_land() is called.
     */
    DisjointSetUnion(int nodes) {
        parent = new int[nodes];
        size = new int[nodes];
        for (int i = 0; i < nodes; i++) {
            parent[i] = i;
            // size[i] stays 0 — water by default
        }
    }

    /*
     * FIND ROOT — Iterative with Path Halving
     * ----------------------------------------------------------
     * Walks up parent pointers to find the root (representative).
     *
     * PATH HALVING — point to grandparent, not root directly:
     * We don't know where root is while walking up.
     * Grandparent = furthest safe jump in a single step.
     *
     * Before:  6 → 5 → 4 → 3 → 2 → 1 → 0(root)
     * After:   6 → 4 (skipped 5)
     *          4 → 2 (skipped 3)
     *          2 → 0 (skipped 1)
     *
     * WHY NOT FULL PATH COMPRESSION?
     * Full compression needs two passes (find root, then rewire all).
     * Path halving achieves same O(α(N)) in a single pass.
     * After a few calls → tree is nearly flat either way.
     *
     * WHY ITERATIVE?
     * Recursive find_root() risks StackOverflow on large grids.
     * Iterative is always safe regardless of tree depth.
     */
    public int find_root(int node) {
        while (node != parent[node]) {
            parent[node] = parent[parent[node]]; // skip to grandparent
            node = parent[node];
        }
        return node;
    }

    /*
     * UNION BY SIZE — Merge two components
     * ----------------------------------------------------------
     * Attaches smaller component under larger component.
     * Keeps tree height bounded at O(log N).
     *
     * WHY SIZE AND NOT RANK?
     * Rank = approximate height, never updated after path compression.
     *        Becomes stale and inaccurate over time.
     * Size = exact node count, updated on every union.
     *        Always accurate → better real-world performance.
     *
     * Returns true  → two DIFFERENT components were merged.
     * Returns false → already in same component, no-op.
     */
    public boolean UnionbySize(int u, int v) {
        int root_u = find_root(u);
        int root_v = find_root(v);

        if (root_u == root_v) return false; // same component, skip

        if (size[root_u] > size[root_v]) {
            parent[root_v] = root_u;
            size[root_u] += size[root_v];
        } else {
            parent[root_u] = root_v;
            size[root_v] += size[root_u];
        }
        return true;
    }

    /*
     * MARK LAND — Activate a cell as land.
     * Sets size[node] = 1 so is_land() returns true.
     * Also correctly initializes component size for union operations.
     */
    public void mark_land(int node) {
        size[node] = 1;
    }

    /*
     * GET SIZE — Component size containing this node.
     * Always queries size at ROOT — non-root nodes don't track this.
     */
    public int get_size(int node) {
        return size[find_root(node)];
    }

    /*
     * IS LAND — Check if cell is active land.
     * size[node] != 0 means mark_land() was called on this node
     * OR it absorbed other land cells via union.
     * Water cells always remain at size = 0.
     *
     * WHY NOT A SEPARATE boolean[] active?
     * size[] already encodes presence — 0 = water, nonzero = land.
     * A third array would add memory and an extra activation step
     * with no correctness benefit. One concern, one array.
     */
    public boolean is_land(int node) {
        return size[node] != 0;
    }
}


class Solution {

    /*
     * DIRECTIONS — Four cardinal sides only (no diagonals).
     * Static final → one shared copy, never modified.
     *
     * WHY 2D AND NOT 1D OFFSETS?
     * 1D offsets (int[] = {-cols, cols, -1, 1}) avoid multiplication
     * but cause row wrap-around bugs: end of row 0 + 1 = start of row 1.
     * Needs extra boundary checks → messier than 2D.
     * Modern JIT compilers optimise the multiplication anyway.
     */
    private static final int[][] DIRECTIONS = {
        {0,  1},  // right
        {0, -1},  // left
        {1,  0},  // down
        {-1, 0}   // up
    };

    public int largestIsland(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        DisjointSetUnion dsu = new DisjointSetUnion(rows * cols);
        int max_island = 0;

        /*
         * PASS 1 — BUILD DSU FROM EXISTING LAND CELLS
         * ----------------------------------------------------------
         * For each land cell (grid[r][c] == 1):
         * → mark it as land in DSU (size = 1)
         * → union with already-active neighbors
         * → update max_island with current component size
         *
         * WHY MARK BEFORE UNION?
         * UnionbySize calls find_root() → size must be set first
         * so is_land() returns true for the current cell.
         * Marking first → union → correct order.
         *
         * WHY UPDATE max_island INSIDE THIS LOOP?
         * Handles edge case: all-1s grid (no 0s exist to flip).
         * Pass 2 never runs → max_island from here is the answer.
         *
         * 2D → 1D MAPPING: cell = r * cols + c
         * Unique index for every (r,c) in [0, rows*cols-1].
         * Example 3×3: (0,0)=0, (0,1)=1, (1,0)=3, (2,2)=8
         */
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    int cell = r * cols + c;
                    dsu.mark_land(cell);

                    for (int[] dir : DIRECTIONS) {
                        int x = r + dir[0];
                        int y = c + dir[1];

                        if (x >= 0 && x < rows && y >= 0 && y < cols) {
                            int adjCell = x * cols + y;
                            if (dsu.is_land(adjCell)) {
                                dsu.UnionbySize(cell, adjCell);
                            }
                        }
                    }

                    // update after unions — component may have grown
                    max_island = Math.max(max_island, dsu.get_size(cell));
                }
            }
        }

        /*
         * PASS 2 — SIMULATE FLIPPING EACH 0 TO 1
         * ----------------------------------------------------------
         * For each water cell (grid[r][c] == 0):
         * → check all 4 neighbors
         * → sum sizes of UNIQUE neighboring island components
         * → +1 for the flipped cell itself (potential_size starts at 1)
         * → update max_island
         *
         * WHY int[4] INSTEAD OF HASHSET?
         * A 0 cell has at most 4 neighbors — bounded, never more.
         * HashSet per 0-cell means:
         * → Integer boxing (int → Integer object) per insertion
         * → internal array + linked list allocation (~48 bytes/instance)
         * → up to 250,000 allocations on a 500×500 grid
         * → all GC'd immediately after each cell → GC pressure
         *
         * int[4] eliminates all of that:
         * → primitive ints, zero boxing
         * → hoisted OUTSIDE the loop → single allocation, reused
         * → max 4 linear comparisons for dedup → constant time
         *
         * HOW int[4] WORKS — flat growing list, not direction-indexed:
         * neighborRoots[0..rootCount-1] = roots seen so far.
         * Before adding a new root → scan list → skip if already present.
         * Since max size = 4, linear scan = O(1) always.
         *
         * Example (no double-counting):
         * Grid: 1 1
         *       0 1
         * (0,0) and (0,1) → same component, root = 0, size = 3
         * Flipping (1,0):
         *   RIGHT (1,1) → root=0 → not seen → add, size += 3
         *   UP    (0,0) → root=0 → SEEN → skip
         *   result = 3 + 1 = 4 ✅
         *
         * WHY CHECK grid[nr][nc] == 1 INSTEAD OF dsu.is_land()?
         * Grid is unchanged during Pass 2.
         * Checking grid directly avoids an extra DSU array lookup.
         * Both are correct — grid check is simpler here.
         *
         * WHY find_root() RESULT CACHED IN root?
         * Without caching → find_root() called twice per neighbor:
         * once for dedup check, once for neighborRoots insertion.
         * Caching in local variable → single call per neighbor.
         */

        // Hoisted outside loop — single allocation, reused per 0-cell
        int[] neighborRoots = new int[4];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) continue; // only process water cells

                int potential_size = 1; // +1 for the flipped cell itself
                int rootCount = 0;

                for (int[] dir : DIRECTIONS) {
                    int x = r + dir[0];
                    int y = c + dir[1];

                    if (x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] == 1) {
                        int root = dsu.find_root(x * cols + y); // cache — used twice below

                        // Linear scan for duplicate root — max 4 iterations, always O(1)
                        boolean alreadySeen = false;
                        for (int i = 0; i < rootCount; i++) {
                            if (neighborRoots[i] == root) {
                                alreadySeen = true;
                                break;
                            }
                        }

                        if (!alreadySeen) {
                            neighborRoots[rootCount++] = root;
                            potential_size += dsu.get_size(root);
                        }
                    }
                }

                max_island = Math.max(max_island, potential_size);
            }
        }

        return max_island;
    }
}
}
