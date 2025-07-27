public class Rotated_BS {
    
    public static void main(String[] args) {
        int[] nums = {5,6,7,8,9,1,2,3};
        System.out.println(search(nums, 7));
    }
    //Search in Rotated Binary Search via Recurrsion . 
    static int search(int[]nums , int target , int start , int end){
        if(start>end){
            return -1;
        }
        int mid = start+(end-start)/2;

        if(nums[mid]==target){
            return mid;
        }
        if (nums[start]<=nums[mid]) {
            if (target>=nums[start] && target<=nums[mid]) {
                return search(nums, target, start, mid-1);
            }
            else{
                return search(nums, target, mid+1, end);
            }
        }
        else{
            if (target>=nums[mid] && target<=nums[end]) {
                return search(nums, target, mid+1, end);
            }
            else{
                return search(nums, target, start, mid-1);
            }
        }
    }
    
    //another method to search without finding the pivot . 

    static int search(int[]nums , int target){
        int start = 0;
        int end = nums.length-1;
        
        while (start<=end) {
            int mid = start+(end-start)/2;

            if(nums[mid]==target){
                return mid;
            }
            if (nums[start]<=nums[mid]) {
                if (target>=nums[start] && target<=nums[mid]) {
                    end = mid-1;
                }
                else{
                    start = mid+1;
                }
            }
            else{
                if (target>=nums[mid] && target<=nums[end]) {
                    start =  mid+1;
                }
                else{
                    end = mid-1;
                }
            }
        }
        return -1;
    }

    
}
