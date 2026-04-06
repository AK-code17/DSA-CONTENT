package GRAPHS;

import java.util.*;

public class G56_Articulations_in_Graph {
    /*
 * ============================================================
 * PROBLEM: Articulation Points (GFG / LC 1568 variant)
 * ============================================================
 * INTUITION — WHAT IS AN ARTICULATION POINT?
 * A node whose removal increases the number of connected
 * components in the graph.
 * In a network: APs = single points of failure.
 * Removing them splits the network into isolated parts.
 *
 * KEY INSIGHT — HOW TO DETECT AN AP:
 * During DFS, every edge is either:
 * 1. TREE EDGE  → edge used to discover a new node
 * 2. BACK EDGE  → edge pointing to an ANCESTOR (already visited)
 *
 * A node u is an AP if ANY of its DFS children v satisfies:
 * low_time[v] >= disc_time[u]
 *
 * WHY >= (NOT > like bridges)?
 * Bridge condition:  low[child] >  disc[node]  (strictly greater)
 *                    child has NO path back to node or above
 *
 * AP condition:      low[child] >= disc[node]  (greater or equal)
 *                    child can reach node itself but NOT above it
 *                    → removing node still disconnects child ✅
 *                    Equal case: child reaches node via back edge
 *                    but node's removal still disconnects child
 *                    from node's parent subtree.
 *
 * SPECIAL CASE — ROOT NODE:
 * Root has no parent → AP condition doesn't apply.
 * Root is an AP if and only if it has MORE THAN ONE DFS child.
 * WHY?
 * If root has 2+ children in DFS tree:
 * → those children have NO path to each other except through root
 *   (otherwise DFS would have connected them before backtracking)
 * → removing root disconnects them ✅
 * If root has 1 child:
 * → removing root just removes a leaf-like node from DFS perspective
 * → rest of graph still connected ✅
 *
 * BRIDGE vs AP RELATIONSHIP:
 * Bridge = critical EDGE, AP = critical NODE
 * Every bridge endpoint (except degree-1 leaf) is an AP.
 * But APs can exist without bridges (cycle-based APs).
 * They are related but NOT the same concept.
 *
 * WHY disc_time FOR BACK EDGES (NOT low_time)?
 * low_time is computed BOTTOM-UP — only FINAL when dfs() returns.
 * Ancestor is still ON stack when back edge processed
 * → low_time[ancestor] = INCOMPLETE/PARTIAL value ❌
 * → disc_time[ancestor] = FIXED, assigned once, never changes ✅
 * Using low_time for back edges = reading unstable value
 * → wrong AP detection (especially for non-bridge APs)
 *
 * WHY LOOP OVER ALL NODES (NOT JUST NODE 0)?
 * Graph may be disconnected — not always guaranteed connected.
 * Starting from node 0 only explores its component.
 * Other components' APs completely missed.
 * Looping over all nodes handles both cases safely.
 * vis[] check ensures already-visited nodes are skipped.
 * Cost = O(1) extra per visited node → negligible.
 *
 * COMPLEXITY:
 * Time  → O(V+E) — single DFS pass over all components
 * Space → O(V+E) — adj list + disc/low arrays + recursion stack
 * ============================================================
 */
    /*
     * DFS — Computes disc_time, low_time and finds APs
     * ----------------------------------------------------------
     * PARAMETERS:
     * parent      → parent node in DFS tree (-1 for root)
     * node        → current node being processed
     * vis[]       → tracks visited nodes
     * disc_time[] → discovery time per node (DFS order)
     * low_time[]  → min disc_time reachable from subtree
     * timer[]     → int[1] mutable timer reference
     *               WHY int[] AND NOT int?
     *               Java passes primitives by VALUE.
     *               Changes inside dfs() won't reflect outside.
     *               int[]{1} acts as mutable reference ✅
     * adj         → adjacency list
     * marked[]    → tracks which nodes are APs
     *               WHY marked[] INSTEAD OF DIRECT ADD TO LIST?
     *               Same node can be detected as AP multiple times
     *               (once per qualifying child).
     *               marked[] prevents duplicate entries in result.
     *
     * WHY PRIVATE?
     * Internal implementation detail — caller doesn't need access.
     * Encapsulation: only articulationPoints() should call this.
     *
     * WHY timer AS int[1] AND NOT INSTANCE VARIABLE?
     * Instance variable persists between method calls on same object.
     * If articulationPoints() called twice → timer carries over
     * → wrong discovery times → wrong APs.
     * int[1] scoped to each articulationPoints() call → fresh. ✅
     *
     * PARENT CHECK — WHY SKIP adjNode == parent?
     * Graph is undirected → edge u-v appears as u→v AND v→u.
     * When processing u's neighbors, v→u looks like back edge to parent.
     * This would incorrectly lower low_time[u].
     * Skipping parent prevents this false back edge. ✅
     *
     * KNOWN LIMITATION — PARALLEL EDGES:
     * If two edges u-v exist (parallel edges):
     * parent check skips BOTH → second parallel edge ignored.
     * Fix: track parent EDGE INDEX not parent NODE.
     * For this problem: constraints typically exclude parallel edges.
     */
    private void dfs(int parent, int node, boolean[] vis,
                     int[] disc_time, int[] low_time, int[] timer,
                     ArrayList<ArrayList<Integer>> adj, boolean[] marked) {

        vis[node]       = true;
        disc_time[node] = timer[0]; // record discovery time
        low_time[node]  = timer[0]; // initialise low = discovery time
        timer[0]++;

        /*
         * CHILD COUNT — Only meaningful for root AP detection
         * ----------------------------------------------------------
         * Counts direct DFS tree children of current node.
         * For root: AP if child > 1.
         * For non-root: AP condition uses low_time instead.
         * Scoped per node — reset for each dfs() call. ✅
         */
        int childCount = 0;

        for (int adjNode : adj.get(node)) {

            // skip parent to avoid false back edge in undirected graph
            if (adjNode == parent) continue;

            if (!vis[adjNode]) {
                /*
                 * TREE EDGE — adjNode not yet visited
                 * ----------------------------------------------------------
                 * Increment child count for root AP detection.
                 * Recurse into adjNode first (DFS post-order).
                 * After returning → low_time[adjNode] fully computed.
                 * Propagate low_time UP: current node may improve
                 * via adjNode's subtree back edges.
                 *
                 * WHY PROPAGATE AFTER RECURSION?
                 * low_time[adjNode] only final AFTER subtree explored.
                 * Propagating before = using incomplete value → wrong.
                 */
                childCount++;
                dfs(node, adjNode, vis, disc_time, low_time, timer, adj, marked);

                // propagate low_time up from child to current node
                low_time[node] = Math.min(low_time[node], low_time[adjNode]);

                /*
                 * AP CHECK FOR NON-ROOT NODES:
                 * ----------------------------------------------------------
                 * low_time[adjNode] >= disc_time[node] means:
                 * → adjNode's subtree cannot reach ABOVE node
                 * → removing node disconnects adjNode's subtree
                 * → node is an AP ✅
                 *
                 * WHY >= AND NOT >?
                 * > (bridge): child has NO path back to node
                 * >= (AP):    child reaches node but NOT node's parent
                 *             removing node still disconnects child
                 *             from rest of graph above node
                 *
                 * WHY parent != -1 CHECK?
                 * Root node handled separately below.
                 * Applying this condition to root gives wrong results
                 * because root has no parent to disconnect from.
                 */
                if (parent != -1 && low_time[adjNode] >= disc_time[node]) {
                    marked[node] = true;
                }

            } else {
                /*
                 * BACK EDGE — adjNode already visited (ancestor)
                 * ----------------------------------------------------------
                 * This edge connects node to an ancestor.
                 * Update low_time[node] with disc_time[adjNode].
                 *
                 * WHY disc_time AND NOT low_time?
                 * Ancestor is still ON the DFS stack → not finished.
                 * low_time[ancestor] = INCOMPLETE at this moment.
                 * disc_time[ancestor] = FIXED, assigned once. ✅
                 *
                 * Using low_time here = reading partial value
                 * → child incorrectly inherits ancestor's subtree info
                 * → non-bridge APs wrongly missed
                 * → passes for bridges (coincidence) but breaks for APs
                 */
                low_time[node] = Math.min(low_time[node], disc_time[adjNode]);
            }
        }

        /*
         * AP CHECK FOR ROOT NODE:
         * ----------------------------------------------------------
         * Root has no parent → standard AP condition inapplicable.
         * Root is AP if and only if it has 2+ DFS tree children.
         *
         * WHY 2+ CHILDREN?
         * Root's children in DFS tree have NO path to each other
         * except through root (otherwise DFS would have visited
         * them as part of same subtree before backtracking).
         * → removing root disconnects them → AP ✅
         *
         * If root has only 1 child:
         * → removing root just disconnects a subtree that
         *   has no other connections anyway
         * → rest still connected → NOT AP
         *
         * WHY parent == -1 CHECK?
         * Only root has parent == -1.
         * This condition should ONLY apply to root.
         */
        if (parent == -1 && childCount > 1) {
            marked[node] = true;
        }
    }

    public ArrayList<Integer> articulationPoints(int V,
                                ArrayList<ArrayList<Integer>> adj) {

        if (V == 0 || adj == null) return new ArrayList<>();

        /*
         * STATE ARRAYS:
         * disc_time[i] → discovery order of node i
         * low_time[i]  → min disc_time reachable from i's subtree
         * vis[i]       → whether node i has been visited
         * marked[i]    → whether node i is an AP
         * timer[0]     → current timer (int[1] for mutability)
         */
        boolean[] vis       = new boolean[V];
        int[] disc_time     = new int[V];
        int[] low_time      = new int[V];
        boolean[] marked    = new boolean[V];
        int[] timer         = new int[]{1};

        /*
         * LOOP OVER ALL NODES — handles disconnected graphs
         * ----------------------------------------------------------
         * WHY NOT JUST dfs(0)?
         * Graph may be disconnected — not always guaranteed connected.
         * Starting from node 0 only explores its component.
         * Other components' APs completely missed → wrong answer.
         *
         * Looping with vis[] check:
         * → visited nodes skipped in O(1) → no extra cost
         * → unvisited nodes = new component → fresh DFS
         * → handles both connected and disconnected safely ✅
         *
         * Each new component starts with parent = -1
         * (its root has no parent within this DFS call).
         */
        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                dfs(-1, i, vis, disc_time, low_time, timer, adj, marked);
            }
        }

        /*
         * COLLECT RESULTS:
         * ----------------------------------------------------------
         * marked[] ensures each AP added exactly once
         * regardless of how many children qualified it.
         *
         * WHY RETURN -1 IF NO APs?
         * GFG problem requirement — empty result = {-1}.
         * Fully connected graph with no single point of failure
         * → no APs → return {-1}.
         */
        ArrayList<Integer> articulations = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (marked[i]) articulations.add(i);
        }

        if (articulations.isEmpty()) articulations.add(-1);

        return articulations;
    }
}
