import java.util.*;

public class All_Missing_Numbers {
    public static void main(String[] args) {
        int[] nums= {4,3,2,7,8,2,3,1};
        int[] ans = All_Missing_Numbers(nums);
        System.out.println(Arrays.toString(ans));
    }

    static int[] All_Missing_Numbers(int[]nums){
        Cycle_Sort(nums);
        int[] miss_Num= new int[2];
        int k=0;
        for (int index = 0; index < nums.length; index++) {
            if ((index+1)!=nums[index]) { 
                miss_Num[k]=index+1;  //we can also return here nums[i]-1 but here may be the 0 itself won't be present .So error will be there . 
                k++;
            }
            else{
                break;
            }
        }
        return miss_Num;
    }

    static void Cycle_Sort(int[]nums){
        int i=0;
        // if (nums[i]==nums.length) {
        //     swap(nums, i, nums.length-1);
        // }
        // if (nums[i]==0) {
        //     swap(nums, i, 0);
        // }
        while (i<nums.length) {
            // int correct_ind = nums[i];  //to specify what;s the current index is . 
            int correct_ind=nums[i]-1;
            if (nums [i] != nums[correct_ind]) {   //edge case of nums[i]<nums.length is checked if nums[i]==nums.length its ignored and by the time it will be at last index itself . 
                swap(nums, i,correct_ind);
            }
            // if (nums [i] < nums.length && nums [i] == nums[correct_ind]) {   //edge case of nums[i]<nums.length is checked if nums[i]==nums.length its ignored and by the time it will be at last index itself . 
            //     i++;
            // }

            else{
                i++;   //increment when all the swaps are checked . 
            }
        }
    }

    static void swap(int[]nums,int first , int second){
        int temp=nums[first];
        nums[first]=nums[second];
        nums[second]=temp;
    }
}
