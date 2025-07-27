public class ith_bit {
    
    public static void main(String[] args) {
        // System.out.println(ith_bit(22, 4));
        System.out.println(ith_bit_real(22,5));
    }
    
    static int ith_bit_real(int num , int n){
        int l_shift = 1 << (n-1);
        return (num & l_shift)>>(n-1);   //nums & l_shift aayega 16 , but usko utne hi bit right shift krna padega jitne zeroes add kiye hai . 
    }



    //My methid correct , but may have some issues . with it  . 
    
    static int ith_bit(int num, int n){
        int cnt=1;
        int copy = n;
        if(n==1){
            return ( num & 1 );
        }
        while (1<copy) {
            cnt*=10;
            copy--;
        }

        if((cnt & num)==0 ){
            return 0;
        }

        return 1;
        
    }


}
