public class Prod_of_digits {
    
    public static void main(String[] args) {
        System.out.println(Prdofdigits(743));
    }

    static int Prdofdigits(int n){
        if(n%10==n){
            return n;
        }
        return n % 10 * Prdofdigits(n/10);
    }

}
