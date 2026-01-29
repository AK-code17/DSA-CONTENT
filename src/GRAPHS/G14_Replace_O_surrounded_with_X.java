package GRAPHS;

public class G14_Replace_O_surrounded_with_X {
    private static final int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
    
    public void fill_adj(int row , int col , char[][] grid){
        int m = grid.length;
        int n = grid[0].length;
        
        if(!(row>=0 && row<m && col>=0 && col<n && grid[row][col] != 'X' && grid[row][col] != '@')) return;
        
        // but if its valid mark it '@'..
        grid[row][col] = '@';
                                
        for(int[] dir : directions){
            fill_adj(row+dir[0] , col+dir[1] , grid);
        }
    }
    public void fill(char[][] grid) {
        
        if(grid == null || grid.length == 0) return;
        int rows = grid.length;
        int colns = grid[0].length;
        // check the top , bottom  , left and right boundary for any 'O'.because the non-surrounded
        // will be the ones who are residing to adjacent of 'X' lying on boundary..
        
        // check the top and bottom layer or row..
        for(int c=0 ; c<colns ; c++){
            if(grid[0][c] == 'O'){
                fill_adj(0,c,grid);
            }
            if(grid[rows-1][c] == 'O'){
                fill_adj(rows-1,c,grid);
            }
        }
        
        // now similarly check for the left and right boundary..
        for(int r = 0 ; r<rows ; r++){
            if(grid[r][0] == 'O'){
                fill_adj(r,0,grid);
            }
            if(grid[r][colns-1] == 'O'){
                fill_adj(r,colns-1,grid);
            }
        }
        
        // Now just traverse the grid and conver the 'O' to 'X' and '@' to 'O'
        // bcz while filling the adjacent we filled the adj values to be @, so as to
        // ensure we clearly differentiate between the surrounded and non-surrounded..
        
        for(int r = 0 ; r<rows ; r++){
            for(int c = 0 ; c<colns ; c++){
                if(grid[r][c] == 'O'){
                    grid[r][c] = 'X';
                }
                if(grid[r][c] == '@'){
                    grid[r][c] = 'O';
                }
            }
        }
        
    }
}
