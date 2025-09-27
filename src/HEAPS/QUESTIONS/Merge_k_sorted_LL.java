package HEAPS.QUESTIONS;

public class Merge_k_sorted_LL {
    	//Much Simpler than Merge 2 sorted Arrays , since we do not need to create new classes , just (next) , that;s LL BEAUTY..
    public static LinkedListNode<Integer> mergeKLists(LinkedListNode<Integer>[] listArray) {
        // Write your code here.

		PriorityQueue<LinkedListNode<Integer>> pq = new PriorityQueue<>((a,b) -> a.data - b.data);

		for(LinkedListNode<Integer> head : listArray){
			if(head != null) pq.offer(head);
		}

		//use of dummy and prev is same as in merge  2 sorted LL , it avoids exception,edge cases.. 
		LinkedListNode<Integer> dummy = new LinkedListNode(-1);
		LinkedListNode<Integer> prev = dummy;

		while(!pq.isEmpty()){
			LinkedListNode<Integer> curr = pq.poll();
			// connecting the prev to it's next , and moving prev ahead..

			prev.next = curr;
			prev = prev.next;

			if(curr.next != null) {
				pq.offer(curr.next);
			} 
		}

		return dummy.next;


    }
}
