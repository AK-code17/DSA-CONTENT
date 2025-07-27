import java.util.*;

public class VarArgs {
    
    public static void main(String[] args) {
        // fun(2,3,4,5);
        // fun("Aryan","Komal");
        multiple(17,59,"Aryan","Kashish");
    }

    //VarArgs , ,we do not know how many inputs will be there . ?? 
    // For multiple args : 
    //
    static void multiple(int a, int b , String ...v){  //we can't provide another arument as String ...v it only takes this type 1 time . 
        //also varArgs should be at last of all args , since we do not know how much it will contain elements so , it's an error . 
        System.out.println(a +" , "+b+" , "+Arrays.toString(v));
    }
    
    
    
    static void fun(String ...v){  //internally takes (v) as an array of data type . of variables , here (int ) . 
        System.out.println(Arrays.toString(v));  // to print its values .

    }
}
