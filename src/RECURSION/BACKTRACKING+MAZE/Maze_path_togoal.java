import java.util.*;

public class Maze_path_togoal{
    public static void main(String[] args) {
        List<String> ans = goal_paths1("", 3, 3) ; 
        System.out.println(ans);
    }

    static List<String> goal_paths1(String p , int r ,int c ){
        // Base case : when both the r and c == 1 .
        
        //Also if r or c is 0 then simply return the empty list . 

        if(r==0 || c==0){
            List<String> temp1 = new ArrayList<>() ; 
            return temp1 ; 
        }

        if(r==1 && c==1){
            List<String> temp2 = new ArrayList<>() ; 
            temp2.add(p) ; 
            return temp2 ; 
        }
        
        List<String> Right = goal_paths1(p+"R", r, c-1);  
        List<String> Down = goal_paths1(p+"D", r-1, c);

        Right.addAll(Down) ; 

        return Right ; 

    }

    //Method 2 : 

    static List<String> goal_paths2(String p , int r ,int c ){
        //Base case : when both the r and c == 1 .

        if(r==1 && c==1){
            List<String> temp2 = new ArrayList<>() ; 
            temp2.add(p) ; 
            return temp2 ; 
        }
        
        List<String> Right = new ArrayList<>() ; 
        List<String> Down = new ArrayList<>() ;

        
        //To avoid the call to column = 0 ; 
        //Hi My name Is Aryan . 

        if(c>1){
            Right = goal_paths2(p+"daay"+" ", r, c-1); 
        }

        //To avoid the call to row = 0 ; 
        if(r>1){
            Down = goal_paths2(p+"neeche"+" ", r-1, c);
        }

        Right.addAll(Down) ; 

        return Right ; 

    }
}