package GRAPHS;
import java.util.*;

public class G_30_Word_Ladder_II {
    
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
