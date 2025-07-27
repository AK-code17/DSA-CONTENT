public class Arranging_coins {
    public static void main(String[] args) {
        System.out.println(arrangeCoins(3));
    }

    static int arrangeCoins(int n) {
        int row_cnt =0;
        if (n==0 || n==1) {
            return n;
        }
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j <=i; j++) {
                n--;
            }
            row_cnt++;
        }    
    
    
    
    
        return row_cnt;
    }
}
