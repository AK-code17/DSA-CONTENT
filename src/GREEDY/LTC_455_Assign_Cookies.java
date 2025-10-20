package GREEDY;

import java.util.*;

public class LTC_455_Assign_Cookies {
    public int findContentChildren(int[] g, int[] s) {
        
        // it's mandate to sort the g and s arrays since we want to assign the lowest cookie that will content the child , bcz there may be a cookie having value 12 which will satisfy need of 5 content , but that will not fulfill further 10 content person..
        Arrays.sort(g);
        Arrays.sort(s);

        int i = 0;
        int j = 0;
        int m = g.length;
        int n  = s.length;
        int cnt = 0;

        // this way we will not get out of bound and if any one got exhausted then checking does not make any sense if either the child got over or the cookies..
        while(i < m && j < n){
            int ele = g[i]; // 2..
            
            // I initially wrote this but not much needed since the outer loop will handle each.. 
            // while( j<n && s[j]<ele ){
            //     j++;
            // }

            // if(j == n) break;

            // if ele satisfies, then move to next child and next cookie , since this cookie can't be re-used (i and j) and increment cnt..
            if(s[j] >= ele) {
                cnt++;
                i++;
            }

            //else my j will move further, despite it satisfied or not..
            j++;

        }

        return cnt;

    }
}
