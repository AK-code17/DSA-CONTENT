public class Reverse_Nodes_K_Groups {
    /**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

    public void reverse(ListNode start){
        ListNode temp = null;
        ListNode Prev = temp;
        ListNode Curr = start;
        ListNode Next = Curr.next;

        while(Curr != null){
            Next = Curr.next;

            Curr.next = Prev;
            Prev = Curr;
            Curr = Next;
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if(head.next == null) return head;

        ListNode dummy = new ListNode(-1);
        ListNode curr = head;
        
        // signifies the EndNode of previously reversed portion...
        ListNode prevEnd = dummy;

        while(curr != null){
            // these nodes we saved since, we need to make sure we connect the prevEnd to revHead and also we need to make sure the reversed End node to be null inorder for an safe reversal..

            ListNode revHead = curr;
            ListNode revEnd = curr;

            int cnt = k;

            while(curr != null && cnt-->0){
                revHead = curr;
                curr = curr.next;
            }

            // if there are not enough Nodes then we will make the prevend to curr next and break the loop here, since we only perform the reversal when there is atleast k nodes remaining..
            if(cnt > 0){
                prevEnd.next = revEnd;
                break;
            }

            // Nulling the revHead so as to ensure we have a safe reversal..
            revHead.next = null;
            reverse(revEnd);

            // after reversal we will be appending the next node of prevEnd to our currently reversed node..
            prevEnd.next = revHead;

            // and also most importantly we will also make sure that now the prevEnd should be the revEnd since for the next reversal this node has to be joined or connected further.
            prevEnd = revEnd;

        }

        // here we are using the dummy.next since we need to make sure that this node needs to be saved.. and we will be returning as the head of node..
        
        return dummy.next;
    }
}
