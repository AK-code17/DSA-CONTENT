public class Spl_Arr {
    public static void main(String[] args) {
        int[] nums = {7, 2, 5, 8, 10};
        int k = 2;
        System.out.println(splitArray(nums, k));
    }

    static int splitArray(int[] nums, int k) {
        int start = 0;
        int end = 0;

        // Calculate the initial values for start and end
        for (int i = 0; i < nums.length; i++) {
            start = Math.max(start, nums[i]); // start is the max element
            end += nums[i];                   // end is the sum of all elements
        }

        // Apply binary search
        while (start < end) {
            // Try for the middle value for potential answer
            int mid = start + (end - start) / 2;

            // Calculate how many pieces we can divide this array into with mid as the max sum
            int sum = 0;
            int pieces = 1;

            for (int num : nums) {
                if (sum + num > mid) {
                    // We can't add this num to the current subarray, make a new subarray
                    pieces++; 
                    sum = num; // start a new subarray with the current num
                } else {
                    sum += num;
                }
            }

            if (pieces > k) {
                // If more pieces than k, we need to increase the allowed sum (mid)
                start = mid + 1;
            } else {
                // If fewer or equal pieces, try for a smaller maximum sum
                end = mid;
            }
        }

        // When start == end, we've found the smallest possible largest sum
        return start;
    }
}
