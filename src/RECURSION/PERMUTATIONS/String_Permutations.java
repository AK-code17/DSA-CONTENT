import java.util.ArrayList;

public class String_Permutations {
    public static void main(String[] args) {
        String s = "abc" ; 
        System.out.println(Permutations_cnt("", "abcde")) ; 
    }

    static void Permutations(String p  , String un_p){
        if(un_p.isEmpty()){
            System.out.println(p);
            return ; //important or will rise error . 
        } 

        int n = p.length() ; 

        for(int i=0 ; i<=n ; i++){
            String first = p.substring(0, i) ; 
            char ch = un_p.charAt(0) ;
            String last = p.substring(i, n) ;
            String fin = first+ch+last ; 
            Permutations(fin, un_p.substring(1));

        }

    }


    static ArrayList<String> Permutations_list(String p  , String un_p){
        if(un_p.isEmpty()){
            ArrayList<String> temp = new ArrayList<>() ; 
            temp.add(p) ; 
            return temp; //important or will rise error . 
        } 

        int n = p.length() ; 

        ArrayList<String> tot = new ArrayList<>() ;

        for(int i=0 ; i<=n ; i++){
            String first = p.substring(0, i) ; 
            char ch = un_p.charAt(0) ;
            String last = p.substring(i, n) ;
            String fin = first+ch+last ; 
            ArrayList<String> internal = Permutations_list(fin, un_p.substring(1));
            tot.addAll(internal) ; //also  u can directly addAll(Permutations(p,un_p)) , but for readability I did it . 

        }

        return tot ; 

    }

    static int Permutations_cnt(String p  , String un_p){
        if(un_p.isEmpty()){ 
            return 1; //important or will rise error . 
        } 

        int cnt = 0 ; 
        int n = p.length() ; 

        for(int i=0 ; i<=n ; i++){
            String first = p.substring(0, i) ; 
            char ch = un_p.charAt(0) ;
            String last = p.substring(i, n) ;
            String fin = first+ch+last ;
            cnt += Permutations_cnt(fin, un_p.substring(1)) ;
             

        }

        return cnt ; 

    }
}
