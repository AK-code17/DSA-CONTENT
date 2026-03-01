package GRAPHS;
import java.util.*;

public class G29_Word_Ladder1{
/**
 * PROBLEM: WORD LADDER I (OPTIMIZED + EDGE CASES)
 * * GOAL: 
 * Find the shortest path length from startWord to targetWord.. only 1 char change 
 * allowed at a time and the word must be present in the wordList..
 * * INTUITION: 
 * Since we need the SHORTEST path and every transformation is weight 1, BFS is 
 * the best choice!! We use the "Gatekeeper" checks at the start to avoid 
 * unnecessary work.. Instead of N^2 comparisons, we use character replacement 
 * (a-z) because it scales much better for large word lists!!
 */
    public int wordLadderLength(String startWord, String targetWord, String[] wordList) {
        
        // 1. If we are already at the target.. the path is just the word itself!!
        if (startWord.equals(targetWord)) return 1;

        Set<String> set = new HashSet<>();
        for (String word : wordList) set.add(word);
        
        // 2. DEADLOCK CHECK: If target is not in the set, we can never reach it.. 
        // Even if transformations are possible, the destination is missing!!
        if (!set.contains(targetWord)) return 0;
        
        Queue<String> que = new LinkedList<>();
        que.add(startWord);
        
        // If startWord is in the set, remove it to mark as visited.. 
        // prevents infinite loops and redundant checks!!
        if (set.contains(startWord)) set.remove(startWord);
        
        int path = 1;
        
        while (!que.isEmpty()) {
            int size = que.size();
            
            // Standard level-wise BFS traversal..
            for (int i = 0; i < size; i++) {
                String word = que.poll();
                
                // If at any time we hit targetWord, return the path length..
                if (word.equals(targetWord)) return path;
                
                // TRYING CHARACTER REPLACEMENT (OPTIMISATION).. 
                // Much better than N^2 comparison because the alphabet is limited!!
                char[] charArray = word.toCharArray();
                for (int j = 0; j < charArray.length; j++) {
                    char originalChar = charArray[j];
                    
                    // change current char from a to z and check existence in set..
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) continue;
                        
                        charArray[j] = c;
                        String nextWord = String.valueOf(charArray);
                        
                        // If word exists in set, add to que and REMOVE IT IMMEDIATELY..
                        // This acts as our visited marking..
                        if (set.contains(nextWord)) {
                            que.add(nextWord);
                            set.remove(nextWord); 
                        }
                    }
                    // Restore to original char to check other positions.. 
                    // VERY IMP STEP..
                    charArray[j] = originalChar;
                }
            }
            // Increment path after each level is fully processed..
            path++;
        }
        
        // If the queue becomes empty and target wasn't found.. return 0
        return 0;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 7766f28 (Pushed Graphs : Word Ladder ( Shortest path transforming beginWord -> endWord ))
