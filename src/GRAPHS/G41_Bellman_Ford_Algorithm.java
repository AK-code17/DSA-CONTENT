package GRAPHS;

import java.util.Arrays;

public class G_41_Bellman_Ford_Algorithm {
    // User function Template for Java..
    
    /*
     * =========================================================================
     * 🛠️ 2-YEAR REFRESHER: BELLMAN-FORD (Negative Weight Specialist)
     * =========================================================================
     * 🚀 THE "WHY" OF BELLMAN-FORD:
     * Dijkstra is "Greedy" and assumes once a node is visited, its distance is 
     * final. Negative weights break this assumption. Bellman-Ford doesn't 
     * "lock" nodes; it iteratively relaxes all edges to find the global minimum.
     *
     * 🎯 THE CRUX: WHY V-1 RELAXATIONS?
     * A shortest path in a graph with V vertices can have at most V-1 edges 
     * (any more would mean a cycle). Each iteration 'i' guarantees that we've 
     * found the shortest path for all nodes reachable within 'i' edges. 
     * By the (V-1)-th pass, every possible shortest path is "settled."
     *
     * ⚠️ NEGATIVE WEIGHT CYCLES:
     * If you can still relax an edge on the V-th pass, you've found a loop 
     * where the sum of weights is negative. This means you could loop 
     * infinitely to reach -Infinity. In this problem, we return {-1}.
     *
     * 🔢 THE "10^8" vs "MAX_VALUE" TRAP:
     * We use 1e8 as 'infinity' because (Integer.MAX_VALUE + positive_weight) 
     * causes an overflow into negative numbers, which would falsely 
     * trigger the "shorter path" condition.
     * =========================================================================
     */
    int max_dist = 100000000;

    public int[] bellmanFord(int V, int[][] edges, int src) {
        // code here
        int[] dist = new int[V];
        Arrays.fill(dist, max_dist);
        
        dist[src] = 0;
        
        // Relaxing V-1 times to ensure all shortest paths are captured
        for(int i=0 ; i<V-1 ; i++){
            // Optimization: If no updates happen in a round, we have converged.
            if(!Relax_Edges(edges, dist)) return dist;
        }
        
        // Extra loop (the V-th pass) to check for the negative weight cycle...
        // If we can still reduce a distance, a negative cycle exists.
        if(Relax_Edges(edges, dist)) return new int[]{-1};
        
        return dist;
    }
    
    private boolean Relax_Edges(int[][] edges , int[] dist){
        boolean updated = false;
        
        for(int[] edge : edges){
            int server = edge[0]; // The source node
            int client = edge[1]; // The destination node
            int cost = edge[2];   // The edge weight
            
            // RELAXATION LOGIC:
            // 1. Ensure the 'server' is reachable (not at max_dist).
            // 2. Check if path through 'server' to 'client' is cheaper than current.
            if(dist[server] != max_dist && dist[server] + cost < dist[client]){
                dist[client] = dist[server] + cost;
                updated = true;
            }
        }
        
        return updated;
    }

}
