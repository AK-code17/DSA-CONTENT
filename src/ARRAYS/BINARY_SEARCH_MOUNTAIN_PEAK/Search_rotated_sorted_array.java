public class Search_rotated_sorted_array {
    public static void main(String[] args) {
        int[] nums= {4,6,7,0,1,2};
        
        // System.out.println(pivot); 

        int target = 6;
        System.out.println(search_new(nums,target));
    }

    //another method to search without finding the pivot . 

    static int search_new(int[]nums , int target){
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

    //Finding with the help of pivot .
    static int search(int [] nums, int target){
        int pivot = find_pivot(nums);
        
        if (pivot==-1) {
            return binarysearch(nums, target, 0, nums.length-1);
        }
        if (nums[pivot]==target) {
            return pivot;
        }

        if (target>=nums[0]) {
            return binarysearch(nums, target, 0, pivot-1);
        }
        
        return binarysearch(nums, target, pivot+1, nums.length-1);
        

    }

    //use this for distinct elements in rotated array . 

    static int find_pivot(int[] nums){
        int start =0;
        int end=nums.length-1;
        while (start<=end) {
            
            int mid = start+(end-start)/2;
            //4 cases over here . 
            if (mid<end && nums[mid]>nums[mid+1]) { //here mid<end uses because it may become out of bound . 
                return mid;
            }
            if (mid > start && nums[mid]<nums[mid-1]) {  //here mid>start uses because it may become out of bound .
                return mid-1;
            }
            if (nums[start]>=nums[mid]) {
                end=mid-1;
            }
            else {
                start=mid+1;
            }

        }
        return -1;
    }


    //USE THIS FOR DUPLICATES ARRAY ELEMENTS IN ROTATED SORTED ARRAY . 

    static int find_pivotwithduplicates(int[] nums){
        int start =0;
        int end=nums.length-1;
        while (start<=end) {
            
            int mid = start+(end-start)/2;
            //4 cases over here . 
            if (mid<end && nums[mid]>nums[mid+1]) {
                return mid;
            }
            if (mid > start && nums[mid]<nums[mid-1]) {
                return mid-1;
            }
            
            //if elements at middle, start , end are equal then just skip the duplicates . 
            if (nums[mid]==nums[start] && nums[mid]==nums[end]) {
                //skip the duplicates
                
                //note what if these elements at start and end were the pivot??
                //Check if start is pivot ??

                if (nums[start]>nums[start+1]) {
                    return start;
                }
                start++;
                
                //check if end is pivot or not?

                if (nums[end]<nums[end-1]) {
                    return end-1;
                }
                end--;
            }
            //left side is sorted so pivot should be in right

            else if (nums[start]<nums[mid] || (nums[mid]==nums[end] && nums[mid]>nums[start])) {
                start=mid+1;
            }
            else{
                end=mid-1;
            }


        }
        return -1;
    }

    static int binarysearch(int[]nums , int target,int start ,int end){
        // int start=0;/
        // int end=peak_index;
        // int newmid= peakIndexInMountainArray(arr);

        while (end>=start) {
            //find the mid element
            int mid = (start+end)/2; //might be possible that the (start + end) exceeds the range of int in java . 
            // int mid = end;
            if (target>nums[mid]) {
                start=mid+1;
            }
            else if (target<nums[mid]) {
                end=mid-1;
            }
            else{
                return mid;
            }
        }
        return -1;

    }
}
