package GRAPHS;
import java.util.*;

public class G28_Shortest_Path_Directed_Unweighted {

/**
 * PROBLEM: SHORTEST PATH IN UNDIRECTED GRAPH (UNIT DISTANCE)
 * * GOAL: Find the shortest path from a source node to all other nodes in an 
 * undirected graph where every edge has a weight of 1.
 * * INTUITION: 
 * For unweighted graphs, Breadth-First Search (BFS) is the most efficient approach 
 * because it explores the graph in "waves" or levels. The first time you reach a node 
 * using BFS, you have found the shortest path to it. This allows us to solve the 
 * problem in O(V+E) time without the complexity of a PriorityQueue.
 */

    public int[] shortestPath(int V, int[][] edges, int src) {
        // 1. Better structure: List of Lists (Type-safe for GFG)
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // 2. Faster edge creation
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        // 3. BFS Logic
        int[] dist = new int[V];
        // Initially marking the distances as -1 to represent unvisited nodes
        Arrays.fill(dist, -1);
        dist[src] = 0;

        // Using ArrayDeque instead of LinkedList for faster queue operations
        Queue<Integer> que = new ArrayDeque<>();
        que.add(src);

        while (!que.isEmpty()) {
            int curr_node = que.poll();

            for (int neighbor : adj.get(curr_node)) {
                // BFS ensures we reach each node in shortest distance.
                // We update the distance only if it was unvisited (-1).
                if (dist[neighbor] == -1) {
                    dist[neighbor] = dist[curr_node] + 1;
                    que.add(neighbor);
                }
            }
        }
        
        return dist;
    }
}
