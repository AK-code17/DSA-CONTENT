import java.util.* ; 

public class Maze_path_obs{
    public static void main(String[] args) {
        // List<String> ans = goal_paths_obs("", 0, 0) ; 
        // System.out.println(ans);

        List<String> ans2 = new ArrayList<>();
        boolean[][] maze = {
                            {true,true,true},
                            {true,false,true},
                            {true,true,true},
                            } ; 
        arr_obs("", maze, 0, 0, ans2) ; 
        System.out.println(ans2);
    }

    /*Here we are adding an obstacle at (1,1) so we need to avoid that block and find the paths from the below things . 
    Firstly we will be taking Right and Down then we will be going to take diagonal also .
 
    In prev. problems we were taking from (3,3) to (1,1) here we wil be taking from (0,0) to (2,2) . 
    */


    //In this particular eg : we are specific to obstacle only at (1,1) , further we will be taking an boolean array , indicating false as obstacle  . 
    static List<String> goal_paths_obs(String p , int r ,int c ){
        //Base case : when both the r and c == 2 .
        if(r==2 && c==2){
            List<String> temp = new ArrayList<>() ; 
            temp.add(p) ; 
            return temp ; 
        }
        
        List<String> uttar = new ArrayList<>() ; 
        
        //either coln is 0 or c!=r since . 
        if( c==0 || (c<2 && c!=r)){
            uttar.addAll(goal_paths_obs(p+"R", r, c+1)); 
        }

        //similarly for row . 
        if(r==0 || (r<2 && r!=c)){
            uttar.addAll(goal_paths_obs(p+"D", r+1, c));
        }

        return uttar ; 

    }

    /*Consider given an boolean arr o=marking false as obstacle , our logic : 
    1.Base case same . just make the row to maze.length-1 and coln to maze[0].length-1 . Since the size of maze won;t be same . 
    2.whenver at maze[r][c] if false  , just return an empty list . 
    3.if(c<2) add , p+"R" and increment coln .
    4.if(r<2) add , p+"D" and increment row .


    Till now we have uderstood how the addAll works hence now will be directly providing the List in args . */

    static List<String> arr_obs(String p , boolean[][] maze , int r , int c , List<String> ans){
        //Base case: 
        if(r==maze.length-1 && c==maze[0].length-1){
            ans.add(p) ; 
            return ans ; 
        }

        if(!maze[r][c]){
            return ans ;
        }

        if(c<maze[0].length-1){
            arr_obs(p+"R", maze, r, c+1, ans) ; 
        }

        if(r<maze.length-1){
            arr_obs(p+"D", maze, r+1, c, ans) ; 
        }

        return ans ; 
    }

}