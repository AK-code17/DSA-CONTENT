package GREEDY;

import java.util.Arrays;

public class N_meeting_a_room {
    /*
    * PROBLEM: N Meetings in One Room (GFG)
    * 
    * GOAL:
    * - Schedule the maximum number of meetings in a single room.
    * - No two meetings should overlap.
    * 
    * REAL-LIFE FEEL:
    * - Imagine you have a single meeting room.
    * - Every meeting has a start and end time.
    * - You can attend only one meeting at a time.
    * - So, how do you attend the **maximum** number of meetings in a day?
    * 
    * INTUITION (KEY IDEA):
    * - Always pick the meeting that **ends earliest**.
    * - Why? Because the sooner one meeting finishes, the more room you have for others later.
    * - This is the "greedy" choice: choosing locally optimal (earliest finish) to get globally best (max count).
    * 
    * APPROACH: Greedy + Sorting
    * 
    * STEP-BY-STEP PLAN:
    * 1️⃣ Make a class to store each meeting’s start and end time.
    * 2️⃣ Sort all meetings by their **end time** in ascending order.
    *     → So we handle earlier finishing meetings first.
    * 3️⃣ Keep a variable `prev_end` = -1 (to track the end time of the last attended meeting).
    * 4️⃣ For each meeting in sorted order:
    *      - If its start time is greater than `prev_end`, attend it.
    *      - Update `prev_end` to this meeting’s end time.
    * 5️⃣ Count how many meetings we could attend.
    * 
    * WHY IT WORKS:
    * - Sorting by end time ensures we always take the earliest finishing option.
    * - This leaves max space for upcoming meetings.
    * - Never backtrack — once a meeting is taken, move to the next possible one.
    * 
    * INTUITION EXAMPLE (Dry Run):
    * start[] = [1, 3, 0, 5, 8, 5]
    * end[]   = [2, 4, 6, 7, 9, 9]
    * 
    * After sorting by end time:
    * Meetings → (1,2), (3,4), (0,6), (5,7), (8,9), (5,9)
    * 
    * Process:
    * - Pick (1,2) ✅ prev_end = 2
    * - Next (3,4) → start > 2 ✅ take it, prev_end = 4
    * - Next (0,6) → start=0 < 4 ❌ skip
    * - Next (5,7) → start > 4 ✅ take it, prev_end = 7
    * - Next (8,9) → start > 7 ✅ take it, prev_end = 9
    * - Next (5,9) → start=5 < 9 ❌ skip
    * 
    * ✅ Total meetings = 4
    * 
    * VISUAL INTUITION:
    * Time line (each box = meeting duration):
    * |(1,2)|...(3,4)|....(5,7)|...(8,9)|
    * 
    * See how we always pick the meeting that frees room earliest.
    * 
    * TIME COMPLEXITY: O(n log n) (for sorting)
    * SPACE COMPLEXITY: O(n) (for meeting array)
    */

    public int maxMeetings(int[] start, int[] end) {
        
        int n = start.length;
        if (n == 0 || n == 1) return n; // Base cases
        
        // Step 1: Create a class to pair each meeting’s start and end time
        class Meet {
            int s, e;
            Meet(int s, int e) {
                this.s = s;
                this.e = e;
            }
        }

        // Step 2: Fill the meeting array
        Meet[] meetings = new Meet[n];
        for (int i = 0; i < n; i++) {
            meetings[i] = new Meet(start[i], end[i]);
        }

        // Step 3: Sort meetings by end time (earliest finishing first)
        Arrays.sort(meetings, (a, b) -> a.e - b.e);

        // Step 4: Select meetings greedily
        int prev_end = -1; // last attended meeting’s end
        int meet_cnt = 0;  // total meetings attended
        
        for (Meet meeting : meetings) {
            // Can attend only if current starts after the previous one ends
            if (meeting.s > prev_end) {
                meet_cnt++;
                prev_end = meeting.e; // update last meeting end
            }
        }

        // Step 5: Return total count of meetings attended
        return meet_cnt;
    }

}
