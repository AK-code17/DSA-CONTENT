package GRAPHS;
import java.util.*;

public class G21_Topological_Kahns_Algorithm {
    public static void main(String[] args) {

        /*
        Copy the input nodes , edges
        edges: 
        6 6 
        2 3
        3 1
        4 0
        4 1
        5 0
        5 2
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

        System.out.println("Topo sort using Kahn's Algo : " + Arrays.toString(Topo_Sort_Kahns_Algo(adj)));
    }

    private static int[] Topo_Sort_Kahns_Algo(List<Integer>[] adj) {
        int nodes = adj.length;
        // Here we do not need visited array because all the nodes of graph are added once if they have adjacent 0
        // and removed once while traversing their adjacent's..
        int[] indegree = new int[nodes]; // storing the individual indegree of nodes..
        int[] topo_sort = new int[nodes];
        int index = 0;

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
            topo_sort[index++] = curr_node;

            // Traverse across the adjacent nodes and decrement their indegree count..
            for (int adjNode : adj[curr_node]) {
                // now since a node visited it means its count of indegree will be decremented..
                indegree[adjNode]--;

                if (indegree[adjNode] == 0) que.offerLast(adjNode);
            }
        }

        return topo_sort;
    }
}