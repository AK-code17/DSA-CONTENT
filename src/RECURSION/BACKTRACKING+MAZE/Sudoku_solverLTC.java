import java.util.Arrays;

public class Sudoku_solverLTC {
    public static void main(String[] args) {
        char[][] board = {
            {'5', '3' , '.' , '.' , '7' , '.' , '.' , '.' , '.'},
            {'6', '.' , '.' , '1' , '9' , '5' , '.' , '.' , '.'},
            {'.', '9' , '8' , '.' , '.' , '.' , '.' , '6' , '.'},
            {'8', '.' , '.' , '.' , '6' , '.' , '.' , '.' , '3'},
            {'4', '.' , '.' , '8' , '.' , '3' , '.' , '.' , '1'},
            {'7', '.' , '.' , '.' , '2' , '.' , '.' , '.' , '6'},
            {'.', '6' , '.' , '.' , '.' , '.' , '2' , '8' , '.'},
            {'.', '.' , '.' , '4' , '1' , '9' , '.' , '.' , '5' },
            {'.', '.' , '.' , '.' , '8' , '.' , '.' , '7' , '9' }
        };
        
        sudoku_cobmns(board, 0, 0);
    }

    static void sudoku_cobmns(char[][] board, int r, int c) {
        // Base case: If we've completed all rows, display the board and return true
        if (r == board.length) {
            // display(board);
            return;
        }

        // If we've reached the end of the column, move to the next row
        if (c == board.length) {
            sudoku_cobmns(board, r + 1, 0);
            return;
        }

        // Skip pre-filled cells
        if (board[r][c] != '.') {
            sudoku_cobmns(board, r, c + 1);
            return;
        }

        // Try placing a number in the empty cell
        for (int num = 1; num <= board.length; num++) {
            if (isSafe(board, r, c, num)) {
                board[r][c] = (char) (num+'0');
                sudoku_cobmns(board, r, c + 1) ; 
                board[r][c] = '.'; // Backtrack if the number didn't work
            }
        }
    
    }

    static boolean isSafe(char[][] board, int r , int c, int num) {
        // Check the column for duplicates
        for (int row = 0; row < board.length; row++) {
            if (board[row][c] == (char)(num+'0')) {
                return false; // Number already in the column
            }
        }

        // Check the row for duplicates
        for (int col = 0; col < board.length; col++) {
            if (board[r][col] == (char)(num+'0')) {
                return false; // Number already in the row
            }
        }

        int r_s = (r/3) * 3 ;
        int c_s = (c/3) * 3;

        int r_e = r_s+3 ; 
        int c_e = c_s+3 ; 

        for (int row = r_s; row < r_e; row++) {
            for (int col = c_s; col < c_e; col++) {
                if(board[row][col] == (char)(num+'0')) return false ; 
            }
        }

        return true;
    }
    
    static void display(char[][] board) {
        char [][] tempo = board ; 
        
        for (char[] arr : tempo) {
            for(char val : arr){
                System.out.println(val+"");
            }
        }
        System.out.println("* ------------ *");
    }
}
