import java.util.Scanner;

public class Sum{
    public static void main(String[] args) {
        // sum();
        // System.out.println(sum2());
        System.out.println(sum3(49, 20));
    }

    static void sum(){
        Scanner in = new Scanner(System.in);
        int num1,num2,sum;

        System.out.print("Enter number 1 : ");
        num1 = in.nextInt();
        System.out.print("Enter number 2 : ");
        num2 = in.nextInt();
        sum=num1+num2;
        System.out.println("Their sum is : "+sum);
    }

    static int sum2(){
        Scanner in = new Scanner(System.in);
        int num1,num2,sum;

        System.out.print("Enter number 1 : ");
        num1 = in.nextInt();
        System.out.print("Enter number 2 : ");
        num2 = in.nextInt();
        return sum=num1+num2;
    }

    static int sum3(int a , int b){
        return a+b;
    }
}