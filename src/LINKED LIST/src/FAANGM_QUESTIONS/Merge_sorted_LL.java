package LINKEDLIST.FAANGM_QUESTIONS;

public class Merge_sorted_LL {
    public static LinkedListNode<Integer> sortTwoLists(LinkedListNode<Integer> first, LinkedListNode<Integer> second) {
		// Write your code here.

        //Initially creating a dummy node..
        LinkedListNode<Integer> dummy = new LinkedListNode(-1); 
        LinkedListNode<Integer> prev = dummy;

        while(first != null && second != null){
            if(first.data > second.data){
                prev.next = second;
                second = second.next; 
            }

            else{
                prev.next = first;
                first = first.next;
            }

            prev = prev.next;
        }

        // just connect prev to first / second , since the remaining nodes are already connected..
        if(first != null){
            prev.next = first;
        }

        if(second != null){
            prev.next = second;
        }

        return dummy.next;
	}
}
