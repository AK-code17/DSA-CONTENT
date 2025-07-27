import java.util.ArrayList;
import java.util.List;

public class All_path_obs {
    public static void main(String[] args) {
        // List<String> ans = new ArrayList<>();
        boolean[][] maze = {
                            {true,true,true},
                            {true,true,true},
                            {true,true,true},
                            } ; 
        all_path_obs("", maze, 0, 0) ; 
        // System.out.println(ans);
    }

    /*Consider given an boolean arr o=marking false as obstacle , our logic : 
    1.Base case same . just make the row to maze.length-1 and coln to maze[0].length-1 . Since the size of maze won;t be same . 
    2.whenver at maze[r][c] if false  , just return an empty list . 
    3.if(c<2) add , p+"R" and increment coln .
    4.if(r<2) add , p+"D" and increment row .


    Till now we have uderstood how the addAll works hence now will be directly providing the List in args . */

    static void all_path_obs(String p , boolean[][] maze , int r , int c ){
        //Base case: 
        if(r==maze.length-1 && c==maze[0].length-1){
            System.out.println(p); 
            return; 
        }

        if(!maze[r][c]){
            return;
        }

        /*Since this path is going overflow bcz the paths are getting copied .So I initally without loking at kk found that we need to mark them as false , but I marked them false after the call . 
        So this was the issue , and hence I learnt the backtracking .  
        
        So we need to mark this(the current cell) as false , after the check so to avoid the path coming back , like DU,RL etc . */
        
        maze[r][c] = false ; 

        //and hence the false value is already checked , so the below calls will run  . 
        
        //for Down .
        if(r<maze.length-1){
            all_path_obs(p+"D", maze, r+1, c) ; 
        }
        
        //for Right . 
        if(c<maze[0].length-1){
            all_path_obs(p+"R", maze, r, c+1) ; 
        }

        //for Up .
        if(r>0){
            all_path_obs(p+"U", maze, r-1, c) ;
        }

        //for Left . 
        if(c>0){
            all_path_obs(p+"L", maze, r, c-1) ; 
        }

        //now since we marked the place as false , let's undo the change and mark it true as before so that future recursion calls could be madepossible . 
        //like as soon as we completed the path/combinations for a particular cell just return by again making it as it was before , making it true . 
        
        maze[r][c] = true ; 


    }

}
