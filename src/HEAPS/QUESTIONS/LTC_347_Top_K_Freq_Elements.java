package HEAPS.QUESTIONS;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class LTC_347_Top_K_Freq_Elements {
    /*
    * PROBLEM: Top K Frequent Elements (Leetcode 347)
    *
    * GOAL:
    * - Find the 'k' most frequent elements from the given array.
    * 
    * EXAMPLE:
    * nums = [1,1,1,2,2,3], k = 2
    * Output → [1, 2]
    * (since 1 appears 3 times, and 2 appears 2 times)
    *
    * INTUITION (CORE IDEA):
    * - We need to know how often each element appears.
    * - Then, we must keep only the top K most frequent ones.
    * - A min-heap is perfect for this:
    *   → It allows us to keep the smallest frequency at the top,
    *      so if a new element has higher frequency, we can replace the weakest one.
    * 
    * APPROACH: HashMap + Min-Heap (PriorityQueue)
    *
    * STEP-BY-STEP PLAN:
    * 1️⃣ Count the frequency of each element using a HashMap.
    *     - Key → element
    *     - Value → frequency (count)
    * 
    * 2️⃣ Use a Min-Heap (PriorityQueue) to keep track of top K elements.
    *     - Heap stores pairs: [element, frequency]
    *     - Sort heap by frequency (ascending)
    * 
    * 3️⃣ Iterate over each (element, frequency) pair:
    *     - If heap size < k → directly add it.
    *     - Else if current frequency > heap's smallest → pop the smallest and insert the new one.
    *     - Else → skip it.
    * 
    * 4️⃣ After processing all elements, heap contains the top K frequent ones.
    *     - Extract the element (first index of each pair) into an array.
    * 
    * WHY THIS WORKS:
    * - The heap always stores K most frequent elements seen so far.
    * - Lower frequency ones get removed automatically.
    * - It’s similar to maintaining a “Kth largest” set of numbers.
    * 
    * DRY RUN (INTUITION):
    * nums = [1,1,1,2,2,3], k = 2
    * Frequency map → {1:3, 2:2, 3:1}
    * 
    * Process:
    * - Add 1 → pq = [1:3]
    * - Add 2 → pq = [1:3, 2:2]
    * - Add 3 → pq = [1:3, 2:2, 3:1] → size > k → remove smallest (3:1)
    * 
    * Final heap → [1:3, 2:2]
    * → Output [1, 2]
    * 
    * TIME COMPLEXITY: O(n log k)
    *   - Counting frequencies: O(n)
    *   - Inserting/removing in heap: O(n log k)
    * SPACE COMPLEXITY: O(n + k)
    *   - O(n) for map
    *   - O(k) for heap
    */

    public int[] topKFrequent(int[] nums, int k) {

        // Step 1: Count frequency of each element
        Map<Integer, Integer> map = new HashMap<>();
        for (int ele : nums) {
            map.put(ele, map.getOrDefault(ele, 0) + 1);
        }

        // Step 2: Create a Min-Heap (PriorityQueue) based on frequency
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        // Step 3: Process each element in the frequency map
        for (Integer ele : map.keySet()) {
            // Case 1: Heap not yet full → directly add
            if (pq.size() < k) {
                pq.offer(new int[]{ele, map.get(ele)});
            }
            // Case 2: Heap full → check if current frequency is higher than smallest in heap
            else if (map.get(ele) > pq.peek()[1]) {
                pq.poll(); // remove smallest frequency
                pq.offer(new int[]{ele, map.get(ele)}); // insert current higher frequency
            }
            // Else: current element is less frequent → ignore
        }

        // Step 4: Extract top K elements from heap
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = pq.poll()[0]; // store element, not frequency
        }

        return ans;
    }

}
