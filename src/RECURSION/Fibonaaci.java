import java.util.*;

public class Fibonaaci {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        System.out.println("Enter a number ");
        int n = sc.nextInt();
        // System.out.println("The Fibonaaci number is "+Fibo(n));
        System.out.println(Fibo_Formuula(n));
    }

    static int Fibo_Formuula(int n){  //understood the explanation in the notebook . 
        //just for demo , use long instead . 
        return (int)(Math.pow(((1+Math.sqrt(5))/2),n)/Math.sqrt(5));
    }

    static int Fibo(int n){
        int cnt =0;
        if(n==1){
            return cnt+1;
        }
        if(n<=0){
            return cnt;
        }
        cnt+=Fibo(n-1)+Fibo(n-2);
        return cnt;
    }
}
