package GRAPHS;
import java.util.*;

public class G31_Dijkstras_Algorithm {

    // ALSO I added the TreeSet approach  , if in case its asked in Interview..

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

    // TREESET APPROACH : 
    // It's make the space complexity from O(E)--> O(V)..
    // but the time to look up to a particular nodes and erasing it is - log(V)..


    /**
     * DIJKSTRA VIA TREESET (THE O(V) SPACE VARIATION)
     * * -----------------------------------------------------------------------------
     * 1. THE GOAL: Mimic the C++ std::set behavior to keep space at strictly O(V).
     * 2. THE MECHANISM: Instead of adding "Zombies," we find the old entry, 
     * REMOVE it, and ADD the updated version.
     * 3. THE CAVEAT: Java's TreeSet needs a custom Comparator that accounts 
     * for BOTH distance AND node ID, otherwise nodes with the same distance 
     * will be "swallowed" by the set!
     * -----------------------------------------------------------------------------
     */
    public int[] dijkstra(int V, List<List<int[]>> adj, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // TreeSet as a balanced BST (O(log V) for add/remove)
        // We sort by distance, then by node ID to keep unique entries!!
        TreeSet<int[]> set = new TreeSet<>((a, b) -> {
            if (a[1] != b[1]) return a[1] - b[1]; // Sort by distance
            return a[0] - b[0]; // If distances equal, sort by node ID
        });

        set.add(new int[]{src, 0});

        while (!set.isEmpty()) {
            int[] current = set.pollFirst(); // Equivalent to pq.poll()
            int node = current[0];
            int d = current[1];

            for (int[] neighbor : adj.get(node)) {
                int adjNode = neighbor[0];
                int weight = neighbor[1];

                if (dist[adjNode] > d + weight) {
                    // THE "UPDATE" LOGIC:
                    // If node already exists in set, remove the OLD version..
                    // This is only possible if dist[adjNode] was previously updated.
                    if (dist[adjNode] != Integer.MAX_VALUE) {
                        set.remove(new int[]{adjNode, dist[adjNode]});
                    }

                    // Update and add the NEW version..
                    dist[adjNode] = d + weight;
                    set.add(new int[]{adjNode, dist[adjNode]});
                }
            }
        }
        return dist;
    }
}
