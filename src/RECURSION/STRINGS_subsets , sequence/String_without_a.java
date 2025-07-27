public class String_without_a{
    public static void main(String[] args) {
        String str = "baccad" ; 
        String ans = remove_a(str, 0) ;
        System.out.println(ans); 
    }

    static String remove_a(String s , int i){
        
        if(i == s.length()) return "" ;
        
        char ch = s.charAt(i) ; 
        if(ch != 'a'){
            return ch+remove_a(s, i+1) ; 
        }

        return remove_a(s, i+1) ; 

    }
}