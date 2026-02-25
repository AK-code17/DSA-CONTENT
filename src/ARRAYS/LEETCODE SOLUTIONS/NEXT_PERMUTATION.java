import java.util.*;

public class NEXT_PERMUTATION {

    public void nextPermutation(int[] nums) {
        int len = nums.length;
        int prev_smaller = -1;

        for(int i=len-1 ; i>0 ; i--){
            if(nums[i] > nums[i-1]) {
                // this is the point where we can generate next greater numbers...
                prev_smaller = i-1;
                break;
            }
        }

        // if prev_smaller is still -1 means its the max val till now , so just return the next permutation , means the smallest till now...
        if(prev_smaller == -1){
            reverse(nums,0,len-1);
            return;
        }

        // we found the possible index for swapping the smaller element with larger element , BUT HERE WE WANT THE ELEMENT WHICH IS JUST NEXT LARGER THAN NUMS[IND] in R.H.S..

        int next_greater = len-1; // just initialisation
        for(int i=len-1 ; i>prev_smaller ; i--){
            if(nums[i] > nums[prev_smaller]){
                next_greater = i;
                break;
            } 
        }

        // now in this step we will SWAP THEM , AND REVERSE(prev_smaller + 1 , len-1);

        // swapping them..
        swap(nums,prev_smaller,next_greater);

        // Now we want the remaining portiong from prev_smaller to len-1 , to be sorted , since after swapping the nums is still decreasingly sorted , but for the next permutation we want it to be sorted ascendingly..REVERSING IT WILL EASILY DO THAT THING..

        reverse(nums,prev_smaller+1 , len-1);
    }

    private void reverse(int[] nums , int start , int end){
        while(start < end){
            swap(nums,start,end);
            start++;
            end--;
        }
    }

    private void swap(int[] nums , int start ,int end){
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
    }
}
