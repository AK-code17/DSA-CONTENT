import java.util.*;


public class Operators {
    public static void main(String[] args) {
        System.out.println('a'+'b');   //add. of chars will give their sum of ascii values.
        System.out.println("a"+"b");   //will give ab since its concatenated like we did in python . 
        System.out.println('a'+3);     //will give 100 bcz ascii values of a is 97 and when added 3 , is 100 . 
        System.out.println((char)('a'+3));  //converts the 100 into its char values . i.e 'd' . 
        System.out.println("a"+1);   //will give a1 unlike python  , in python it will give error . 
        //But here its converted to int obj as we studied earlier and then to .toString() . 
        //Afterall . it same as Syso("a"+"1");
    
        System.out.println("Aryan"+ new ArrayList<>());
        //give Aryan[] bcz its empty . 

        System.out.println("Aryan"+ new Integer(56));

        //System.out.println(new Integer(56) + new ArrayList<>()); 
        /*Above command wil give error bcz , in Java + is only used for printing if:
        1.are primitives or
        2.also can be used with complex objs but 
        one of them must be string*/ 

        //but can be used if a string added but BETWEEN THEM NOT AT START OR AT THE END .
        System.out.println(new Integer(56) +""+ new ArrayList<>());

        //we can modify / change the function of + operator in c and python but not in java . 
        //but in c and python we cannot add two datatypers as above even with strings . 

        




    
    
    
    
    
    
    
    }
}
