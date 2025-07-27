import java.util.*;

public class Pracallpath {
    public static void main(String[] args) {
        
        boolean[][]maze = {
                            {true , true , true},
                            {true , true , true},
                            {true , true , true},
                        } ; 
        int[][] path = new int[maze.length][maze[0].length] ;
        all_path("", maze, 0, 0, path,1);

        // for(int[] r : maze ){
        //     System.out.println(Arrays.toString(r)); ; 
        // }
    }

    static void all_path(String p , boolean[][] maze , int r , int c,int[][] path ,int step){
        
        if(r==maze.length-1 && c==maze[0].length-1){
            path[r][c] = step; //Added to also include the last cell step when it reaches the end . 
            System.out.println("*------Path--------* : "+p);
            
            //Since our aim is to show the path step , we need to print the whole matrix .  
            for(int[] row : path ){
                System.out.println(Arrays.toString(row)); ; 
            }

            System.out.println();
            return ; 
        }

        //initially the matrix is empty , so move only when the cell is 0 . 
        if(!maze[r][c]) return ; 

        //Making the current cell as the step given , so as to indicate the path view .  
        maze[r][c] = false;
        path[r][c] = step ;  
        
        //for Left path . 
        if(c>0){
            all_path(p+"L", maze, r, c-1,path,step+1);
        }

        //for Right path . 
        if(c<maze[0].length-1){
            all_path(p+"R", maze, r, c+1,path,step+1);
        }

        //for Up path . 
        if(r>0){
            all_path(p+"U", maze, r-1, c,path,step+1);
        }

        //for Down path . 
        if(r<maze.length-1){
            all_path(p+"D", maze, r+1, c,path,step+1);
        }

        //again making it 0 so as to allow the future calls to run . 
        maze[r][c] = true ; 
        path[r][c] = 0 ; 
        
    }
}
