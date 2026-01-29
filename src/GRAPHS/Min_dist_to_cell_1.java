package GRAPHS;

import java.util.*;

public class Min_dist_to_cell_1 {
        // Custom Pair class for the cell in the Queue..
    class Pair{
        int r;
        int c;
        
        Pair(int r , int c){
            this.r = r;
            this.c = c;
        }
    }
    
    public ArrayList<ArrayList<Integer>> nearest(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
         
        // Convert the matrix to ArrayList as the matrix provides faster implemntation
        int[][] ans = new int[m][n];
        
        // for efficient and faster addition and removal of elements..
        Deque<Pair> que = new ArrayDeque<>();
        
        for(int row=0 ; row<m ; row++){
            for(int col=0 ; col<n ;col++){
                // first we r trying to add the cells having value as 1.,and marking
                // their value to 0 in the ans matrix..
                if(grid[row][col] == 1){
                    ans[row][col] = 0;
                    que.offerLast(new Pair(row,col));
                }
                
                else ans[row][col] = Integer.MAX_VALUE;
            }
        }
        
        int[][] LRUD = {{-1,0},{1,0},{0,-1},{0,1}};
        while(!que.isEmpty()){
            
            int curr_row = que.peekFirst().r;
            int curr_col = que.peekFirst().c;
            que.pollFirst();
            
            for(int[] dir : LRUD){
                
                int x = dir[0];
                int y = dir[1];
                
                // adjacent row and columns..
                int adj_row = curr_row+x;
                int adj_col = curr_col+y;
                
                /*
                 Now here is the IMPORTANT PART.. we first checked for the valid-
                 ation of the newly created adjacent cell and then WE ARE CHECKING
                 IF ITS MAX OR NOT , BECAUSE IT THE VALUE THERE IS MAX IT MEANS 
                 THAT IT HAS VALUE 0 IN THE GRID BECAUSE IF IT WAS 1 , IT WOULD HAVE
                 BEEN =0 , BECAUSE WE ALREADY INITALIED THAT VALUE WHILE ADDING CELL
                 OF VALUE 1.. Also VALUE AS AN MAX SYMBOLIZES ONE MORE THING THAT
                 THIS CELL HAS NOT FOUND A CELL WITH 1 , SO NOW COMES THE MAGIC OF BFS
                 IT ENSURES THAT MIN DIST. COMES FIRST SO IF A CELL IS MAX WE CAN GO
                 AND DIRECTLY UPDATE ITS VALUE IN THE CELL AND NO MORE SHORTER DISTANCE 
                 CAN BE FOUND BECAUSE WE ARE HEAVYILY RELYING ON BFS, SO JUST ADD IN THE 
                 QUEUE AND UPDATE THE VALUE..
                */
                if( adj_row >=0 && adj_row < m && adj_col >=0 && adj_col < n
                    && ans[adj_row][adj_col] == Integer.MAX_VALUE ){
                        
                    que.offerLast(new Pair(adj_row,adj_col));
                    int curr_dist = ans[curr_row][curr_col];
                    /* Here I did the distance +1 to the initial value of currrow
                    because that value has a distance to cell 1 and BFS wise if we
                    go then this is the min possible distacne to fiind 1 , and I 
                    added 1 because we are only looking in four directions ,so
                    each cell is at a distant of 1..
                    */
                    ans[adj_row][adj_col] = curr_dist+1;
                
                }
            }
        }
        
        // this is more preferred as updating values is Arraylist are overhead so , 
        // it's better to use matrix for that and then convert it to ArrayList..
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        
        for(int row = 0 ; row<m ; row++){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int col = 0; col<n ; col++){
                temp.add(ans[row][col]);
            }
            res.add(temp);
        }
        return res;
    }
}
