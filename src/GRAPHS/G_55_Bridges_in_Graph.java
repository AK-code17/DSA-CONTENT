package GRAPHS;

import java.util.ArrayList;
import java.util.List;

public class G_55_Bridges_in_Graph {
    /*
 * ============================================================
 * PROBLEM: Critical Connections in a Network (LC 1192)
 * ============================================================
 * INTUITION — WHAT IS A BRIDGE?
 * A bridge is an edge whose removal disconnects the graph.
 * In a network, bridges = critical connections = single points
 * of failure. Removing them splits the network into components.
 *
 * KEY INSIGHT — HOW TO DETECT A BRIDGE:
 * During DFS, every edge is either:
 * 1. TREE EDGE    → edge used to discover a new node
 * 2. BACK EDGE    → edge pointing to an ANCESTOR (already visited)
 *
 * A tree edge (u→v) is a BRIDGE if and only if:
 * → v cannot reach u or any ancestor of u via back edges
 * → meaning: the ONLY way to reach v from u is this edge
 * → removing it disconnects v from u's component
 *
 * HOW DO WE KNOW IF v CAN REACH AN ANCESTOR?
 * Track two values per node:
 * disc_time[node] = when this node was first discovered (DFS order)
 * low_time[node]  = minimum disc_time reachable from this node's
 *                   subtree via back edges
 *
 * BRIDGE CONDITION:
 * Tree edge (u→v) is a bridge if:
 * low_time[v] > disc_time[u]
 *
 * WHY THIS CONDITION WORKS:
 * low_time[v] > disc_time[u] means:
 * → v's subtree cannot reach u or any ancestor of u
 * → the edge u→v is the ONLY connection between them
 * → removing it disconnects v's subtree from u ✅
 *
 * low_time[v] <= disc_time[u] means:
 * → v's subtree has a back edge to u or an ancestor of u
 * → even without edge u→v, v can reach u via back edge
 * → not a bridge ✅
 *
 * VISUAL EXAMPLE:
 * 0 — 1 — 2 — 3
 *     |___|
 *
 * disc: 0=1, 1=2, 2=3, 3=4
 * low:  0=1, 1=2, 2=2, 3=4
 *
 * Edge 2→3: low[3]=4 > disc[2]=3 → BRIDGE ✅
 * Edge 1→2: low[2]=2 = disc[1]=2 → NOT bridge (cycle exists)
 *
 * WHY DFS AND NOT BFS?
 * Back edges only meaningful in DFS tree.
 * BFS doesn't create a clear tree/back edge distinction.
 * DFS naturally builds the traversal tree needed here.
 *
 * COMPLEXITY:
 * Time  → O(V+E) — single DFS pass
 * Space → O(V+E) — adj list + disc/low arrays + recursion stack
 * ============================================================
 */
    /*
     * DFS — Computes disc_time, low_time and finds bridges
     * ----------------------------------------------------------
     * PARAMETERS:
     * parent    → parent node in DFS tree (to avoid back-tracking)
     * node      → current node being processed
     * vis[]     → tracks visited nodes
     * disc_time → discovery time of each node (DFS order)
     * low_time  → minimum discovery time reachable from subtree
     * timer[]   → int[1] wrapper to pass timer by reference
     *             WHY int[] AND NOT int?
     *             Java passes primitives by value — changes inside
     *             dfs() won't reflect outside. int[]{1} acts as
     *             mutable reference → increments persist. ✅
     * adj       → adjacency list
     * bridges   → result list, populated during DFS
     *
     * WHY PASS timer AS int[1] AND NOT INSTANCE VARIABLE?
     * Instance variable persists between method calls on same object.
     * If criticalConnections() called twice → timer carries over
     * → wrong discovery times → wrong bridges.
     * int[1] scoped to each call → fresh timer every time. ✅
     *
     * PARENT CHECK — WHY SKIP adjNode == parent?
     * Graph is undirected → edge u-v appears as both u→v and v→u.
     * When processing u's neighbors, v→u would look like a back edge
     * to parent. This would incorrectly lower low_time[u]
     * and prevent detecting real bridges.
     * Skipping parent prevents this false back edge. ✅
     *
     * KNOWN LIMITATION — PARALLEL EDGES:
     * If two edges exist between same nodes (u-v, u-v):
     * parent check skips BOTH when processing second edge.
     * Fix: track parent EDGE INDEX instead of parent NODE.
     * For LC 1192: constraints guarantee no parallel edges → safe.
     *
     * WHY RECURSIVE DFS HERE?
     * Iterative post-order DFS (needed for low_time propagation)
     * requires complex state tracking — recursive is cleaner.
     * For LC 1192: n ≤ 10^5 → recursion depth bounded by graph
     * depth → acceptable for this problem's constraints.
     * For production code → iterative preferred.
     */
    private void dfs(int parent, int node, boolean[] vis,
                     int[] disc_time, int[] low_time, int[] timer,
                     List<Integer>[] adj, List<List<Integer>> bridges) {

        vis[node] = true;
        disc_time[node] = timer[0];  // record discovery time
        low_time[node]  = timer[0];  // initialise low = discovery time
        timer[0]++;                  // increment timer for next node

        for (int adjNode : adj[node]) {

            // skip parent to avoid false back edge in undirected graph
            if (adjNode == parent) continue;

            if (!vis[adjNode]) {
                /*
                 * TREE EDGE — adjNode not yet visited
                 * ----------------------------------------------------------
                 * Recurse into adjNode first (DFS).
                 * After returning, propagate low_time UP:
                 * → current node's low_time may improve via adjNode's subtree
                 *
                 * WHY PROPAGATE AFTER RECURSION?
                 * low_time[adjNode] only finalized AFTER its subtree explored.
                 * Propagating before recursion = using unfinished value → wrong.
                 * Post-order update ensures correct low_time propagation. ✅
                 */
                dfs(node, adjNode, vis, disc_time, low_time, timer, adj, bridges);

                // propagate low_time up from child to parent
                low_time[node] = Math.min(low_time[node], low_time[adjNode]);

                /*
                 * BRIDGE CHECK:
                 * ----------------------------------------------------------
                 * low_time[adjNode] > disc_time[node] means:
                 * → adjNode's subtree cannot reach node or any ancestor
                 * → edge node→adjNode is the ONLY path to adjNode's subtree
                 * → removing it disconnects graph → BRIDGE ✅
                 *
                 * low_time[adjNode] <= disc_time[node] means:
                 * → adjNode can reach node via a back edge
                 * → edge node→adjNode is redundant → NOT a bridge
                 */
                if (low_time[adjNode] > disc_time[node]) {
                    List<Integer> bridge = new ArrayList<>();
                    bridge.add(node);
                    bridge.add(adjNode);
                    bridges.add(bridge);
                }

            } else {
                /*
                 * BACK EDGE — adjNode already visited (ancestor)
                 * ----------------------------------------------------------
                 * This edge connects current node to an ancestor.
                 * Update low_time[node] — we can reach ancestor's
                 * discovery time via this back edge.
                 *
                 * WHY USE disc_time[adjNode] AND NOT low_time[adjNode]?
                 * Using low_time[adjNode] can cause incorrect propagation
                 * through cross edges in some graph configurations.
                 * disc_time[adjNode] = safe, correct for undirected graphs.
                 */
                low_time[node] = Math.min(low_time[node], disc_time[adjNode]);
            }
        }
    }

    public List<List<Integer>> criticalConnections(int n,
                                List<List<Integer>> connections) {

        if (n == 0 || connections == null || connections.isEmpty()) {
            return new ArrayList<>();
        }

        /*
         * BUILD UNDIRECTED ADJACENCY LIST
         * ----------------------------------------------------------
         * Each connection u-v added in BOTH directions.
         * Graph is undirected — signal travels both ways.
         */
        @SuppressWarnings("unchecked")
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (List<Integer> edge : connections) {
            adj[edge.get(0)].add(edge.get(1));
            adj[edge.get(1)].add(edge.get(0));
        }

        /*
         * DFS STATE ARRAYS
         * ----------------------------------------------------------
         * disc_time[i] → discovery order of node i (1-indexed timer)
         * low_time[i]  → minimum disc_time reachable from i's subtree
         * vis[i]       → whether node i has been visited
         * timer[0]     → current timer value (int[1] for pass-by-ref)
         *
         * WHY INITIALIZE disc_time AND low_time TO 0?
         * They get set on first visit — initial value irrelevant.
         * 0 is Java's default for int[] → no explicit fill needed.
         */
        boolean[] vis       = new boolean[n];
        int[] disc_time     = new int[n];
        int[] low_time      = new int[n];
        int[] timer         = new int[]{1}; // starts at 1

        List<List<Integer>> bridges = new ArrayList<>();

        /*
         * RUN DFS FROM ALL UNVISITED NODES
         * ----------------------------------------------------------
         * Graph may be disconnected — must ensure all nodes visited.
         * For LC 1192: graph guaranteed connected → loop runs once.
         * Keeping loop handles general case safely.
         *
         * Start with parent = -1 (no parent for root node).
         * -1 guaranteed not a valid node index (nodes: 0 to n-1).
         */
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                dfs(-1, i, vis, disc_time, low_time, timer, adj, bridges);
            }
        }

        return bridges;
    }

}
