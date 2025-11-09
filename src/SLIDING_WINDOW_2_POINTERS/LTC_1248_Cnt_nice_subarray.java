package SLIDING_WINDOW_2_POINTERS;

public class LTC_1248_Cnt_nice_subarray {
    /*
    * PROBLEM: Count Number of Nice Subarrays (LeetCode 1248)
    *
    * GOAL:
    * - Find the total number of continuous subarrays that have *exactly* 'k' odd numbers.
    *
    * REAL-LIFE FEEL:
    * - Imagine you have a long series of data points, some are "special" (odd) and
    * some are "normal" (even).
    * - You need to find every single data segment that contains exactly 'k' of those
    * "special" points.
    *
    * INTUITION (KEY IDEA):
    * - This is a single-pass, "direct counting" approach.
    * - The main idea is to find a "minimal" valid window with 'k' odds.
    * - Then, we count how many valid *starting* positions exist for that window
    * (this includes all the "extra" even numbers at the start).
    * - This count is stored in `prev_subarray` (or `count_at_start`).
    * - We then "coast" forward: for every new *even* number we see at the end,
    * it can be added to *all* our `prev_subarray` starting positions, so we
    * just add `prev_subarray` to our total `ans`.
    * - When we hit a *new* odd number, the "group" of 'k' odds has changed. We
    * must reset `prev_subarray = 0` and re-calculate the new count of
    * valid starting positions.
    *
    * APPROACH: Single-Pass Sliding Window (Direct Counting)
    *
    * STEP-BY-STEP PLAN:
    * 1. Initialize `start = 0`, `cnt_odd = 0`, `prev_subarray = 0`, `ans = 0`.
    * 2. Loop `end` from 0 to n:
    * 3.  **Acquire:** Get `nums[end]`.
    * 4.  **Check for Odd:** If `nums[end]` is odd:
    * - Increment `cnt_odd`.
    * - **Reset:** Set `prev_subarray = 0`. This is crucial. We've found a new
    * 'k' group, so the old count of starting positions is invalid.
    * 5.  **Count & Shrink Loop:** `while (cnt_odd == k)`:
    * - This loop finds all valid starting positions for the current 'k' group.
    * - Increment `prev_subarray` (we found a new valid start).
    * - **Shrink:** Check `nums[start]`. If it's odd, decrement `cnt_odd`
    * (we're about to break the window).
    * - `start++`.
    * 6.  **Coast & Add Answer:** `ans += prev_subarray;`
    * - This is the magic line. If `end` is on an even number, `cnt_odd` won't
    * be 'k', the `while` loop is skipped, and we just add the
    * "precomputed" `prev_subarray` count to our total.
    * 7. Return `ans`.
    *
    * INTUITION EXAMPLE (Dry Run):
    * nums = [E, E, O, E, O, E], k = 2
    *
    * - end=0 ('E'): cnt_odd=0. while loop skips. ans += 0. ans=0.
    * - end=1 ('E'): cnt_odd=0. while loop skips. ans += 0. ans=0.
    * - end=2 ('O'): cnt_odd=1. while loop skips. ans += 0. ans=0.
    * - end=3 ('E'): cnt_odd=1. while loop skips. ans += 0. ans=0.
    * - end=4 ('O'): cnt_odd=2. prev_subarray=0.
    * - Enter `while(cnt_odd == 2)`:
    * - prev_subarray=1. Shrink 'E' (start=0). start=1.
    * - prev_subarray=2. Shrink 'E' (start=1). start=2.
    * - prev_subarray=3. Shrink 'O' (start=2). cnt_odd=1. start=3.
    * - Exit `while` loop.
    * - `ans += prev_subarray` (3). ans=3.
    * - end=5 ('E'): cnt_odd=1.
    * - `while` loop skips.
    * - `ans += prev_subarray` (3). ans=6.
    *
    * ✅ Total subarrays = 6
    *
    * TIME COMPLEXITY: O(n) (Each pointer `start` and `end` only moves forward)
    * SPACE COMPLEXITY: O(1)
    */
    public int numberOfSubarrays(int[] nums, int k) {
        /* This was the better / less optimal soln doing it in max O(2*N) and same as the binary subarray with sum..

       Now let's start with the most optimal and the single pass soln..
     Approach : Just find the min window and keep expanding (end) till cnt_odd < k , then simple shrink the window while(cnt_odd == k) while shrinking increment the (prev_subarray_cnt++) meaning, those even values who are not contributing in k odd _cnts , so that we can get the cnt of even_vals denoting the subarray count computed till now , 

     Now if the next arr[end] is even simply add the prev_subarray_cnt , bcz if this cnt till now is 3 and next is even then the subarray cnt in ans will be incremented by 1 for each of the subarray that mean for every given set of k odd vals we need to keep adding the subarray_cnt ,till we get a new odd val..

     Now if the next is odd , hence the cnt_odd again equals k , since while shrinking we cnt_odd-- if arr[start]  == odd , so now if we get a new odd hence the subarray_cnt for this set will be 0 ,so each time getting an odd we will increment its cnt , and reset the val of prev_subarray to 0...
        */

        int len = nums.length;
        int start = 0;
        int cnt_odd = 0;
        int prev_subarray = 0;
        int ans = 0;

        for (int end = 0; end < len; end++) {
            if (nums[end] % 2 != 0) {
                cnt_odd++; // ofc if num is odd then cnt_odd++;
                prev_subarray = 0; // this is imp this will be very IMP when we shringked the window and cnt_odd == k-1 because if this is odd then the cnt_odd again becomes == k and then we cannot keep the cnt of prev_subarray having the older set of k odds , since we got a new odd val ,then we need to reset this and form a new cnt of prev_subarray in the below while loop...
            }

            while (cnt_odd == k) {
                prev_subarray++; // incr. the count of subarray marking as subarray in the given range where cnt_odd == k..
                if (nums[start] % 2 != 0) {
                    cnt_odd--;
                }
                start++;
            }

            // now this is Also , VERY VERY imp step ,now see if the curr element is EVEN then we will add the current prev_subarray count in the ans but why?? because we calculated the evens in range meaning the cnt of subarray , now if new even is there it will incr. the count of subarray with 1 * no. of subarray so basocally adding the cnt into ans for each even in the given range..

            // if it was odd then above only values got reset ...
            ans += prev_subarray;
        }

        return ans;


        // int ans = cnt_subarray_k(nums,k) - cnt_subarray_k(nums,k-1);
        // return ans;
    }

    // static int cnt_subarray_k(int[] arr , int k){
    //     if(k<0) return 0;
    //     int cnt_odd = 0;
    //     int ans = 0;

    //     int start = 0,end=0;

    //     while(end < arr.length){
    //         int num = arr[end++];
    //         if(num % 2 !=0) cnt_odd++;

    //         //when the cnt_odd gets above k
    //         while(cnt_odd > k){
    //             int num_s = arr[start++];
    //             if(num_s % 2 !=0) cnt_odd--;
    //         }

    //         //now since cnt_odd<=k , we can add the length to add , as the length is the no. of more subarrays can be formed..
    //         ans += end-start+1;
    //     }

    //     return  ans;
    // }

}
