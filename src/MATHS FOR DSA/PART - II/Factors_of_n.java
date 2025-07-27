public class Factors_of_n {
    
    public static void main(String[] args) {
        int n =30;
        boolean[] factors  = new boolean[n+1];
        Factorsofn(n, factors);
    }

    static void Factorsofn(int n , boolean[] factors){
        if (n==0 || n==1) {
            System.out.println(n);
            return;
        }

        
        for (int i = 1; i*i <=n; i++) {
            if (n % i ==0) {
                if (!factors[i]) {
                    factors[i]=true;
                    factors[n/i]=true;
                }
            }
        }

        for (int i = 0; i < factors.length; i++) {
            if (factors[i]) {
                System.out.println(i);
            }
        }
    }
}
