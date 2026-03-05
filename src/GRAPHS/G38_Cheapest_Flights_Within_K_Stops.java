package GRAPHS;

import java.util.*;

public class G38_Cheapest_Flights_Within_K_Stops {

    // BELOW THIS APPROACH IS DIJKSTRA PRUNING APPROACH: 
    // THIS IS THE MOST OPTIMAL AND STRAIGTFORWARD APPROACH :
    /*
     * =========================================================================
     * GOAL: Find the cheapest flight path from 'src' to 'dst' with AT MOST 'K' stops.
     * * * THE "1-YEAR REVISION" INTUITION:
     * 1. STOPS AS LEVELS: Since every flight (edge) counts as exactly 1 stop, 
     * this is a Level-Order problem. 0 stops = Level 0, 1 stop = Level 1, etc.
     * 2. WHY BFS > DIJKSTRA HERE: Dijkstra prioritize Price. But a cheap price 
     * might use too many stops. BFS prioritize STOPS (Levels). By processing 
     * level-by-level, we naturally stay within the 'K' limit.
     * 3. THE K+1 RULE: "At most K stops" means we can take a maximum of K+1 flights 
     * (edges). So the BFS must run for K+1 levels.
     * * * DRY RUN EXAMPLE:
     * n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
     * * Initialization: costs = [0, INF, INF], que = [{0,0}], stops = 0
     * * LEVEL 0 (Processing node 0):
     * - Neighbor 1: newCost = 0+100 = 100. (100 < INF). costs[1]=100. que={ {1,100} }
     * - Neighbor 2: newCost = 0+500 = 500. (500 < INF). costs[2]=500. que={ {1,100}, {2,500} }
     * stops becomes 1.
     * * LEVEL 1 (Processing node 1 & 2):
     * - From node 1: Neighbor 2: newCost = 100+100 = 200. (200 < 500). costs[2]=200. que={ {2,200} }
     * - From node 2: No neighbors.
     * stops becomes 2. (stops > k, Loop Ends).
     * * RESULT: costs[2] is 200. (The 2-hop path was cheaper than the direct 500 path!)
     * =========================================================================
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

    // Approach 2: 
    // SIMPLE BFS.. TRAVEL TILL K+1 DISTANCE.. FROM SRC..
    private int cheapest_with_atmostK(int n , List<int[]>[] adj , int src , int dst , int k){
        
        int[] costs = new int[n];
        Arrays.fill(costs,Integer.MAX_VALUE);

        // Deque for Level-Order Traversal
        Deque<int[]> que = new ArrayDeque<>();
        // {current_city, total_cost_to_reach_it}
        que.offerLast(new int[]{src,0});
        costs[src] = 0;
        
        // 'stops' tracks how many flights (edges) we have taken so far.
        int stops = 0;

        // JUST TRAVEL WITH AT MOST K DISTANCE...
        // stops <= k ensures we can take one more flight (total k+1 edges)
        while(!que.isEmpty() && stops <= k){    
            int len = que.size();

            // Process all nodes at the CURRENT stop level (Standard BFS pattern)
            for(int i = 0 ; i < len ; i++){
                int[] route = que.pollFirst();
                int city = route[0];
                int cost = route[1];

                for(int[] flight : adj[city]){
                    int adjCity = flight[0];
                    int price = flight[1];

                    /* * ORIGINAL COMMENT:
                     * Here we need not to worry about stops, because if the price is cheaper 
                     * then either it will be the optimal or it won't reach the dst, but it 
                     * would not happen that we update a cheaper cost in costs[adjCity] and 
                     * that will not cover K stops, and also if the cheaper current has 
                     * updated and could not reach the dst the next path with more cost will 
                     * also be not reachble to dst, for this clarification.. 
                     * I RECOMMEND DEBUG USING PEN P PAPER...
                     */
                    if(costs[adjCity] > cost + price){
                        costs[adjCity] = cost + price;
                        que.offerLast(new int[]{adjCity , costs[adjCity]});
                    }
                }
            }
            // After processing one full level of flights, increment stops.
            stops++;
        } 

        // if dst was not reachable then its value will be unchangd... 
        if(costs[dst] == Integer.MAX_VALUE) return -1;
        else return costs[dst];
    }

    /*
     * GOAL: Find the cheapest flight path from 'src' to 'dst' with AT MOST 'K' stops.
     * * INTUITION: 
     * This is a multi-objective optimization problem (Price vs. Stops). 
     * STtandard Dijkstra fails here because it only optimizes for 'Price'. 
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
    public int findCheapestPrice2(int n, int[][] flights, int src, int dst, int k) {
        
        // Creating the adjacency lists...
        List<int[]> [] adj = new ArrayList[n];

        for(int i=0 ; i<n ; i++) adj[i] = new ArrayList<>();

        for(int[] flight : flights){    
            adj[flight[0]].add(new int[]{flight[1],flight[2]});
        }

        return cheapest_with_atmostK(n,adj,src,dst,k);
    }

    private int cheapest_with_atmostK2(int n , List<int[]>[] adj , int src , int dst , int k){
        
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
