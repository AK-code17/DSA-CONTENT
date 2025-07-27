public class Check_sorted {
    
    public static void main(String[] args) {
        int[] nums = {1,2,5,4,8,9,12};
        System.out.println(Check_sorted(nums, 0));
    }

    static boolean Check_sorted(int[] nums , int start){
        if (nums[start]>nums[nums.length-1]) {
            return false;
        }
        if(start==nums.length-1){
            return true;
        }
        if (nums[start]<nums[start+1]) {
            return true && Check_sorted(nums, start+1);
        }
        return false;
    }
}
