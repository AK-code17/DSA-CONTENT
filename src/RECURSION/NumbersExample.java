public class NumbersExample{

    public static void main(String[] args) {
        print1(1);
    }

    static void print1(int n){
        System.out.println(n);
        while(n<6){
            print1(n+1);
        }
        n++;
        
    }
}