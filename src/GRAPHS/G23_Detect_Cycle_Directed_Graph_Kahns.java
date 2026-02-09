package GRAPHS;

import java.util.*;

public class G23_Detect_Cycle_Directed_Graph_Kahns {
    public static void main(String[] args) {

        /*
        10 11
        0 1
        1 2
        2 3
        3 4
        4 5
        2 6
        6 4
        7 1
        7 8
        9 7
        8 9
        */
        Scanner sc = new Scanner(System.in);
        int nodes = sc.nextInt();
        int edges = sc.nextInt();

        List<Integer>[] adj = new ArrayList[nodes];
        for (int i = 0; i < nodes; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
        }

        System.out.println("The graph is cyclic ? : " + Check_Cycle(adj));
    }

    /*
    Here our main logic is to check whether we can form a topo sort , if its a DAG then its node count == n , 
    if node_count < n it means it has a cycle , bcz toposort produces size n..
    */
    private static boolean Check_Cycle(List<Integer>[] adj) {
        int nodes = adj.length;
        // Here we do not need visited array because all the nodes of graph are added once if they have adjacent 0
        // and removed once while traversing their adjacent's..
        int[] indegree = new int[nodes]; // storing the individual indegree of nodes..
        // int[] topo_sort = new int[nodes]; 
        int count_nodes = 0;

        // Populate the indegree of nodes by traversing to each list -> nodes..
        for (int i = 0; i < nodes; i++) {
            for (int node : adj[i]) {
                indegree[node]++;
            }
        }

        Deque<Integer> que = new ArrayDeque<>();

        // Initially add all the nodes having indegree 0;
        for (int node = 0; node < nodes; node++) {
            if (indegree[node] == 0) que.offerLast(node);
        }

        /*
        We are adding the nodes of indegree 0 bcz, these are the elements who do not have a prev ordering meaning
        there isn't a node which is able to reach this, so for these nodes we do not have to maintain a proper 
        ordering so we keep removing them from que and then add that node in the ans , explore all its adjacent
        and add the adjNodes with 0 indegreees....
        */
        while (!que.isEmpty()) {
            int curr_node = que.pollFirst();
            count_nodes++;

            // Traverse across the adjacent nodes and decrement their indegree count..
            for (int adjNode : adj[curr_node]) {
                // now since a node visited it means its count of indegree will be decremented..
                indegree[adjNode]--;

                if (indegree[adjNode] == 0) que.offerLast(adjNode);
            }
        }
        
        // count_nodes < nodes means it means that our graph has cycle..
        return count_nodes != nodes;
    }
}
