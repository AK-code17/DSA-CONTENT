package GRAPHS;
import java.util.*;

public class G27_Shortest_path_in_DAG {

/**
 * PROBLEM: SHORTEST PATH IN A DIRECTED ACYCLIC GRAPH (DAG)
 * * GOAL: Find the shortest distance from a source node to all other nodes in a graph that 
 * has no cycles.
 * * INTUITION: 
 * Since the graph is a DAG, we can use its "Topological Order" to solve the problem in linear 
 * time O(V+E). In a topological sort, if there is an edge u -> v, then u always appears 
 * before v. By relaxing edges in this specific order, we guarantee that when we reach 
 * node 'v', we have already considered every possible incoming path to it. This eliminates 
 * the need for the complex PriorityQueue used in Dijkstra.
 */

    public int[] shortestPath(int V, int E, int[][] edges) {
        // Code here
        
        // Use List of Lists to satisfy Java Generics safety and avoid "Unsafe" warnings
        List<List<int[]>> adj = new ArrayList<>();
        
        for(int i=0 ; i<V ; i++){
            adj.add(new ArrayList<>());
        }
        
        for(int[] edge : edges){
            // for node (0,1,2) : Stored at index 0 to edge 1 having 
            // weight 2..
            
            adj.get(edge[0]).add(new int[] {edge[1],edge[2]});
        }
        
        return Shortest_paths(0,V,adj);
    }
    
    private int[] Shortest_paths(int srcNode,int V,List<List<int[]>> adj){
        // Perform Topo sort to get the order of prev dependencies , 
        // to ensure the current node has the minimum path before moving
        // to its own children ...
        
        Deque<Integer> stack = new ArrayDeque<>();
        int[] shortest_src_dist = new int[V];
        // we ensures we can use the .min function to find the min dist..
        Arrays.fill(shortest_src_dist,Integer.MAX_VALUE);
        shortest_src_dist[srcNode] = 0;
        
        // here provided the srcNode to toposort ,to ensure we start topo
        // sort from the srcNode , so need to find the ordering of prev nodes
        // which are not reachable from srcNode , and also then no need to remove
        // them from Stack..
        boolean[] vis = new boolean[V];
        Topo_sort(srcNode,vis,adj,stack);
        
        while(!stack.isEmpty()){
            int node = stack.pollFirst();
            
            // Relax edges: Because we follow Topo order, we process parents before children.
            // Note: In this partial Topo version, shortest_src_dist[node] is guaranteed 
            // to be a real value (not Infinity).
            for(int[] adjNode : adj.get(node)){
                shortest_src_dist[adjNode[0]] = Math.min(shortest_src_dist[adjNode[0]] , shortest_src_dist[node] + adjNode[1]);
            }
        }
        
        // Post-processing: If a node remains at MAX_VALUE, it was never reached.
        for(int node = 0; node<V ; node++){
            if(shortest_src_dist[node] == Integer.MAX_VALUE) {
                shortest_src_dist[node] = -1;
            }
        }
        
        return shortest_src_dist;
    }
    
    private void Topo_sort(int node,boolean[] vis, List<List<int[]>> adj , Deque<Integer> stack){
        vis[node] = true;
        
        // push all the dependents first..
        for(int[] adjNode : adj.get(node)){
            if(!vis[adjNode[0]]){
                Topo_sort(adjNode[0],vis,adj,stack);
            }
        }
        
        // at last add the srcNode..
        // offerFirst ensures the node processed last in DFS is at the top of the stack.
        stack.offerFirst(node);
    }
}
