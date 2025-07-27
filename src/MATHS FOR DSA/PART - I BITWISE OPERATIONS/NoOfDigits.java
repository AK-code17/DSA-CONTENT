import java.util.Scanner;

public class NoOfDigits {
    
    public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
        System.out.println("Enter no to find length : ");
        int n = sc.nextInt();
        System.out.println("Enter the base of the number : ");
        int base = sc.nextInt();
        System.out.println("Its num length is : "+NoOfDigits( n , base));
    }

    static int NoOfDigits(int n , int base ){
        int ans = (int)(Math.log(n) / Math.log(base))  + 1 ;
        return ans;
    }
}
