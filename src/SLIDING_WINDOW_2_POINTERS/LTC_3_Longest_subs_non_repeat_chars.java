package SLIDING_WINDOW_2_POINTERS;

public class LTC_3_Longest_subs_non_repeat_chars {
    /*GOAL : 

    Basically here we are checking if char is present if not then put it , along with the current index and else if the char is present in the map then move check the max between current start and current char's last position  , so by keeping char,position we can easily remove the repeated char instead of shrinking the windows and checking the map.size() which is more time taking . 


    find max_len substring having distinct chars . 
    Simple do this using Sliding Window since string is there and longest substring type thing is asked , 
    have 2 pointers  ; start and end at first initailly , keep on incrmenting end && check if element is already present in map , if not add && update max_len , if its there , move left to duplicate char's index , later update its index && length .

    Here used Map since its helpful in keeping the index of element helps to avoid while loop , also major reason for not using while  is that , if we shrink the windown size from left++ , then doing this is not benefitting us as we do not want substrings  ,we want max_len its not gonna increase while shrinking hence kept window size to max_len and expand it only while satisfying conditions . 
    */


    public int lengthOfLongestSubstring(String s) {
        //edge case , 
        if(s.length() ==0 || s.length() == 1 )return s.length();
        
        Map<Character , Integer> map = new HashMap<>();
        int max_len = 0 ;
        int start=0 ;
        int end=0;

        while(end<s.length()){
            char ch = s.charAt(end);

            if(map.containsKey(ch)){
                //avoided use of while , explained above
                start = Math.max(map.get(ch)+1, start);
            }

            //if element is not there || updating the duplicate element's index . 
            map.put(ch,end);
            max_len = Math.max(max_len , end-start+1);
            end++;

        }

        return max_len;

    }
    
}
