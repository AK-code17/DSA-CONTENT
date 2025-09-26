package HEAPS.QUESTIONS;

public class kth_largest_sum_subarray {
    public static int getKthLargest(ArrayList<Integer> arr, int k) {

		if(arr.size() ==0 ) return 0;

		PriorityQueue<Integer> pq = new PriorityQueue<>();

        // we must calculate all the subarray sum.. ans also we will maintain a k spaced min_heap , just like we did in kth largest element in array..
		for(int i = 0 ; i<arr.size() ; i++){
			int cnt = 0;
			for(int j = i ; j<arr.size() ; j++){
				cnt += arr.get(j);
                // if size == k  , offer only if the curr_cnt > top..
				if(pq.size() == k){
					if(cnt > pq.peek()){
						pq.poll();
						pq.offer(cnt);
					}
				}

                // keep offering till size reaches k..
				else{
					pq.offer(cnt);
				}
			}
		}
		
		return pq.poll();
	}
}
