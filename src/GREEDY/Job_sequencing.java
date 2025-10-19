package GREEDY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Job_sequencing {

    // Brute is using Arrays of max deadline and search the slots in the from its current deadline index -1 to 0th index and put in the empty slot ..

    
        /*
    * PROBLEM: Job Sequencing Problem (GFG)
    * -------------------------------------
    * We are given:
    * - 'deadline[i]' → the last day a job can be done
    * - 'profit[i]' → the profit earned if that job is completed before or on its deadline
    *
    * GOAL:
    * - Schedule as many jobs as possible within their deadlines
    * - So that the total profit is maximum
    * 
    * Example:
    *  deadline = [2, 1, 2]
    *  profit   = [100, 50, 10]
    *  Output: [2, 150]  (we can do 2 jobs, total profit = 150)
    *
    * -------------------------------------
    * APPROACH: GREEDY + MIN-HEAP
    * -------------------------------------
    * Step 1: Sort all jobs by their deadlines (smallest first)
    *         → So we handle jobs with earlier deadlines first.
    *
    * Step 2: Use a Min-Heap (PriorityQueue in Java) to store the profits of selected jobs.
    *         → The heap automatically keeps the smallest profit on top.
    *
    * Step 3: For each job:
    *         - If we still have space to do it (its deadline > heap size), add it.
    *           (Meaning: we have an empty time slot before its deadline)
    *         - If no space (all slots filled), check:
    *             → If current job’s profit is more than the smallest profit in heap,
    *                remove that smaller one and add the current (replace weaker with stronger).
    *
    * -------------------------------------
    * WHY IT WORKS:
    * -------------------------------------
    * - Sorting by deadlines ensures we never miss an earlier job due to a later one.
    * - Heap size = number of jobs we have taken so far (like used time slots).
    * - Min-heap helps us quickly find the job with least profit among chosen ones.
    *   → This allows us to easily replace it when a better job appears.
    *
    * -------------------------------------
    * KEY INSIGHT (Remember this line!):
    * -------------------------------------
    * “At any point, our heap contains the most profitable jobs that can fit within their deadlines.”
    * - If a new job can be added, we do it.
    * - If we are full, we kick out the weakest one if the new job gives better profit.
    *
    * -------------------------------------
    * INTUITION (Visualize it):
    * -------------------------------------
    * - Imagine you have a few time slots before deadlines (like boxes of time).
    * - You want to fill them with the most profitable jobs.
    * - If you find a new job that gives more profit than the weakest one you already have,
    *   swap it out — just like replacing a cheap product with a premium one in your cart.
    *
    * -------------------------------------
    * COMPLEXITY:
    * -------------------------------------
    * - Sorting: O(n log n)
    * - Heap operations: O(n log n)
    *   → Each insertion/removal in heap is log n
    * - Overall: O(n log n)
    * - Space: O(n) for heap
    */

    public ArrayList<Integer> jobSequencing(int[] deadline, int[] profit) {

        // A simple Job class to link each job’s deadline and profit together
        class Job {
            int dl;   // deadline
            int pft;  // profit

            Job(int dl, int pft) {
                this.dl = dl;
                this.pft = pft;
            }
        }

        // Step 1: Combine deadline and profit into Job objects
        int n = deadline.length;
        Job[] jobs = new Job[n];

        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(deadline[i], profit[i]);
        }

        // Step 2: Sort jobs by their deadlines (earliest first)
        // This ensures we schedule jobs that expire sooner first
        Arrays.sort(jobs, Comparator.comparingInt(a -> a.dl));

        // Step 3: Use a Min-Heap (PriorityQueue) to keep track of selected jobs’ profits
        // The heap always keeps the job with the smallest profit at the top
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // Step 4: Process each job one by one
        for (Job job : jobs) {
            int d = job.dl;   // deadline
            int p = job.pft;  // profit

            // CASE 1: There’s still time to take this job
            // Example: deadline = 3, heap.size() = 2 → we can fit this job
            if (d > pq.size()) {
                pq.offer(p);  // just add it to the heap
            }

            // CASE 2: All slots up to this job’s deadline are full
            // Then, check if the current job has higher profit than the smallest one we picked so far
            else if (pq.peek() < p) {
                pq.poll();     // remove the least profitable job
                pq.offer(p);   // add the more profitable one
            }
            // else: current job has lower profit → skip it
        }

        // Step 5: Calculate total profit and job count
        int totalProfit = 0;
        int totalJobs = pq.size();

        while (!pq.isEmpty()) {
            totalProfit += pq.poll(); // sum up profits of selected jobs
        }

        // Step 6: Return result as [number_of_jobs, total_profit]
        return new ArrayList<>(Arrays.asList(totalJobs, totalProfit));
    }

}
