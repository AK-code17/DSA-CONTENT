package GRAPHS;
import java.util.*;

public class G35_Shortest_Dist_Undirected_Weighted {

/**
 * PROBLEM: SHORTEST PATH IN WEIGHTED UNDIRECTED GRAPH
 * * -----------------------------------------------------------------------------
 * THE GOAL: Find the shortest path from node 1 to node n.
 * THE OUTPUT: [Total Weight, Node 1, Node 2, ... Node n]
 * * -----------------------------------------------------------------------------
 * 1-YEAR REVISION CHECK (THE "WHY"):
 * 1. THE ZOMBIE CHECK: 'if(dist > distances[node]) continue;' is the MVP.
 * In Java, PriorityQueue doesn't update-in-place. We push new distances,
 * so we must skip the "Zombies" (older, longer paths) to keep O(E log V).
 * 2. THE PARENT ARRAY: This is our "Breadcrumb Trail." Every time we relax 
 * an edge, we record who brought us there. parent[child] = parent_node.
 * 3. THE GREEDY LIMIT: This only works for POSITIVE weights. If there is a 
 * Negative Cycle, this logic will spin forever or give wrong results!!
 * -----------------------------------------------------------------------------
 */
    
    public int dijkstra(int V, int[][] edges, int src, int[] parent, int[] distances) {
        // Step 1: BUILD THE ADJACENCY LIST.. 
        // Using 1-based indexing as per the standard problem requirement..
        List<List<int[]>> adj = new ArrayList<>();
        for(int i = 0; i <= V; i++){
            adj.add(new ArrayList<>());
        }
        
        for(int[] edge : edges){
            // for each node , stored its edge , weight..
            // Mapping: [neighborNode, weight]
            adj.get(edge[0]).add(new int[]{edge[1], edge[2]});
            adj.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }
        
        // Initialize distances as Infinity.. 
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[src] = 0;
        
        // PriorityQueue to always get the node with the shortest distance till now..
        // (a,b) -> a[1]-b[1] creates a MIN-HEAP based on the distance value..
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); 
        pq.offer(new int[]{src, 0}); // node, distance..
        
        while(!pq.isEmpty()){
            // take the node having the shortest distance till now , and explore its neighbours..
            int node = pq.peek()[0];
            int dist = pq.peek()[1];
            pq.poll();
            
            // THE "ZOMBIE" OPTIMIZATION (STRIKER STYLE):
            // if this curr dist is not better than what we have for this node , 
            // do not proceed further because then it will check the adjacent 
            // ones with the greater distances again and again!!
            if(dist > distances[node]) continue;
            
            for(int[] adjEdge : adj.get(node)){
                int adjNode = adjEdge[0];
                int edgeWeight = adjEdge[1];
                
                // RELAXATION LOGIC:
                // if new dist is less than prev , update in distances , and push into the queue...
                if(distances[adjNode] > dist + edgeWeight){
                    distances[adjNode] = dist + edgeWeight;
                    
                    // Update parent whenever we find a shorter way to reach adjNode..
                    parent[adjNode] = node;
                    
                    pq.offer(new int[]{adjNode, distances[adjNode]});
                }
            }
        }
        
        // Return the distance of the target node (V)..
        return distances[V];
    }
    
    public List<Integer> shortestPath(int n, int m, int edges[][]) {
        // Step 1: Initialize Parent array and Distances..
        int[] parent = new int[n + 1];
        int[] distances = new int[n + 1];
        
        // Initially every node is its own parent..
        for(int i = 1; i <= n; i++) parent[i] = i;
        
        // Step 2: Run Dijkstra to find shortest path count and fill parent array..
        int shortest_dist = dijkstra(n, edges, 1, parent, distances);
        
        List<Integer> ans = new ArrayList<>();
        
        // DEADLOCK CHECK: If the target node was never reached..
        if(shortest_dist == Integer.MAX_VALUE){
            ans.add(-1);
            return ans;
        }

        // Step 3: Now find the path from parent(n) to child(1) (Backtracking)..
        int curr_node = n;
        while(parent[curr_node] != curr_node){
            ans.add(curr_node);
            curr_node = parent[curr_node];
        }
        ans.add(1); // Add the source node..
        
        // THE SPECIFIC REQUIREMENT: Add total weight at the end so it's 1st after reverse!!
        ans.add(shortest_dist);
        
        // Reverse because we built the path end -> start..
        Collections.reverse(ans);

        return ans;
    }
}
