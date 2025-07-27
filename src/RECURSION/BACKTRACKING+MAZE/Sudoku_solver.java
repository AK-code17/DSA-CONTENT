import java.util.Arrays;

public class Sudoku_solver {
    public static void main(String[] args) {
        int[][] board = {
            {5, 3 , 0 , 0 , 7 , 0 , 0 , 0 , 0 },
            {6, 0 , 0 , 1 , 9 , 5 , 0 , 0 , 0 },
            {0, 9 , 8 , 0 , 0 , 0 , 0 , 6 , 0 },
            {8, 0 , 0 , 0 , 6 , 0 , 0 , 0 , 3 },
            {4, 0 , 0 , 8 , 0 , 3 , 0 , 0 , 1 },
            {7, 0 , 0 , 0 , 2 , 0 , 0 , 0 , 6 },
            {0, 6 , 0 , 0 , 0 , 0 , 2 , 8 , 0 },
            {0, 0 , 0 , 4 , 1 , 9 , 0 , 0 , 5 },
            {0, 0 , 0 , 0 , 8 , 0 , 0 , 7 , 9 }
        };
        
        System.out.println("Starting Sudoku Solver...");
        sudoku_cobmns(board, 0, 0);
    }

    static boolean sudoku_cobmns(int[][] board, int r, int c) {
        // Base case: If we've completed all rows, display the board and return true
        if (r == board.length) {
            display(board);
            return true;
        }

        // If we've reached the end of the column, move to the next row
        if (c == board.length) {
            return sudoku_cobmns(board, r + 1, 0);
        }

        // Skip pre-filled cells
        if (board[r][c] != 0) {
            return sudoku_cobmns(board, r, c + 1);
        }

        // Try placing a number in the empty cell
        for (int num = 1; num <= board.length; num++) {
            if (isSafe(board, r, c, num)) {
                board[r][c] = num;
                if (sudoku_cobmns(board, r, c + 1)) {
                    return true; // Return true as soon as the solution is found
                }
                board[r][c] = 0; // Backtrack if the number didn't work
            }
        }

        return false; // No solution found, return false
    }

    static boolean isSafe(int[][] board, int r , int c, int num) {
        // Check the column for duplicates
        for (int row = 0; row < board.length; row++) {
            if (board[row][c] == num) {
                return false; // Number already in the column
            }
        }

        // Check the row for duplicates
        for (int col = 0; col < board.length; col++) {
            if (board[r][col] == num) {
                return false; // Number already in the row
            }
        }

        int r_s = (r/3) * 3 ;
        int c_s = (c/3) * 3;

        int r_e = r_s+3 ; 
        int c_e = c_s+3 ; 

        for (int row = r_s; row < r_e; row++) {
            for (int col = c_s; col < c_e; col++) {
                if(board[row][col] == num) return false ; 
            }
        }

        return true;
    }
    
    static void display(int[][] board) {
        for (int[] arr : board) {
            System.out.println(Arrays.toString(arr));
        }
        System.out.println("* ------------ *");
    }
}
