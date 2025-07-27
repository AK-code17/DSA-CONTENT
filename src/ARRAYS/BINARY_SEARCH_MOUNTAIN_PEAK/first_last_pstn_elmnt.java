import java.util.Arrays;

public class first_last_pstn_elmnt {
    public static void main(String[] args) {
        int[] nums = {5,7,7,7,7,8,8,10};
        int target = 7;
        int[] result = searchRange2(nums, target);
        System.out.println("The position of 1 and last of "+target+" is "+Arrays.toString(result));
    }

    static int[] searchRange(int[] nums, int target) {
        
        int[] ans = {-1,-1};
        
        //check of r the 2 st occurence if the target first . 
        ans[0] = search(nums, target,true);
        if (ans[0]!=-1) {
            ans[1] = search(nums, target, false);
        }

        return ans;


        
    }


    //this funciton just reutrns the target . 

    static int search(int[] nums , int target,boolean findstartIndex){
        int ans=-1; //bcz if no value found the default is ans i.e -1 . 
        int start=0;
        int end=nums.length-1;

        while (end>=start) {
            //find the mid element
            // int mid = (start+end)/2; //might be possible that the (start + end) exceeds the range of int in java . 
            int mid = start+(end-start)/2;
            if (target>nums[mid]) {
                start=mid+1;
            }
            else if (target<nums[mid]) {
                end=mid-1;
            }
            else{
                //potential ans is found . 
                ans=mid; // Mosts imp step as it renews the ans value . ho sakta hai ki yehi ans hoo  . 
                if(findstartIndex){
                    end=mid-1;
                }
                else{
                    start=mid+1;
                }
            }
        }
        return ans;
    }


    static int[] searchRange2(int[] nums, int target){
        int[] position = {-1,-1};
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]==target) {
                position[0]=i;
                break;
            }
        }

        for (int j = nums.length-1; j >=0 ; j--) {
            if (nums[j]==target) {
                position[1]=j;
                break;
            }
        }
        
        return position;
    }
}
