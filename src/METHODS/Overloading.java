import java.util.Arrays;

public class Overloading {
    
    public static void main(String[] args) {
        // fun(69);
        System.out.println(fun());
        System.out.println(fun(3, 4));
        System.out.println(fun(3, 4, 5));
    }

    static String fun(){   //also fun with same name but no args can also be there . 
        System.out.println("First one ");
        return ("Kyu Bhai input nahi daala kya . ");
    }
    
    static int fun(int a , int b){
        System.out.println("Second one ");
        return (a+b);
    }

    static void fun(int ...a ){  //only taking the same type of variable will cause an error . instead either change the no. of args or change the args type .Also we can input VarArgs of same type .  
        System.out.println("Second one");                               
        // System.out.println(name);
        System.out.println(Arrays.toString(a));
    }
}
