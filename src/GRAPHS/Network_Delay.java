package GRAPHS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Network_Delay {
    /*
 * ============================================================
 * PROBLEM: Network Delay Time (LC 743)
 * ============================================================
 * INTUITION:
 * A signal is sent from node k to all other nodes.
 * Each edge has a travel time (weight).
 * Find the minimum time for ALL nodes to receive the signal.
 *
 * KEY INSIGHT:
 * "Minimum time for ALL nodes to receive"
 * = maximum of shortest paths from k to every node.
 *
 * WHY MAXIMUM?
 * Each node receives signal at its shortest path time from k.
 * Multiple nodes can receive signal simultaneously.
 * The LAST node to receive = bottleneck = our answer.
 * Sum makes no sense — nodes receive in parallel, not sequence.
 *
 * WHY DIJKSTRA?
 * We need shortest path from single source (k) to all nodes.
 * Dijkstra = optimal for single-source shortest path
 * with non-negative weights. ✅
 *
 * WHY NOT BFS?
 * BFS finds shortest path by STEPS (unweighted).
 * Here edges have different weights → need cost-aware traversal.
 * Dijkstra's min heap ensures cost-optimal ordering.
 *
 * WHY NOT BELLMAN-FORD?
 * Bellman-Ford handles negative weights → O(VE).
 * All weights here are positive → Dijkstra is faster O((V+E)logV).
 * Bellman-Ford = overkill for positive weight graphs.
 *
 * WHY NOT FLOYD-WARSHALL?
 * Floyd-Warshall finds ALL-PAIRS shortest paths → O(V³).
 * We only need single-source (from k) → Dijkstra is faster.
 * Floyd-Warshall needed if signal sent from ALL nodes.
 *
 * NEGATIVE WEIGHTS — WHY DIJKSTRA FAILS:
 * Dijkstra's greedy assumption:
 * "first time a node is popped = optimal cost finalized"
 * Negative weights BREAK this:
 * → a later path with negative edge can give smaller cost
 *   to an already-finalized node → Dijkstra misses it
 * → wrong answer (not infinite loop — wrong answer)
 *
 * COMPLEXITY:
 * Time  → O((V+E) log V) — each edge relaxed once O(E log V)
 *                          each node popped once  O(V log V)
 * Space → O(V+E) — adjacency list + min_time array + PQ
 * ============================================================
 */
    /*
     * DIRECTIONS not needed here — weighted directed graph.
     * Neighbors defined by adjacency list, not grid directions.
     */

    public int networkDelayTime(int[][] times, int n, int k) {
        if (times == null || times.length == 0) return -1;

        /*
         * ADJACENCY LIST — 1-indexed weighted directed graph
         * ----------------------------------------------------------
         * Nodes are 1 to n → size n+1 to use direct indexing.
         * adj[0] intentionally unused (nodes start from 1).
         * Each entry: int[]{destination, weight}
         *
         * WHY List<int[]>[] OVER Map<Integer, List>?
         * Direct array access = O(1) vs O(1) average for HashMap.
         * No boxing overhead for integer keys.
         * Standard pattern for competitive programming. ✅
         */
        @SuppressWarnings("unchecked")
        List<int[]>[] adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) adj[i] = new ArrayList<>();

        for (int[] time : times) {
            adj[time[0]].add(new int[]{time[1], time[2]});
        }

        /*
         * MIN_TIME ARRAY — Shortest time from k to each node
         * ----------------------------------------------------------
         * min_time[i] = minimum time for signal to reach node i.
         * Initialized to MAX_VALUE → "not yet reached" sentinel.
         *
         * WHY INTEGER.MAX_VALUE?
         * Standard Dijkstra sentinel — any real path cost smaller.
         * Relaxation condition: new_cost < min_time[node]
         * → always true for first visit → correct initialization.
         *
         * WHY NOT 0?
         * 0 means "already optimally reached" → PQ never updates.
         */
        int[] min_time = new int[n + 1];
        Arrays.fill(min_time, Integer.MAX_VALUE);
        min_time[k] = 0; // source node costs 0 to reach itself

        /*
         * PRIORITY QUEUE — Min heap on travel time
         * ----------------------------------------------------------
         * Entry format: int[]{node, time}
         * Ordered by time → always process cheapest node first.
         *
         * WHY Integer.compare AND NOT SUBTRACTION?
         * Subtraction comparator (a[1]-b[1]) risks overflow:
         * Integer.MAX_VALUE - (-1) = overflow → wrong ordering
         * → wrong Dijkstra results → wrong answer.
         * Integer.compare = safe, handles all values correctly. ✅
         *
         * WHY MIN HEAP?
         * Dijkstra's greedy: process node with minimum cost first.
         * Guarantees first pop of any node = optimal cost.
         * Max heap would process expensive nodes first → wrong.
         */
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[1], b[1])
        );
        pq.offer(new int[]{k, 0});

        while (!pq.isEmpty()) {
            int[] curr   = pq.poll();
            int currNode = curr[0];
            int currTime = curr[1];

            /*
             * STALE ENTRY CHECK:
             * ----------------------------------------------------------
             * Same node can be pushed multiple times before popped.
             * (once per incoming edge that improves its cost)
             * If we've already found a better path to currNode:
             * → min_time[currNode] < currTime → this entry is stale
             * → processing it would re-explore neighbors with
             *   a suboptimal cost → wasted work
             *
             * WHY THIS MATTERS FOR DENSE GRAPHS:
             * Dense graph: E = V² → many stale entries possible
             * Without check → exponential blowup in processing
             * With check → each node effectively processed once
             *
             * CORRECTNESS GUARANTEE:
             * First pop of any node = optimal (min heap property)
             * → all subsequent pops of same node are stale
             * → stale check correctly identifies and skips them
             */
            if (currTime > min_time[currNode]) continue;

            /*
             * NEIGHBOR RELAXATION — Core Dijkstra step
             * ----------------------------------------------------------
             * For each neighbor, check if path through currNode
             * gives a better (smaller) travel time.
             *
             * RELAXATION CONDITION:
             * currTime + adjTime < min_time[adjNode]
             * → new path is strictly better → update + push
             *
             * HANDLES MULTIPLE EDGES BETWEEN SAME NODES:
             * If two edges u→v with weights 3 and 7 exist:
             * → first relaxation: min_time[v] = currTime + 3
             * → second relaxation: currTime + 7 > min_time[v]
             * → condition fails → ignored automatically ✅
             *
             * WHY PUSH ONLY ON IMPROVEMENT?
             * Avoids redundant PQ entries.
             * If no improvement → pushing wastes PQ space + time.
             * Only push when we've found a genuinely better path.
             */
            for (int[] neighbor : adj[currNode]) {
                int adjNode = neighbor[0];
                int adjTime = neighbor[1];

                int newTime = currTime + adjTime;
                if (newTime < min_time[adjNode]) {
                    min_time[adjNode] = newTime;
                    pq.offer(new int[]{adjNode, newTime});
                }
            }
        }

        /*
         * FINAL ANSWER — Maximum of all shortest paths
         * ----------------------------------------------------------
         * Each node receives signal at min_time[node].
         * All nodes receive in parallel — answer = last to receive.
         * Last to receive = maximum across all min_times.
         *
         * WHY NOT SUM?
         * Nodes receive simultaneously — not sequentially.
         * Sum would overcount — node 3 receiving at t=5 doesn't
         * add to node 2 receiving at t=3. They're independent.
         *
         * UNREACHABLE NODE CHECK:
         * If any min_time[node] = MAX_VALUE → signal never reached
         * → impossible for all nodes to receive → return -1.
         * Must check BEFORE taking max (MAX_VALUE would corrupt answer).
         */
        int minimum_time = 0;
        for (int node = 1; node <= n; node++) {
            if (min_time[node] == Integer.MAX_VALUE) return -1;
            minimum_time = Math.max(minimum_time, min_time[node]);
        }

        return minimum_time;
    }
}
