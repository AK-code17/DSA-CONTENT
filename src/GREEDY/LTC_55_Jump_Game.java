package HEAPS.QUESTIONS;

public class LTC_55_Jump_Game {
    /*
 * PROBLEM: Jump Game (Leetcode 55)
 * 
 * GOAL: Determine if we can reach the last index starting from index 0
 * 
 * APPROACH: Greedy (Backwards)
 * - Start from the end and work backwards
 * - Keep track of the nearest position that can reach our target
 * - If we can reach index 0, then a complete path exists
 * 
 * WHY IT WORKS:
 * - Break the problem into smaller subproblems
 * - Like going: Home → Market → Shop → School (backwards)
 * - If each step is reachable from the previous, the whole path is valid
 * 
 * INTUITION:
 * Instead of asking "Can I reach the end from start?"
 * We ask "Can I reach the current target from any earlier position?"
 * Keep moving the target closer until we reach index 0
 * 
 * TIME COMPLEXITY: O(n)
 * SPACE COMPLEXITY: O(1)
 */

    public boolean canJump(int[] nums) {
        int n = nums.length;
        
        // Start with the last index as our initial target
        int j = n - 1; 
        int target = j;

        // Work backwards: find positions that can reach our current target
        // Similar to planning route: School→Shop→Market→Home
        // Each position that reaches target becomes the new target
        while(j >= 0) {
            // If current position can reach target, update target
            // This creates a chain of reachable positions
            if(j + nums[j] >= target) {
                target = j;
            }
            j--;
        }

        // If we successfully moved target back to index 0,
        // it means there's a complete path from start to end
        return target == 0;
    }
}
