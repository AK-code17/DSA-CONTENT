package GRAPHS;
import java.util.*;

public class G45_Prims_Algorithm {
    /**
 * 🛰️ ARCHITECT'S DEEP DIVE: MINIMUM SPANNING TREE (PRIM'S ALGORITHM)
 * -------------------------------------------------------------------------
 * 🚀 THE "WHY" OF PRIM'S:
 * Prim's is a "Greedy" algorithm that builds the MST one node at a time.
 * It's essentially Dijkstra's sibling, but instead of tracking the total distance 
 * from a single source, it only tracks the MINIMUM weight required to pull 
 * a new node into the existing MST "cloud."
 *
 * 🎯 THE CRUX: POP-TIME VISITATION
 * A node must only be marked 'visited' and its weight added to the MST sum 
 * when it is POLL'D from the Min-Heap. Why? Because the Min-Heap might 
 * contain multiple edges leading to the same node; the first one to be 
 * popped is guaranteed to be the cheapest edge.
 *
 * ⏱️ COMPLEXITY ANALYSIS:
 * - Time: O(E log E) or O(E log V) — due to PriorityQueue operations.
 * - Space: O(V + E) — Adjacency list and the PriorityQueue.
 * -------------------------------------------------------------------------
 */
    public int spanningTree_sparse_graph(int V, int[][] edges) {
        
        // 1. TOPOLOGY INITIALIZATION
        // MST requires an undirected graph representation.
        List<List<int[]>> adj = new ArrayList<>();
        for(int i=0 ; i<V ; i++) adj.add(new ArrayList<>());
        
        for(int[] edge : edges){
            adj.get(edge[0]).add(new int[]{edge[1], edge[2]});
            adj.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }
        
        int MST_SUM = 0;
        
        // 2. STATE TRACKING
        // 'vis' represents the nodes already integrated into the MST "cloud."
        boolean vis[] = new boolean[V];
        
        // min_heap stores [node_index, edge_weight_to_reach_it]
        PriorityQueue<int[]> min_heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        
        // Initial Seed: Start from node 0 with 0 weight cost.
        min_heap.offer(new int[]{0, 0});
        
        while(!min_heap.isEmpty()){
            int[] node = min_heap.poll();
            int val = node[0];
            int wt = node[1];
            
            // 🛑 CRITICAL CHECK:
            // If the node is already in our MST cloud, ignore this edge. 
            // It means we found a cheaper way to include this node earlier.
            if(vis[val]) continue;
            
            // Integrate node into the MST
            vis[val] = true;
            MST_SUM += wt;
            
            // 3. FRONTIER EXPLORATION
            // Look at all neighbors and offer the edges to the heap if the neighbor 
            // isn't already part of the MST cloud.
            for(int[] adjNode : adj.get(val)){
                int adjVal = adjNode[0];
                int adjWt = adjNode[1];
                
                if(!vis[adjVal]) {
                    min_heap.offer(new int[]{adjVal, adjWt});
                }
            }
        }
        
        return MST_SUM;
    }

    /**
     * 🛰️ ARCHITECTURAL VARIANT: MATRIX-NATIVE PRIM'S (O(V²))
     * -------------------------------------------------------------------------
     * 🚀 STRATEGY:
     * This implementation is optimized for DENSE GRAPHS where an Adjacency Matrix
     * is either provided or preferred. By using a matrix, we gain O(1) edge access
     * and superior cache locality compared to pointer-heavy Adjacency Lists.
     *
     * ⚖️ COMPLEXITY:
     * - Time: O(V²) -> We perform V manual scans, each taking O(V).
     * - Space: O(V²) -> For the matrix (if created) or O(V) auxiliary space.
     * -------------------------------------------------------------------------
     */
    public int spanningTree_dense_graph(int V, int[][] edges) {
        // 1. MATRIX INITIALIZATION
        // We use a safe INF (1e9) to represent "No Edge." 
        // We avoid Integer.MAX_VALUE to prevent any potential overflow issues.
        int[][] adj = new int[V][V];
        int INF = (int)1e9;
        
        for (int i = 0; i < V; i++) {
            Arrays.fill(adj[i], INF);
            adj[i][i] = 0; // Distance to self
        }

        // Populate the Matrix from the edge list
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], wt = edge[2];
            adj[u][v] = wt;
            adj[v][u] = wt;
        }

        // 2. STATE TRACKERS
        int[] minEdgeWeight = new int[V]; // Smallest edge connecting node i to MST
        boolean[] vis = new boolean[V]; // Nodes integrated into the MST cloud
        Arrays.fill(minEdgeWeight, INF);
        
        minEdgeWeight[0] = 0; // Seed the algorithm at Node 0
        int MST_Sum = 0;

        // 3. CORE MST EXPANSION (V Iterations)
        for (int iteration = 0; iteration < V; iteration++) {
            
            // STEP A: MANUAL MIN-SCAN (O(V))
            // Instead of using a PriorityQueue, we scan the array to find the 
            // cheapest unvisited node. This is more efficient in dense graphs.
            int node = -1;
            for (int i = 0; i < V; i++) {
                if (!vis[i] && (node == -1 || minEdgeWeight[i] < minEdgeWeight[node])) {
                    node = i;
                }
            }

            // EARLY TERMINATION: If no new unvisited found then 
            // simply break off..
            if (node == -1 || minEdgeWeight[node] == INF) break;

            vis[node] = true;
            MST_Sum += minEdgeWeight[node];

            // STEP C: NEIGHBOR UPDATE (O(V))
            // We scan the row of the matrix for node 'u' to update all Adj_neighbors.
            for (int adjNode = 0; adjNode < V; adjNode++) {
                int weight = adj[node][adjNode];
                
                // If node 'adjNode' is not in MST and the edge (u, adjNode) is cheaper 
                // than the previously recorded best edge for 'adjNode', we update.
                if (!vis[adjNode] && weight < minEdgeWeight[adjNode]) {
                    minEdgeWeight[adjNode] = weight;
                }
            }
        }

        return MST_Sum;
    }
}
