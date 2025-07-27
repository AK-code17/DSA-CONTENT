public class Count_zeroes {
    
    public static void main(String[] args) {
        System.out.println(Cnt_zeroes(30,0));
    }

    static int Cnt_zeroes(int n,int cnt){
        //Check the base condition . 
        if (n==0) {
            return cnt;
        }
        //If the last digit is 0 , then return n/10 with cnt+1;
        if (n % 10==0) {
            return Cnt_zeroes(n / 10, cnt+1);
        }
        //else return with cnt(intial i.e 0) . 
        return Cnt_zeroes(n / 10,cnt);
    }
}
