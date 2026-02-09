package GRAPHS;

import java.util.*;

public class G22_Topological_Sort_Algorithm {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int nodes = sc.nextInt();
        int edges = sc.nextInt();

        List<Integer>[] adj = new ArrayList[nodes];

        for (int ind = 0; ind < adj.length; ind++) {
            adj[ind] = new ArrayList<>();
        }

        for(int i=0 ; i<edges ; i++){
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
        }
        
        int[] topo_sort = Topo_Sort_Algo(adj);
        System.out.println("The Topo Sort of Above graphs is : "+ Arrays.toString(topo_sort));

    }

    private static int[] Topo_Sort_Algo(List<Integer> [] adj){
        int len = adj.length;
        int[] ans = new int[len];
        boolean[] vis = new boolean[len];
        Deque<Integer> Stack = new ArrayDeque<>();

        for(int node = 0 ; node<len ; node++){
            if(!vis[node]) {
                dfs(node,adj,vis,Stack);
            }
        }
        
        // Here we can also reduce the Stack space by directly inserting the elements from backwards ,but then we will have to make t
        
        // the index variable as global and making global variables should be avoided as adviced by Striver earlier..
        // Now here the elements are present in the opposite order of their arrival from recent --> earlier..
        for(int node=0 ; node<len ; node++){
            ans[node] = Stack.pollLast();
        }

        return ans;
    }

    private static void dfs(int node , List<Integer>[] adj , boolean[] vis , Deque<Integer> Stack){
        vis[node] = true;
        
        for(int adjNode : adj[node]){
            if(!vis[adjNode]) dfs(adjNode , adj , vis , Stack);
        }
        
        // Adding the current node in stack while returning, after exploring adjacent nodes..
        Stack.offerLast(node);
    }
}
