import java.util.Arrays;

public class SB {  //we caanot name it as Stringbuilder bcz  , it itself is an class so we caanoit name it separately . 
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        // for (int i = 0; i < 26; i++) {
        //     char ch = (char)('a'+i);
        //     // System.out.println(ch);
        //     builder.append(ch+" ");
        //     builder.replace(i, ch, null); 
             

        // } 

        // System.out.println(builder);

        // // builder.delete(0,4+2);
        // System.out.println(builder);

        // String str = "1.1.1.1"  ;
        // System.out.println("OG string" + str);
        // String mod = str.replace(".","[.]") ;
        // System.out.println("Modified string " + mod);
        
        /*String s = "codeleet" ; 
        int[] indices  = {4,5,6,7,0,2,1,3} ; 

        char[] output = new char[indices.length] ; 
        for(int i=0  ; i<indices.length ; i++){
            output[indices[i]] = s.charAt(i) ; 
        }*/

        // System.out.println(new String (output));
        // System.out.println(String.valueOf(output));
        
        String command = "G()(al)" ; 
        String op = command.replace("()","0") ;
        command.contains(op) ; 
        command.
         
        
    }
}
