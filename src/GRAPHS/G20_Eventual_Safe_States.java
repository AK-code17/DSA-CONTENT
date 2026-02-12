package GRAPHS;

import java.util.*;
/**
 * PROBLEM: Eventual Safe Nodes
 * A node is "safe" if every possible path starting from that node leads to a terminal node.
 * A "terminal node" is a node with no outgoing edges.
 * IN PLAIN ENGLISH: A node is safe if it is NOT part of a cycle and cannot reach a cycle.
 */
public class G20_Eventual_Safe_States {
    // =====================================================================================
    // APPROACH 1: DFS (Cycle Detection / Three-State Coloring)
    // =====================================================================================
    /* INTUITION: 
       Any node that is part of a cycle (or leads to one) can never be safe because you could 
       loop infinitely and never reach a terminal node. We use "Three-State Coloring":
       - State 0 (Unvisited): Initial state.
       - State 1 (Visiting/Path-Visited): Node is in the current recursion stack. 
                  If we encounter a node in State 1, we've found a CYCLE.
       - State 2 (Safe): Node has been fully explored and doesn't lead to any cycles.
    */
    public List<Integer> eventualSafeNodes_DFS(int[][] graph) {
        List<Integer> SafeNodes = new ArrayList<>();
        int nodes = graph.length;
        int[] vis = new int[nodes]; // 0: unvisited, 1: visiting, 2: safe
        
        for(int node = 0; node < nodes ; node++){
            // If the DFS confirms the node leads to no cycles, it is safe.
            if(Check_Safe_Node(node, vis, graph)) {
                SafeNodes.add(node);
            }
        }
        return SafeNodes;
    }

    private static boolean Check_Safe_Node(int node, int[] vis, int[][] adj){
        // BASE CASE: If already marked Safe (2), return true.
        if(vis[node] == 2) return true;
        
        // BASE CASE: If marked Visiting (1), we hit a cycle! Return false.
        if(vis[node] == 1) return false;
        
        // STEP 1: Mark as 'Visiting' (1) before exploring neighbors.
        vis[node] = 1;

        // STEP 2: Explore all paths from this node.
        for(int adjNode : adj[node]){
            // If any path leads to a cycle (returns false), this node is unsafe.
            if(!Check_Safe_Node(adjNode, vis, adj)) {
                return false; 
            }
        }

        // STEP 3: If we finished the loop, no cycles were found on any path.
        // Mark as 'Safe' (2) so future calls can reuse this result (Memoization).
        vis[node] = 2;
        return true;
    }


    // =====================================================================================
    // APPROACH 2: BFS (Kahn's Algorithm on Reversed Graph)
    // =====================================================================================
    /* INTUITION: 
       In a normal graph, terminal nodes have Out-Degree = 0.
       If we REVERSE the edges:
       - Terminal nodes now have IN-Degree = 0.
       - We can use Kahn's Algorithm (BFS) starting from these terminal nodes.
       - As we "remove" a safe node from the queue, we decrement the in-degrees of nodes 
         that originally pointed to it.
       - If a node's in-degree hits 0, it means all its original outgoing edges 
         lead to confirmed safe nodes. It is now safe itself!
    */
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int nodes = graph.length;
        
        // STEP 1: Create a REVERSED Adjacency List.
        // original: u -> v | reversed: v -> u
        List<Integer>[] adj = new ArrayList[nodes];
        for(int i=0 ; i<nodes ; i++){
            adj[i] = new ArrayList<>();
        }

        for(int i = 0 ; i<nodes ; i++){
            for(int val : graph[i]){
                // Reversing: instead of i -> val, we store val -> i
                adj[val].add(i);
            }
        }

        return Safe_Nodes_Kahn(adj);
    }

    private static List<Integer> Safe_Nodes_Kahn(List<Integer>[] adj){
        int nodes = adj.length;
        int[] indegree = new int[nodes];

        // STEP 2: Calculate In-degrees of the REVERSED graph.
        for(List<Integer> list : adj){
            for(int val : list){
                indegree[val]++;
            }
        }

        Deque<Integer> que = new ArrayDeque<>();

        // STEP 3: Push all nodes with In-degree 0 into the Queue.
        // In the reversed graph, these were the original 'terminal' nodes.
        for(int node=0 ; node < nodes ; node++){
            if(indegree[node] == 0) que.offerLast(node);
        }

        // STEP 4: Standard Kahn's BFS.
        while(!que.isEmpty()){
            int node = que.pollFirst();
            for(int adjNode : adj[node]){
                // Decrement dependency.
                indegree[adjNode]--;

                // If in-degree hits 0, all original paths from 'adjNode' lead to safe nodes.
                if(indegree[adjNode] == 0) que.offerLast(adjNode);
            }
        }

        // STEP 5: Final Check.
        // Nodes that were successfully processed (indegree became 0) are safe.
        List<Integer> safe_nodes = new ArrayList<>();
        for(int node = 0 ; node<nodes ; node++){
            // We loop 0 to nodes-1 to ensure the final list is sorted.
            if(indegree[node] == 0) safe_nodes.add(node);
        }

        return safe_nodes;
    }
}
