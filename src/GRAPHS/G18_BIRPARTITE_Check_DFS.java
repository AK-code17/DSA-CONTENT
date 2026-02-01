package GRAPHS;
import java.util.*;

public class G18_BIRPARTITE_Check_DFS { 
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int nodes = sc.nextInt();
        int edges = sc.nextInt();

        List<Integer>[] adj = new ArrayList[nodes+1];

        for(int i=0 ; i<=nodes ; i++){
            adj[i] = new ArrayList<>();
        }

        for(int i=0 ; i<edges ; i++){
            int u = sc.nextInt();
            int v = sc.nextInt();

            adj[u].add(v);
            adj[v].add(u);
        }

        System.out.println("The above Graphs is Bipartite : "+ Is_Bipartite(nodes,adj));
    }

    public static boolean Is_Bipartite(int nodes ,List<Integer>[] adj){
        int[] vis = new int[nodes+1];
        Arrays.fill(vis,-1);
        for(int node=1 ; node<=nodes ; node++ ){
            if(vis[node] == -1){
                if(!dfs(node,vis,adj,1)) return false;
            }
        }

        return true;
    }

    public static boolean dfs(int node , int[] vis ,List<Integer>[] adj , int color){
        vis[node] = color;

        for(int adjNode : adj[node]){
            // If not visited go and find..
            if(vis[adjNode] == -1){
                if(!dfs(adjNode,vis,adj,vis[node])) return false;
            }
            // if already visited then check if it has the same color as current node ..
            else if(vis[adjNode]  == color) return false;

        }

        return true;
    }
}
