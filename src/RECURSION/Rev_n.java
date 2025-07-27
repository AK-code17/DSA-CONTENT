public class Rev_n {

    public static void main(String[] args) {
        System.out.println(Rev(741, 0));
    }

    static int Rev(int n, int reversed) {
        if (n == 0) {
            return reversed;
        }
        return Rev(n / 10, reversed * 10 + (n % 10)); // Recursively build the reversed number
    }
}
