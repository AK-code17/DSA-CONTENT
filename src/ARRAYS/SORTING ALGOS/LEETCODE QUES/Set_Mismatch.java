import java.util.*;
import java.util.Arrays;

//https://leetcode.com/problems/set-mismatch/description/

//SELF SOLVES NOTHING JUST CHANGED THE MISS_DUPL CODE WITH RETURNING WITH ARRAY . 



public class Set_Mismatch {
    public static void main(String[] args) {
        int[] nums={};
        int[] ans=findErrorNums(nums);
        System.out.println(Arrays.toString(ans));
    }

    static int[] findErrorNums(int[] nums){
        Cycle_Sort(nums);
        if(nums.length==0 || nums.length==1){
            return new int[] {-1,-1};
        }

        for (int index = 0; index < nums.length; index++) {
            if (index!=nums[index]-1) {
                return new int[] {nums[index],index+1};
            }
            
            // if ((nums[index]==nums[0]) && (nums[index]==nums[nums.length-1])) {
            //     return new int[]{nums[index],-1};
            // }

        }

        return new int[]{-1,-1};
    }

    static void Cycle_Sort(int[]arr){
        int i=0;
        while (i<arr.length) {
            int correct_ind = arr[i]-1;  //to specify what;s the current index is . 
            if (arr[i]!=arr[correct_ind]) {
                swap(arr, i,correct_ind);
            }
            else{
                i++;
            }
        }
    }

    static void swap(int[]arr,int first , int second){
        int temp=arr[first];
        arr[first]=arr[second];
        arr[second]=temp;
    }
}
