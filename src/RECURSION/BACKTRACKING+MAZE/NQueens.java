import java.util.Arrays;

public class NQueens {
    public static void main(String[] args) {
        boolean[][] board = new boolean[5][5]; 
        // System.out.println(queens(board, 0));
        System.out.println(queens(board, 0));

    }

    static int queens(boolean [][] board , int row){
        if(row == board.length){
            display(board) ; 
            return 1 ; 
        }

        int count=0;
        //placing the queen and checking for every row and col . 
        for (int col = 0; col < board.length; col++) {
            //place the queen if it is safe . 
            boolean check = isSafe(board,row,col) ; 
            if(check){
                board[row][col] = true ; 
                count += queens(board, row+1) ; 
                board[row][col] = false ;
            }
             
        }

        return count ;
    }

    static boolean isSafe(boolean[][]board,int row,int coln){
        
        //for checking the coln
        for(int i=0 ; i<=row ; i++){
            if(board[i][coln]){
                return false ; 
            }
        }

        //for left diagonal . 
        int r=row ; 
        for (int c = coln; c>=0 && r>=0 ; c--,r--) {
            if(board[r][c]){
                return false ; 
            }
        }
        
        //for right diagonal . 
        r=row ; 
        for (int c = coln; c<board.length && r>=0 ; c++,r--) {
            if(board[r][c]){
                return false ; 
            }
        }
        
        return true ; 
    }

    static void display(boolean[][] board){
        for(boolean[] arr : board){
            for(boolean val : arr){
                if(val){
                    System.out.print("Q"+" ");
                }
                else{
                    System.out.print("X"+" ");
                }
            }
            System.out.println();
        }

        System.out.println("* ------------ * ");
    }
}
