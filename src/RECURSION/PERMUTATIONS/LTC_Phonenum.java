import java.util.*;

public class LTC_Phonenum {
    public static void main(String[] args) {
        String s = "235" ; 
        String[] digits = {"a" , "b" , "abc" , "def" , "ghi" , "jkl" , "mno" ,"pqrs" , "tuv" , "wxyz"} ;
        List<String> ans = letter("","235", digits) ; 
        // for(List<String> list : ans){
        //     if(list.size() == s.length()) {
        //         System.out.println(list);
        //     }
        // }
        System.out.println(ans);
    
    }

    //Using for loops . 

    static List<List<String>> letters(String main , String[] digits){
       
        List<List<String>> outer = new ArrayList<>() ; 

        outer.add(new ArrayList<>()) ; 

        for(int i=0 ; i<main.length() ; i++){
            int end = outer.size() - 1 ;
            char ch = main.charAt(i) ; 
            int num = ch - '0'; 
            String dhag = digits[num] ; 
            for(char chr : dhag.toCharArray()){
                for(int j=0 ; j<=end ; j++){
                    List<String> internal = new ArrayList<>(outer.get(j));
                    if(internal.size() >= i){
                        internal.add(chr+"") ; 
                        outer.add(internal) ; 
                    }


                }
            }
        }

        return outer ; 





    }

    //Refined with gpt and understood . 

    static List<String> letters_2(String main, String[] digits) {
        // List to store the combinations
        List<String> outer = new ArrayList<>();
        outer.add("");  // Start with an empty string

        for (int i = 0; i < main.length(); i++) {
            int end = outer.size() - 1;
            char ch = main.charAt(i);
            int num = ch - '0';
            String dhag = digits[num];

            // Process each character of the digit's string
            List<String> newCombinations = new ArrayList<>();

            for (char chr : dhag.toCharArray()) {
                for (int j = 0; j <= end; j++) {
                    String newStr = outer.get(j) + chr;  // Append character to the current combination
                    newCombinations.add(newStr);
                }
            }

            // Replace outer with the newly formed combinations after processing this digit , so no need to add extra check . 
            outer = newCombinations;
        }

        return outer;  // Return the final list of combinations
    }
    
    // Using 2 for loops reduced one for loop . 

    static List<String> letter(String un_p, String[] digits) {
        // Base Case: If the unprocessed string is empty, return a list with an empty string
        if (un_p.isEmpty()) {
            List<String> temp = new ArrayList<>();
            temp.add("");
            return temp;
        }
    
        int num = un_p.charAt(0) - '0';
        String dhag = digits[num];
    
        List<String> internal = letter(un_p.substring(1), digits);
    
        List<String> tempo = new ArrayList<>();   //to avoid adding the elements which are not relevant that is having the length lower than the current un_p . length . 
        for (char letter : dhag.toCharArray()) {
            for (String s : internal) {
                tempo.add(letter + s);
            }
        }
    
        return tempo;
    }


    static List<String> letter( String p, String un_p, String[] digits) {
        // Base Case: If the unprocessed string is empty, return a list with an empty string
        if (un_p.isEmpty()) {
            List<String> temp = new ArrayList<>();
            temp.add(p);
            return temp;
        }
    
        int num = un_p.charAt(0) - '0';
        String dhag = digits[num];
        
        List<String> internal = new ArrayList<>();   //to avoid adding the elements which are not relevant that is having the length lower than the current un_p . length . 
        for (char letter : dhag.toCharArray()) {
            internal.addAll(letter(p+letter, un_p.substring(1), digits)) ; 
        }
    
        return internal;
    }
    


}
