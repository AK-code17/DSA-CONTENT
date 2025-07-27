import java.util.*;

public class a_power_b {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no to check : ");
        int a = sc.nextInt();
        System.out.println("Enter the power : ");
        int b = sc.nextInt();
        System.out.println(a+" to the power "+b+" is "+a_power_b(a,b));
    }
    
    static int apowerb(int base , int power){
        int ans  = 1;
        while (power>0) {
            if ((power & 1)==1) {
                ans=ans*base;
            }
            base=base*base;
            power = power>>1;
        } 
    }
    

    //My Method ok , but needs improvement . 
    static int a_power_b(int a , int b){
        if (b==0) {
            return 1;
        }
        int ans=a;
        int l_shift = (1<<b)-1;
        while (l_shift>1) {
            ans=ans*a;
            l_shift>>=1;
        }
        return ans;
    }
}
