import java.util.Arrays;

public class SearchIn2DArray {
    public static void main(String[] args) {
        int [][] arr2D= {
            {23,4,1},
            {18,12,3,9},
            {78,99,34,56},
            {18,12}
        };
        int target = 56;
        // int[] ans=search2D(arr2D, target);
        // System.out.println(Arrays.toString(ans));
        // System.out.println("The max element in the 2D Array is "+max2D(arr2D));
        int[] nums = {12,345,2,6,7896};
        // System.out.println("The total numbers having even no. of digits is "+ even_digits(nums));
        
        int[][] accounts = {
                    {1,5},
                    {7,3},
                    {3,5}

        };
        int [][] aacnts= {
                    {1,2,3},
                    {3,2,1}
        };

        System.out.println("The maximum wealth is "+max_wealth(accounts));

    }

    static int[] search2D(int[][] array , int target){
        
        for (int row = 0; row < array.length; row++) {
            for (int coln = 0; coln < (array[row]).length; coln++) {
                if (array[row][coln]== target) {
                    return new int[]{row,coln};
                }
            }
        }
        return new int[]{-1,-1};
    }

    static int max2D(int[][] array ){
        
        int max_elmnt = array[0][0];
        for (int[] row : array) {
            for (int element : row) {
                if (element>max_elmnt) {
                    max_elmnt=element;
                }
            }
        }
        return max_elmnt;
        
    }
    
    //LEETCODE EASY LEVEL QUESTIONS . 
    
    static int even_digits(int [] nums){
        int cnt_even=0;
        for (int i : nums) {
            if ((Integer.toString(i).length()%2)==0) {
                cnt_even++;
            }
        }
        return cnt_even;
    }

    static int max_wealth(int[][] acc){
        int ans = 0;
        for (int[] row : acc) {
            int sum = 0;         //otherwise we have to make sum =0 after every iteration . 
            for ( int amt : row) {
                sum+=amt;
            }
            if (sum>ans) {
                ans=sum;
            }
        }
        return ans;
    
    }
}
