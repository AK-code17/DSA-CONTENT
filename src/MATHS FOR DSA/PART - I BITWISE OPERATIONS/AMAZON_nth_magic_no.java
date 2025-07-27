import java.util.*;

public class AMAZON_nth_magic_no {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter magical no to find : ");
        int n = sc.nextInt();
        System.out.println(Kunal_Magic_no(n));
    }


    static int nth_magic_no(int n){
        //Added and edge case . 
        if (n == 0) {
            return n; // Or any value indicating no set bits
        }
        
        int cnt=1;
        int sum=0;
        int bitLength = Integer.toBinaryString(n).length();

        while(cnt<=bitLength){    /*int ans = (int)(Math.log(n) / Math.log(base))  + 1 ;
                                    return ans;
                                    It gives the bit length and we can use it .*/ 
            if((n & 1)==1){
                sum+=Math.pow(5,cnt);  
            }
            n = (n >> 1);
            cnt++;
        }
        return sum;
    }


  static int Kunal_Magic_no(int n){
    int ans= 0 ;
    int base = 5;
    while(n>0){
        int last = n & 1;
        n = n>>1;
        ans+=last*base;
        base*=5;   //here no need to calc pow again and again . 
    }

    return ans;
  }

  
}
