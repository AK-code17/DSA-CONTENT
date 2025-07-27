import java.util.*;

public class Bubble_Sort {
    
    public static void main(String[] args) {
        int[] nums = {3,1,5,4,2};
        System.out.println(Arrays.toString(nums));
        Bubble_Sort2(nums, nums.length-1, 0,false);
        System.out.println(Arrays.toString(nums));
        
    }


    static void Bubble_Sort2(int[] nums  , int row , int coln , boolean swapped){
        //Checking the base contiton
        if (row==0) {
            return ;
        }
        if (coln < row) {
            if (nums[coln] > nums[coln+1]) {
                //swap . 
                int temp = nums[coln];
                nums[coln] = nums[coln+1];
                nums[coln+1] = temp;
                swapped = true ; 
            }

            Bubble_Sort2(nums , row  , coln+1,swapped);
        }
        else {
            if(!swapped){
                return  ; 
            }
            Bubble_Sort2(nums , row-1 , 0,false);
        }
    }
}
