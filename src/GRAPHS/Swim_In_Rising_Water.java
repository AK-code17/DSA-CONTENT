package GRAPHS;

import java.util.*;
// BOTH VERSIONS OF DIKSTRA AND BS on Answer Variants are documented...
public class Swim_In_Rising_Water {
    /*
 * ============================================================
 * PROBLEM: Swim In Rising Water (LC 778)
 * ============================================================
 * INTUITION:
 * Grid cells have elevation values. Water rises uniformly.
 * At time T, we can swim to any cell with elevation <= T.
 * Find the minimum T to reach (n-1,n-1) from (0,0).
 *
 * KEY INSIGHT — WHY "MINIMIZE THE MAXIMUM"?
 * We don't minimize total cost (sum of path).
 * We minimize the MAXIMUM elevation on the path.
 * The answer = minimum possible maximum elevation
 * across all paths from (0,0) to (n-1,n-1).
 *
 * WHY DIJKSTRA AND NOT STANDARD BFS?
 * Standard BFS → finds shortest path by STEPS
 * Dijkstra     → finds shortest path by COST
 *
 * Here cost = maximum elevation seen so far on path.
 * We want to minimize this maximum.
 * Dijkstra's greedy property:
 * → always processes cell with minimum "max so far"
 * → guarantees first time we reach (n-1,n-1)
 *   is via the optimal (minimum max) path ✅
 *
 * WHY NOT STANDARD SHORTEST PATH DIJKSTRA?
 * Standard: dist[v] = dist[u] + edge_weight
 * Here:     dist[v] = max(dist[u], grid[v])
 * Same greedy structure — different cost function.
 * PriorityQueue still works — just update formula.
 *
 * WHY visited[] WORKS HERE (KEY INSIGHT):
 * Cost function = max(path_cost, grid[cell])
 * This is MONOTONICALLY NON-DECREASING:
 * → costs only stay same or increase as we move
 * → Dijkstra's guarantee holds perfectly:
 *   first time a cell is popped = optimal cost
 * → mark visited on first pop → never process again
 * → no dist[][] array needed → cleaner code
 *
 * WHY NOT dist[][] APPROACH?
 * dist[][] needed when:
 * → costs can decrease via different paths
 * → need to update and re-push better costs
 * Here costs only increase (max function) →
 * first pop is always optimal → visited[] sufficient
 * visited[] = simpler, same space complexity O(N²)
 *
 * THREE VARIANTS FOR THIS PROBLEM:
 * 1. Dijkstra + visited[] → O(N² log N)  ← this file
 * 2. BS + BFS             → O(N² log N)  ← same complexity
 * 3. DSU                  → O(N² α(N))   ← fastest variant
 *
 * WHY DIJKSTRA OVER BS FOR OA?
 * BS needs two components (search shell + feasibility checker)
 * → more lines of code → more bug-prone under pressure
 * Dijkstra = single clean pass → safer OA submission
 *
 * COMPLEXITY:
 * Time  → O(N² log N) — N² cells, each pushed to PQ at most once
 * Space → O(N²)       — visited[][] + PQ storage
 * ============================================================
 */
    /*
     * DIRECTIONS — Four cardinal sides
     * ----------------------------------------------------------
     * Movement allowed in 4 directions only (not diagonal).
     * Static final → shared constant, never modified.
     *
     * WHY 2D AND NOT 1D OFFSETS?
     * 1D offsets cause row wrap-around bugs for left/right:
     * end of row 0 + 1 = start of row 1 → wrong neighbor.
     * 2D = cleaner, safer, no wrap-around risk. No contest.
     */
    private static final int[][] DIRECTIONS = {
        {0,  1},  // right
        {1,  0},  // down
        {0, -1},  // left
        {-1, 0}   // up
    };

    public int swimInWater(int[][] grid) {
        int n = grid.length;

        /*
         * VISITED ARRAY — Marks optimally processed cells
         * ----------------------------------------------------------
         * visited[r][c] = true means:
         * → cell (r,c) has been popped from PQ
         * → its optimal cost is finalized
         * → no need to process it again
         *
         * WHY THIS WORKS (the core guarantee):
         * Dijkstra pops cells in order of INCREASING cost.
         * Since cost = max(path, elevation) only increases:
         * → first pop of any cell = minimum possible cost
         * → marking visited on first pop is always correct
         * → any future pop of same cell = stale, skip it
         *
         * WHY NOT dist[][] INSTEAD?
         * dist[][] tracks best known cost per cell.
         * Needed when better paths can be found later.
         * Here → costs only increase → first pop = best path.
         * visited[] is simpler and equally correct. ✅
         */
        boolean[][] visited = new boolean[n][n];

        /*
         * PRIORITY QUEUE — Min heap on current path cost
         * ----------------------------------------------------------
         * Entry format: int[]{cost, row, col}
         * cost = maximum elevation seen on path to this cell
         *
         * WHY MIN HEAP ON COST?
         * Dijkstra's greedy: always process cheapest cell first.
         * "Cheapest" = path with lowest maximum elevation so far.
         * This guarantees first time we pop (n-1,n-1)
         * = optimal answer (minimum max elevation path). ✅
         *
         * WHY NOT PLAIN BFS QUEUE?
         * BFS processes by STEPS not by COST.
         * A cell 1 step away might have high elevation,
         * while a cell 3 steps away has low elevation.
         * BFS would wrongly prefer the 1-step high-cost cell.
         * PQ ensures cost-optimal ordering always.
         *
         * START STATE:
         * Push (0,0) with cost = grid[0][0].
         * We must wait at least grid[0][0] time just to
         * enter the starting cell → minimum initial cost.
         */
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{grid[0][0], 0, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int cost = curr[0];
            int r    = curr[1];
            int c    = curr[2];

            /*
             * VISITED CHECK — Skip stale entries
             * ----------------------------------------------------------
             * Same cell can be pushed multiple times before
             * being popped (via different paths).
             * Once popped and marked visited → all future
             * pops of same cell are stale → skip immediately.
             *
             * WHY POST-POP CHECK AND NOT PRE-PUSH CHECK?
             * Pre-push: check visited before pushing neighbor
             * → prevents duplicate pushes → leaner PQ
             * Post-pop: check visited after popping
             * → safety net for correctness
             *
             * BOTH TOGETHER = optimal:
             * Pre-push  → keeps PQ lean (fewer entries)
             * Post-pop  → guarantees no double processing
             *
             * Here we use BOTH:
             * Pre-push:  !visited[x][y] check before pushing
             * Post-pop:  if(visited[r][c]) continue here
             */
            if (visited[r][c]) continue;
            visited[r][c] = true;

            /*
             * EARLY TERMINATION:
             * ----------------------------------------------------------
             * First time we pop (n-1,n-1) = optimal path found.
             * Dijkstra's guarantee: cells popped in order of
             * increasing cost → first pop of destination
             * = minimum possible maximum elevation.
             * Return immediately — no need to explore further.
             */
            if (r == n - 1 && c == n - 1) return cost;

            /*
             * NEIGHBOR RELAXATION — The key formula difference
             * ----------------------------------------------------------
             * Standard Dijkstra: new_cost = cost + edge_weight
             * This problem:      new_cost = max(cost, grid[x][y])
             *
             * WHY max() AND NOT +?
             * We track the MAXIMUM elevation on the path.
             * The bottleneck = highest cell encountered so far.
             * Taking max() correctly updates this bottleneck.
             * Adding would give total elevation sum → wrong.
             *
             * Example:
             * Path so far max = 5, next cell elevation = 3
             * new_cost = max(5,3) = 5  ← bottleneck unchanged ✅
             * new_cost = 5+3 = 8       ← wrong, not what we want
             *
             * WHY PRE-PUSH visited CHECK?
             * Avoids pushing already-optimal cells back into PQ.
             * Keeps PQ size bounded → better performance.
             * Works correctly because:
             * once visited → optimal cost already found
             * → pushing again = wasted PQ entry
             */
            for (int[] dir : DIRECTIONS) {
                int x = r + dir[0];
                int y = c + dir[1];

                if (x >= 0 && x < n && y >= 0 && y < n && !visited[x][y]) {
                    // bottleneck cost = max of current path max and next cell elevation
                    int new_cost = Math.max(cost, grid[x][y]);
                    pq.offer(new int[]{new_cost, x, y});
                }
            }
        }

        /*
         * UNREACHABLE — Safety net for invalid inputs.
         * For valid inputs grid is always fully connected
         * → (n-1,n-1) always reachable → never hits this.
         */
        return -1;
    }


    // BINARY SEARCH ON ANSWER VARIANT...

    
// ## Bugs Fixed From Your Version
    // 1. high = len*len     → fixed to n*n-1 (off by one)
    // 2. low = grid[0][0]   → fixed to 0 (safer lower bound)
    // 3. lo <= hi pattern   → fixed to lo < hi (standard BS pattern)
    // 4. return min_time    → fixed to return lo directly
    // 5. Recursive DFS      → fixed to iterative (StackOverflow safe)
    // 6. vis instance var   → fixed to local, passed as parameter
    // 7. canReach signature → cleaned up, n passed instead of recomputing
    
    /*
 * ============================================================
 * PROBLEM: Swim In Rising Water (LC 778) — Binary Search Variant
 * ============================================================
 * INTUITION:
 * Grid cells have elevation values. Water rises uniformly.
 * At time T, we can swim to any cell with elevation <= T.
 * Find the minimum T to reach (n-1,n-1) from (0,0).
 *
 * WHY BINARY SEARCH ON ANSWER?
 * Instead of directly finding minimum T (optimization),
 * ask a simpler YES/NO question:
 * "Is it POSSIBLE to reach (n-1,n-1) if water level = T?"
 *
 * This works because the answer is MONOTONIC:
 * → if possible at T → possible at T+1, T+2... (more water)
 * → if impossible at T → impossible at T-1, T-2... (less water)
 *
 * Monotonic YES/NO = Binary Search on answer ✅
 * Find the FIRST T where answer flips from NO to YES.
 *
 * THE TWO COMPONENTS:
 * 1. Binary Search shell → narrows down T in O(log N²)
 * 2. Feasibility checker → DFS checks reachability in O(N²)
 *    (can we reach (n-1,n-1) using only cells with elevation <= T?)
 *
 * WHY DFS FOR FEASIBILITY CHECK AND NOT BFS?
 * We only care about reachability (YES/NO) — not shortest path.
 * DFS and BFS both answer reachability correctly.
 * DFS chosen for simplicity — early return on finding destination.
 *
 * WHY ITERATIVE DFS AND NOT RECURSIVE?
 * Recursive DFS risks StackOverflow for large grids:
 * n=50 → 2500 cells → recursion depth up to 2500 → unsafe.
 * Iterative DFS uses explicit stack → always safe.
 *
 * BINARY SEARCH BOUNDS:
 * lo = 0           → minimum possible T (grid[0][0] could be 0)
 * hi = n*n - 1     → maximum value in grid (permutation of 0..n²-1)
 *
 * WHY lo=0 AND NOT grid[0][0]?
 * grid[0][0] seems like a safe lower bound BUT:
 * → answer must also be >= grid[n-1][n-1]
 * → computing max(grid[0][0], grid[n-1][n-1]) adds complexity
 * → lo=0 is always correct, costs at most 1-2 extra BS iterations
 * → simpler and safer under OA pressure
 *
 * WHY hi=n*n-1 AND NOT n*n?
 * Grid contains permutation of 0 to n²-1.
 * Maximum possible value = n²-1, never n².
 * hi=n*n overshoots by 1 → wastes one BS iteration.
 *
 * BINARY SEARCH PATTERN — lo < hi (NOT lo <= hi):
 * We search for FIRST T where canReach = true.
 * lo < hi naturally converges to boundary point.
 * When lo == hi → that IS the answer → stop.
 * lo <= hi risks one extra unnecessary iteration.
 * Return lo directly → no min_time tracking needed.
 *
 * WHY vis[][] RESET EACH ITERATION?
 * Each BS iteration asks a FRESH question with different T.
 * Fresh question = fresh visited state.
 * Reset is unavoidable — fundamental to correctness.
 * Created ONCE outside loop → reset inside loop → O(N²) per reset
 * → absorbed into O(N²) DFS cost anyway.
 *
 * WHY vis[][] AS LOCAL VARIABLE (NOT INSTANCE VARIABLE)?
 * Instance variable persists between method calls → design smell.
 * Local variable scoped to swimInWater → clean, self-contained.
 * Pass to canReach as parameter → explicit dependency.
 *
 * COMPLEXITY:
 * Time  → O(N² log N) — O(log N²) BS iterations × O(N²) DFS each
 * Space → O(N²)       — vis[][] + iterative DFS stack
 * ============================================================
 */

class Solution {

    /*
     * DIRECTIONS — Four cardinal sides
     * ----------------------------------------------------------
     * Movement allowed in 4 directions only (not diagonal).
     * Static final → shared constant, never modified.
     *
     * WHY 2D AND NOT 1D OFFSETS?
     * 1D offsets cause row wrap-around bugs for left/right:
     * end of row 0 + 1 = start of row 1 → wrong neighbor.
     * 2D = cleaner, safer, no wrap-around risk.
     */
    // // private static final int[][] DIRECTIONS = {
    //     {0,  1},  // right
    //     {1,  0},  // down
    //     {0, -1},  // left
    //     {-1, 0}   // up
    };

    /*
     * CAN REACH — Iterative DFS feasibility checker
     * ----------------------------------------------------------
     * Checks if (n-1,n-1) is reachable from (0,0) using only
     * cells with elevation <= max_elevation.
     *
     * WHY ITERATIVE AND NOT RECURSIVE?
     * Recursive DFS: for n=50 → up to 2500 recursive calls
     * → StackOverflow risk for large inputs.
     * Iterative DFS uses explicit ArrayDeque stack → always safe.
     *
     * WHY ArrayDeque AND NOT Stack?
     * Java's Stack is synchronized → thread-safe overhead.
     * ArrayDeque is faster for single-threaded use → preferred.
     *
     * PRE-PUSH + POST-POP visited CHECKS:
     * Pre-push:  !vis[x][y] before pushing → keeps stack lean
     *            prevents same cell pushed N times → avoids MLE
     * Post-pop:  if(vis[r][c]) after popping → safety net
     *            handles race condition where same cell
     *            pushed by multiple neighbors before visited
     * Both together = bulletproof iterative DFS.
     *
     * EARLY RETURN ON FINDING DESTINATION:
     * Return true immediately when (n-1,n-1) reached.
     * No need to explore rest of component → saves time.
     * This is why DFS chosen over BFS — early return is natural.
     */
    private boolean canReach(int[][] grid, int max_elevation,
                              boolean[][] vis, int n) {

        // start cell itself must be reachable
        if (grid[0][0] > max_elevation) return false;

        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0, 0});

        while (!stack.isEmpty()) {
            int[] curr = stack.pop();
            int r = curr[0];
            int c = curr[1];

            // post-pop safety check — may have been pushed multiple times
            if (vis[r][c]) continue;
            vis[r][c] = true;

            // destination reached → answer is YES for this T
            if (r == n - 1 && c == n - 1) return true;

            /*
             * NEIGHBOR EXPANSION:
             * ----------------------------------------------------------
             * Only expand to cells with elevation <= max_elevation.
             * This is the core constraint — water level T.
             * Cells above water level are unreachable at time T.
             *
             * WHY CHECK elevation BEFORE PUSHING?
             * Avoids pushing unreachable cells onto stack.
             * Keeps stack lean → better performance.
             * Combined with !vis check → each cell pushed once. ✅
             */
            for (int[] dir : DIRECTIONS) {
                int x = r + dir[0];
                int y = c + dir[1];

                if (x >= 0 && x < n && y >= 0 && y < n
                        && !vis[x][y]
                        && grid[x][y] <= max_elevation) {
                    stack.push(new int[]{x, y});
                }
            }
        }

        return false; // (n-1,n-1) not reachable at this water level
    }

    public int swimInWaterBS(int[][] grid) {
        int n = grid.length;

        /*
         * vis[][] — Created once, reset each BS iteration
         * ----------------------------------------------------------
         * WHY CREATE OUTSIDE LOOP?
         * Creating inside loop → O(N²) allocation per BS iteration
         * → unnecessary GC pressure.
         * Creating once outside → single allocation → reset inside.
         *
         * WHY RESET IS UNAVOIDABLE:
         * Each BS iteration asks a FRESH question with different T.
         * Visited state from previous T is irrelevant.
         * Reset cost = O(N²) per iteration → absorbed into DFS cost.
         */
        boolean[][] vis = new boolean[n][n];

        /*
         * BINARY SEARCH BOUNDS:
         * ----------------------------------------------------------
         * lo = 0       → always safe lower bound
         * hi = n*n-1   → maximum grid value (permutation of 0..n²-1)
         *
         * WHY lo=0 AND NOT grid[0][0]?
         * grid[0][0] seems tighter but answer also depends on
         * grid[n-1][n-1] and intermediate cells.
         * lo=0 is always correct → simpler → safer under pressure.
         *
         * WHY hi=n*n-1 AND NOT n*n?
         * Grid is permutation of 0 to n²-1.
         * n*n never appears in grid → wastes one BS iteration.
         * n*n-1 = exact maximum → tighter bound ✅
         */
        int lo = 0;
        int hi = n * n - 1;

        /*
         * BINARY SEARCH — Find first T where canReach = true
         * ----------------------------------------------------------
         * WHY lo < hi AND NOT lo <= hi?
         * lo < hi converges naturally to boundary point.
         * When lo == hi → answer found → loop exits.
         * lo <= hi risks processing lo==hi case unnecessarily.
         *
         * WHY hi = mid AND NOT hi = mid-1 WHEN canReach IS TRUE?
         * mid itself might be the answer — don't exclude it.
         * hi = mid keeps mid in search space.
         * hi = mid-1 would wrongly exclude the answer.
         *
         * WHY lo = mid+1 WHEN canReach IS FALSE?
         * mid definitely not the answer → safely exclude it.
         * lo = mid+1 correct here.
         *
         * RETURN lo DIRECTLY:
         * When loop exits → lo == hi → boundary point found.
         * lo = first T where canReach flips to true = answer.
         * No need for separate min_time tracking.
         */
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            // reset vis for fresh DFS at this water level
            for (boolean[] row : vis) Arrays.fill(row, false);

            if (canReach(grid, mid, vis, n)) {
                hi = mid;      // mid works → try smaller T
            } else {
                lo = mid + 1;  // mid doesn't work → need larger T
            }
        }

        // lo == hi → minimum T where destination is reachable
        return lo;
    }
}

