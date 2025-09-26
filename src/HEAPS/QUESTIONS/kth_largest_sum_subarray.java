package HEAPS.QUESTIONS;

public class kth_largest_sum_subarray {
    public static int getKthLargest(ArrayList<Integer> arr, int k) {

		if(arr.size() ==0 ) return 0;

		PriorityQueue<Integer> pq = new PriorityQueue<>();

		for(int i = 0 ; i<arr.size() ; i++){
			int cnt = 0;
			for(int j = i ; j<arr.size() ; j++){
				cnt += arr.get(j);
				if(pq.size() == k){
					if(cnt > pq.peek()){
						pq.poll();
						pq.offer(cnt);
					}
				}
				else{
					pq.offer(cnt);
				}
			}
		}
		
		return pq.poll();
	}
}
