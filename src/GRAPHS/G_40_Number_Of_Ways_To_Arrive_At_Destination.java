package GRAPHS;

import java.util.*;

public class G_40_Number_Of_Ways_To_Arrive_At_Destination {
    /*
     * =========================================================================
     * 🧠 MECHANICAL DEEP DIVE: THE "WHY" & "HOW" OF PATH PROPAGATION
     * =========================================================================
     * 1. THE "HOW": INHERITANCE VS. AGGREGATION
     * Think of each node as a reservoir. 'parents[u]' is the amount of "water" 
     * (paths) currently held at node 'u'.
     * * - INHERITANCE (New Min): When we find a shorter path to 'v' via 'u', we 
     * realize the old water in reservoir 'v' was "stale" (not shortest). 
     * We flush it and pour ALL of 'u's water into 'v'.
     * -> parents[v] = parents[u]
     * * - AGGREGATION (Equal Min): When a path via 'u' reaches 'v' in the same 
     * time as the current best, we keep 'v's current water and ADD 'u's water 
     * to it.
     * -> parents[v] = (parents[v] + parents[u]) % MOD
     *
     * 2. THE "WHY" (The Global Summation):
     * This works beautifully because Dijkstra processes nodes in increasing 
     * order of distance. By the time we "settle" node 'v', we have already 
     * processed every possible 'u' that could reach 'v' at an earlier or equal 
     * time. 
     *
     * Every single unique branch that splits off and later merges back at the 
     * same total distance is accounted for. The 'parents' count at the 
     * destination is the sum of all these valid branches.
     *
     * 3. TYPE SAFETY (Long & Modulo):
     * - min_time[]: long (prevents overflow during distance summation).
     * - parents[]: int (kept small via MOD at every addition).
     * - Promotion: (1L * parents[v] + parents[u]) ensures the addition doesn't 
     * overflow 32-bit space before the modulo reduces it.
     * =========================================================================
     */
    final int MOD = 1_000_000_007;

    public int countPaths(int n, int[][] roads) {
        List<int[]>[] adj = new ArrayList[n];
        for(int i=0 ; i<n ; i++) adj[i] = new ArrayList<>();

        for(int[] route : roads){
            adj[route[0]].add(new int[]{route[1] , route[2]});
            adj[route[1]].add(new int[]{route[0], route[2]});
        }

        long[] min_time = new long[n];
        Arrays.fill(min_time, Long.MAX_VALUE);

        // This array stores the "Path Count" for each node
        int[] parents = new int[n];

        PriorityQueue<long[]> pq = new PriorityQueue<>(
            Comparator.comparingLong(a -> a[1])
        );

        pq.offer(new long[]{0, 0});
        min_time[0] = 0;
        parents[0] = 1; // Base case: 1 way to be at the start

        while(!pq.isEmpty()){
            long[] road = pq.poll();
            int val = (int)road[0];
            long time = road[1];

            if(time > min_time[val]) continue;

            for(int[] adjRoad : adj[val]){
                int adjVal = adjRoad[0];
                long adjTime = adjRoad[1];

                // RELAXATION LOGIC
                if(min_time[adjVal] >= time + adjTime){
                    
                    // Case 1: STRICTLY BETTER - Flush and Inherit
                    if(min_time[adjVal] > time + adjTime) {
                        min_time[adjVal] = time + adjTime;
                        parents[adjVal] = parents[val]; 
                        pq.offer(new long[]{adjVal , min_time[adjVal]});
                    }
                    // Case 2: EQUALLY GOOD - Accumulate/Sum
                    else {
                        // The beauty: This sums up all incoming shortest path branches
                        parents[adjVal] = (int)((1L * parents[adjVal] + parents[val]) % MOD);
                    }
                }
            }
        }

        return parents[n-1];
    }
}
