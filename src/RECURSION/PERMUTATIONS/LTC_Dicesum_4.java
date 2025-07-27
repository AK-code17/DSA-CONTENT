import java.util.*;

public class LTC_Dicesum_4 {
    public static void main(String[] args) {
        // Dicesum("", 4); 
        List<String> ans = Dicesum_list("", 4) ; 
        System.out.println(ans);       
    }

    //Printing the possible combinations directly . 
    static void Dicesum(String p ,int target){
        if(target == 0) {
            System.out.println(p);
            return ;  
        }

        for(int i=1 ; i<=6 && i<= target ; i++){
            Dicesum(p+i, target-i); //adding i into p , and similarly subtracting i from target makes the sum ,equal hence it works . 

            //isse hoga yeh ki hamare combinations bante jayenge .
        }

    }

    //Returning as an ArrayList[ . ]

    static List<String> Dicesum_list(String p ,int target){
        if(target == 0) {
            List<String> list = new ArrayList<>() ; 
            list.add(p) ; 
            return list;  
        }

        List<String> internal = new ArrayList<>() ; 
        for(int i=1 ; i<=6 && i<= target ; i++){
            internal.addAll(Dicesum_list(p+i, target-i)) ;  //adding i into p , and similarly subtracting i from target makes the sum ,equal hence it works . 

            //isse hoga yeh ki hamare combinations bante jayenge .
        }

        return internal ; 

    }
}
