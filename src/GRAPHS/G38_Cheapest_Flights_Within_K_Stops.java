package GRAPHS;

import java.util.*;

public class G38_Cheapest_Flights_Within_K_Stops {
    /*
     * GOAL: Find the cheapest flight path from 'src' to 'dst' with AT MOST 'K' stops.
     * * INTUITION: 
     * This is a multi-objective optimization problem (Price vs. Stops). 
     * Standard Dijkstra fails here because it only optimizes for 'Price'. 
     * We need a "High-Potential" logic: A path is only useless if it is BOTH 
     * more expensive AND has more stops than a path we've already seen.
     *
     * APPROACH: "1D Non-Dominance Dijkstra"
     * 1. Use two tracking arrays: min_costs[] and min_stops[].
     * 2. Only push a path to the PriorityQueue if it's CHEAPER or has FEWER STOPS.
     * 3. Price-based PQ ensures the first time we 'poll' the destination, it's our winner.
     *
     * WHERE IT FAILS (The Trap): 
     * If you only use min_costs[], a cheap path with 10 stops might "block" a $10 
     * more expensive path with 2 stops. If K=5, the cheap path fails later, and 
     * you've already wrongly pruned the only path that could actually reach 'dst'.
     *
     * EXAMPLE: K = 1 (Max 2 flights)
     * Path A: src -> B ($50, 0 stops) -> C ($50, 1 stop) -> dst ($50, 2 stops) [TOTAL: $150, 2 stops - INVALID]
     * Path B: src -> D ($100, 0 stops) -> dst ($50, 1 stop) [TOTAL: $150, 1 stop - VALID]
     * If Dijkstra sees Path A first, it might prune Path B's entry to 'D' or 'dst' because 
     * it's "more expensive," not realizing Path A will eventually die due to K=1.
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        
        // Creating the adjacency lists...
        List<int[]> [] adj = new ArrayList[n];

        for(int i=0 ; i<n ; i++) adj[i] = new ArrayList<>();

        for(int[] flight : flights){    
            adj[flight[0]].add(new int[]{flight[1],flight[2]});
        }

        return cheapest_with_atmostK(n,adj,src,dst,k);
    }

    private int cheapest_with_atmostK(int n , List<int[]>[] adj , int src , int dst , int k){
        
        int[] min_costs = new int[n];
        Arrays.fill(min_costs,Integer.MAX_VALUE);

        int[] min_stops = new int[n];
        Arrays.fill(min_stops , Integer.MAX_VALUE);

        // Standard Dijkstra: Priority to the cheapest price..
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2]-b[2]);
        // new int[]{city , stops , price}..
        pq.offer(new int[]{src,0,0});
        min_costs[src] = 0;
        min_stops[src] = 0;

        while(!pq.isEmpty()){
            int[] route = pq.poll();
            int city = route[0];
            int stop =  route[1];
            int cost = route[2];

            // If we poll it, it's the cheapest valid price so far..
            if(city == dst) return cost;

            // early termination: if we already used K stops, we can't take another flight to a NEW city..
            if(stop > k) continue;
            
            // for future calls we need +1 , stops
            int nextStop = stop + 1;
            
            for(int[] adjflight : adj[city]){
                int adjCity = adjflight[0];
                int price = adjflight[1];
                int newCost = cost + price;

                // CRUX: Path is only better if it's cheaper OR if it has fewer stops (higher survival potential)..
                if(min_costs[adjCity] > newCost){
                    min_costs[adjCity] = newCost;
                    min_stops[adjCity] = nextStop; // Update survival record..
                    pq.offer(new int[]{adjCity, nextStop, newCost});
                }
                // Also if it has fewer stops than the record, it's a probable candidate..
                else if(nextStop < min_stops[adjCity]){
                    min_stops[adjCity] = nextStop;
                    // We don't update min_costs here because this path is more expensive,
                    // but we push it because it has more "stop-budget" left..
                    pq.offer(new int[]{adjCity, nextStop, newCost});
                }
            }
        }

        return -1;
    }
}
