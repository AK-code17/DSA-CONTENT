package GRAPHS;
import java.util.*;

public class G26_Alien_Dictionary {

/**
 * PROBLEM: Alien Dictionary
 * GOAL: Given a sorted dictionary of an alien language, find the order of characters.
 * * INTUITION: 
 * 1. The dictionary is sorted lexicographically. This means for any two adjacent words, 
 * the first differing character tells us the relative order.
 * Example: "wrt", "wrf" -> 't' comes before 'f' (t -> f).
 * 2. This forms a Directed Graph where an edge u -> v means 'u' comes before 'v'.
 * 3. A Topological Sort of this graph gives the alphabet order.
 * 4. If a cycle exists, the dictionary is invalid.
 */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int len = sc.nextInt();
        int K = sc.nextInt();

        String[] dict = new String[len];
        for(int i=0 ; i<len ; i++){
            dict[i] = sc.next();
        }

        System.out.println(getAlienLanguage(dict, K));    
    }

    public static String getAlienLanguage(String []dictionary, int K) {
        int N = dictionary.length;
        
        // Step 1: Initialize Adjacency List for K characters
        List<Integer>[] adj = new ArrayList[K];
        for (int i = 0; i < K; i++) {
            adj[i] = new ArrayList<>();
        }

        // Step 2: Build the Graph
        // Compare consecutive words to find the first differing character
        for (int word = 0; word < N - 1; word++) {
            String s1 = dictionary[word];
            String s2 = dictionary[word + 1];

            // Check for the prefix "Gotcha"
            if (s1.length() > s2.length() && s1.startsWith(s2)) {
                return ""; // Entire dictionary is invalid
            }
            
            int ind = get_ind_differ(s1, s2);
            
            // If they differ at some index, the character in the first word 
            // must rank higher than the character in the second word.
            if (ind != -1) {
                int u = s1.charAt(ind) - 'a';
                int v = s2.charAt(ind) - 'a';
                adj[u].add(v);
            }
        }

        return letter_order(adj);
    }

    // Helper to find the first index where two strings differ
    private static int get_ind_differ(String a, String b) {
        int len_a = a.length();
        int len_b = b.length();
        int min_len = Math.min(len_a, len_b);

        for (int i = 0; i < min_len; i++) {
            if (a.charAt(i) != b.charAt(i)) return i;
        }

        // If no difference found in the prefix, we can't determine an edge.
        return -1;
    }

    // Step 3: Perform Topological Sort using DFS
    private static String letter_order(List<Integer>[] adj) {
        int len = adj.length;
        int[] vis = new int[len]; // 0: unvisited, 1: visiting (stack), 2: visited (safe)
        StringBuilder order = new StringBuilder();

        for (int i = 0; i < len; i++) {
            // If DFS returns false, a cycle was detected
            if (vis[i] == 0) {
                if (!dfs(i, vis, adj, order)) return "";
            }
        }

        // The characters are added to the SB in post-order, so we must reverse
        return order.reverse().toString();
    }

    private static boolean dfs(int node, int[] vis, List<Integer>[] adj, StringBuilder order) {
        // Cycle detected: we hit a node currently in our recursion stack
        if (vis[node] == 1) return false;
        
        // Already processed this node and its descendants
        if (vis[node] == 2) return true;

        vis[node] = 1; // Mark as visiting

        for (int adjNode : adj[node]) {
            if (!dfs(adjNode, vis, adj, order)) return false;
        }

        vis[node] = 2; // Mark as safe
        order.append((char) ('a' + node)); // Add to ordering
        return true;
    }
}