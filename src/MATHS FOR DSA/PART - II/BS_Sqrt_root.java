public class BS_Sqrt_root {
    
    public static void main(String[] args) {
        int n =40;
        int p =3;
        
        System.out.println(sqrt(n,p));
    }

    static double sqrt(int n , int p){
        int start =0;
        int end = n;
        int mid=0;

        double root = 0.0;

        while(start<=end){
            mid = start+(end-start)/2;
            if(mid*mid==n){
                return mid;
            }
            else if(mid * mid > n){
                end=mid-1;
            }
            else{
                start=mid+1;
            }
        }

        //If it's a perfect sq and is already returned .

        //else this part is going to run  .

        double incr =0.1;  //iniializing a increment counter . 
        root=end;   //because its gives the int part of the sqrt we need . or we can say when the while loop is breaked , the end is lower than the mid/start . So here , no need to decrement the mid instaead use end . which is already decremented . 
        for (int i = 0; i < p; i++) {   //i=0,1 and 2 since we want it to be 3 deci. places precised . 
            while(root * root <= n){
                root+=incr;   //increment the decimal till it exceeds n i.e 40(here) . 
            }

            root-=incr;  //decrementing it by 1 since it's exceeded 40 . 
            incr /=10;   //for 2nd deci . place divide the incr/10 . 
        }


        return root; //return the root  .
    }


}
