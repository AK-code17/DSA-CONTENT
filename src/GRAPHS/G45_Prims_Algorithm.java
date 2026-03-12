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
    public int spanningTree(int V, int[][] edges) {
        
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
}
