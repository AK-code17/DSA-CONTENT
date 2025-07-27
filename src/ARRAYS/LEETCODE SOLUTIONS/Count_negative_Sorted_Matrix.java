public class Count_negative_Sorted_Matrix {
    public static void main(String[] args) {
        int[][] grid = {{4, 3, 2, -1}, {3, 2, 1, -1}, {1, 1, -1, -2}, {-1, -1, -2, -3}};
        System.out.println(countNegatives(grid)); // line 4
    }

    static int countNegatives(int[][] grid) {
        int cnt = 0;
        int row = grid.length - 1; // Start from the last row
        int colns = grid[0].length - 1; // Number of columns in the last row
        int coln_end = colns; // Start from the last column

        while (row >= 0) {
            // Ensure coln_end is within bounds
            if (coln_end >= 0 && grid[row][coln_end] < 0) {
                cnt++; // Count the negative number
                coln_end--; // Move to the next column to the left
            }

            // If we've reached the beginning of the row, move to the previous row
            if (coln_end < 0) {
                coln_end = colns; // Reset to the last column
                row--; // Move to the previous row
            }
        }

        return cnt;
    }
}
