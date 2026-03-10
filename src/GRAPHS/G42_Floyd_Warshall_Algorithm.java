package GRAPHS;

public class G42_Floyd_Warshall_Algorithm {
    /*
     * =========================================================================
     * 🛠️ 2-YEAR REFRESHER: FLOYD-WARSHALL (All-Pairs Shortest Path)
     * =========================================================================
     * 🚀 THE "WHY" OF FLOYD-WARSHALL:
     * While Dijkstra is for "Single Source," Floyd-Warshall is for "All-Pairs." 
     * It answers: "What is the shortest path between EVERY possible pair of 
     * nodes?" using Dynamic Programming.
     *
     * 🎯 THE CRUX: THE INTERMEDIATE NODE (The "Stop")
     * The heart of the algorithm is the 'stop' loop (outermost). 
     * - In round 0, we find the shortest paths using only node 0 as a possible stop.
     * - In round 1, we use nodes 0 and 1 as possible stops.
     * - By round N-1, we've considered every node as a potential shortcut.
     * * REVISION RULE: The 'stop' loop MUST be the outermost. If you put it 
     * inside, you won't correctly propagate the shortcuts.
     *
     * ⚠️ NEGATIVE CYCLE DETECTION:
     * After the algorithm finishes, check the diagonal (dist[i][i]). 
     * If any dist[i][i] < 0, it means there is a negative cycle passing through 
     * node 'i' because the path from a node to itself became negative.
     *
     * 🔢 COMPLEXITY:
     * Time: O(N³) -> Triple nested loops.
     * Space: O(N²) -> To store the adjacency matrix.
     * =========================================================================
     */
    public void floydWarshall(int[][] dist) {
        int nodes = dist.length;
        
        // Using a high value for infinity that won't overflow during addition
        int max_dist = (int)1e7; 
        
        // OUTER LOOP: Every node gets a turn to be an 'intermediate stop'
        for(int stop = 0 ; stop < nodes ; stop++){
            
            // INNER LOOPS: Check every pair (start, end)
            for(int start = 0 ; start < nodes ; start++){
                
                // OPTIMIZATION: Just skip the entire loop if start-->stop is max.
                // If we can't even reach the 'stop' node, we can't use it as a shortcut.
                if(dist[start][stop] == max_dist) continue;
                
                for(int end = 0 ; end < nodes ; end++){
                    
                    // RELAXATION LOGIC:
                    // Is the path (start -> end) longer than (start -> stop -> end)?
                    if(dist[stop][end] != max_dist && 
                       dist[start][end] > dist[start][stop] + dist[stop][end]){
                        
                        // Shortcut found! Update the global matrix.
                        dist[start][end] = dist[start][stop] + dist[stop][end];
                    }
                }
            }
        }
    }
}
