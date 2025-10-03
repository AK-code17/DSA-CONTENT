package HEAPS.QUESTIONS;

import java.util.PriorityQueue;

public class k_sorted_list {
    /*
    Approach : since our goal is to sort the array , so now after brute force what we can do is we can take the use of k sorted array , here every ele is atmost k distance far from its sorted arr ind/postn , so what we can do is firstly put first k elements in pq, then take and ind j  = ind-k , it will denote first the oth index , so now pq(min) has k elements and currently we r at k ind so k-ind(0) = k so its already k distance away from it sorted position so what we can do is curr ele, pq.offer(ele) which is at distacne k so just put arr[j] = pq.poll(), in this we we got the max till at max k distance and we also updated the heap...

    THIS WAS MY INITIAL ATTEMPT , AND ITS A BIT OPTIMISED ALSO SINCE IF ELE Is small then why to insert and take out.. , but one thing is that my intial approach is optimised since if ele < peek then why to put that and remove since we already know this ele is what we wil lget and the content of heap will not change.

    or what we can do after putting first k elemts , since we r at ind = k , so k elements are already there and if curr ele is less than peek that means at , at max distacne k curr ele is smallest hence it will be placed at arr[j] = curr.ele ,no removal from heap, and if the curr ele > peek it means peek is the next smallest present.. 
    */
    public void nearlySorted(int[] arr, int k) {
        // code here
        /*edge case is very much imp. since if k is 0 ,then first of all
        no need to do all this simply return  , else the pq will be empty
        && then peek will cause error..
        */
        if(k == 0) return;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        int n = arr.length;
        int ind = 0;

        while(ind < k){
            pq.offer(arr[ind++]);
        }
        
        int j = 0;
        
        while(ind < n){
            int ele = arr[ind];
            // if by adding a few lines our code can be optimised then why not do it..
            if(ele < pq.peek()) arr[j] = ele;
            else {
                
                arr[j] = pq.poll();
                pq.offer(ele);
            }
            j++;
            ind++;
        }
        
        while(!pq.isEmpty()){
            arr[j] = pq.poll();
            j++;
        }
        
        
    }
}
