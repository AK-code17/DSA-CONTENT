package OOP_6.generics;
// import java.rmi.server.Operation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

public class LambdaFunctions {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>() ; 
        for(int i=0 ; i<5 ; i++){
            arr.add(i+1);
        }

        /*
            * forEach calls the passed lambda function on each element.
            * - action -> The lambda function (item -> System.out.println(2 * item))
            * - accept(t) -> Runs System.out.println(2 * t) for each element.
            * - T -> Type of ArrayList (Integer here).
            * - t -> Each individual element of ArrayList.
        */
        
        // arr.forEach((item) -> System.out.println(2*item));  

        //since the forEach is taking the consumer type as an argument we can make it out . 
        Consumer<Integer> fun = (item) -> System.out.println(2*item);
        
        arr.forEach(fun);

        //.since consumer is bound to only single argument hence will make interface inorder to perform with multiple operators . 
        Methods sum = (a,b) -> a+b;
        Methods prod = (a,b) -> a*b;
        Methods sub = (a,b) -> a-b;

        System.out.println(sum.kaam(5, 3));

        LambdaFunctions myCalculator = new LambdaFunctions();
        System.out.println(myCalculator.operate(5, 3, sum));
        System.out.println(myCalculator.operate(5, 3, prod));
        System.out.println(myCalculator.operate(5, 3, sub));
    
    }

    // int sum(int a , int b){
    //     return a+b;
    // }

    private int operate(int a , int b , Methods op){
        return op.kaam(a, b);   // op = name .kaam = what action it does, is here used to execute the action declared by the lambda . 
    }
}


interface Methods{
    int kaam(int a , int b) ; 
}