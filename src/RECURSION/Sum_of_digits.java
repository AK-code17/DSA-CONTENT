public class Sum_of_digits {
    
    public static void main(String[] args) {
        System.out.println(Sumofdigits(743));
    }

    static int Sumofdigits(int n){
        if(n==0){
            return 0;
        }
        return n % 10 + Sumofdigits(n/10);
    }
}
