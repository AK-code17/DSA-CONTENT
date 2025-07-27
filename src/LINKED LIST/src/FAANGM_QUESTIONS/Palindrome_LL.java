package FAANGM_QUESTIONS;

public class Palindrome_LL {
    private ListNode head;
    private int size;
    private ListNode tail;

    public Reverse_LL2(){
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

    public boolean isPalindrome(ListNode head) {
        ListNode mid = get_mid(head);
        ListNode head_s = reverseList(mid);
        ListNode re_rev_head = head_s;

        // compare both the halves 
        while(head!=null && head_s!=null){
            if(head.val != head_s.val){
                break;
            }
            head = head.next;
            head_s = head_s.next;
        }
    
        reverseList(re_rev_head);

        return head==null || head_s==null;
        
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
        ListNode present = head;
        ListNode next = present.next;

        while (present != null) {
            present.next = prev;
            prev = present;
            present = next;
            if (next != null) {
                next = next.next;
            }
        }
        return prev;
    }

    private ListNode get_node(ListNode head ,int ind) {     

        if (ind < 2) return null;
        ListNode temp = head;
        int i=1;
        while(i != ind-1){
            temp = temp.next;
            i++;
        }

        return temp;
        
    }

    
}
