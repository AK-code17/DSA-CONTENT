package GRAPHS;

import java.nio.file.Path;
import java.util.*;

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
 * → Second time: is_present() returns true → skip islands++
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
 * WHY -1 AS SENTINEL AND NOT A SEPARATE boolean[] visited?
 * ----------------------------------------------------------
 * Two obvious approaches:
 *
 * Option A: Separate boolean[] array
 * boolean[] isLand = new boolean[rows*cols];
 * → isLand[cell] = true when cell is added
 * → extra O(rows*cols) space
 * → two arrays to maintain (parent[] AND isLand[])
 * → two reads per presence check
 *
 * Option B: Sentinel value in parent[] (what we use)
 * parent[i] = -1  → inactive (water)
 * parent[i] = i   → active (land, own root)
 * parent[i] = x   → active (land, merged under x)
 *
 * WHY -1 IS SAFE AS SENTINEL:
 * Valid parent values = 0 to rows*cols-1 (all non-negative)
 * -1 is outside this range → never a valid parent pointer
 * → zero collision risk with real values
 *
 * WHY OPTION B IS BETTER:
 * → No extra boolean[] array → saves O(rows*cols) space
 * → Single array to maintain → simpler code
 * → Single read per presence check → faster
 * → parent[] already exists → reuse it for free
 *
 * THE TRADEOFF:
 * → Slightly less readable than explicit boolean[]
 * → Requires understanding the sentinel convention
 * → That's exactly why this comment exists
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
     *
     * WHY NOT boolean[] for tracking active cells?
     * → sentinel -1 in parent[] serves double duty:
     *   tracks activation AND acts as parent pointer
     * → saves O(rows*cols) extra space vs boolean[]
     * → single array read per presence check vs two
     */
    DisjointSetUnion(int nodes) {
        parent = new int[nodes];
        size = new int[nodes];

        for (int i = 0; i < nodes; i++) {
            parent[i] = -1; // sentinel: inactive until mark_presence() called
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
     * PATH HALVING — WHY GRANDPARENT SPECIFICALLY?
     * We want to flatten the tree but have two choices:
     *
     * Option A: Full Path Compression
     * → find the root first (one full pass up)
     * → then rewire EVERY node directly to root (second pass)
     * Before:  6 → 5 → 4 → 3 → 2 → 1 → 0(root)
     * After:   6 → 0, 5 → 0, 4 → 0 (all point to root)
     *
     * Option B: Path Halving (what we use)
     * → single pass up the tree
     * → each node skips ONE level → points to grandparent
     * Before:  6 → 5 → 4 → 3 → 2 → 1 → 0(root)
     * After:   6 → 4 (skipped 5)
     *          4 → 2 (skipped 3)
     *          2 → 0 (skipped 1)
     *
     * WHY GRANDPARENT AND NOT ROOT DIRECTLY?
     * Because we don't KNOW where root is yet!
     * We're still walking UP to find it.
     * Grandparent = the furthest we can safely jump
     * in a single step without knowing the root.
     *
     * WHY PATH HALVING OVER FULL COMPRESSION?
     * Full compression: 2 passes (find root, then rewire)
     * Path halving:     1 pass  (flatten as you walk up)
     * Same O(α(N)) complexity — path halving does it
     * in HALF the work. That's why we chose it.
     *
     * After a few find_root calls on same path:
     * → tree becomes nearly flat anyway
     * → future calls approach O(1)
     *
     * WHY ITERATIVE AND NOT RECURSIVE?
     * Recursive version risks StackOverflow for large grids.
     * Iterative is always safe regardless of tree depth.
     */
    public int find_root(int node) {
        while (node != parent[node]) {
            parent[node] = parent[parent[node]]; // jump to grandparent
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
     * WHY SIZE AND NOT RANK?
     * Two strategies to keep tree shallow:
     *
     * Option A: Union by Rank
     * → rank = approximate height of the tree
     * → always attach lower rank under higher rank
     * → rank only increases when two equal-rank trees merge
     * PROBLEM: after path compression, actual height changes
     * but rank is NEVER updated (stale approximation)
     * → rank becomes inaccurate over time
     *
     * Option B: Union by Size (what we use)
     * → size = actual number of nodes in component
     * → always attach smaller component under larger
     * → size is always accurate — updated on every union
     *
     * WHY SIZE KEEPS TREE SHALLOW:
     * When you attach small under large:
     * → small tree's depth increases by at most 1
     * → large tree's depth unchanged
     * → overall tree height grows at most O(log N)
     *
     * Visual:
     * Size 3 tree + Size 1 tree:
     *     A              A
     *    / \    →       /|\
     *   B   C          B C D  ← D under A, depth unchanged for A,B,C
     *
     * BOTTOM LINE:
     * Rank  = works but uses stale approximation
     * Size  = works and always accurate → we use size
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

        // attach smaller tree under larger tree (union by size)
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

    /*
     * DIRECTIONS — Four cardinal sides
     * ----------------------------------------------------------
     * Islands are connected via shared SIDES only (not diagonals).
     * These four directions cover: LEFT, RIGHT, UP, DOWN.
     *
     * WHY STATIC FINAL?
     * This array never changes — it's a constant.
     * static = one copy shared across all instances (saves memory)
     * final  = prevents accidental modification
     *
     * WHY 2D DIRECTIONS AND NOT 1D OFFSETS?
     * Alternative: int[] offsets = {-cols, cols, -1, 1}
     * → saves one multiplication per neighbor
     * → BUT left/right offsets cause wrap-around bugs:
     *   end of row 0 + 1 = start of row 1 (wrong union!)
     * → needs extra boundary checks → messier code
     * → modern JIT compilers optimise multiplication anyway
     * → real world performance difference = negligible
     *
     * 2D directions = cleaner, safer, easier to debug.
     * Readable code > micro-optimisation here.
     */
    private static final int[][] DIRECTIONS = {
        {0, -1},  // left
        {0,  1},  // right
        {-1, 0},  // up
        {1,  0}   // down
    };

    public List<Integer> numOfIslands(int rows, int cols, int[][] operators) {

        List<Integer> ans = new ArrayList<>();
        if (operators == null || operators.length == 0) return ans;

        DisjointSetUnion dsu = new DisjointSetUnion(rows * cols);
        int islands = 0;

        for (int[] operator : operators) {

            int row = operator[0];
            int col = operator[1];

            /*
             * 2D → 1D CELL INDEX MAPPING:
             * ----------------------------------------------------------
             * DSU operates on flat integer indexes.
             * (row, col) → row * cols + col gives a unique index
             * for every cell in the grid, ranging 0 to rows*cols-1.
             *
             * Example: 3x3 grid
             * (0,0)=0  (0,1)=1  (0,2)=2
             * (1,0)=3  (1,1)=4  (1,2)=5
             * (2,0)=6  (2,1)=7  (2,2)=8
             *
             * WHY THIS FORMULA?
             * Each row has exactly 'cols' cells.
             * Row r starts at index r*cols.
             * Column c adds c more → r*cols + c.
             * Every (row,col) pair maps to a UNIQUE index. ✅
             */
            int cell = row * cols + col;

            /*
             * DUPLICATE GUARD + NEIGHBOR CHECK
             * ----------------------------------------------------------
             * WHY CHECK is_present BEFORE everything?
             * If cell is already active land → skip ALL processing.
             * Two reasons:
             *
             * 1. Don't increment islands (not a new island)
             *
             * 2. Don't call mark_presence() again:
             *    → would reset parent[cell] = cell
             *    → detaches cell from its merged component
             *    → corrupts entire DSU tree structure
             *    → THIS WAS THE ORIGINAL BUG IN THIS CODE
             *
             * WHY NEIGHBOR LOOP INSIDE THIS GUARD?
             * Duplicate cells don't need neighbor checks either:
             * → cell is already merged with its neighbors
             * → running union() again = redundant work
             * → moving loop inside guard saves 4 neighbor checks
             *   per duplicate operator — real saving for
             *   duplicate-heavy inputs
             *
             * WHY mark_presence BEFORE neighbor loop?
             * union() calls find_root() on current cell.
             * find_root() needs parent[cell] != -1 to work.
             * If we mark after the loop → find_root crashes.
             * Order matters: mark first, then union.
             *
             * WHY islands-- only on successful union?
             * Two cells already in same island → union returns false
             * → already counted as one island → no change needed.
             * Only a NEW merge actually reduces island count.
             */
            if (!dsu.is_present(cell)) {
                dsu.mark_presence(cell); // activate BEFORE union calls
                islands++;               // new cell = new island initially

                for (int[] side : DIRECTIONS) {
                    int x = row + side[0];
                    int y = col + side[1];

                    // bounds check first — only compute adjCell if in bounds
                    // avoids unnecessary multiplication for out-of-bounds cells
                    if (x >= 0 && x < rows && y >= 0 && y < cols) {
                        int adjCell = x * cols + y;
                        if (dsu.is_present(adjCell)) {
                            // everytime a merge happens then the islands gets reduced..
                            if (dsu.UnionbySize(cell, adjCell)) islands--;
                        }
                    }
                }
            }

            // snapshot current island count after this operator
            ans.add(islands);
        }

        return ans;
    }
}
}
