package GRAPHS;

import java.util.*;

public class G43_City_With_Smallest_Reachable_Cities {
    /**
     🛰️ ARCHITECTURAL BLUEPRINT: GEOSPATIAL CONNECTIVITY OPTIMIZATION
     -------------------------------------------------------------------------
     🚀 PROBLEM SCOPE:
     We need to identify a central 'hub' (city) that has the least amount of 
     connectivity to other nodes within a strict latency bound (distanceThreshold).
     If multiple hubs share the same connectivity count, we prioritize the 
     highest-indexed node (The "Tie-Breaker").
    
     🏗️ DESIGN PATTERN: MULTI-SOURCE DIJKSTRA (DISTRIBUTED APPROACH)
     While Floyd-Warshall is the traditional "All-Pairs" solver, it is memory-heavy O(V²).
     We chose Multi-Source Dijkstra because:
     1. MEMORY EFFICIENCY: It maintains a O(V) local state per iteration.
     2. SPARSITY HANDLING: In graphs where E << V², it outperforms O(V³).
     3. EARLY EXIT: Unlike FW, Dijkstra can stop exploring a path the moment 
     the 'distanceThreshold' is breached.
    
     ⏱️ COMPLEXITY ANALYSIS:
     - Time: O(V  * (E log V)) — Running Dijkstra V times.
     - Space: O(V + E) — Adjacency List + local distance array.
     -------------------------------------------------------------------------
    /
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        
        /**
         * 1. TOPOLOGY INITIALIZATION
         * We represent the city network as an Adjacency List. This provides
         * O(1) access to neighbors, crucial for the iterative relaxation 
         * steps in Dijkstra.
         */
        List<int[]>[] adj = new ArrayList[n];
        for(int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for(int[] edge : edges) {
            int src = edge[0], dst = edge[1], cost = edge[2];
            adj[src].add(new int[]{dst, cost});
            adj[dst].add(new int[]{src, cost});
        }

        // 'min_city' tracks the current best candidate (highest index by default).
        // 'min_city_cnt' tracks the lowest number of reachable cities found so far.
        int min_city = n - 1;
        int min_city_cnt = n;

        /**
         * 2. ITERATIVE STATE EXPLORATION
         * We process each city as a potential origin. By iterating from (n-1)
         * down to 0, we ensure that the first city to hit a specific 'min_city_cnt'
         * is the one with the highest index, satisfying the tie-breaker rule.
         */
        for(int src = n - 1; src >= 0; src--) {
            
            // LOCAL HEURISTIC: A fresh distance map for the current origin.
            int[] min_dist = new int[n];
            Arrays.fill(min_dist, Integer.MAX_VALUE);
            min_dist[src] = 0; 

            // PriorityQueue ensures we always expand the "cheapest" node first.
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
            pq.offer(new int[]{src, 0});
            
            int city_cnt = 0;
            
            while(!pq.isEmpty()) {
                int[] node = pq.poll();
                int tar = node[0];
                int currentDist = node[1];

                // OPTIMIZATION: Stale path pruning.
                if(currentDist > min_dist[tar]) continue;

                for(int[] adjNode : adj[tar]) {
                    int adjCity = adjNode[0];
                    int edgeWeight = adjNode[1];
                    int totalDist = currentDist + edgeWeight;

                    /**
                     * ⚡ THE CORE RELAXATION & STATE-TRANSITION LOGIC:
                     * A city is only counted if it crosses the "threshold barrier."
                     * This 'On-The-Fly' counting avoids a redundant O(V) pass 
                     * after the Dijkstra execution completes.
                     */
                    if(min_dist[adjCity] > totalDist) {
                        
                        // Condition: City was unreachable/over-limit, but is now within threshold.
                        if(min_dist[adjCity] > distanceThreshold && totalDist <= distanceThreshold) {
                            city_cnt++;
                        }
                        
                        min_dist[adjCity] = totalDist;
                        pq.offer(new int[]{adjCity, totalDist});
                    }
                }
            }

            /**
             * 3. GLOBAL MINIMIZATION
             * We apply a greedy update. If the current source city reaches 
             * fewer nodes than our previous best candidate, we pivot.
             */
            if(city_cnt < min_city_cnt) {
                min_city_cnt = city_cnt;
                min_city = src;
            }
        }

        return min_city;


        /**
         * 🏛️ LEGACY ALTERNATIVE: FLOYD-WARSHALL ALGORITHM (ALL-PAIRS)
         * ---------------------------------------------------------------------
         * 🚀 USE CASE: Ideal when the graph is small (N < 500) and very dense.
         * 🚀 WHY: Computes every possible shortcut globally in O(V³) time.
         * ---------------------------------------------------------------------
         * int[][] min_dist = new int[n][n];
         * int max_dist = Integer.MAX_VALUE;
         *
         * // 1. INITIALIZE MATRIX
         * for(int r=0; r<n ; r++){
         * for(int c=0 ; c<n ; c++){
         * min_dist[r][c] = max_dist;
         * }
         * min_dist[r][r] = 0;
         * }
         *
         * // 2. POPULATE DIRECT EDGES
         * for(int[] edge : edges ){
         * min_dist[edge[0]][edge[1]] = edge[2];
         * min_dist[edge[1]][edge[0]] = edge[2];
         * }
         * * // 3. SHORTEST PATH PROPAGATION (Tri-nested)
         * for(int stop = 0 ; stop < n ; stop++){
         * for(int start = 0; start < n ; start++){
         * int part1 = min_dist[start][stop];
         * if(part1 == max_dist) continue;
         *
         * for(int end=0 ; end < n ; end++){
         * int part2 = min_dist[stop][end];
         * if(part2 != max_dist && min_dist[start][end] > part1 + part2){
         * min_dist[start][end] = part1 + part2;
         * }
         * }
         * }
         * }
         *
         * // 4. HUB SELECTION (Fewest neighbors within distanceThreshold)
         * int min_reachable_city = n - 1;
         * int min_cnt = n; 
         *
         * for(int src = n - 1; src >= 0 ; src--){
         * int cnt = 0;
         * for(int tar = 0 ; tar < n ; tar++){
         * if(src != tar && min_dist[src][tar] <= distanceThreshold) cnt++;    
         * }
         * if(cnt < min_cnt){
         * min_cnt = cnt;
         * min_reachable_city = src;
         * }
         * }
         * return min_reachable_city;
         */
    }

}
