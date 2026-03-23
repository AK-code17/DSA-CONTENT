package GRAPHS;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class G53_Most_Stones_Removed_with_Same_row_and_col {
    Hows' this version How's this for future documentation , does it explain each n every nuance and why behind the intuition and approach and we got the dfs approach :



/*

 * ============================================================

 * PROBLEM: Most Stones Removed With Same Row or Column (LC 947)

 * ============================================================

 * INTUITION — DERIVING THE FORMULA FROM SCRATCH:

 * At first glance this looks like a simulation problem.

 * Naive approach: greedily remove stones sharing row/col.

 *

 * WHY GREEDY SIMULATION FAILS:

 * Removing stone A might disconnect stone B from stone C,

 * even though B and C share a row. Order of removal matters

 * and greedy simulation misses future connections.

 *

 * THE KEY REALIZATION:

 * A stone can be removed if ANY other stone shares its row OR col.

 * As long as ONE stone in the connected group survives,

 * ALL others can be removed one by one via chain of connections.

 *

 * WHY "CONNECTED GROUP"?

 * Stone A shares row with Stone B → connected

 * Stone B shares col with Stone C → connected

 * Therefore A, B, C are all in same group (transitive!)

 * → remove C (shares col with B)

 * → remove B (shares row with A)

 * → A is the last stone, must remain

 * → 3 stones, 1 island, 2 removed ✅

 *

 * THE FORMULA:

 * Each connected island keeps exactly 1 stone (the last one).

 * All others can always be removed via chain of connections.

 * removed = total_stones - number_of_islands

 *

 * ============================================================

 * TWO DFS VARIANTS — WHY THIS ONE?

 * ============================================================

 *

 * VARIANT 1 — DFS O(N²):

 * → build adj list by comparing ALL stone pairs

 * → DFS on adj list

 * → TLE for N=10^5

 * → O(N²) time AND space (adj list storage)

 *

 * VARIANT 2 — DFS O(N) with rowMap/colMap (THIS FILE):

 * → group stones by row and col using HashMaps

 * → DFS jumps directly via maps — no adj list needed

 * → each stone visited exactly once via visited[] guard

 * → handles N=10^5 ✅

 *

 * WHY rowMap/colMap ELIMINATES O(N²):

 * O(N²) approach: "is stone i connected to stone j?"

 * → must compare every pair → N² comparisons

 *

 * O(N) approach: "which stones share row r?"

 * → just lookup rowMap.get(r) → instant answer

 * → build maps in O(N), lookup in O(1)

 * → no pairwise comparison needed at all

 *

 * WHY EACH ROW/COL TRAVERSED ONLY ONCE:

 * When DFS visits stone A in row 2:

 * → marks all stones in row 2 as visited

 * → when main loop reaches any of those stones

 *   → visited[] = true → SKIP entirely

 * → row 2's map entry never scanned again

 * → total work = O(N) across entire algorithm

 *

 * COMPLEXITY:

 * Time  → O(N) — build maps O(N) + DFS traversal O(N)

 * Space → O(N) — rowMap + colMap + visited[] + stack

 * ============================================================

 */



class Solution {



    public int removeStones(int[][] stones) {

        if (stones == null || stones.length == 0) return 0;



        int n = stones.length;



        /*

         * BUILD rowMap AND colMap — O(N)

         * ----------------------------------------------------------

         * rowMap: row index → list of stone indexes in that row

         * colMap: col index → list of stone indexes in that col

         *

         * WHY STONE INDEXES AND NOT COORDINATES AS VALUES?

         * We need to jump to neighboring STONES during DFS.

         * Storing stone indexes lets us directly mark visited[]

         * and access stone coordinates via stones[idx].

         *

         * WHY computeIfAbsent?

         * Cleanly handles first occurrence of a row/col.

         * If key doesn't exist → create new ArrayList → add index.

         * If key exists → just add index to existing list.

         * Single line replaces 3-line null check pattern.

         *

         * WHY THIS REPLACES O(N²) ADJ LIST:

         * O(N²): for each pair (i,j) → check if connected → add edge

         * O(N):  for each stone i → add to rowMap[r] and colMap[c]

         * → connection info encoded in maps, not explicit edges

         */

        Map<Integer, List<Integer>> rowMap = new HashMap<>();

        Map<Integer, List<Integer>> colMap = new HashMap<>();



        for (int i = 0; i < n; i++) {

            rowMap.computeIfAbsent(stones[i][0], k -> new ArrayList<>()).add(i);

            colMap.computeIfAbsent(stones[i][1], k -> new ArrayList<>()).add(i);

        }



        /*

         * COUNT ISLANDS VIA ITERATIVE DFS — O(N)

         * ----------------------------------------------------------

         * For each unvisited stone → new island → DFS explores

         * entire component via rowMap/colMap jumps.

         *

         * WHY ITERATIVE DFS AND NOT RECURSIVE?

         * Recursive DFS risks StackOverflow when all N stones

         * share a row → recursion depth = N → crash for N=10^5.

         * Iterative DFS uses explicit stack → always safe.

         *

         * WHY ArrayDeque AND NOT Stack?

         * Java's Stack class is synchronized → thread-safe overhead.

         * ArrayDeque is faster for single-threaded use → preferred.

         */

        boolean[] visited = new boolean[n];

        int islands = 0;



        for (int i = 0; i < n; i++) {

            if (!visited[i]) {

                islands++;



                /*

                 * ITERATIVE DFS — Explore entire connected component

                 * ----------------------------------------------------------

                 * HOW IT WORKS:

                 * Push start stone → pop → mark visited →

                 * push all unvisited neighbors from same row AND col

                 * Repeat until stack empty → component fully explored.

                 *

                 * WHY CHECK visited BEFORE PUSHING (pre-push guard)?

                 * Without pre-push guard:

                 * → 100 stones in same row → each pushes 99 others

                 * → stack holds O(N²) duplicate entries → MLE

                 * With pre-push guard:

                 * → each stone pushed at most once → stack stays O(N)

                 *

                 * WHY ALSO CHECK visited AFTER POPPING?

                 * Safety net — multiple paths can push same stone

                 * before pre-push check marks it.

                 * Double check (before push AND after pop) = bulletproof.

                 *

                 * PRE-PUSH + POST-POP TOGETHER:

                 * Pre-push  → keeps stack lean (memory efficient)

                 * Post-pop  → ensures correctness (no double processing)

                 * Both needed for truly optimal iterative DFS.

                 */

                Deque<Integer> stack = new ArrayDeque<>();

                stack.push(i);



                while (!stack.isEmpty()) {

                    int curr = stack.pop();

                    if (visited[curr]) continue; // post-pop safety check

                    visited[curr] = true;



                    /*

                     * JUMP VIA rowMap AND colMap:

                     * ----------------------------------------------------------

                     * Instead of scanning adj list (built in O(N²)),

                     * directly access all stones in same row/col via maps.

                     *

                     * WHY SAME ROW AND SAME COL BOTH NEEDED?

                     * Connectivity = shares row OR col.

                     * Must explore BOTH directions from each stone.

                     * Missing either → disconnected components → wrong count.

                     *

                     * WHY PRE-PUSH visited CHECK HERE SPECIFICALLY?

                     * rowMap.get(r) can have 100 entries.

                     * Without check → all 100 pushed even if 99 visited.

                     * With check → only unvisited ones pushed → lean stack.

                     */

                    int r = stones[curr][0];

                    int c = stones[curr][1];



                    // push all unvisited stones sharing same row

                    for (int neighbor : rowMap.get(r)) {

                        if (!visited[neighbor]) stack.push(neighbor);

                    }



                    // push all unvisited stones sharing same col

                    for (int neighbor : colMap.get(c)) {

                        if (!visited[neighbor]) stack.push(neighbor);

                    }

                }

            }

        }



        /*

         * FORMULA: removed = total - islands

         * ----------------------------------------------------------

         * Each island keeps exactly 1 stone (the last survivor).

         * All other stones removable via chain of shared row/col.

         * Therefore: removed = n - islands

         *

         * Verification:

         * 1 island of 5 stones → 4 removed ✅

         * 5 islands of 1 stone → 0 removed ✅ (isolated, can't remove)

         */

        return n - islands;

    }

}
}
