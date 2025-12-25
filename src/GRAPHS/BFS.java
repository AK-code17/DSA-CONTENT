package GRAPHS;
import java.util.*;

public class BFS {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        int nodes = 12; // 1-9..
        int edges = 11;
        
        for(int i = 0 ; i<= nodes ; i++){
            adj.add(new ArrayList<>());
        }

        adj.get(1).add(2);
        adj.get(2).add(1);
        adj.get(1).add(6);
        adj.get(6).add(1);
        adj.get(2).add(3);
        adj.get(3).add(2);
        adj.get(2).add(4);
        adj.get(3).add(11);
        adj.get(11).add(3);
        adj.get(4).add(2);
        adj.get(6).add(7);
        adj.get(7).add(6);
        adj.get(6).add(9);
        adj.get(9).add(6);
        adj.get(9).add(12);
        adj.get(12).add(9);
        adj.get(4).add(5);
        adj.get(5).add(4);
        adj.get(4).add(5);
        adj.get(7).add(8);
        adj.get(8).add(7);
        adj.get(5).add(8);
        adj.get(8).add(5);

        BFS traversal = new BFS();
        ArrayList<Integer> ans = traversal.BFS(1, adj);
        System.out.println(ans);

    }

    public ArrayList<Integer> BFS(int start,ArrayList<ArrayList<Integer>> adj){
        ArrayList<Integer> ans = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        boolean vis[] = new boolean[adj.size()];
        // initially put the starting node of the bfs..
        q.offer(start);
        vis[start] = true;

        while (!q.isEmpty()) {
            int node = q.poll();
            ans.add(node);

            for (int cc : adj.get(node)) {
                if(!vis[cc]){
                    vis[cc] = true;
                    q.offer(cc);
                }
            }
        }

        return ans;
    }
}
