package GRAPHS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DFS {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        int nodes = 8; // 1-9..
        int edges = 8;
        
        for(int i = 0 ; i<= nodes ; i++){
            adj.add(new ArrayList<>());
        }

        adj.get(1).add(2);
        adj.get(2).add(1);
        adj.get(1).add(3);
        adj.get(3).add(1);
        adj.get(2).add(5);
        adj.get(5).add(2);
        adj.get(2).add(6);
        adj.get(6).add(2);
        adj.get(3).add(4);
        adj.get(4).add(3);
        adj.get(3).add(7);
        adj.get(7).add(3);
        adj.get(4).add(8);
        adj.get(8).add(4);
        adj.get(7).add(8);
        adj.get(8).add(7);

        DFS traversal = new DFS();
        boolean vis[] = new boolean[adj.size()];
        ArrayList<Integer> ans = traversal.DFS(3,vis,adj);
        System.out.println(ans);

    }

    public ArrayList<Integer> DFS(int start,boolean vis[],ArrayList<ArrayList<Integer>> adj){
        ArrayList<Integer> ans = new ArrayList<>();
        
        // initially put the starting node of the dfs..
        ans.add(start);
        vis[start] = true;

        for(int ele : adj.get(start)){
            if(!vis[ele]) {
                ans.addAll(DFS(ele, vis, adj));
            }
        }

        return ans;
    }
}
