import java.util.*;

public class RangeXOR {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1st terminal : ");
        int a = sc.nextInt();
        System.out.println("Enter 2nd terminal : ");
        int b = sc.nextInt();
        System.out.println("XOR from "+a + " to "+b+"is "+RangeXOR(a, b));
    }
    
    
    //range xor from a to b .  
    static int RangeXOR(int a , int b){
        return XOR_0_to_a(b)^XOR_0_to_a(a-1);
    }

    static int XOR_0_to_a(int a){
        if(a%4 ==0 ){
            return a;
        }
        if(a%4 == 1 ){
            return 1;
        }
        if(a%4 == 2){
            return a+1;
        }
        return 0;
    }
}
