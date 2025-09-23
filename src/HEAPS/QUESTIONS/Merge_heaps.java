package HEAPS.QUESTIONS;


public class Merge_heaps {
    public int[] mergeHeaps(int[] a, int[] b, int n, int m) {
        // // your code here
        
        // PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        // // Add all elements from both arrays
        // for(int x : a) maxHeap.offer(x);
        // for(int x : b) maxHeap.offer(x);
        
        // int[] ans = new int[n+m];
        // // Extract in descending order
        // for(int i = 0; i < n + m; i++) {
        //     ans[i] = maxHeap.poll();
        // }
        
        // return ans;
        
        int[] ans = new int[n+m];
        int ind = 0;
        // Add all elements from both arrays
        for(int ele : a){
            ans[ind++] = ele;
        }
        
        for(int ele : b){
            ans[ind++] = ele;
        }
        
        int size = n+m;
        for(int i = size/2 - 1 ; i>=0 ; i-- ){
            heapify(i,size,ans);
        }
        
        return ans;
        
    }
    
    public void heapify(int ind, int n , int[] arr){
        int left = ind*2+1;
        int right = ind*2+2;
        int max = ind;
        
        if(left < n && arr[left] > arr[max]){
            max = left; 
        }
        
        if(right < n && arr[right] > arr[max]){
            max = right;
        }
        
        if(max != ind){
            swap(ind,max,arr);
            heapify(max,n,arr);
        }
    }
    
    public void swap(int a , int b , int[] arr){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
