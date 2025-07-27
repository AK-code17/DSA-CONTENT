import java.util.*;

public class Cnt_set_bits {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no to check : ");
        int n = sc.nextInt();
        System.out.println("No. of set bits are : "+Cnt_set_bits(n));
    }

    static int Cnt_set_bits(int n ){
        int cnt=0;
        while (n>0) {
            n = n - (n & -n); //n & -n gives the R_most set bit , then minus with  n resets it . 
            n = n & (n-1);   //since difference is of 1  . We can & it ans it reset the set bit . 
            cnt++;
        }
        return cnt;
    }
}
