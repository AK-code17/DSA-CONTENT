public class LTC_57_Insert_Intervals {
    /*
    * PROBLEM: Insert Interval (LC 57)
    *
    * GOAL: I have a list of *already sorted* (and non-overlapping)
    * intervals. I need to stick a `newInt` into this list, and
    * merge it with any intervals it overlaps with.
    *
    * MY INTUITION (The "Aha!" Moment):
    *
    * - This is like "Merge Intervals," but easier because the list
    * is already sorted!
    *
    * - This means I don't have to compare everything with everything.
    * I can just do a single pass.
    *
    * - My brain just sees this in three simple phases:
    * 1.  **"Before" Phase:** A bunch of intervals that end
    * *before* my `newInt` even starts. Just add 'em to the list.
    * 2.  **"Overlap" Phase:** A bunch of intervals that are
    * touching or overlapping my `newInt`. I'll just "eat"
    * all of these by expanding my `newInt` to be one big
    * merged interval.
    * 3.  **"After" Phase:** A bunch of intervals that start
    * *after* my (now possibly bigger) `newInt` ends.
    * Just add 'em to the list.
    *
    * MY APPROACH (The Game Plan):
    *
    * 1. Create my `ans` list.
    *
    * 2. (My sort step - good for safety, but technically not
    * needed for this specific LC problem since it's pre-sorted).
    *
    * 3. Loop through the `intervals` using an index `i`.
    *
    * 4.  **PHASE 1 (Before):** Keep looping as long as the
    * current `interval[1] < newInt[0]`. These are all
    * "before" intervals, so just `ans.add(interval)`.
    *
    * 5.  **PHASE 2 (Overlap):** Keep looping as long as the
    * current `interval[0] <= newInt[1]`. This is the
    * merge! I *won't* add to `ans` yet. I'll just
    * update `newInt` to be the `min` of the starts and
    * `max` of the ends. This "eats" the current interval.
    *
    * 6.  **Add the Merged Interval:** After the "Overlap" loop
    * (Phase 2) is done, `newInt` is now the final, big,
    * merged interval. I'll `ans.add(newInt)` *just one time*.
    *
    * 7.  **PHASE 3 (After):** The main loop might have stopped
    * (or `break;`'ed). I just need a final `while` loop
    * to add all the remaining intervals from `i` to the end.
    *
    * ---
    *
    * WHY IS THIS A GREEDY PROBLEM?
    *
    * - It's "Greedy" because at each step, I'm making the simple,
    * "locally optimal" choice, and the sorting *guarantees*
    * this works.
    *
    * - **My Greedy Choices:**
    * 1.  "Does this interval end *before* `newInt` starts?"
    * If yes, I greedily add it to the `ans` list,
    * assuming it's final.
    * 2.  "Does this interval *overlap* `newInt`?" If yes,
    * I greedily merge it *now* by updating `newInt`,
    * assuming this is the best way to build the
    * final merged block.
    *
    * - Because the list is pre-sorted, I never have to
    * "look back." My greedy, local choice at each step
    * is guaranteed to be the right one for the global solution.
    *
    * WHY THIS DATA STRUCTURE & ALGORITHM?
    *
    * - **Algorithm (Single Pass / 3-Phase):** The most
    * important part is that the input is **already sorted**.
    * This lets us skip the $O(n \log n)$ sort and solve
    * everything in a single, fast **$O(n)$** pass.
    * The 3-phase (Before, Overlap, After) logic is just
    * the cleanest way to implement this single pass.
    *
    * - **Data Structure (ArrayList):** Same as "Merge Intervals."
    * I don't know the final size of the list. It could be
    * `n+1` (if no merges) or just `1` (if everything
    * merges). An `ArrayList` is perfect because it can
    * grow as I add the final intervals.
    *
    * ---
    *
    * TIME/SPACE:
    * - TIME: O(n log n) (because of my `Arrays.sort` line).
    * - *True Optimal Time:* **O(n)** (if I remove that sort
    * line, since the input is pre-sorted).
    * - SPACE: O(n) (for the `ans` list).
    */

    public int[][] insert(int[][] intervals, int[] newInt) {
            
        // This sort is good for a general-purpose solution,
        // but for LC 57, the input is already sorted, so
        // this line makes it O(n log n) instead of O(n).
        // Arrays.sort(intervals,(a,b) -> a[0]-b[0]);

        List<int[]> ans = new ArrayList<>();
        int i = 0;
        int len = intervals.length;

        // PHASE 1: Add all intervals that come "Before"
        while (i < len) {
            int[] interval = intervals[i];
            
            // if interval's end < newInt[0] , hence its ending
            // before the start of the newInt..
            if (interval[1] < newInt[0]) {
                ans.add(interval);
            }

            // if interval's start > newInt's end, then it means
            // its starting after the interval is merged so just
            // break from here , because if its merged then we
            // need to add that first and then add the
            // remainig intervals starting afterwards..
            
            // This is the "After" case
            else if (interval[0] > newInt[1]) {
                break;
            }
            
            // PHASE 2: This is the "Overlap" case
            else {
                // haha , else we know that this is the place where
                // the merging will take place , so just merge and
                // update the newInt by min of start and max of end
                // to ensure proper overlapping..
                //  1----3
                //     2----4
                // overlap = 1----4
                newInt[0] = Math.min(interval[0], newInt[0]);
                newInt[1] = Math.max(interval[1], newInt[1]);

                // but remember we will not add this , as there
                // might be overlapping further so after all the
                // overalpping is done then we will add this..
            }
            i++;
        }

        // PHASE 2.5: Add the (now fully merged) newInt
        // this stat is very helpful as this adds the merged ones
        // first then adds the afte ones , but what if there
        // are all smaller than this , then also by adding
        // this is valid since then it will be on the last of all...
        ans.add(newInt);

        // PHASE 3: Add all the remaining "After" intervals
        // to add the remainig ones..
        while (i < len) {
            ans.add(intervals[i++]);
        }

        return ans.toArray(new int[ans.size()][]);
    }
}
