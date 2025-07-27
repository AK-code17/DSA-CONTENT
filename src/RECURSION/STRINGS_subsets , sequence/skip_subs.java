public class skip_subs {
    public static void main(String[] args) {
        String str = "baccappleappad" ; 
        String ans1 = skip_apple(str,"apple" , 0) ;
        String ans2 = skip_app_not_apple(str, "app", "apple", 0);
        System.out.println(ans1); 
        System.out.println(ans2);
    }

    static String skip_apple(String s , String sub , int i){
         
        if(i == s.length()) return ""  ;
        
        if(s.startsWith(sub, i)){
            return skip_apple(s,sub , i+sub.length()) ; 
        }
        //else add the curr char .  
        return s.charAt(i)+skip_apple(s, sub , i+1) ; 

    }

    //skip app not apple . 

    static String skip_app_not_apple(String s , String noskip , String sub , int i){
         
        if(i == s.length()) return ""  ;
        
        if(s.startsWith(noskip, i) && !s.startsWith(sub,i)){
            return skip_app_not_apple(s,noskip , sub , i+noskip.length()) ; 
        }
        //else add the curr char .  
        return s.charAt(i)+skip_app_not_apple(s, noskip , sub , i+1) ; 

    }
}
