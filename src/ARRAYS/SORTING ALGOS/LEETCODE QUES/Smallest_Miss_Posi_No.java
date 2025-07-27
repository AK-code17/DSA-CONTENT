import java.util.*;
import java.util.Arrays;

public class Smallest_Miss_Posi_No {
    public static void main(String[] args) {
        int[] nums={1,2,0};
        // Cycle_Sort(nums);
        // System.out.println(Arrays.toString(nums));
        // System.out.println(firstMissingPositive(nums));
        // firstMissingPositive(nums, found);
        // Cycle_Sort(nums, true);
        // System.out.println(Arrays.toString(nums));
        System.out.println(firstMissingPositive(nums));
    }

    static int firstMissingPositive(int[] nums){     
        Cycle_Sort(nums);
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != index+1) {
                return index+1;
            }
        }

        // case 2
        return nums.length+1;
    }

    static void Cycle_Sort(int[]nums){
        int i = 0;
        while (i < nums.length) {
            int correct = nums[i]-1;
            if ( nums[i]>0 && nums[i] <= nums.length && nums[i] != nums[correct]) {
                swap(nums, i , correct);
            } else {
                i++;
            }
        }
    }

    static void swap(int[]nums,int first , int second){
        int temp=nums[first];
        nums[first]=nums[second];
        nums[second]=temp;
    }


}
