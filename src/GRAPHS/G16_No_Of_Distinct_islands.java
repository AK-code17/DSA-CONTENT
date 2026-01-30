package GRAPHS;

import java.util.HashSet;
import java.util.Set;

/**
 * =====================================================================================
 * GOAL: 
 * Count the number of distinct (unique) island shapes in a binary grid. 
 * Two islands are identical if they can be moved (shifted) to match exactly.
 *
 * INTUITION: 
 * Since we cannot compare shapes by their absolute coordinates, we record the 
 * "Path" taken during a DFS traversal. If we explore every island using the 
 * exact same directional order, identical shapes will produce identical 
 * path strings (signatures).
 *
 * DATA STRUCTURES:
 * 1. HashSet<String>: Stores the unique path strings. It automatically handles 
 * duplicates so that identical shapes are only counted once.
 * 2. StringBuilder: Used to efficiently build the path string during DFS.
 * 3. int[][] vis: Tracks visited cells to ensure we don't process the same cell twice.
 * =====================================================================================
 */

public class G16_No_Of_Distinct_islands {
    /**
     * ALGORITHM - DFS Path Encoding:
     * 1. If the current cell is valid land, mark it visited and add the direction 
     * (R, U, L, D) to the path string.
     * 2. Recursively visit neighbors in a strict order: Right, Up, Left, Down.
     * 3. IMPORTANT: After visiting all neighbors, add a "B" (Backtrack) to the path.
     * This ensures that shapes with the same forward path but different 
     * branching structures are distinguished correctly.
     */
    void dfs(int row, int col, StringBuilder path, String dir, int[][] grid, int[][] vis) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Base case: Check bounds, water (0), or if already visited
        if (row < 0 || row >= rows || col < 0 || col >= cols || grid[row][col] == 0 || vis[row][col] == 1) {
            return;
        }

        vis[row][col] = 1; 
        path.append(dir); // Record current move

        // Visit neighbours in a fixed order
        dfs(row, col + 1, path, "R", grid, vis); // Right
        dfs(row - 1, col, path, "U", grid, vis); // Up
        dfs(row, col - 1, path, "L", grid, vis); // Left
        dfs(row + 1, col, path, "D", grid, vis); // Down

        path.append("B"); // Record backtracking
    }

    /**
     * SUMMARY:
     * - Time Complexity: O(M * N) where M is rows and N is columns.
     * - Space Complexity: O(M * N) for the visited array and the Set of strings.
     */
    int countDistinctIslands(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        
        Set<String> dist_islands = new HashSet<>();
        int m = grid.length;
        int n = grid[0].length;
        int[][] vis = new int[m][n];
        
        // Scan every cell in the grid
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                
                // If unvisited land is found, it's the start of a new island
                if (grid[r][c] == 1 && vis[r][c] == 0) {
                    StringBuilder path = new StringBuilder();
                    
                    // 'S' marks the start of the island traversal
                    dfs(r, c, path, "S", grid, vis); 
                    
                    // Add the completed path signature to the HashSet
                    dist_islands.add(path.toString());
                }        
            }
        }
        
        // The size of the set is the count of unique island shapes
        return dist_islands.size(); 
    }
}
