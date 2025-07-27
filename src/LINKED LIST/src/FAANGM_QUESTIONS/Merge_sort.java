package FAANGM_QUESTIONS;


public class Merge_sort {
    private ListNode head;
    private int size;
    private ListNode tail;

    public Merge_sort(){
        this.size=0;
    }


    private class ListNode{
        private int val;
        private ListNode next ; 

        public ListNode(int val){
            this.val = val;
        }

        public ListNode(int val , ListNode next){
            this.val = val;
            this.next = next ; 
        }
    }

    public void insertFirst(int val){
        ListNode node = new ListNode(val);
        node.next = head ;
        head=node;

        if(tail==null) tail = head;
        size++;

    }

    public void insertLast(int val){
        if(tail==null){
            insertFirst(val); //if tail=null hence only single item need to be added . 
            return;
        }
        
        //otherwise , make new node and do accordingly .
        ListNode node = new ListNode(val);
        tail.next = node;
        tail=node ;
        size++;

    }

    public ListNode sort_list(Merge_sort list){
        return sort(list.head, list.tail);
    }


    private ListNode sort(ListNode head , ListNode eNode){
        if(head == null || head.next == eNode) {
            if(head != null) head.next = eNode;
            return head;
        }

        ListNode mid = get_mid(head, eNode);

        ListNode left = sort(head , mid);
        ListNode right = sort(mid, eNode);

        return merge_list(left, right, eNode);
    }

    private ListNode merge_list(ListNode left, ListNode right , ListNode eNode) {
        if (left == eNode|| left==null) return right;
        if (right == eNode || right==null) return left;

        if (left.val <= right.val) {
            left.next = merge_list(left.next, right, eNode);
            return left;
        }

        right.next = merge_list(left, right.next, eNode);
        return right;
    }

    private ListNode get_mid(ListNode h1, ListNode eNode) {
        ListNode slow = h1;
        ListNode fast = h1;

        while (fast != eNode && fast.next != eNode) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // Utility function to print the list
    public void printList() {
        ListNode temp = head;
        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
    }

}
