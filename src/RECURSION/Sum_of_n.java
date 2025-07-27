public class Sum_of_n {
    
    public static void main(String[] args) {
        System.out.println(Sumofn(3));
    }

    static int Sumofn(int n){
        if (n<=1) {
            return 1;
        }
        return n + Sumofn(n-1);
    }
}
