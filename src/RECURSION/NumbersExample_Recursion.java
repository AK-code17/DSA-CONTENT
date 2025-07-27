public class NumbersExample_Recursion {
    
    public static void main(String[] args) {
        print1(1);
    }

    static void print1(int n){
        if(n==6){
            return ;  //here break cannot be used since its a recurrsion funtion
        }
        System.out.println(n);
        print1(n+1);
    }

    //a functipon called again cal be treates as new function bcz , it calling with new parameter  . 
    //and new calling takes new memo . 


}
