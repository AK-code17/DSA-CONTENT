public class LEETCODE_Cnt_steps {
    
    public static void main(String[] args) {
        System.out.println(numberOfSteps(14));
    }

    static int numberOfSteps(int num) {
        return Cnt_zeroes(num,0);
    }

    static int Cnt_zeroes(int num,int cnt){
        if (num==0) {
            return cnt;
        }
        if (num % 2==0) {
            return Cnt_zeroes(num / 2, cnt+1);
        }
        return Cnt_zeroes(num-1 ,cnt+1);
    }
}
