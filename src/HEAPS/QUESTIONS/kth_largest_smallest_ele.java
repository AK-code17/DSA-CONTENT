package HEAPS.QUESTIONS;

import java.util.*;

public class kth_largest_smallest_ele {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int i = 0;

        while(i<k){
            pq.offer(nums[i++]);
        }

        while(i<n){
            if(nums[i] > pq.peek()){
                pq.poll();
                pq.offer(nums[i]);
            }
            i++;
        }

        return pq.poll();

    }

    public static int kthSmallest(int n,int k, int[]arr) {
        // Write your code here.
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        int i=0;

        while(i<k){
            pq.offer(arr[i++]);
        }

        while(i<n){
            if(arr[i] < pq.peek()){
                pq.poll();
                pq.offer(arr[i]);

            }
            i++;
        }

        return pq.poll();


    }
}

