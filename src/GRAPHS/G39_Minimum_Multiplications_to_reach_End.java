package GRAPHS;

import java.util.*;

public class G39_Minimum_Multiplications_to_reach_End {
    /*
        * =========================================================================
        * 🛠️ 2-YEAR REFRESHER: GRAPH THEORY & SEARCH STRATEGY
        * =========================================================================
        * 1. THE PROBLEM AS A GRAPH:
        * - Nodes (V): Every number from 0 to 99,999.
        * - Edges (E): A directed edge exists from 'A' to 'B' if (A * num) % 10^5 = B.
        * - Weight: Every edge is exactly 1. This is a "Unit-Weight Directed Graph."
        * * 2. THE BFS "GURANTEE" (SHORT-CIRCUITING):
        * In an unweighted graph, Breadth-First Search (BFS) explores nodes in 
        * strictly increasing order of distance from the source. 
        * - Level 0: start
        * - Level 1: All nodes reachable by 1 multiplication.
        * - Level 2: All nodes reachable by 2 multiplications.
        * This ensures that the FIRST time you see a node, you've found the 
        * absolute shortest path to it. No "cheaper" path can exist later.
        * * 3. WHY BFS OVER DIJKSTRA (MATHEMATICAL EFFICIENCY):
        * - Dijkstra's Complexity: O(E log V). It uses a Heap to find the minimum.
        * - BFS Complexity: O(V + E). It uses a simple FIFO Queue.
        * Since all edges are "1", Dijkstra's Heap would sort a list of identical 
        * numbers. This is mathematically redundant. BFS achieves the same result
        * without the Log(V) overhead, making it faster in OAs and Interviews.
        * * 4. STATE SPACE & MEMORY:
        * With a modulo of 10^5, the "State Space" is finite. 
        * The array `min_stops` acts as a "Distance Table." If a value is -1, 
        * the state is "Unvisited." This prevents infinite loops in cycles.
        * =========================================================================
     */
    int minimumMultiplications(int[] arr, int start, int end) {

        if(start == end) return 0;

        // dist[] serves as both the shortest-path record and the 'visited' set.
        int[] min_stops = new int[100000];
        Arrays.fill(min_stops , -1);
        
        /* * ORIGINAL COMMENT:
         * Instead of DIJKSTRA WE CAN USE Normal que can be used because here we need 
         * to get the elements will lesser stops, so that is equal to level order 
         * traversal BECAUSE IF WE POLL FROM THE PQ IN DIJKSRTRA THEN IT WILL GIVE 
         * ME THE SHORTEST STOPS FIRST, THAT IS NOTHING BUT THE STOPS IN ASCENDING ORDER..
         */
        Deque<Integer> que = new ArrayDeque<>();
        min_stops[start] = 0;
        que.offerLast(start);
        
        while(!que.isEmpty()){
            int val = que.pollFirst();
            int stops = min_stops[val];
            
            /* * ORIGINAL COMMENT:
             * It is guranteed the first node we get it will surely have minimum steps...
             * because it can happen that we might find a path having better values, 
             * but HERE DISTANCE IS THE MAIN PRIORITY..
             */
            if(val == end) return stops;
            
            for(int num : arr){
                // State Transition: (Current * Multiplier) % 10^5
                // Cast to long to handle intermediate product before modulo.
                int product = (int)(((long)num * val) % 100000);
                
                /* * ORIGINAL COMMENT:
                 * If its -1 that means it has been visited first time, 
                 * and via bfs node visited first time is the min path to visit
                 * that node, so update and push UPDATE AND THEN PUSH IF 
                 * CURRENT STOP (IS/ARE) EFFICIENT...
                 */
                if(min_stops[product] == -1){
                    min_stops[product] = stops + 1;
                    
                    // Early exit: Validate target state during expansion phase.
                    // This is an O(1) optimization that skips the last queue cycle.
                    if(product == end) return min_stops[product];
                    
                    que.offerLast(product);
                }
            }
        }
        
        return -1;
    }
}
