public class Rotation_cnt_RSA {
    public static void main(String[] args) {
        int[]nums ={1,2,3,4,5,6};
        // System.out.println("Rotaion count is "+Rot_cnt_RSA(nums));
        
        System.out.println("Pivot through binary search is "+binarysearch_Rot_cnt_RSA(nums));

    
    
    }

    static int binarysearch_Rot_cnt_RSA(int[] nums){
        int pivot = find_pivotwithduplicates(nums);
        return pivot+1;
    }
    
    
    
    
    static int Linear_Rot_cnt_RSA(int[] nums){
        int max_ele = nums[0];
        int max_index=0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]>max_ele) {
                max_ele=nums[i];
                max_index=i;
            }
        }
        return max_index+1;
    }



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
            if (nums[start]>=nums[mid]) {
                end=mid-1;
            }
            else {
                start=mid+1;
            }

        }
        return -1;
    }
}
