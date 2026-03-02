package GRAPHS;
import java.util.*;

public class G_001_All_Shortest_Paths_Undirected_Weighted {

    // Helper class to wrap the logic you developed..
    static class Solution {
        List<List<Integer>> results = new ArrayList<>();

        public List<List<Integer>> findAllPaths(int n, int[][] edges, int src, int target) {
            // Step 1: BUILD ADJACENCY LIST..
            List<List<int[]>> adj = new ArrayList<>();
            for(int i = 0; i <= n; i++) adj.add(new ArrayList<>());
            for(int[] e : edges) {
                // edge format: {u, v, weight}
                adj.get(e[0]).add(new int[]{e[1], e[2]});
                adj.get(e[1]).add(new int[]{e[0], e[2]});
            }

            int[] dist = new int[n + 1];
            Arrays.fill(dist, Integer.MAX_VALUE);
            
            // OG Parent Logic: A list of parents for every node to capture "Ties"..
            List<Integer>[] parents = new ArrayList[n + 1];
            for(int i = 0; i <= n; i++) parents[i] = new ArrayList<>();

            dist[src] = 0;
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
            pq.offer(new int[]{src, 0});

            while(!pq.isEmpty()) {
                int node = pq.peek()[0];
                int d = pq.peek()[1];
                pq.poll();

                // THE ZOMBIE CHECK (STRIKER STYLE)..
                // Critical for maintaining O(E log V) in Java's PriorityQueue..
                if(d > dist[node]) continue;

                for(int[] edge : adj.get(node)) {
                    int adjNode = edge[0];
                    int weight = edge[1];

                    // CASE 1: Found a STRICTLY SHORTER path..
                    if(d + weight < dist[adjNode]) {
                        dist[adjNode] = d + weight;
                        parents[adjNode].clear(); // Clear suboptimal parents..
                        parents[adjNode].add(node);
                        pq.offer(new int[]{adjNode, dist[adjNode]});
                    }
                    // CASE 2: Found a path with EQUAL distance..
                    else if(d + weight == dist[adjNode]) {
                        parents[adjNode].add(node); // Append alternative optimal parent..
                    }
                }
            }

            // Step 2: RECONSTRUCT ALL PATHS (DFS/Backtracking)..
            backtrack(target, src, new ArrayList<>(), parents);
            return results;
        }

        private void backtrack(int curr, int src, List<Integer> currentPath, List<Integer>[] parents) {
            currentPath.add(curr);
            
            if(curr == src) {
                List<Integer> finalPath = new ArrayList<>(currentPath);
                Collections.reverse(finalPath);
                results.add(finalPath);
            } else {
                for(int p : parents[curr]) {
                    backtrack(p, src, currentPath, parents);
                }
            }
            
            // Backtrack: Remove last element to explore other parent branches..
            currentPath.remove(currentPath.size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes (n) and edges (m): ");
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] edges = new int[m][3];
        System.out.println("Enter edges (u v weight):");
        for (int i = 0; i < m; i++) {
            edges[i][0] = sc.nextInt();
            edges[i][1] = sc.nextInt();
            edges[i][2] = sc.nextInt();
        }

        System.out.print("Enter source and target: ");
        int src = sc.nextInt();
        int target = sc.nextInt();

        Solution sol = new Solution();
        List<List<Integer>> allShortestPaths = sol.findAllPaths(n, edges, src, target);

        System.out.println("\n--- All Shortest Paths ---");
        for (List<Integer> path : allShortestPaths) {
           System.out.println(path);
        }
        
        sc.close();
    }
}