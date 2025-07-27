public class Pos_R_Set_bit {
    
    public static void main(String[] args) {
        System.out.println(Pos_R_Set_bit(203));
    }

    static int Pos_R_Set_bit(int n){
        boolean found = false;
        int cnt=1;
        while(!found){
            if((n & 1)==0){
                n = (n >> 1);
                found=false;
                cnt++;
            }
            else{
                found=true;
            }
        }
        return cnt;
    }
}
