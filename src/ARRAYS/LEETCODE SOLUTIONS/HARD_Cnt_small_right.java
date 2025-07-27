import java.util.ArrayList;
import java.util.List;

public class HARD_Cnt_small_right {
    
    public static void main(String[] args) {
        int[] nums = {5,2,6,1};
        List<Integer> ans = countSmaller(nums);
        System.out.println(ans);
    }

    static List<Integer> countSmaller(int[] nums) {
        ArrayList<Integer>list= new ArrayList<>(nums.length);
        for(int i=0;i<nums.length;i++){
            int cnt = Cnt_right_small(nums , i+1 , nums.length-1 , nums[i]);
            list.add(cnt); 
        }

        return list;

    }

    static int Cnt_right_small(int[] nums, int start, int end, int target) {
        if (start > end) {
            return 0;  // No more elements to check, so return 0
        }
    
        int count;
        if (nums[start] < target) {
            count = 1;  // Current element is smaller, so start with 1
        }
        else {
            count = 0;  // Current element is not smaller
        }

        // Add count from the remaining elements
        return count + Cnt_right_small(nums, start + 1, end, target);
    }
}
