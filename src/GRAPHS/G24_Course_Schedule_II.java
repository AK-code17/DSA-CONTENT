package GRAPHS;

import java.util.*;

public class G24_Course_Schedule_II {

/**
 * PROBLEM: COURSE SCHEDULE II (With String-based Names)
 * THE ARCHITECT'S GUIDE: Optimizing for Performance vs. Flexibility
 * * Scenario: Courses are named "Science", "Math", "Art" instead of 0...N-1.
 * This file contains two world-class approaches.
 */

/* -------------------------------------------------------------------------- */
/* APPROACH 1: THE "DOUBLE-MAPPING" ARRAY STRATEGY (Most Optimal)             */
/* -------------------------------------------------------------------------- */
/**
 * GOAL: Achieve raw CPU performance.
 * * INTUITION: 
 * In high-performance systems or competitive programming (OAs), we want to 
 * avoid the overhead of hashing. We "translate" Strings into an integer 
 * range [0, n-1] exactly once. This allows the core algorithm to run 
 * using lightning-fast array indexing.
 * * PROS: O(1) edge lookups, minimal memory footprint.
 * CONS: Slightly more "setup" code to manage the mapping.
 */
class EfficientTopology {
    public List<String> findOrder(String[] courses, String[][] prerequisites) {
        int n = courses.length;
        
        // STEP 1: THE TRANSLATOR
        // We create a map to give every String a unique ID (0 to n-1)
        Map<String, Integer> nameToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            nameToIndex.put(courses[i], i);
        }

        // STEP 2: THE ADJACENCY STRUCTURE
        // Since we have IDs, we can use a raw Array of Lists (Scalpel approach)
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        
        int[] indegree = new int[n];
        for (String[] pre : prerequisites) {
            // prereq (v) -> course (u)
            int u = nameToIndex.get(pre[0]); 
            int v = nameToIndex.get(pre[1]); 
            adj[v].add(u);
            indegree[u]++;
        }

        // STEP 3: THE ENGINE (Kahn's Algorithm)
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) q.offer(i);
        }

        List<String> result = new ArrayList<>();
        while (!q.isEmpty()) {
            int curr = q.poll();
            // Map the ID back to the original String using the 'courses' array
            result.add(courses[curr]); 

            for (int neighbor : adj[curr]) {
                if (--indegree[neighbor] == 0) {
                    q.offer(neighbor);
                }
            }
        }

        // Cycle Check: If we didn't visit all nodes, it's impossible
        return result.size() == n ? result : new ArrayList<>();
    }
}

/* -------------------------------------------------------------------------- */
/* APPROACH 2: THE "PURE MAP" FLEXIBILITY STRATEGY (Most Maintainable)         */
/* -------------------------------------------------------------------------- */
/**
 * GOAL: Code Readability and Dynamic Scaling.
 * * INTUITION: 
 * In a Software Engineering interview (Google/Uber F2F), we prioritize 
 * "Production-Ready" code. Using a Map directly tells the interviewer 
 * that our code is flexible and can handle sparse data or non-sequential 
 * IDs without complex translation layers.
 * * PROS: Highly readable, self-documenting, handles any object type.
 * CONS: Slower due to constant Hash calculations for every lookup.
 */
class FlexibleTopology {
    public List<String> findOrder(String[] courses, String[][] prerequisites) {
        // STEP 1: INITIALIZATION
        // Use Maps to store everything. No assumptions about ID ranges.
        Map<String, List<String>> adj = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();
        
        // Ensure every course is initialized (nodes with 0 edges must exist)
        for (String course : courses) {
            adj.put(course, new ArrayList<>());
            indegree.put(course, 0);
        }

        // STEP 2: BUILDING RELATIONSHIPS
        for (String[] pre : prerequisites) {
            String course = pre[0];
            String prereq = pre[1];
            adj.get(prereq).add(course);
            indegree.put(course, indegree.get(course) + 1);
        }

        // STEP 3: THE ENGINE (Kahn's using String Keys)
        Queue<String> q = new LinkedList<>();
        for (String course : courses) {
            if (indegree.get(course) == 0) q.offer(course);
        }

        List<String> result = new ArrayList<>();
        while (!q.isEmpty()) {
            String curr = q.poll();
            result.add(curr);

            // Traverse neighbors directly using the Map
            for (String neighbor : adj.get(curr)) {
                indegree.put(neighbor, indegree.get(neighbor) - 1);
                if (indegree.get(neighbor) == 0) {
                    q.offer(neighbor);
                }
            }
        }

        return result.size() == courses.length ? result : new ArrayList<>();
    }
}

}
