public class LTC_56_Merge_Intervals {
    /*
    * PROBLEM: Merge Intervals (LC 56)
    *
    * GOAL: Got a bunch of intervals. If they overlap, I need to
    * squish 'em together into one.
    *
    * MY INTUITION (The "Aha!" Moment):
    *
    * - At first, I thought I could just loop and compare, but what
    * if they're out of order? Like `[10, 12], [1, 5]`. That fails.
    *
    * - THE KEY: I *must* sort them by their START time first.
    *
    * - Why? Bcz after sorting, all the intervals that *could*
    * overlap are sitting right next to each other.
    * - Example: `[1, 5], [3, 7], [10, 12]`.
    *
    * - Now I can just do a single pass and check each one against
    * the *last* one I processed.
    *
    * MY APPROACH (The Game Plan):
    *
    * 1. Sort all intervals by their `start` time. This is the main step.
    *
    * 2. Create my `ans` list.
    *
    * 3. I'll need to track the `end` of the last interval I've merged.
    * Let's call it `prev_end` and start it at -1 (so the
    * very first interval always gets processed).
    *
    * 4. Loop through each `row` in the sorted intervals:
    *
    * 5.  - CASE 1 (No Overlap): If the current `row[0]` (start)
    * is *after* `prev_end`...
    * - ...it's a new, separate interval. Just `ans.add(row)`.
    *
    * 6.  - CASE 2 (Overlap!): If the current `row[0]` (start) is
    * *before or at* `prev_end`...
    * - ...this is a merge. I need to update the *last*
    * interval I already put in `ans`.
    * - How? Just `ans.get(ans.size()-1)[1] = ...`
    * - The new end time is just the `Math.max` of the
    * `prev_end` and the current `row[1]`.
    *
    * 7.  - After either case, I have to update `prev_end` for the
    * *next* loop. It's just the end-time of whatever is now
    * last in the `ans` list.
    *
    * ---
    *
    * WHY IS THIS A GREEDY PROBLEM?
    *
    * - The "Greedy" part is the choice we make *after* sorting.
    *
    * - At each step in our loop, we see an interval. We make the
    * "locally optimal" or greedy choice to **merge it *now***
    * if it overlaps with the previous one.
    *
    * - We are greedily assuming it's always correct to merge
    * with the immediately preceding interval, and the sorting
    * *guarantees* this greedy choice will lead to the
    * globally correct answer.
    *
    * WHY THIS DATA STRUCTURE & ALGORITHM?
    *
    * - **Algorithm (Sorting by Start Time):** This is the most
    * important part. We *must* sort by `start` time. This
    * groups all potential overlaps together. It allows us to
    * solve the problem in a single `O(n)` pass *after* the sort.
    * If we didn't sort, we'd have to compare every interval
    * with every other interval, which is a slow `O(n^2)` solution.
    *
    * - **Data Structure (ArrayList):** We need to store the
    * final, merged intervals. Since we have *no idea* how
    * many intervals we'll have at the end (e.g., 100 intervals
    * might all merge into 1), we can't use a fixed-size array.
    * An `ArrayList` is the perfect tool because it's a
    * dynamic array that can grow as we add our merged intervals.
    *
    * ---
    *
    * TIME/SPACE:
    * - TIME: O(n log n) -> O(n log n) for the sort + O(n) for the loop.
    * - SPACE: O(n) -> for the `ans` list.
    */
    public int[][] merge(int[][] intervals) {

        // Sort the intervals by their start time ,bcz after
        // sorting by the start only we will be able to merge
        // all the overlappings in sequence and in single
        // pass we will be able to form the ans..
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        ArrayList<int[]> ans = new ArrayList<>();

        // Tracks the end of the last interval in our 'ans' list.
        // Start at -1 so the first interval (e.g., [1,5])
        // is always `row[0] > prev_end`.
        int prev_end = -1;

        for (int[] row : intervals) {

            // CASE 1: No Overlap.
            // if the current start > prev_end hence no overlap,
            // simply add in ans..
            if (row[0] > prev_end) {
                ans.add(row);
            }

            // CASE 2: Overlap exists.
            // if the current start <= prev_end, they're touching or overlapping
            else {
                // My old, inefficient idea (commented out):
                // ans.remove(ans.size()-1);
                // ans.add(new int[] {Math.min(prev_start,row[0]),Math.max(prev_end,row[1])});

                // The *better* way: Just update the last interval *in place*.
                // Grab the last interval we added to 'ans'.
                int[] last = ans.get(ans.size() - 1);

                // Now, just update its end time.
                // We don't need to update the start (like Math.min(prev_start, row[0]))
                // bcz we already sorted by start time. The `last[0]` is
                // *guaranteed* to be the smallest start for this merged block.
                last[1] = Math.max(prev_end, row[1]);
            }

            // No matter what, update prev_end for the *next* loop iteration.
            // It's just the end-time of the last interval currently in our 'ans' list.
            prev_end = ans.get(ans.size() - 1)[1];
        }

        return ans.toArray(new int[ans.size()][]);
    }
}
