package GRAPHS;

import java.util.*;

/*
 * ============================================================
 * PROBLEM: Accounts Merge (LC 721) — DSU Approach
 * ============================================================
 * INTUITION:
 * Each account has a name and emails. Two accounts belong to
 * the same person if they share AT LEAST one common email.
 * We need to merge all such accounts and return sorted emails.
 *
 * ============================================================
 * META INTUITION — WHEN TO IDENTIFY DSU IN ANY PROBLEM:
 * ============================================================
 * DSU is the right tool whenever the problem is about
 * GROUPING or MERGING elements based on some relationship.
 *
 * THE THREE SIGNALS TO LOOK FOR:
 *
 * SIGNAL 1 — "Connected Components" language:
 * Keywords like "merge", "group", "same person", "province",
 * "connected", "belongs to" → scream DSU.
 * Here: "merge accounts that share an email" = merge groups.
 *
 * SIGNAL 2 — Relationship is TRANSITIVE:
 * If A relates to B, and B relates to C → A relates to C.
 * Here: account 0 shares email with account 1,
 *       account 1 shares email with account 2
 *       → all three belong to the same person.
 * Transitivity = DSU's core strength.
 *
 * SIGNAL 3 — You need to ask "are these two in the same group?":
 * find_root(x) == find_root(y) answers this in O(α(n)).
 * Here: do these two accounts belong to the same person?
 *
 * ============================================================
 * THE HARDEST PART — IDENTIFYING NODES AND PARENTS:
 * ============================================================
 * This is where most people get stuck in non-trivial DSU problems.
 * The data isn't integers 0 to n — it's STRINGS (emails).
 * So the question is: WHAT SHOULD THE DSU NODES BE?
 *
 * WRONG INSTINCT:
 * "emails are the entities being merged → make emails the nodes"
 * → DSU on strings → needs HashMap<String,String> for parent[]
 * → complex, messy, harder to reconstruct the answer
 *
 * RIGHT INSTINCT:
 * "what is the SIMPLEST unit that represents a group?"
 * → ACCOUNT INDEXES are already integers 0 to n-1
 * → DSU loves integers → use account indexes as nodes
 * → emails are just the BRIDGE that tells us WHICH indexes to union
 *
 * THE MAPPING TRICK (key insight for this problem type):
 * Whenever the actual entities are non-integers (strings, names),
 * ask yourself:
 * "Can I map these to integers that represent their GROUP?"
 *
 * Here the mapping is:
 * email → first account index that owned it (emailToAccount map)
 * This single map is the BRIDGE between string world and DSU world:
 * → Lets us detect when two accounts share an email
 * → Lets us call union(accInd1, accInd2) on integers
 * → Lets us call find_root(accInd) to get the group representative
 *
 * GENERAL RULE FOR FINDING NODES IN NON-INTEGER DSU PROBLEMS:
 * ┌─────────────────────────────────────────────────────────┐
 * │ 1. What are the GROUPS you are merging?                 │
 * │    → Those are your DSU nodes                           │
 * │    → Here: ACCOUNTS (not emails)                        │
 * │                                                         │
 * │ 2. What is the CONDITION that merges two groups?        │
 * │    → That condition drives your union() calls           │
 * │    → Here: shared email between two accounts            │
 * │                                                         │
 * │ 3. What MAPPING bridges non-integers to DSU integers?   │
 * │    → Build a HashMap for that                           │
 * │    → Here: emailToAccount (email → account index)       │
 * └─────────────────────────────────────────────────────────┘
 *
 * RECONSTRUCTION INSIGHT:
 * After all unions, find_root(accInd) tells you which account
 * is the "leader" of that merged group. This is how you know
 * whose NAME to use and which group to add each email to.
 * The root account index IS the identity of the merged person.
 *
 * ============================================================
 * KEY INSIGHT — WHY DSU OVER DFS HERE?
 * ============================================================
 * DFS approach:
 * → Build adjList (email → account indexes) explicitly
 * → Traverse the graph component by component
 * → Recursive → stackoverflow risk on large inputs
 *
 * DSU approach:
 * → No graph construction needed
 * → Merge directly while scanning emails
 * → Iterative → no stackoverflow risk
 * → Cleaner, less code, more direct
 *
 * THREE STEP APPROACH:
 * 1. Scan all emails → union accounts that share an email
 * 2. Group emails by their ultimate root account (find_root)
 * 3. Reconstruct final answer: name + sorted emails per group
 *
 * ============================================================
 * CONCRETE EXAMPLE — ALL 3 STEPS TRACED:
 * ============================================================
 * accounts[0] = ["John", "a@a.com", "b@b.com"]
 * accounts[1] = ["John", "b@b.com", "c@c.com"]
 * accounts[2] = ["Mary", "d@d.com"]
 *
 * STEP 1 — Scan emails:
 * "a@a.com" → not seen → emailToAccount["a@a.com"] = 0
 * "b@b.com" → not seen → emailToAccount["b@b.com"] = 0
 * "b@b.com" → SEEN at 0 → union(1, 0) → accounts 0 and 1 merged
 * "c@c.com" → not seen → emailToAccount["c@c.com"] = 1
 * "d@d.com" → not seen → emailToAccount["d@d.com"] = 2
 *
 * STEP 2 — Group by root:
 * "a@a.com" → emailToAccount=0 → find_root(0)=0 → group[0]
 * "b@b.com" → emailToAccount=0 → find_root(0)=0 → group[0]
 * "c@c.com" → emailToAccount=1 → find_root(1)=0 → group[0] ← merged!
 * "d@d.com" → emailToAccount=2 → find_root(2)=2 → group[2]
 *
 * STEP 3 — Reconstruct:
 * group[0] → ["John", "a@a.com", "b@b.com", "c@c.com"]
 * group[2] → ["Mary", "d@d.com"]
 *
 * COMPLEXITY:
 * Time  → O(N*K*α(N)) ≈ O(N*K) — N accounts, K emails each
 * Space → O(N*K)               — emailToAccount map + mergedGroups
 * ============================================================
 */


/*
 * ============================================================
 * DSU (Disjoint Set Union) — Union Find
 * ============================================================
 * Nodes here = ACCOUNT INDEXES (not emails directly).
 * Two account indexes get unioned when they share an email.
 * find_root(acc_ind) gives the ultimate representative account
 * for that entire merged group.
 *
 * OPTIMIZATIONS:
 * 1. Union by Size     → attach smaller tree under larger
 * 2. Path Halving      → flatten tree during find_root
 * Both together give nearly O(1) per operation.
 * ============================================================
 */
class DisjointSetUnion {
    int[] parent; // parent[i] = parent of account index i
    int[] size;   // size[i]   = size of component rooted at i

    /*
     * Initialize n account indexes as isolated components.
     * Each account starts as its own group (its own province).
     */
    DisjointSetUnion(int n) {
        parent = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i; // each account is its own root
            size[i] = 1;   // each group has just 1 account
        }
    }

    /*
     * FIND ROOT — Iterative with Path Halving
     * Walks up parent pointers to find the root (representative)
     * of the component. Path halving flattens the tree as we go,
     * making future calls faster.
     */
    public int find_root(int node) {
        while (node != parent[node]) {
            parent[node] = parent[parent[node]]; // skip one level up (path halving)
            node = parent[node];
        }
        return node;
    }

    /*
     * UNION BY SIZE — Merge two account groups
     * Attaches the smaller group's root under the larger group's root.
     * Keeps tree shallow → find_root stays fast.
     * Returns void here since caller doesn't need merge status.
     */
    public void UnionbySize(int u, int v) {
        int root_u = find_root(u);
        int root_v = find_root(v);

        if (root_u == root_v) return; // already in same group, nothing to do

        if (size[root_u] > size[root_v]) {
            parent[root_v] = root_u;
            size[root_u] += size[root_v];
        } else {
            parent[root_u] = root_v;
            size[root_v] += size[root_u];
        }
    }
}


class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        List<List<String>> ans = new ArrayList<>();
        if (accounts == null || accounts.isEmpty()) return ans;

        int totalAccounts = accounts.size();

        /*
         * emailToAccount: email → index of the FIRST account that saw this email
         * ----------------------------------------------------------
         * This is NOT a unique email ID system.
         * It simply records: "which account do I union with
         * if I see this email again in a different account?"
         *
         * First occurrence  → store it, no union needed yet
         * Second occurrence → union current account with stored account
         *
         * This map is the BRIDGE between string world and DSU world.
         * Without it we cannot call union() on integers because
         * we wouldn't know WHICH two account indexes to merge.
         *
         * Example:
         * "b@b.com" first seen in account 0 → emailToAccount["b@b.com"] = 0
         * "b@b.com" seen again in account 1 → union(1, 0) → same person!
         */
        Map<String, Integer> emailToAccount = new HashMap<>();
        DisjointSetUnion dsu = new DisjointSetUnion(totalAccounts);

        /*
         * STEP 1: SCAN ALL EMAILS — UNION ACCOUNTS SHARING AN EMAIL
         * ----------------------------------------------------------
         * For each account, scan its emails (starting i=1 to skip name).
         * If email is new → record its account index.
         * If email was seen before → union the two account indexes.
         * After this loop, all accounts belonging to the same person
         * are in the same DSU component.
         */
        // Traverse each email in the accounts, and add if its not already added,
        // else if its added just call UnionbySize to current account index
        // with the index where the email is currently present...
        for (int accInd = 0; accInd < totalAccounts; accInd++) {
            int accSize = accounts.get(accInd).size();

            for (int emInd = 1; emInd < accSize; emInd++) { // emInd=1 skips name at index 0
                String email = accounts.get(accInd).get(emInd);

                if (!emailToAccount.containsKey(email)) {
                    // first time seeing this email — record which account owns it
                    emailToAccount.put(email, accInd);
                } else {
                    // email seen before in another account — same person, union them
                    dsu.UnionbySize(accInd, emailToAccount.get(email));
                }
            }
        }

        /*
         * STEP 2: GROUP EMAILS BY THEIR ULTIMATE ROOT ACCOUNT
         * ----------------------------------------------------------
         * Now every email in emailToAccount points to SOME account index
         * that first saw it — but that account may have been merged into
         * a larger group via DSU unions.
         *
         * find_root() gives us the ULTIMATE representative of that group.
         * We group all emails under their ultimate root account index.
         *
         * WHY find_root AND NOT just emailToAccount value directly?
         * emailToAccount stores the FIRST account that saw the email.
         * But after unions, that account may no longer be the root —
         * it got merged under another account. find_root() chases
         * the parent pointers to find the TRUE group leader.
         *
         * Example:
         * "c@c.com" → emailToAccount=1 → but account 1 was merged into 0
         *           → find_root(1) = 0 → correctly goes into group[0]
         *
         * TreeSet per group → emails auto-sorted, duplicates ignored.
         */
        // Now we have added the email to map, but now we want them to correctly
        // get added to their ultimate parent because we want to merge the common
        // email accounts to single account, so add the email to their ultimate parent..
        Map<Integer, Set<String>> mergedGroups = new HashMap<>();

        for (String email : emailToAccount.keySet()) {
            int firstSeenAccount = emailToAccount.get(email);
            int rootAccount = dsu.find_root(firstSeenAccount); // ultimate group representative
            mergedGroups.computeIfAbsent(rootAccount, k -> new TreeSet<>()).add(email);
        }

        /*
         * STEP 3: RECONSTRUCT FINAL ANSWER
         * ----------------------------------------------------------
         * Each key in mergedGroups = a root account index.
         * The name of the merged account = name of that root account
         * (accounts.get(rootAccInd).get(0)).
         * The emails = the sorted TreeSet for that group.
         *
         * WHY IS THE NAME ALWAYS CORRECT HERE?
         * The root account index is always a valid account index
         * from the original input — so its name at index 0 is always
         * the right name to use for that merged group.
         *
         * Final structure per merged account:
         * [name, email1, email2, ...] (emails in sorted order)
         */
        // Now we have added the email to their ultimate accounts now simply
        // traverse the mergedGroups and add to the final List<List<String>> ans..
        for (int rootAccInd : mergedGroups.keySet()) {
            List<String> mergedAccount = new ArrayList<>();
            mergedAccount.add(accounts.get(rootAccInd).get(0)); // name of the root account
            mergedAccount.addAll(mergedGroups.get(rootAccInd)); // sorted emails via TreeSet
            ans.add(mergedAccount);
        }

        return ans;
    }
}

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
