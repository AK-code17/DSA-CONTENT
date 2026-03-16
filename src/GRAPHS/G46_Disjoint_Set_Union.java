package GRAPHS;

import java.util.*;

public class G46_Disjoint_Set_Union {
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter total_nodes");
        int nodes = sc.nextInt();
        DisjointSetUnion dsu = new DisjointSetUnion(nodes);
        
        // int edges = sc.nextInt();
        dsu.UnionbyRank(1, 2);
        dsu.UnionbyRank(2   , 3);
        dsu.UnionbyRank(4, 5);
        dsu.UnionbyRank(6, 7);
        dsu.UnionbyRank(5,6);

        dsu.Is_Same_Group(3,7);

        dsu.UnionbyRank(3,7);

        dsu.Is_Same_Group(3,7);

    }

    static class DisjointSetUnion{
        int[] parent;
        int[] rank;

        protected DisjointSetUnion(int n) {
            parent = new int[n+1];
            rank = new int[n+1];

            for(int i=0 ; i<=n ; i++){
                // Initialized the parent of node , with itself..
                parent[i] = i;
                rank[i]= 0;
            }
        }

        // PATH COMPRESSION : WHERE WE TRY TO REDUCE THE LENGTH FROM CHILDRREN TO THEIR ROOT...
        protected int find_root(int node){
            // base case : 
            if(node == parent[node]) return node;
            
            // backtracking here , we assign the parent of current node to prev call , this assings each node in the 
            // bottom to have the root as the parent..
            return parent[node] = find_root(parent[node]);

        }

        protected void UnionbyRank(int u , int v){
            int root_u = find_root(u);
            int root_v = find_root(v);

            // If roots are the same just return...
            if(root_u == root_v) return;

            // else check respective ranks and update the parent and rank(if needed)..
            if(rank[root_u] > rank[root_v]) parent[root_v] = root_u;
            else if (rank[root_u] < rank[root_v]) parent[root_u] = root_v;

            // else both of them have the same rank attach one to other and update the rank to whom other gets attached...
            else{
                parent[root_v] = root_u;
                rank[root_u]++;
            }
        }

        protected void Is_Same_Group(int u , int v){
            if (find_root(u) == find_root(v)) System.out.println(u + " " + v + " Same group");
            else System.out.println(u + " " + v + " Differ group");
        }
    }
}
