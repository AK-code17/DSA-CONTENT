package FAANGM_QUESTIONS;

public class Reverse_LL2 {
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

    public void Reverse_Left_right(int left , int right){
        rev_LR(head,left,right) ; 
    }

    private void rev_LR(ListNode head, int left, int right) {
        ListNode pNode = null;
        pNode.next = head;
        ListNode sNode = head;

        while(sNode.val != left){
            if(pNode.next.val != left){
                pNode = pNode.next;
            }

            sNode = sNode.next;
        }

        ListNode prev = sNode;
        ListNode curr = prev.next;
        
        while(curr.val != right){
            ListNode next = curr.next ;
            curr.next = prev;
            prev = curr ; 
            curr = next ; 

        }

        pNode.next = prev;
        sNode.next = curr;


    }

    
}
