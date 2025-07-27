import java.util.Scanner;

public class Questions {
    
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        // System.out.println("Enter a number : ");
        // int n = in.nextInt();
        // boolean ans= IsPrime(n);
        // System.out.println(ans);
        // System.out.println(Is_Arm_Strong(n));
        for (int i = 100; i < 1000; i++) {
            if(Is_Arm_Strong(i)){
                System.out.println(i);
            }
        }

    }

    //print all the three digit armstrong numbers . 
    static boolean Is_Arm_Strong(int a){
        int cnt=0;
        int orignial_num = a;
        while(a>0){
            int rem = a%10;
            a =a/10;
            cnt+=Math.pow(rem, 3);
        }
        return (cnt==orignial_num);
    }

    //print all three digit armstrong numbers 
    

    //Primme check funtion  . 
    static boolean IsPrime(int n ){
        
        if(n<=1){
            return false;
        }

        int c = 2;
        while(c*c <= n){
            if(n%c==0){
                return false;
            }
            c++;
        }

        return c*c>n;
    }

}
