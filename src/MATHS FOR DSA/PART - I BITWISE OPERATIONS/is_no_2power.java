import java.util.*;

public class is_no_2power {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no to check : ");
        int n = sc.nextInt();
        System.out.println(Pow_Of_2(n));
    }
    
    //Kunal's efficient approach . 
    static boolean Pow_Of_2(int n ){
        if(n==0){
            return false;
        }
        boolean ans = (n & (n-1)) == 0 ;   //n=n-1+1 is correct for power of 2 numbers . 
        return ans;
    }
    
    
    //My Approach . 
    static boolean no_2power(int n){
        int n_2power = 1;
        while(n_2power<n){
            n_2power = n_2power<<1;
        }
        return n_2power==n;
    }  
    
    //ok but not meant for bitwise operators . 
    static boolean is_no_2power(int n){
        int base=2;
        while (n>=base) {
            if (n==base) {
                return true;   
            }
            base*=2;
        }

        return false;
    } 

}

