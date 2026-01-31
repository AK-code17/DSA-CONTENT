package GRAPHS;

import java.util.*;

/**
 * GOAL: Check if a graph is Bipartite (can be colored with 2 colors).
 * INTUITION: A graph is Bipartite if it contains NO ODD-LENGTH CYCLES.
 * In BFS, we assign opposite colors to neighbors; if we find neighbors 
 * with the same color, a conflict exists (not Bipartite).
 */
public class G17_BIPARTITE_Check {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nodes = sc.nextInt();
        int edges = sc.nextInt();

        // Adjacency List: Array of Lists for CP efficiency
        List<Integer>[] adj = new ArrayList[nodes + 1];
        for (int i = 0; i <= nodes; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        System.out.println("The above graph is Bi-Partite : " + Is_Bipartite(adj));
    }

    public static boolean Is_Bipartite(List<Integer>[] adj) {
        int len = adj.length;
        int[] vis = new int[len];
        
        // REVISION TIP: int[] defaults to 0. Since 0 is a color, 
        // we must fill with -1 to represent "Not Visited".
        Arrays.fill(vis, -1);

        // OUTER LOOP: Essential to handle disconnected graph components
        for (int node = 1; node < len; node++) {
            if (vis[node] == -1) {
                if (!bfs(node, vis, adj)) return false; 
            }
        }
        return true;
    }

    private static boolean bfs(int start, int[] vis, List<Integer>[] adj) {
        // CP TIP: ArrayDeque is faster than LinkedList for Queue operations
        Deque<Integer> que = new ArrayDeque<>();
        que.offerLast(start);
        vis[start] = 0; // Assign first color (0)

        while (!que.isEmpty()) {
            int node = que.pollFirst();

            // TRICK: The color of any neighbor must be (1 - current_color)
            int adjColour = 1 - vis[node];

            for (int adjNode : adj[node]) {
                // CASE 1: Neighbor is not visited -> Color it and add to Queue
                if (vis[adjNode] == -1) {
                    vis[adjNode] = adjColour;
                    que.offerLast(adjNode);
                } 
                // CASE 2: Neighbor is visited -> Check for color conflict
                // If adjacent nodes have the same color, it's an Odd Cycle!
                else if (vis[adjNode] == vis[node]) {
                    return false;
                }
            }
        }
        return true;
    }
}