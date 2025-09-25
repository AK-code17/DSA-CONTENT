package HEAPS.QUESTIONS;

import java.util.*;

public class Min_cost_ropes {

class Solution {

    // TC: O(n) (ele into pq) + O(3.logn)(deletn)(insetion)(insetion);
    // SC: O(n) (store every ele into pq)
    public static int minCost(int[] arr) {
        // code here..
        PriorityQueue <Integer> pq = new PriorityQueue<>();
        //insert all the ele into min_heap..
        for(int ele : arr){
            pq.offer(ele);
        }
        
        int count = 0;
        //while size > 1 . take 2 smallest ele , update the cnt , add curr cnt into min_heap..
        while(pq.size()>1){
            // O(logn) for deletion..
            int a = pq.poll();
            int b = pq.poll();
            count += a+b;
            // O(logn) for insertion..
            pq.offer(a+b);
        }
        
        return count;
        
    }
}
}
