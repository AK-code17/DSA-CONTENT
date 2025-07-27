import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Find_All_Dupl {
    public static void main(String[] args) {
        int[] nums= {4,3,2,7,8,2,3,1};
        // int[] ans = All_Missing_Numbers(nums);
        // System.out.println(Arrays.toString(ans));
    }

    public List<Integer> findDuplicates(int[]nums){
        Cycle_Sort(nums);
        List<Integer> ans= new ArrayList<>();
        for (int index = 0; index < nums.length; index++) {
            if (nums[index]!=index+1) {
                ans.add(nums[index]);
            }
        }
        return ans;
    }

    static void Cycle_Sort(int[]nums){
        int i=0;
        while (i<nums.length) {
           
            int correct_ind=nums[i]-1;
            if (nums [i] != nums[correct_ind]) {   //edge case of nums[i]<nums.length is checked if nums[i]==nums.length its ignored and by the time it will be at last index itself . 
                swap(nums, i,correct_ind);
            }

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
