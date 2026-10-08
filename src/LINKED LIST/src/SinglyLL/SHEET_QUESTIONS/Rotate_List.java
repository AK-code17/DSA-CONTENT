public class Rotate_List {
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
    public ListNode rotateRight(ListNode head, int k) {
        
        ListNode slow = head;
        ListNode fast = head;

        int len = 0;

        ListNode temp = head;

        while(temp != null){
            len++;
            temp = temp.next;
        }

        if(len == 0 || k==0) return head; 

        // this ensures we only move the loop by the remaining remainder ,since if len = 4 and we move in multiplies of len then it will gonna same , so it will save the time..
        k = k % len;

        for(int i=0 ; i<k ; i++) fast = fast.next;

        while(fast.next != null){
            slow = slow.next;
            fast = fast.next;
        }

        fast.next = head;
        head = slow.next;
        slow.next = null;

        return head;

    }
}
