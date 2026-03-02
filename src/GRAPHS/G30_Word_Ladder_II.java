package GRAPHS;
import java.util.*;

public class G30_Word_Ladder_II {

    // THIS IS MOST OPTIMISED TERMS OF MEMORY BUT BELOW THIS IS ALSO ACCEPTED IF MEMO CONSTRAINTS ARE NOT MUCH TIGHT...

/**
 * PROBLEM: WORD LADDER II (THE "GPS + BACKTRACKING" OPTIMIZATION)
 * * -----------------------------------------------------------------------------
 * WHY THIS IS THE GOLD STANDARD FOR SCALING:
 * 1. STEP 1 (THE GPS): We run a standard BFS to map the shortest distance 
 * of every word from the start. This "Distance Map" acts as our GPS. 
 * We use O(1) removal to prevent cycles and find the minimum steps.
 * * 2. STEP 2 (THE BACKTRACKING): Instead of carrying paths in a heavy Queue, 
 * we use DFS to "walk backward" from endWord to startWord. 
 * By only stepping on words where (Distance == CurrentDistance - 1), 
 * we guarantee we only follow SHORTEST paths. No dead ends, no wasted memory!!
 * -----------------------------------------------------------------------------
 */

    /**
     * Step 1: Word Ladder 1 Logic to build our "GPS" (Distance Map)..
     * We record the level/distance of each word found during BFS.
     */
    private int Shortest_distance(String startWord, String endWord, List<String> wordList, Map<String, Integer> map) {
        // 1. If we are already at the target.. the path is just the word itself!!
        if (startWord.equals(endWord)) {
            map.put(startWord, 1);
            return 1;
        }

        Set<String> set = new HashSet<>(wordList);
        
        // 2. DEADLOCK CHECK: If target is not in the set, we can never reach it.. 
        // Even if transformations are possible, the destination is missing!!
        if (!set.contains(endWord)) return 0;
        
        // Using Deque for better performance over LinkedList..
        Deque<String> que = new ArrayDeque<>();
        que.add(startWord);
        
        // If beginWord is in the set, remove it to mark as visited.. 
        // prevents infinite loops and redundant checks!!
        if (set.contains(startWord)) set.remove(startWord);
        
        // IMPORTANT: Map the startWord so DFS knows where to stop!
        map.put(startWord, 1);
        
        int path = 1;
        
        while (!que.isEmpty()) {
            int size = que.size();
            
            // Standard level-wise BFS traversal..
            for (int i = 0; i < size; i++) {
                String word = que.poll();
                
                // If at any time we hit endWord, return the path length..
                if (word.equals(endWord)) return path;
                
                // TRYING CHARACTER REPLACEMENT (OPTIMISATION).. 
                // Much better than N^2 comparison because the alphabets are limited!!
                char[] charArray = word.toCharArray(); 
                for (int j = 0; j < charArray.length; j++) {
                    char og = charArray[j];
                    
                    // change current char from a to z and check existence in set..
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == og) continue;
                        
                        charArray[j] = c;
                        String nextWord = String.valueOf(charArray);
                        
                        // If word exists in set, add to que and REMOVE IT IMMEDIATELY..
                        // This acts as our visited marking..
                        if (set.contains(nextWord)) {
                            // Record the level of this word in our Map GPS..
                            map.put(nextWord, path + 1);
                            que.add(nextWord);
                            set.remove(nextWord); 
                        }
                    }
                    // Restore to original char to check other positions.. 
                    // VERY IMP STEP..
                    charArray[j] = og;
                }
            }
            // Increment path after each level is fully processed..
            path++;
        }
        
        return 0;
    }

    /**
     * Step 2: Backtracking from endWord -> startWord using the Map GPS..
     * We use the distances stored in the map to find valid neighbors.
     */
    private void Find_valid_paths(String startWord, Map<String, Integer> map, List<String> path, List<List<String>> ans) {
        int len = path.size();
        String curr = path.get(len - 1);

        // Base case : If we reached the startWord, we found a complete path!!
        if (curr.equals(startWord)) {
            // SNAPSHOT: Create a new list because 'path' is a reference that changes!!
            // Reverse because currently path is built end -> start..
            ArrayList<String> validPath = new ArrayList<>(path);
            Collections.reverse(validPath);
            ans.add(validPath);
            return;
        }

        // Check and try every combo , if found checks if its level is currlevel-1 , 
        // and call further DFS call from that....
        int currLevel = map.get(curr);
        char[] arr = curr.toCharArray(); 
        
        for (int i = 0; i < arr.length; i++) {
            char og = arr[i];
            
            for (char ch = 'a'; ch <= 'z'; ch++) {
                arr[i] = ch;
                String prev = String.valueOf(arr);

                // THE MAGIC LOGIC: Only backtrack to a word if it's exactly 1 step 
                // closer to the start according to our BFS GPS..
                if (map.containsKey(prev) && map.get(prev) == currLevel - 1) {
                    path.add(prev);
                    Find_valid_paths(startWord, map, path, ans);
                    path.remove(path.size() - 1); // Backtrack.. remove last to try other branches!!
                }
            }
            // Restore char for next position..
            arr[i] = og;
        }
    }

    public List<List<String>> findLadders(String startWord, String endWord, List<String> wordList) {
        List<List<String>> ans = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        
        // Step 1 : Word Ladder 1 : find shortest pathcount , and add valid 
        // next words generated step / level wise into our GPS (Map).. 
        int shortest_distance = Shortest_distance(startWord, endWord, wordList, map);
        if (shortest_distance == 0) return ans;

        // Step 2 : Now find all shortest valid paths from endWord to startWord 
        // using the distances in the Map to guide the DFS.. 
        List<String> path = new ArrayList<>();
        path.add(endWord);
        Find_valid_paths(startWord, map, path, ans);

        return ans;
    }
    
/**
 * PROBLEM: WORD LADDER II (THE ALL-SHORTEST-PATHS CHALLENGE)
 * * -----------------------------------------------------------------------------
 * THE "FUTURE-SELF" DEEP-DIVE (WHY THIS WORKS):
 * -----------------------------------------------------------------------------
 * 1. THE GOAL: Find ALL shortest paths. If the shortest distance is 5, 
 * we need every single path of length 5, but ZERO paths of length 6.
 * * 2. THE QUEUE OF PATHS: We store ArrayList<String> in the queue. 
 * This keeps the "history" of each journey separate as they branch out.
 * * 3. THE BATCH REMOVAL (CRITICAL): In Word Ladder 1, we delete words 
 * immediately. In Word Ladder 2, we "wait" until the level is finished. 
 * If 'dot' and 'lot' both hit 'log' at the same level, 'log' must stay 
 * in the set for both of them to "see" it.
 * * 4. THE FOUND FLAG: Once any path in the current level hits the target, 
 * we finish the rest of the current level (to catch other shortest paths) 
 * but we NEVER look at the next level.
 * -----------------------------------------------------------------------------
 */
    public ArrayList<ArrayList<String>> findSequences(String startWord,
                                                      String targetWord,
                                                      String[] wordList) {
        // Code here
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for(String s : wordList){
            set.add(s);
        }
        
        // Gatekeeper check: target MUST be in the set!!
        if(!set.contains(targetWord)) return ans;
        if(set.contains(startWord)) set.remove(startWord);

        // ArrayList<String> que because WE CAN STORE THE COMPLETE PATH 
        // startWord -> s1 -> s2 -> ... sk-> targetWord..
        Deque<ArrayList<String>> que = new ArrayDeque<>();
        ArrayList<String> path = new ArrayList<>();
        path.add(startWord);
        que.offerLast(path);

        while(!que.isEmpty()){
            int len = que.size();
            
            // boolean flag in order to break off when we reached the shortest path(s);
            boolean found = false;
            
            // inorder to ensure that EACH PATH AT THE SAME LEVEL CAN ACCESS THE GENERATED NEXT WORD , 
            // BECAUSE IT MAY BE POSSIBLE MULTIPLE PATH USE THE SAME WORD TO DIRECTLY OR 
            // INDIRECTLY REACH THE targetWORD... 
            Set<String> used = new HashSet<>();
            
            for(int i=0 ; i<len ; i++){
                path = que.pollFirst();
                
                // last word inserted will be the parent word and using that we will add the 
                // next set of possible children words , which will update the path...
                String parent = path.get(path.size()-1);

                // check if parent is the targetWord then add path to ans , 
                // and mark the boolean flag as FOUND = TRUE..
                if(parent.equals(targetWord)){
                    found = true;
                    ans.add(path);
                    continue;
                }

                // THIS CHECKS PREVENTS THE INVALID PATHS WHICH ARE AT THE SAME LEVEL OF 
                // VALID SHORTEST PATH, BECAUSE IT CAN HAPPEN THAT NOT ALL PATHS AT THE 
                // SHORTEST PATH DISTANCE ARE VALID ONES..
                if(!found) {
                    char[] arr = parent.toCharArray();
                    
                    for(int j=0 ; j<arr.length ; j++){  
                        char og = arr[j];
                        
                        // try all alphabets at each index..
                        for(char ch='a' ; ch<='z'; ch++){
                            arr[j] = ch;

                            String next = new String(arr);
                        
                            if(set.contains(next)){
                                used.add(next); // add the used words at the same level in used,,,
                                ArrayList<String> path_updated = new ArrayList<>(path);
                                path_updated.add(next);
                                que.offer(path_updated);
                            }
                        }

                        // converted back to og char...
                        arr[j] = og;
                    }  
                }
            }
            
            // remove the used words in particular level.. AFTER level is done!!
            for(String s : used) set.remove(s);

            // terminate the path finding process as soon as we found the shortest path(s) , 
            // because all the valid paths are added into ans..
            if(found) break;
        }

        return ans;
    }
}
