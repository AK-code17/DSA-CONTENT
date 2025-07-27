import java.util.ArrayList;

public class String_subset {
    public static void main(String[] args) {
        ArrayList<String> str_L = subset("","abc");

        System.out.println(str_L);
    }

    static ArrayList<String> subset(String p , String un_p ){
        
        if(un_p.isEmpty()) {
            ArrayList<String> list = new ArrayList<>() ; 
            list.add(p) ;
            return list ;
        } 

        char ch = un_p.charAt(0) ;  
        
        ArrayList<String> str1 = subset(p+ch, un_p.substring(1)) ;  

        ArrayList<String> str2 = subset( p , un_p.substring(1) ) ;
        
        str1.addAll(str2) ; 

        return str1 ; 


    }
}
