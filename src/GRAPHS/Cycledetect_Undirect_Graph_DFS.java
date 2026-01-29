package GRAPHS;
import java.util.*;

public class Cycledetect_Undirect_Graph_DFS {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int nodes = sc.nextInt();
        int edges = sc.nextInt();

        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        for(int i=0 ; i<=nodes ; i++){
            adjList.add(new ArrayList<>());
        }

        for(int i=0 ; i<edges ; i++){
            System.out.println("Enter edge (u,v) : ");
            int u = sc.nextInt();
            int v = sc.nextInt();
            adjList.get(u).add(v);
            adjList.get(v).add(u);
        }


        System.out.println("The result of graphs having a cycle is "+Is_cycle(adjList));
    }

    static boolean Is_cycle(ArrayList<ArrayList<Integer>> adjList){
        int len = adjList.size();
        boolean[] vis = new boolean[len];
        
        for(int node = 1 ; node < len ; node++){
            if(!vis[node]){
                // If a node is not visited it means that there is no parent to this node hence we will send -1 , as its parent..
                if(detect_cycle(node,-1,vis,adjList)) return true;
            }
        }

        return false;
    }

    static boolean detect_cycle(int node ,int parent, boolean vis[] , ArrayList<ArrayList<Integer>> adjList){
        vis[node] = true;

        for(int adjNode : adjList.get(node)){
            // either the node will be unvDSAisited..
            if(!vis[adjNode]) {
                if (detect_cycle(adjNode,node,vis,adjList)) return true;
            }
            // else if it indicated a nonparent node is already been visited indicating the presence of a cycle..
            else if(adjNode != parent) return true;
        }

        return false;
    }
}
