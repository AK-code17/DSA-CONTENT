package GRAPHS;

import java.util.*;

public class G50_Accounts_Merge {
    /*
 * ============================================================
 * PROBLEM: Accounts Merge (LC 721)
 * ============================================================
 * INTUITION:
 * Each account has a name and a list of emails. Two accounts
 * belong to the same person if they share AT LEAST one email.
 * We need to merge all such accounts and return sorted emails.
 *
 * KEY INSIGHT:
 * Model this as a GRAPH problem:
 * - Each account index = a node
 * - Shared email between two accounts = an edge between them
 * - All accounts in the same connected component = same person
 *
 * APPROACH (DFS on Account Indexes):
 * 1. Build adjList: email → [list of account indexes that have it]
 *    (This implicitly encodes edges between accounts)
 * 2. DFS from each unvisited account index
 *    → collects ALL emails from the connected component
 *    → TreeSet keeps them sorted automatically
 * 3. Reconstruct: name + sorted emails → final merged account
 *
 * WHY TREESETS?
 * Problem requires sorted emails — TreeSet maintains
 * sorted order automatically on insertion. No need to
 * sort at the end.
 *
 * COMPLEXITY:
 * Time  → O(N * K * logK)  — N accounts, K emails each, logK for TreeSet
 * Space → O(N * K)         — adjList + visited + result
 * ============================================================
 */

class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        /*
         * STEP 1: BUILD ADJACENCY LIST
         * ----------------------------------------------------------
         * Map each email → list of account indexes where it appears.
         * This acts as our graph edges:
         * if email X appears in accounts 2 and 5 → accounts 2 and 5
         * are connected (same person).
         *
         * WHY START FROM i=1?
         * index 0 of each account is the NAME, not an email.
         * Including name would incorrectly merge all accounts
         * with the same name regardless of shared emails.
         *
         * Example:
         * accounts[0] = ["John", "a@a.com", "b@b.com"]
         * accounts[1] = ["John", "b@b.com", "c@c.com"]
         * adjList = { "a@a.com":[0], "b@b.com":[0,1], "c@c.com":[1] }
         *            "b@b.com" links accounts 0 and 1 → same person
         */
        // 1. Create Adj list for each email, append the indexes wherever we see that email...
        Map<String, List<Integer>> adjList = new HashMap<>();

        int len = accounts.size();
        for (int ind = 0; ind < len; ind++) {
            List<String> list = accounts.get(ind);
            int list_len = list.size();
            for (int i = 1; i < list_len; i++) { // i=1 skips the name at index 0
                adjList.computeIfAbsent(list.get(i), k -> new ArrayList<>()).add(ind);
            }
        }

        /*
         * STEP 2: DFS OVER EACH ACCOUNT
         * ----------------------------------------------------------
         * For each unvisited account, launch a DFS.
         * DFS explores the entire connected component — collecting
         * ALL emails from ALL accounts linked via shared emails.
         *
         * vis[] prevents revisiting accounts already merged into
         * a previous component → if set is empty after dfs(),
         * this account was already absorbed into another. Skip it.
         *
         * TreeSet ensures emails are sorted as we collect them —
         * no explicit sort step needed at the end.
         */
        // 2. Traverse each account and call dfs, it will automatically populate the set...
        boolean[] vis = new boolean[len];
        List<List<String>> ans = new ArrayList<>();

        for (int ind = 0; ind < len; ind++) {
            Set<String> set = new TreeSet<>(); // auto-sorted email collector
            dfs(ind, accounts, set, adjList, vis);

            // if set is empty that means this acc was already visited..,i.e,
            // atleast one email was common in prev account so it got added into that...
            if (!set.isEmpty()) {
                List<String> list = new ArrayList<>();
                list.add(accounts.get(ind).get(0)); // add name first (index 0)
                list.addAll(set);                   // then add sorted emails
                ans.add(list);
            }
        }

        return ans;
    }

    /*
     * ============================================================
     * DFS — The Core Engine
     * ============================================================
     *
     * WHAT THIS FUNCTION DOES IN ONE LINE:
     * Starting from account[ind], it collects EVERY email that
     * belongs to the same person by chaining through shared emails.
     *
     * ----------------------------------------------------------
     * THE MENTAL MODEL — THINK OF IT LIKE THIS:
     * ----------------------------------------------------------
     *
     * Imagine each account is a ROOM.
     * Each email in the room is a DOOR.
     * Each door can connect to OTHER rooms (accounts) that also
     * have that same email.
     *
     * DFS says:
     * "Enter this room → collect all emails in it →
     *  for each email, open its door → enter connected rooms →
     *  collect their emails too → keep going until no new rooms"
     *
     * ----------------------------------------------------------
     * STEP BY STEP TRACE (read this carefully when revising):
     * ----------------------------------------------------------
     *
     * accounts[0] = ["John", "a@a.com", "b@b.com"]
     * accounts[1] = ["Mary", "b@b.com", "c@c.com"]
     * accounts[2] = ["John", "d@d.com"]
     *
     * adjList = {
     *   "a@a.com" → [0]
     *   "b@b.com" → [0, 1]   ← shared! links account 0 and 1
     *   "c@c.com" → [1]
     *   "d@d.com" → [2]
     * }
     *
     * CALL: dfs(0, ...)
     * ┌─ Enter account[0], mark vis[0]=true
     * │
     * ├─ i=1 → email = "a@a.com"
     * │         add "a@a.com" to set
     * │         adjList["a@a.com"] = [0]
     * │         → dfs(0) → vis[0]=true → RETURN immediately (already visited)
     * │
     * ├─ i=2 → email = "b@b.com"
     * │         add "b@b.com" to set
     * │         adjList["b@b.com"] = [0, 1]
     * │         → dfs(0) → RETURN immediately (already visited)
     * │         → dfs(1) → vis[1]=false → ENTER account[1]
     * │              ┌─ mark vis[1]=true
     * │              ├─ i=1 → email = "b@b.com"
     * │              │         add "b@b.com" to set (already there, TreeSet ignores)
     * │              │         adjList["b@b.com"] = [0,1]
     * │              │         → dfs(0) → RETURN, → dfs(1) → RETURN
     * │              ├─ i=2 → email = "c@c.com"
     * │              │         add "c@c.com" to set
     * │              │         adjList["c@c.com"] = [1]
     * │              │         → dfs(1) → RETURN
     * │              └─ account[1] fully explored
     * │
     * └─ account[0] fully explored
     *
     * Final set = {"a@a.com", "b@b.com", "c@c.com"}  (sorted by TreeSet)
     * Result    = ["John", "a@a.com", "b@b.com", "c@c.com"]
     *
     * account[2] → dfs(2) → separate component → ["John", "d@d.com"]
     *
     * ----------------------------------------------------------
     * WHY vis[] IS CRITICAL:
     * ----------------------------------------------------------
     * Without vis[], dfs(0) calls dfs(1) which calls dfs(0) again
     * → infinite loop → StackOverflow.
     * vis[] breaks the cycle by marking accounts as done.
     *
     * ----------------------------------------------------------
     * WHY TreeSet AND NOT ArrayList?
     * ----------------------------------------------------------
     * Problem demands sorted emails in output.
     * TreeSet insertion = O(logK), auto-sorted.
     * ArrayList would need an extra O(KlogK) sort call at the end.
     * TreeSet handles it inline as we collect.
     *
     * ----------------------------------------------------------
     * KNOWN LIMITATION:
     * ----------------------------------------------------------
     * Recursive DFS risks StackOverflow for very large inputs
     * with deeply chained accounts (account 0→1→2→...→n).
     * BFS (iterative queue) or DSU avoids this entirely.
     * ============================================================
     */
    private void dfs(int ind, List<List<String>> acc, Set<String> set,
                     Map<String, List<Integer>> adjList, boolean[] vis) {

        // if already visited return..
        if (vis[ind]) return;

        // else mark this as true..
        vis[ind] = true;

        // Check every word in this account , and then from each account move to further indexes
        // where the email is present and there also explore all emails of that ind,
        // keep doing that..
        List<String> list = acc.get(ind);
        int size = list.size();

        for (int i = 1; i < size; i++) {

            String email = list.get(i);

            // collect this email into our running set for this component
            set.add(email);

            /*
             * THIS IS THE CORE CHAINING STEP:
             * For this email, find every OTHER account that also has it.
             * Each of those accounts is in the same component (same person).
             * Recursively DFS into them to collect their emails too.
             * They in turn will chain into further accounts via their emails.
             * This cascades until the entire connected component is explored.
             */
            for (int common_acc : adjList.get(email)) {
                dfs(common_acc, acc, set, adjList, vis);
            }
        }
    }
}
}
