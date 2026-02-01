package GRAPHS;

import java.util.*;

/**
 * GOAL: Detect a cycle in a Directed Graph using DFS.
 * * THE "THREE-STATE" INTUITION:
 * -1 (UNVISITED): We haven't touched this node yet.
 * 1 (VISITING):  The node is in our CURRENT recursion stack. 
 * If we hit a node with this state, we've found a BACK-EDGE (Cycle!).
 * 0 (VISITED):   The node is fully processed. It is safe and does not lead to a cycle.
 */
public class G19_Detect_Cycle_Directed_Graph {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int nodes = sc.nextInt();
        int edges = sc.nextInt();

        // Adjacency List for Directed Graph
        List<Integer>[] adj = new ArrayList[nodes + 1];
        for(int i = 0; i <= nodes; i++) {
            adj[i] = new ArrayList<>();
        }

        /*
        10 11
        1 2
        2 3
        3 4
        4 5
        5 6
        3 7
        7 5
        8 2
        8 9
        8 10(use 10 8) for cycle detection...
        9 10
        */

        for(int i = 0; i < edges; i++){
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v); // Only u -> v because it is Directed
        }

        System.out.println("The above Graph has cycle: " + Is_Cycle(nodes, adj));
    }

    public static boolean Is_Cycle(int nodes, List<Integer>[] adj){
        int[] vis = new int[nodes + 1];
        Arrays.fill(vis, -1); // Initialize all as Unvisited

        // REVISION TIP: 1-based indexing check. Loop through every node to
        // handle disconnected components (forest of directed trees).
        for(int node = 1; node <= nodes; node++){
            if(vis[node] == -1){
                if(detect_cycle(node, vis, adj)) return true;
            }
        }
        return false;
    }

    public static boolean detect_cycle(int node, int[] vis, List<Integer>[] adj){
        // STEP 1: Mark as 1 (VISITING). We are now exploring this path.
        vis[node] = 1;

        for(int adjNode : adj[node]){
            // CASE 1: Neighbor is unvisited -> Recurse
            if(vis[adjNode] == -1){
                if(detect_cycle(adjNode, vis, adj)) return true;
            }
            // CASE 2: Neighbor is '1'. 
            // This means adjNode is an ancestor in the current recursion stack.
            // A "Back-Edge" exists -> CYCLE DETECTED!
            else if(vis[adjNode] == 1) {
                return true;
            }
            
            // CASE 3: If vis[adjNode] == 0, it's a "Cross-Edge".
            // This node was already fully explored in a different path. Ignore it.
        }

        // STEP 2: BACKTRACKING.
        // Mark as 0 (VISITED). We are popping this node off the recursion stack.
        // It is no longer part of the "Current Path."
        vis[node] = 0;

        return false;
    }
}