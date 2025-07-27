import java.util.ArrayList;
import java.util.List;

public class Maze_path_diag {
    public static void main(String[] args) {
        List<String> ans = goal_paths_diag("", 3, 3) ; 
        System.out.println(ans);
    }

    static List<String> goal_paths_diag(String p , int r ,int c ){
        //Base case : when both the r and c == 1 .

        if(r==1 && c==1){
            List<String> temp2 = new ArrayList<>() ; 
            temp2.add(p) ; 
            return temp2 ; 
        }
        
        List<String> Right = new ArrayList<>() ; 
        List<String> Down = new ArrayList<>() ;
        List<String> Diagonal = new ArrayList<>() ; 

        
        //To avoid the call to column = 0 ; 
        
        if(r>1 && c>1){
            Diagonal = goal_paths_diag(p+"beech"+" ", r-1, c-1); 
        }

        if(c>1){
            Right = goal_paths_diag(p+"daay"+" ", r, c-1); 
        }

        //To avoid the call to row = 0 ; 
        if(r>1){
            Down = goal_paths_diag(p+"neeche"+" ", r-1, c);
        }

        Right.addAll(Down) ; 
        Right.addAll(Diagonal) ; 

        return Right ; 

    }
}
