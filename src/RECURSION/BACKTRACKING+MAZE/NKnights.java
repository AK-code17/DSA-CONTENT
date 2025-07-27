import java.util.* ; 

public class NKnights {
    public static void main(String[] args) {
        int n=3; 
        char[][] board = new char[n][n];
        for(char[] row : board){
            Arrays.fill(row,'-') ; 
        } 
        // System.out.println(queens(board, 0));
        bottom_3(board, 0, 0, n);

    }

    static int bottom(char [][] board , int row ,int col, int n){
        if(n == 0){
            display(board) ; 
            return 1 ; 
        }

        if(row == board.length) return 0; 

        // if(col == board.length || ){
        //     bottom(board, row+1,0, n);
        //     return ; 
        // }

        int cnt  =0 ;

        for (int r = row; r < board.length; r++) {
            int str_col = (r == row) ? col : 0; // Only start from `col` for the first row, then reset to 0
            for (int c = str_col; c < board.length; c++) {
                if (isSafe(board, r, c)) {
                    board[r][c] = 'K';
                    cnt += bottom(board, r, c + 1, n - 1);
                    board[r][c] = '-'; // Backtrack
                }
            }
        }

        
        return  cnt ; 
    }

    /*I was absolutely doing it correct firstly but what I was doing the erroe was that , I tried to move to the next recursion call whenever the col reaches to end , so it was vanishing other possibilities and 
    2nd mistake was that I was not writing cnt += while calling to the next row calling  . 
    
    Otherwise I need not to have wasted my 3 days . */

    
    
    static int bottom_2(char[][] board, int row, int col, int n) {
        if (n == 0) {
            display(board); // All knights placed, print the board.
            return 1;
        }
    
        if (row == board.length) {
            return 0; // No more rows to process.
        }
    
        int cnt = 0;
    
        // Loop through columns in the current row.
        for (int c = col; c < board.length; c++) {
            if (isSafe(board, row, c)) {
                board[row][c] = 'K'; // Place the knight.
                cnt += bottom_2(board, row, c + 1, n - 1); // Recurse for the next knight.
                board[row][c] = '-'; // Backtrack.
            }
        }
    
        // After finishing the row, move to the next row and start from column 0.
        cnt += bottom_2(board, row + 1, 0, n);
    
        return cnt;
    
    }

    //approach 3 , By KK menon . 

    static void bottom_3(char[][] board , int row , int col , int n){
        //Base case: 
        if(n == 0){
            display(board);
            return ; 
        }

        if(row == board.length) return; 

        //when ti=he col is out of bound so since we are just here checking the current col , we need not to worry about losing the possible combinations , the mistake which I was doing earlier . 
        
        int cnt = 0 ; 
        
        if(col != board.length){
            //checking the current cell is valid or not . 
            boolean check = isSafe(board, row, col)  ;
            if(check){
                board[row][col] = 'K' ; 
                bottom_3(board, row, col+1, n-1) ; 
                board[row][col] = '-' ; 
            }

            bottom_3(board, row, col+1, n) ; 
            
        }

        else{
            // When the column is out of bounds, move to the next row.
            bottom_3(board, row+1, 0, n) ; 
        }
    }
    

    static boolean isSafe(char[][]board,int row,int coln){
        //Not writing them properly because it helps me to understand aftewards while I am revising . 

        //Right top . 
        if(row-2>=0 && coln+1<board.length){  
            if(board[row-2][coln+1] == 'K') return false ; 
        } 

        //Left top . 
        if(row-2>=0 && coln-1>=0){
            if(board[row-2][coln-1] == 'K') return false ; 
        }
        
        //Right bottom . 
        if(row-1>=0 && coln+2<board.length){
            if(board[row-1][coln+2] == 'K') return false ; 
        }

        //Left bottom . 
        if(row-1>=0 && coln-2>=0){
            if(board[row-1][coln-2] == 'K') return false ; 
        }

        return true ; 
    }

    static void display(char[][] board){
        for(char[] arr : board){
            System.out.println(Arrays.toString(arr));
            System.out.println();
        }

        System.out.println("* ------------ * ");

    }

}
