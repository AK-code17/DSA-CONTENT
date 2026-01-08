package GRAPHS;
import java.util.*;

public class Cycle_Undirect_Graph_BFS {
    // 1. Made it STATIC so static methods can access it
    static class Pair {
        int node;
        int parent;

        // 2. Used primitive 'int' for better memory/speed
        Pair(int node, int parent) {
            this.node = node;
            this.parent = parent;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter total nodes and total edges:");
        // Standard way for inputting graphs values..
        int nodes = sc.nextInt();
        int edges = sc.nextInt();

        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        // Initialize adjacency list
        for (int i = 0; i <= nodes; i++) {
            adj.add(new ArrayList<>());
        }

        System.out.println("Enter edges (u v) - you can type '1 2' on one line:");
        for (int i = 0; i < edges; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            // Add both ways for undirected graph
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        System.out.println("The result of Graph having cycle is "+Is_Cycle(adj));
    }

    static boolean Is_Cycle(ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis = new boolean[adj.size()];
        for (int i = 1; i < adj.size(); i++) {
            if (!vis[i]) {
                if (detect_cycle(i, adj, vis)) return true;
            }
        }
        return false;
    }

    static boolean detect_cycle(int startNode, ArrayList<ArrayList<Integer>> adj, boolean[] vis) {
        vis[startNode] = true;
        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(startNode, -1));

        while (!q.isEmpty()) {
            Pair current = q.poll();
            int val = current.node;
            int parent = current.parent;

            for (Integer adjNode : adj.get(val)) {
                if (!vis[adjNode]) {
                    vis[adjNode] = true;
                    q.offer(new Pair(adjNode, val));
                } 
                // 4. The core logic: If visited and not the parent, it's a cycle!
                else if (parent != adjNode) {
                    return true;
                }
            }
        }
        return false;
    }
}