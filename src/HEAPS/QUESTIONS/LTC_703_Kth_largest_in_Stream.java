package HEAPS.QUESTIONS;

import java.util.PriorityQueue;

public class LTC_703_Kth_largest_in_Stream {
    /*
 * PROBLEM: Kth Largest Element in a Stream (LeetCode 703)
 * 
 * GOAL: Design a class to find the kth largest element in a stream of numbers
 *       Support adding new elements and querying kth largest efficiently
 * 
 * APPROACH: Min-Heap of Size K
 * - Maintain a min-heap that stores exactly the k largest elements seen so far
 * - The root of min-heap (smallest in heap) = kth largest overall
 * - When adding new element: if better than current kth largest, replace it
 * 
 * WHY IT WORKS:
 * - Min-heap of size k contains the "top k" elements
 * - Smallest element in this top-k group = kth largest in stream
 * - By keeping heap size = k, peek always gives kth largest
 * 
 * WHY MIN-HEAP (KEY INSIGHT):
 * - We want to track k LARGEST elements
 * - Among these k elements, the SMALLEST one is our answer (kth largest)
 * - Min-heap gives us quick access to this smallest-of-the-largest
 * - When new element comes, we compare with this boundary (peek)
 * - If new element is better, we evict the weakest (poll) and add new one
 * 
 * INTUITION:
 * - Like a VIP club with k spots
 * - Always keep the k most impressive members
 * - The "least impressive" VIP = kth largest = heap top
 * - New candidate better than least impressive? Replace them!
 * 
 * TIME COMPLEXITY: 
 * - Constructor: O(n log k) where n = initial array size
 * - add(): O(log k) for heap operations
 * SPACE COMPLEXITY: O(k) for the heap
 */

class KthLargest {
    PriorityQueue<Integer> pq;  // Min-heap to store k largest elements
    int k;                       // The k value for kth largest

    public KthLargest(int k, int[] nums) {
        this.pq = new PriorityQueue<>();  // Min-heap: smallest at top
        this.k = k;
        
        int ind = 0;
        int n = nums.length;

        // for adding elements u can also add min(k,n) but then its wont; be clean..

        // Reuse the add() for clean code..
        
        for(int ele : nums){
            // by this we ensured that we do not exceed k size and also add uptill k , basically using powers of add()..
            add(ele);

        }
    }
    
    public int add(int val) {
        /* Case 1: Heap not full yet (have fewer than k elements)
         * Simply add the new value - we need more elements to reach k
         */
        if(pq.size() < k) {
            pq.offer(val);
        }
        
        /* Case 2: Heap is full (has k elements) AND new value is larger than kth largest
         * Replace the current kth largest (peek) with this better element
         * This maintains our "top k" invariant
         */
        else if(val > pq.peek()) {
            pq.poll();      // Remove weakest in top-k
            pq.offer(val);  // Add stronger element
        }
        // Case 3 (implicit): val <= peek → do nothing, it's not in top k

        // The kth largest element is always at the top of our min-heap
        return pq.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
}
