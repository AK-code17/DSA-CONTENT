package GRAPHS;

import java.util.*;

public class G54_SCC {
    /*
 * ============================================================
 * PROBLEM: Kosaraju's Algorithm — Strongly Connected Components
 * ============================================================
 * INTUITION — WHAT IS AN SCC?
 * A Strongly Connected Component (SCC) is a group of nodes
 * where EVERY node can reach EVERY other node in the group.
 *
 * Example:
 * 1 → 2 → 3 → 1   ← SCC: {1,2,3} (all mutually reachable)
 * 3 → 4            ← SCC: {4}     (4 cannot reach anyone)
 *
 * WHY NOT NAIVE APPROACH?
 * Naive: from every node run DFS, check mutual reachability
 * → O(V²) time → too slow for large graphs
 *
 * ============================================================
 * KOSARAJU'S KEY INSIGHT:
 * ============================================================
 * In a directed graph with multiple SCCs:
 *
 * Original:  SCC1 → SCC2 → SCC3
 * If we DFS from SCC1 → bleeds into SCC2, SCC3 → can't isolate
 *
 * Reversed:  SCC1 ← SCC2 ← SCC3
 * If we DFS from SCC1 → CANNOT cross to SCC2 (arrows reversed)
 * → stays contained within SCC1 only ✅
 *
 * WHY DOES REVERSAL ISOLATE SCCs?
 * Inside an SCC: edges form cycles → reversing keeps them cycles
 * → internal connectivity preserved ✅
 * Between SCCs: one-way edges → reversing flips direction
 * → DFS can no longer cross SCC boundaries ✅
 *
 * BUT HOW DO WE KNOW WHERE TO START DFS ON REVERSED GRAPH?
 * We must start from the SOURCE SCC (no incoming edges from outside)
 * → in reversed graph, source SCC has no OUTGOING edges
 * → DFS from it stays contained ✅
 *
 * HOW TO FIND SOURCE SCC?
 * Node that finishes LAST in DFS on original graph
 * = node in source SCC (can reach the most nodes)
 * → record finish order → process in reverse finish order
 *
 * ============================================================
 * THREE STEPS OF KOSARAJU:
 * ============================================================
 * STEP 1 — DFS on original graph
 *          Record nodes in a stack by finish time
 *          (last finished = top of stack = source SCC node)
 *
 * STEP 2 — Reverse all edges
 *
 * STEP 3 — DFS on reversed graph in reverse finish order
 *          Each DFS call from unvisited node = exactly one SCC
 *
 * COMPLEXITY:
 * Time  → O(V + E) — two DFS passes + edge reversal
 * Space → O(V + E) — adjacency lists + stack + visited array
 * ============================================================
 */

    /*
     * DFS PASS 1 — Record finish order on original graph
     * ----------------------------------------------------------
     * Visits all reachable nodes from 'node'.
     * Pushes node onto stack AFTER all its neighbors finish.
     * This ensures: deeper nodes (finish earlier) = bottom of stack
     *               source SCC nodes (finish last) = top of stack
     *
     * WHY PUSH AFTER NEIGHBORS?
     * We want finish order — a node "finishes" only when
     * ALL nodes reachable from it have been visited.
     * Pushing after neighbors guarantees this ordering.
     *
     * WHY THIS GIVES US SOURCE SCC AT TOP:
     * Source SCC has paths TO other SCCs.
     * DFS from source visits other SCCs first → they finish first.
     * Source SCC nodes finish LAST → sit at top of stack.
     * Reversing graph + starting from top = correct isolation. ✅
     *
     * WHY ITERATIVE AND NOT RECURSIVE?
     * Recursive DFS risks StackOverflow for large graphs.
     * V can be up to 10^5 → recursion depth up to 10^5 → crash.
     * Iterative with explicit stack → always safe.
     *
     * ITERATIVE FINISH ORDER TRICK:
     * To simulate recursive post-order (push after neighbors),
     * we use a "visited but not finished" state:
     * → first time popped → mark visited, re-push as "finishing"
     *   push all unvisited neighbors
     * → second time popped (neighbors done) → push to finish stack
     * This correctly simulates recursive DFS finish order.
     */
    private void dfs1(int start, boolean[] vis,
                      List<List<Integer>> adj, Deque<Integer> finishStack) {

        Deque<int[]> stack = new ArrayDeque<>();
        // int[]{node, state} — state 0=first visit, 1=finishing
        stack.push(new int[]{start, 0});

        while (!stack.isEmpty()) {
            int[] curr = stack.peek();
            int node  = curr[0];
            int state = curr[1];

            if (state == 0) {
                // first visit — mark visited, prepare to finish
                if (vis[node]) {
                    stack.pop(); // already visited by another path
                    continue;
                }
                vis[node] = true;
                curr[1] = 1; // mark as "finishing" on next pop

                // push all unvisited neighbors
                for (int neighbor : adj.get(node)) {
                    if (!vis[neighbor]) {
                        stack.push(new int[]{neighbor, 0});
                    }
                }
            } else {
                // state == 1 → all neighbors done → node finished
                stack.pop();
                finishStack.push(node); // push to finish order stack
            }
        }
    }

    /*
     * DFS PASS 2 — Mark all nodes in one SCC on reversed graph
     * ----------------------------------------------------------
     * Simple DFS that marks all reachable nodes as visited.
     * No finish order tracking needed here.
     *
     * WHY SIMPLE DFS IS ENOUGH HERE?
     * Reversed graph isolates SCCs completely.
     * Any node reachable from start = same SCC as start.
     * No risk of bleeding into other SCCs.
     * Just mark visited → count one SCC per call. ✅
     *
     * WHY ITERATIVE HERE TOO?
     * Same StackOverflow risk as DFS1.
     * Iterative is always safer for large inputs.
     */
    private void dfs2(int start, boolean[] vis,
                      List<List<Integer>> revAdj) {

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (vis[node]) continue; // post-pop safety check
            vis[node] = true;

            for (int neighbor : revAdj.get(node)) {
                if (!vis[neighbor]) stack.push(neighbor);
            }
        }
    }

    public int kosaraju(int V, int[][] edges) {

        /*
         * STEP 1 — BUILD ORIGINAL ADJACENCY LIST
         * ----------------------------------------------------------
         * Standard directed graph representation.
         * edge[0] → edge[1] direction preserved.
         */
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
        }

        /*
         * STEP 2 — DFS ON ORIGINAL GRAPH, RECORD FINISH ORDER
         * ----------------------------------------------------------
         * Run DFS from every unvisited node.
         * finishStack top = node that finished last = source SCC.
         *
         * WHY VISIT ALL NODES (not just node 0)?
         * Graph may be disconnected — some nodes unreachable from 0.
         * Must ensure every node gets a finish time.
         */
        boolean[] vis = new boolean[V];
        Deque<Integer> finishStack = new ArrayDeque<>();

        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                dfs1(i, vis, adj, finishStack);
            }
        }

        /*
         * STEP 3 — BUILD REVERSED ADJACENCY LIST
         * ----------------------------------------------------------
         * Reverse all edges: edge[0] → edge[1] becomes edge[1] → edge[0]
         *
         * WHY REVERSAL ISOLATES SCCs:
         * Original: SCC1 → SCC2 (one-way crossing edge)
         * Reversed: SCC1 ← SCC2 (arrow flipped)
         * DFS from SCC1 node on reversed graph:
         * → tries to follow reversed edges
         * → crossing edge now points INTO SCC1, not out
         * → DFS stays within SCC1 ✅
         *
         * Internal SCC edges (cycles) reverse but remain cycles:
         * 1→2→3→1 reversed = 1←2←3←1 = still a cycle ✅
         */
        List<List<Integer>> revAdj = new ArrayList<>();
        for (int i = 0; i < V; i++) revAdj.add(new ArrayList<>());
        for (int[] edge : edges) {
            revAdj.get(edge[1]).add(edge[0]); // reverse direction
        }

        /*
         * STEP 4 — RESET vis[] FOR SECOND DFS PASS
         * ----------------------------------------------------------
         * vis[] from Step 2 has all nodes marked.
         * Must reset to track fresh visited state for Step 5.
         * Without reset → Step 5 DFS visits nothing → SCCs = 0.
         */
        Arrays.fill(vis, false);

        /*
         * STEP 5 — DFS ON REVERSED GRAPH IN REVERSE FINISH ORDER
         * ----------------------------------------------------------
         * Process nodes from finishStack (top = last finished = source SCC).
         * Each DFS2 call from unvisited node = exactly ONE SCC.
         *
         * WHY PROCESS IN REVERSE FINISH ORDER?
         * Last finished = source SCC (reaches most nodes).
         * On reversed graph, source SCC is now isolated.
         * DFS from it visits ONLY its own SCC.
         * Next unvisited node = next SCC's representative.
         * Continue until all nodes processed = all SCCs found.
         *
         * WHY EACH DFS2 = EXACTLY ONE SCC?
         * Reversed graph breaks inter-SCC edges.
         * DFS from any SCC node can ONLY reach nodes in same SCC.
         * → each call finds exactly one complete SCC ✅
         */
        int SCCs = 0;

        while (!finishStack.isEmpty()) {
            int node = finishStack.pop();
            if (!vis[node]) {
                dfs2(node, vis, revAdj);
                SCCs++; // one complete SCC found
            }
        }

        return SCCs;
    }
}
