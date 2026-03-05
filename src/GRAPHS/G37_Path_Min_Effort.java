package GRAPHS;

import java.util.*;

public class G37_Path_Min_Effort {
    /*
     * GOAL: Find a path from (0,0) to (rows-1, cols-1) such that the 
     * MAXIMUM absolute difference between consecutive cells is MINIMIZED.
     * * INTUITION: This is a classic "Shortest Path" variation. 
     * 1. We use Dijkstra because the "Effort" (max diff) only stays same or increases
     * as we move—it never shrinks (Monotonicity).
     * 2. The first time we POLL the destination from the PriorityQueue, 
     * it is guaranteed to be the minimum possible effort path.
     */
    public int minimumEffortPath(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;

        // Create new Efforts grid where we will be storing the min possible efforts..
        int[][] Efforts = new int[rows][cols];
        for(int[] row  : Efforts){
            Arrays.fill(row,Integer.MAX_VALUE);
        }

        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        // sort by the minimum effort...
        PriorityQueue<int[]> que = new PriorityQueue<>((a,b) -> (a[2]-b[2]));
        que.offer(new int[]{0,0,0});
        Efforts[0][0] = 0;

        while(!que.isEmpty()){
            int[] coordinates = que.poll();
            int r = coordinates[0];
            int c = coordinates[1];
            int effort = coordinates[2];

            // IF CURRENT EFFORT IS SMALLEST AND IT IS THE END NODE, THEN IT IS SURELY THE MINIMUM EFFORT.
            if(r == rows-1 && c == cols-1) return effort;

            // optimization : if current effort > existing value , no need to check again..
            // it reduces the time to check all four adjacent cells...
            if(effort > Efforts[r][c]) continue;

            for(int[] dir : directions){
                int x = dir[0]+r;
                int y = dir[1]+c;

                if(x>=0 && x<rows && y>=0 && y<cols){
                    // IMPORTANT CODE BLOCK..

                    // SINCE WE WANT EFFORT TO BE THE MAX DIFF OF CONSECTUTIVE TILL NOW..
                    // First find the max diff till now...
                    int curr_diff = Math.abs(heights[r][c]-heights[x][y]);
                    int max_diff = Math.max(effort,curr_diff);

                    // Now we want our final ans to be the minimum effort..
                    // If new max_diff < existing max_diff , HURRAY!!! WE found minimum effort..
                    if(max_diff < Efforts[x][y]){
                        Efforts[x][y] = max_diff;
                        que.offer(new int[]{x,y,max_diff});
                    }
                }
            }
        }

        return -1;
    }
}
