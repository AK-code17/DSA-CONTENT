package FAANGM_QUESTIONS;

public class Reorder_LL {
    private ListNode head;
    private int size;
    private ListNode tail;

    public Reorder_LL(){
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

    public void display() {
        ListNode temp = head;
        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public int getsize(){
        return size;
    }

    public void reorderList(){
        // First get the mid of the LL , and...
        ListNode mid = get_mid(head);
        //then reverse mid to end . 
        ListNode head_s = reverseList(mid);

        //since we have to return the head  , hence we are not modifying it directly . 
        
        ListNode f = head;
        ListNode s = head_s;

        while(f != null && s!=null){
            ListNode t1 = f;
            f=f.next;
            t1.next = s;
            ListNode t2 = s;
            s=s.next;
            if(s != null){
                t2.next = f;
            }
        }
    }

    public ListNode get_mid(ListNode h1) {
        ListNode slow = h1;
        ListNode fast = h1;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = curr.next;

        while (curr != null) {
            curr.next = prev;
            prev = curr;
            curr = next;
            if (next != null) {
                next = next.next;
            }
        }
        return prev;
    }

}
