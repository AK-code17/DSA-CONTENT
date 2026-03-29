package GRAPHS;

import java.util.*;

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
}
