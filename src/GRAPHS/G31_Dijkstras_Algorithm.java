package GRAPHS;
import java.util.*;

public class G31_Dijkstras_Algorithm {

/**
 * CORE DIJKSTRA'S ALGORITHM (OPTIMIZED)
 * * -----------------------------------------------------------------------------
 * THE "ZOMBIE" OPTIMIZATION:
 * Inside the while loop, we check 'if(dist > distances[node]) continue;'.
 * Why? Because the PriorityQueue might contain multiple entries for the same 
 * node with different distances. If we already found and processed a SHORTER 
 * path to this node, we must skip the older, longer "zombie" entries. 
 * This prevents redundant neighbor-checks and keeps the complexity tight!!
 * -----------------------------------------------------------------------------
 */
 
    public int[] dijkstra(int V, int[][] edges, int src) {
        // Step 1: BUILD THE ADJACENCY LIST..
        List<List<int[]>> adj = new ArrayList<>();
        for(int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        
        for(int[] edge : edges) {
            // Mapping: [neighborNode, weight]
            // Standard Undirected Graph logic..
            adj.get(edge[0]).add(new int[]{edge[1], edge[2]});
            adj.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }
        
        return shortest_paths(V, adj, src);
    }
    
    private int[] shortest_paths(int V, List<List<int[]>> adj, int src) {
        // Initialize distances as Infinity.. 
        int[] distances = new int[V];
        Arrays.fill(distances, Integer.MAX_VALUE);
        
        // Starting point distance is always 0..
        distances[src] = 0;
        
        // PriorityQueue to always get the node with the shortest distance till now..
        // (a,b) -> a[1]-b[1] creates a MIN-HEAP based on the distance value..
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); 
        pq.offer(new int[]{src, 0}); // [node, distance]
        
        while(!pq.isEmpty()){
            // take the node having the shortest distance till now..
            int node = pq.peek()[0];
            int dist = pq.peek()[1];
            pq.poll();
            
            // ************************************************************
            // THE "ZOMBIE" OPTIMIZATION:
            // if this curr dist is not better than what we have for this node , 
            // do not proceed further because then it will check the adjacent 
            // ones with the greater distances again and again!!
            // ************************************************************
            if(dist > distances[node]) continue;
            
            // Explore neighbours..
            for(int[] adjEdge : adj.get(node)){
                int adjNode = adjEdge[0];
                int weight = adjEdge[1];
                
                // RELAXATION LOGIC:
                // if new dist is less than prev , update in distances ,and push into the queue...
                if(distances[adjNode] > dist + weight){
                    distances[adjNode] = dist + weight;
                    pq.offer(new int[]{adjNode, distances[adjNode]});
                }
            }
        }
        
        return distances;
    }
}
