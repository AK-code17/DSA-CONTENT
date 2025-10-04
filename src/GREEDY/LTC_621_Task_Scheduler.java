package HEAPS.QUESTIONS;

public class LTC_621_Task_Scheduler {
    /*
 * PROBLEM: Task Scheduler (Leetcode 621)
 * 
 * GOAL: Find minimum time needed to execute all tasks with cooldown period 'n'
 * 
 * APPROACH: Greedy + Max Heap (Priority Queue)..
 * - We want to execute high-frequency tasks first to avoid them piling up
 * - Process tasks in cycles of (n+1) time units
 * - In each cycle, pick up to (n+1) tasks (highest frequency first)
 * - Tasks used in a cycle go into cooldown and return after the cycle completes
 * 
 * WHY IT WORKS:
 * - By always picking the most frequent task, we spread them out optimally
 * - Processing in cycles naturally handles the cooldown period
 * - If we run out of tasks mid-cycle, those slots become idle time
 * 
 * TIME COMPLEXITY: O(tasks × log(26)) ≈ O(tasks) 
 * SPACE COMPLEXITY: O(26) ≈ O(1)
 */

    public int leastInterval(char[] tasks, int n) {
        // Step 1: Count frequency of each task (A-Z)
        int[] map = new int[26];
        
        for(char ch : tasks){
            map[ch - 'A']++;
        }

        // Step 2: Max heap to always get the most frequent task
        // (Greedy: tackle high-frequency tasks first)
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for(int freq : map){
            if(freq > 0) pq.offer(freq);
        }

        int time = 0;
        
        // Step 3: Process tasks in cycles until all are done
        while(!pq.isEmpty()){
            // Temp list to store tasks used in this cycle (they're in cooldown)
            ArrayList<Integer> list = new ArrayList<>();
            
            // Try to fill (n+1) slots in this cycle
            for(int i = 0; i < n + 1; i++){
                if(!pq.isEmpty()){
                    int freq = pq.poll();
                    freq--;  // Use this task once
                    list.add(freq);
                }
            }

            // Put tasks back into heap (cooldown period over)
            for(int val : list){
                if(val > 0) pq.offer(val);
            }

            /*
             * CRITICAL SECTION: Time Calculation Logic
             * =========================================
             * 
             * SCENARIO 1: pq.isEmpty() == true (LAST CYCLE)
             * - All tasks done! No more tasks left to process
             * - Only count actual tasks executed (list.size())
             * - No need to pad with idle time
             * 
             * Example: Last cycle executes A, B (2 tasks)
             * → time += 2 (NOT n+1)
             * Why? We're done, no need to wait for cooldown!
             * 
             * SCENARIO 2: pq.isEmpty() == false (MORE CYCLES AHEAD)
             * - Heap still has tasks, more cycles coming
             * - MUST complete full (n+1) time slots
             * - Respects cooldown period 'n' between same tasks
             * 
             * Example: Cycle executes A, B but n=2
             * → time += 3 (full cycle with 1 idle)
             * Why? Tasks in cooldown need 'n' gaps before reuse
             * 
             * KEY INSIGHT:
             * - More work remaining → wait full (n+1) time
             * - No more work → only count actual tasks
             */
            if(pq.isEmpty()) {
                // Last cycle: only count actual tasks executed
                time += list.size();
            }
            else {
                // Full cycle with potential idle slots
                time += (n + 1);
            }
        }

        return time;
    }
}
