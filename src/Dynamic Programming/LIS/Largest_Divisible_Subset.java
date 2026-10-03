/*
================================================================================
                    ELITE VERSION — LARGEST DIVISIBLE SUBSET
================================================================================

Problem:
LeetCode 368 — Largest Divisible Subset

================================================================================
1. PROBLEM CONTRACT
================================================================================

Given an array of distinct positive integers, return a largest subset such that
for every pair of elements a and b in the subset:

        a % b == 0 OR b % a == 0

If multiple largest subsets exist, any one valid largest subset is acceptable.

Important constraints/assumptions:

    - nums contains distinct positive integers.
    - n <= 1000.
    - Since all values are positive, sorting creates a natural smaller -> larger
      direction.
    - After sorting, for j < i:

            nums[j] <= nums[i]

      Therefore, if nums[j] and nums[i] are divisible, the relevant check is:

            nums[i] % nums[j] == 0

Platform-specific consideration:

    - The problem asks for the actual subset, not only its size.
    - Therefore, storing only the DP lengths is insufficient.
    - We need predecessor/parent information for reconstruction.
    - No special tie-breaking is required; any maximum-size valid subset works.

Rule to remember:

    Mathematical validity and platform acceptance are both part of the contract.

================================================================================
2. HEADER BLOCK — PATTERN IDENTIFICATION
================================================================================

EXACT PATTERN:

    1D DP — Ending-at-current-index + Parent Reconstruction

This is structurally the same framework as:

    Print LIS / Longest Increasing Subsequence reconstruction

The reusable skeleton is:

    Sort / Establish ordering
            ↓
    dp[i] = best valid chain ending at i
            ↓
    parent[i] = predecessor used for that best chain
            ↓
    Find globally best endpoint
            ↓
    Reconstruct backwards
            ↓
    Reverse answer

The major difference from LIS is only the compatibility condition.

LIS:

    nums[j] < nums[i]

Largest Divisible Subset:

    nums[i] % nums[j] == 0


RECOGNITION TRIGGER:

When a problem asks for:

    "largest/longest subset or subsequence"

and each new element must satisfy some compatibility relation with an
earlier element, immediately ask:

    Can I sort/order the elements and define a DP state as
    "best valid chain ending at i"?

If yes, this ending-at-i DP pattern should come to mind.


SIBLING-PATTERN CONNECTION:

    Print LIS
          ↓
    same dp[i] + parent[i] reconstruction framework
          ↓
    Largest Divisible Subset
          ↓
    only the compatibility transition changes

================================================================================
3. GOAL + STATE + APPROACH
================================================================================

GOAL:

Find the maximum number of elements that can form a valid divisible subset
and reconstruct those actual elements.


WHY SORTING?

Suppose the selected numbers after sorting are:

    a1 < a2 < a3 < ... < ak

For any earlier/smaller element ai and later/larger element aj:

    ai <= aj

Therefore, if they are divisible, the relevant direction is:

    aj % ai == 0


THE KEY AHA:

Suppose we build:

    a1 -> a2 -> a3 -> a4

such that:

    a2 % a1 == 0
    a3 % a2 == 0
    a4 % a3 == 0

Because divisibility is transitive:

    a1 | a2
    a2 | a3
    a3 | a4

therefore:

    a1 | a3
    a1 | a4
    a2 | a4

So the whole subset automatically satisfies the required pairwise
divisibility property.

THIS is why we only need to connect the current element to a compatible
previous element instead of repeatedly checking every pair inside the
constructed subset.


================================================================================
4. DP STATE
================================================================================

STATE 1:

    LDS[i]

Meaning:

    LDS[i] = length of the largest divisible chain whose LAST/LARGEST
             element is nums[i].

Example:

    nums = [1, 2, 4, 8]

Then:

    LDS[0] = 1      -> [1]
    LDS[1] = 2      -> [1,2]
    LDS[2] = 3      -> [1,2,4]
    LDS[3] = 4      -> [1,2,4,8]


STATE 2:

    parentIndex[i]

Meaning:

    parentIndex[i] = index of the previous element used immediately
                     before nums[i] in the best chain ending at i.

Example:

    parentIndex[3] = 2

means:

    nums[2] -> nums[3]

belongs to the selected chain.

INITIALIZATION:

    parentIndex[i] = i

This means:

    "i currently has no predecessor and is the root/start
     of its own chain."

This self-parent representation also gives us a clean reconstruction
termination condition.


================================================================================
5. RECURRENCE / TRANSITION
================================================================================

For every current index i:

    Examine every previous index j < i.

If:

    nums[i] % nums[j] == 0

then nums[j] can legally precede nums[i].

If using j gives a longer chain:

    LDS[j] + 1 > LDS[i]

then update:

    LDS[i] = LDS[j] + 1
    parentIndex[i] = j


TRANSITION:

    if nums[i] % nums[j] == 0
       AND
       LDS[j] + 1 > LDS[i]

            LDS[i] = LDS[j] + 1
            parentIndex[i] = j


WHY STRICT '>'?

If two different predecessors produce the same length, either resulting
subset is acceptable because the problem does not impose a tie-breaking rule.

Therefore:

    >

is enough.

There is no need to replace an equally good predecessor.


================================================================================
6. WHERE DOES THE FINAL ANSWER COME FROM?
================================================================================

LDS[i] describes the best divisible subset ENDING EXACTLY AT i.

Therefore the global answer is:

    max(LDS[i])

We maintain:

    maxLen
    maxIndex

where maxIndex is the endpoint of the globally best chain.

Example:

    LDS = [1, 2, 3, 4, 2]

then:

    maxLen   = 4
    maxIndex = index of that 4


================================================================================
7. RECONSTRUCTION
================================================================================

Once maxIndex is known, follow the parent links:

    maxIndex
        ↓
    parentIndex[maxIndex]
        ↓
    parentIndex[parentIndex[maxIndex]]
        ↓
    ...
        ↓
    root

The reconstruction naturally happens backwards:

    last -> previous -> previous -> ... -> first

So we:

    1. Add every visited element to ans.
    2. Stop when parentIndex[index] == index.
    3. Add the root.
    4. Reverse the answer.

Therefore:

    backward reconstruction
            +
       Collections.reverse()
            =
       correct forward order


================================================================================
8. WHY THIS WORKS — CORRECTNESS INVARIANT / PROOF
================================================================================

CORE INVARIANT:

After processing index i:

    LDS[i] is the maximum possible length of a valid divisible chain
    ending exactly at nums[i].

And:

    parentIndex[i]

stores the predecessor used by that optimal chain.


WHY EVERY VALID PREDECESSOR IS CONSIDERED:

After sorting, every possible predecessor of nums[i] must occur at some
index j < i.

The inner loop examines:

    j = 0 ... i-1

Therefore every possible previous candidate is considered.


WHY THE TRANSITION IS VALID:

Suppose:

    nums[i] % nums[j] == 0

and the chain represented by LDS[j] is already valid.

Every earlier element in that chain divides nums[j].

Since:

    previous | nums[j]
and
    nums[j] | nums[i]

divisibility is transitive.

Therefore every earlier selected element also divides nums[i].

So appending nums[i] preserves the divisible-subset property.


WHY NO BETTER CHAIN IS MISSED:

Every possible predecessor j is examined.

For every compatible j, we calculate:

    LDS[j] + 1

and take the maximum.

Therefore the best possible chain ending at i is found.


WHY RECONSTRUCTION IS CORRECT:

Whenever LDS[i] is improved, we simultaneously store:

    parentIndex[i] = j

Therefore every parent link represents an actual predecessor in the selected
optimal chain.

Following those links from the globally best endpoint reconstructs a valid
maximum-size subset.


================================================================================
9. WHY THIS APPROACH?
================================================================================

APPROACH 1 — BRUTE FORCE

Idea:

    Generate all subsets.
    Check whether every subset satisfies pairwise divisibility.
    Return the largest.

Problem:

    Number of subsets = 2^n

This becomes completely impractical for n up to 1000.

Rejected because:

    Exponential time.


--------------------------------------------------------------------------------
APPROACH 2 — SIMPLE GREEDY

Temptation:

    Sort the array and repeatedly choose a compatible number,
    perhaps always taking the largest/next available option.

Problem:

    A locally attractive predecessor may not contain the longest valid
    chain behind it.

The optimal predecessor must be chosen based on the BEST chain ending there,
not simply on numerical value.

Therefore we need:

    LDS[j]

for every possible predecessor.

Rejected because there is no safe local greedy rule of that form.


--------------------------------------------------------------------------------
APPROACH 3 — LIS-STYLE O(n log n) BINARY SEARCH

Classic LIS can be optimized using:

    tails[]

and binary search.

However, Largest Divisible Subset does not have the same special tail
monotonicity/property that makes LIS tail replacement safe.

Divisibility is not equivalent to ordinary increasing order.

Therefore the standard LIS O(n log n) optimization does not transfer directly.

Also, this problem requires actual subset reconstruction, making the
predecessor relationships important.

For current placement/interview preparation:

    O(n^2) DP + parent reconstruction

is the appropriate interview-standard / constraint-appropriate solution.

Do NOT claim:

    "O(n^2) is the universal mathematically optimal complexity"

without qualification.

The correct statement is:

    "This is the standard, constraint-appropriate O(n^2) DP solution."


================================================================================
10. FULL CODE — FINAL VERIFIED IMPLEMENTATION
================================================================================
*/

// ============================================================================
// ACTUAL EXECUTABLE CODE STARTS HERE
// ============================================================================

// import java.util.*;

// class Largest_Divisible_Subset {

//     public List<Integer> largestDivisibleSubset(int[] nums) {

//         int n = nums.length;

//         // Sorting creates the smaller -> larger direction needed so that
//         // nums[i] can only have a smaller/equal predecessor nums[j].
//         Arrays.sort(nums);

//         // LDS[i] stores the longest divisible chain ending exactly at i.
//         Arrays.fill(new int[0], 0); // <-- REMOVE? NO. See corrected code below.
//     }
// }

/*
IMPORTANT:

The executable implementation below is the clean verified version.
The temporary line above is intentionally not part of the final solution.

================================================================================
11. FINAL VERIFIED CODE
================================================================================
*/

import java.util.*;

class Solution {

    public List<Integer> largestDivisibleSubset(int[] nums) {

        int n = nums.length;

        // Sorting gives every potential predecessor a smaller index.
        // Since all numbers are positive, divisibility can then be checked
        // in only one direction: nums[i] % nums[j] == 0.
        Arrays.sort(nums);

        // LDS[i] = length of the longest divisible chain ending at nums[i].
        int[] LDS = new int[n];
        Arrays.fill(LDS, 1);

        // parentIndex[i] = predecessor index used to obtain the best chain
        // ending at i. A self-parent means i is the start of its own chain.
        int[] parentIndex = new int[n];
        Arrays.setAll(parentIndex, i -> i);

        // maxIndex stores the endpoint of the globally longest chain.
        int maxLen = 1;
        int maxIndex = 0;

        for (int i = 1; i < n; i++) {

            // Every earlier element is a possible predecessor of nums[i].
            for (int j = 0; j < i; j++) {

                // If nums[j] divides nums[i], we can append nums[i] to the
                // valid chain ending at j.
                if (nums[i] % nums[j] == 0 &&
                    LDS[j] + 1 > LDS[i]) {

                    // Extend the best chain ending at j.
                    LDS[i] = LDS[j] + 1;

                    // Remember j so we can reconstruct the actual subset.
                    parentIndex[i] = j;
                }
            }

            // Keep the globally best endpoint.
            // Strictly greater is enough because any tied maximum subset
            // is accepted by the problem.
            if (LDS[i] > maxLen) {
                maxLen = LDS[i];
                maxIndex = i;
            }
        }

        List<Integer> ans = new ArrayList<>();

        // Follow parent links backwards from the globally best endpoint.
        while (parentIndex[maxIndex] != maxIndex) {
            ans.add(nums[maxIndex]);
            maxIndex = parentIndex[maxIndex];
        }

        // Add the root of the reconstructed chain.
        ans.add(nums[maxIndex]);

        // Reconstruction happened backwards, so restore forward order.
        Collections.reverse(ans);

        return ans;
    }
}

/*
================================================================================
12. BUGS ACTUALLY HIT DURING THE SESSION
================================================================================

For the submitted original implementation:

    No algorithmic bug was found.

The logic is correct.

Important points that may LOOK suspicious but are actually correct:


A) parentIndex[i] = i

This is intentional.

It means:

    "i has no predecessor yet."

It also gives us the termination condition:

    parentIndex[index] == index


B) maxIndex updates only when LDS[i] > maxLen

Correct.

Suppose two chains both have length 4.

Because any largest valid subset is acceptable, replacing the first one with
another length-4 subset is unnecessary.


C) Arrays.sort(nums)

This modifies the input array.

For the current platform implementation this is acceptable because the task
asks for the subset, not preservation of the original array.

However, in an interview, if the interviewer says:

    "Do not modify the input"

then use:

    int[] sorted = nums.clone();
    Arrays.sort(sorted);

This would introduce an additional O(n) array.


================================================================================
13. VERIFICATION PACK
================================================================================

CASE 1 — Complete Divisibility Chain

Input:

    [1, 2, 4, 8]

Sorted:

    [1, 2, 4, 8]

DP:

    LDS[0] = 1

    2 % 1 == 0
    LDS[1] = 2
    parent[1] = 0

    4 % 1 == 0
    4 % 2 == 0
    LDS[2] = 3
    parent[2] = 1

    8 % 1 == 0
    8 % 2 == 0
    8 % 4 == 0
    LDS[3] = 4
    parent[3] = 2

Parent chain:

    8 -> 4 -> 2 -> 1

Reverse:

    [1, 2, 4, 8]

Correct.


--------------------------------------------------------------------------------
CASE 2 — Multiple Valid Answers

Input:

    [1, 2, 3]

Possible largest subsets include:

    [1, 2]

or:

    [1, 3]

Both have length 2.

Any one is valid.

This is why no special tie-breaking logic is required.


--------------------------------------------------------------------------------
CASE 3 — No Pair Is Divisible

Input:

    [2, 3, 5, 7]

Each element starts with:

    LDS[i] = 1

No valid extension exists.

Therefore any single element is a valid maximum subset.

Example:

    [2]


--------------------------------------------------------------------------------
CASE 4 — Single Element

Input:

    [5]

Initially:

    LDS[0] = 1
    parentIndex[0] = 0

Reconstruction immediately terminates.

Answer:

    [5]


--------------------------------------------------------------------------------
CASE 5 — Useful Pattern-Distinguishing Example

Input:

    [1, 2, 4, 6, 8, 12, 24]

Important chains include:

    1 -> 2 -> 4 -> 8 -> 24

and:

    1 -> 6 -> 12 -> 24

The first has length 5.

This demonstrates why blindly selecting a locally attractive/larger compatible
predecessor is not enough.

The DP must compare the BEST chain length behind each compatible predecessor.


================================================================================
14. COMPLEXITY DERIVATION
================================================================================

SORTING:

    O(n log n)


DP:

    Outer loop:
        n elements

    Inner loop:
        up to n previous elements

Therefore:

    O(n^2)


RECONSTRUCTION:

    Every selected element is visited at most once:

    O(n)


TOTAL:

    O(n log n) + O(n^2) + O(n)

Therefore:

    O(n^2)


SPACE:

    LDS[]          -> O(n)
    parentIndex[]  -> O(n)
    answer         -> O(n)

Auxiliary algorithmic space:

    O(n)

Returned output is naturally O(n).


FINAL COMPLEXITY:

    Time  : O(n^2)
    Space : O(n)

================================================================================
15. SPACE OPTIMIZATION — INTERVIEW FOLLOW-UP
================================================================================

QUESTION:

Can parentIndex[] be removed?

ANSWER:

Not without losing efficient reconstruction.

LDS[i] tells us:

    HOW LONG is the best chain ending at i?

but it does not tell us:

    WHICH predecessor produced that chain?


Without parentIndex[], we would have to rediscover predecessor relationships
during reconstruction.

Therefore parentIndex[] is the natural structure for direct reconstruction.


QUESTION:

Can LDS[] be removed?

ANSWER:

Not cleanly in this standard DP formulation.

When processing i, we need the previously computed best chain lengths:

    LDS[j]

to determine whether:

    LDS[j] + 1

improves the chain ending at i.

Therefore the standard solution naturally maintains both:

    LDS[]
    parentIndex[]


INTERVIEW CALIBRATION:

The O(n) space is a reasonable and standard trade-off for an O(n^2)
reconstruction-based DP solution.


================================================================================
16. CONSTRAINT VARIATIONS
================================================================================

DUPLICATES:

The original problem guarantees distinct values.

If duplicates are introduced, equal values satisfy:

    x % x == 0

but whether repeated equal values are allowed depends on the exact definition
of the modified problem.

Therefore do not blindly transfer assumptions from this problem.


ZERO:

The current problem uses positive integers.

Therefore there is no zero divisor.

If zero becomes possible, the modulo operation and divisibility semantics must
be reconsidered.


NEGATIVE VALUES:

The clean sorting argument is based on positive values.

If negatives are allowed, revisit the ordering and exact divisibility semantics
instead of automatically reusing the current proof.


INPUT MUTATION:

Arrays.sort(nums) modifies the input array.

If input preservation is required:

    int[] sorted = nums.clone();
    Arrays.sort(sorted);

================================================================================
17. COMMON MISTAKES / TRAPS
================================================================================

TRAP 1 — Treating LDS as a completely new DP pattern

It is largely the same structure as Print LIS.

Remember:

    Print LIS
        ↓
    dp[i] + parent[i]
        ↓
    Largest Divisible Subset
        ↓
    only compatibility changes


TRAP 2 — Forgetting WHY we sort

Sorting is not merely a convenience.

It creates:

    smaller -> larger

which allows us to use:

    nums[i] % nums[j] == 0

for j < i.


TRAP 3 — Checking every pair inside the subset

Unnecessary.

Once we build:

    a | b
    b | c
    c | d

transitivity gives the required relationships between every earlier and later
selected pair.

Therefore connecting the chain locally is enough.


TRAP 4 — Forgetting reconstruction

If the problem asks for the actual subset:

    LDS[]

alone is insufficient.

Need:

    parentIndex[]


TRAP 5 — Defining LDS[i] vaguely

Weak definition:

    "LDS[i] = best subset up to i."

Correct definition:

    "LDS[i] = length of the largest divisible chain ending exactly
              at nums[i]."

The word ENDING is important.


TRAP 6 — Using a greedy predecessor

The largest compatible predecessor is not necessarily the predecessor with the
longest optimal chain behind it.

Use:

    LDS[j]

to make that decision.


TRAP 7 — Thinking LIS binary-search optimization automatically transfers

LIS's O(n log n) tails optimization depends on a special ordered-tail property.

Divisibility does not provide the same structure.

Therefore do not force a binary-search optimization here.


TRAP 8 — Confusing the pairwise condition with the DP transition

Problem says:

    every pair must be divisible.

DP checks only:

    current % previous == 0

This is valid only because:

    sorting
        +
    divisibility transitivity

turns the pairwise requirement into a chain condition.


================================================================================
18. FOLLOW-UP QUESTIONS — INTERVIEW READY
================================================================================

Q1. Why do we sort?

ANSWER:

    Sorting gives us a smaller -> larger direction.

    Since all values are positive, for j < i we have nums[j] <= nums[i].
    Therefore the relevant divisibility direction is:

        nums[i] % nums[j] == 0

    and every possible predecessor has already been processed.


--------------------------------------------------------------------------------
Q2. Why is checking only the previous selected element enough?

ANSWER:

    Divisibility is transitive.

    If:

        a | b
        b | c

    then:

        a | c

    Therefore if each newly appended element is divisible by the previous
    selected element, the entire selected set satisfies the pairwise
    divisibility requirement.


--------------------------------------------------------------------------------
Q3. Why isn't greedy enough?

ANSWER:

    A compatible predecessor may have a short chain behind it while another
    compatible predecessor may have a much longer chain.

    Therefore we must compare:

        LDS[j] + 1

    over all valid predecessors.


--------------------------------------------------------------------------------
Q4. Why do we need parentIndex[]?

ANSWER:

    LDS[i] stores only the optimal LENGTH.

    The problem asks for the actual subset, so we must remember which
    predecessor produced that optimal length.

    parentIndex[] provides exactly that information.


--------------------------------------------------------------------------------
Q5. Why is parentIndex[i] initialized to i?

ANSWER:

    It represents that i currently starts its own chain.

    It also gives us a clean stopping condition during reconstruction:

        parentIndex[i] == i


--------------------------------------------------------------------------------
Q6. Why do we reverse the answer?

ANSWER:

    Parent links point backwards:

        last -> previous -> ... -> first

    Therefore the collected result is in reverse order.

    Reversing restores ascending/original sorted-chain order.


--------------------------------------------------------------------------------
Q7. Why do we use '>' rather than '>='?

ANSWER:

    Because equal-length solutions are both valid.

    Using '>' changes the parent only when a strictly longer solution is found,
    which is simpler and sufficient under the problem's acceptance contract.


--------------------------------------------------------------------------------
Q8. Can we optimize this to O(n log n) like LIS?

ANSWER:

    Not directly using the standard LIS tails/binary-search technique.

    LIS has a special tail ordering property that makes tail replacement safe.
    Divisibility does not provide the same one-dimensional structure.

    Therefore O(n^2) DP is the standard approach here.


--------------------------------------------------------------------------------
Q9. Can we reduce the space below O(n)?

ANSWER:

    The standard direct-reconstruction solution naturally requires O(n)
    DP and parent information.

    Removing parentIndex[] means we lose the direct predecessor chain and
    would need additional rediscovery work.


--------------------------------------------------------------------------------
Q10. What if the interviewer says the input cannot be modified?

ANSWER:

    Clone before sorting:

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

    This preserves the input at the cost of O(n) additional space.


--------------------------------------------------------------------------------
Q11. Is O(n^2) mathematically optimal?

ANSWER:

    Do not make an unsupported universal lower-bound claim.

    The appropriate characterization is:

        "O(n^2) is the standard, constraint-appropriate and
         interview-suitable solution for the given constraints."


================================================================================
19. LONG-TERM MEMORY HOOK
================================================================================

ONE-SENTENCE RECONSTRUCTION CUE:

    Sort -> think Print LIS -> LDS[i] = best divisible chain ending at i
    -> nums[i] % nums[j] == 0 -> parent[i] = j -> global maximum
    -> reconstruct backwards -> reverse.


MINIMAL RECALL TRIGGERS:

    Pattern
        ↓
    Ending-at-i DP
        ↓
    Divisibility transition
        ↓
    Parent reconstruction
        ↓
    Global maximum
        ↓
    Reverse
        ↓
    O(n^2) time / O(n) space


TINY AHA TRACE:

    1 | 2
    2 | 4
    4 | 8

Therefore:

    1 | 4
    1 | 8
    2 | 8

This is exactly why local chain construction is sufficient for the global
pairwise divisibility requirement.


================================================================================
20. DOCUMENTATION DEPTH CALIBRATION
================================================================================

Things NOT worth spending a year remembering:

    Arrays.sort()
    ArrayList
    Collections.reverse()
    Basic loop syntax

These are mechanics.

Things absolutely worth retaining:

    1. LDS is defined as ENDING AT i.
    2. Sorting creates direction.
    3. Divisibility is transitive.
    4. parent[] reconstructs the actual solution.
    5. This is structurally the same as Print LIS.
    6. Only the compatibility transition changes.
    7. Greedy cannot safely choose a locally best predecessor.
    8. Standard LIS O(n log n) tails optimization does not directly transfer.


================================================================================
21. SHIPPING GATE
================================================================================

[✓] Problem contract understood.

[✓] Platform acceptance requirements understood.

[✓] Exact pattern identified.

[✓] Sibling pattern with Print LIS identified.

[✓] Recognition trigger identified.

[✓] Goal precisely defined.

[✓] LDS state precisely defined.

[✓] parentIndex state precisely defined.

[✓] Transition defined.

[✓] Base initialization defined.

[✓] Final-answer mapping defined.

[✓] Reconstruction logic defined.

[✓] Correctness invariant established.

[✓] Sorting justification established.

[✓] Divisibility-transitivity justification established.

[✓] Brute force rejected for exponential complexity.

[✓] Greedy rejected due to lack of safe local choice.

[✓] LIS binary-search optimization distinguished.

[✓] Submitted implementation reviewed.

[✓] No actual algorithmic bug found.

[✓] Normal case verified.

[✓] Edge case verified.

[✓] Tie case verified.

[✓] Incompatible-elements case verified.

[✓] Complexity derived.

[✓] Space optimization discussed.

[✓] Constraint variations discussed.

[✓] Interview follow-ups resolved.

[✓] Common traps documented.

[✓] Long-term memory hook established.


================================================================================
CANONICAL FINAL TAKEAWAY
================================================================================

Largest Divisible Subset is NOT a completely new DP pattern.

It is the same:

    dp[i] + parent[i] + reconstruction

framework used in Print LIS.

The key transformation is:

    LIS:
        previous element satisfies an ordering relation.

    LDS:
        previous element must divide the current element.

Sorting creates the required direction, and divisibility transitivity proves
that a chain of local divisibility relations satisfies the original global
pairwise condition.

FINAL MENTAL MODEL:

    SORT
      ↓
    BEST CHAIN ENDING AT i
      ↓
    COMPATIBILITY:
        nums[i] % nums[j] == 0
      ↓
    STORE PARENT
      ↓
    FIND GLOBAL MAX
      ↓
    RECONSTRUCT BACKWARDS
      ↓
    REVERSE
      ↓
    O(n²) TIME, O(n) SPACE


================================================================================
                           END OF ELITE VERSION
================================================================================
*/