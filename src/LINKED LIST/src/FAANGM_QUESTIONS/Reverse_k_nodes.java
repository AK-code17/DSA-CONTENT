package FAANGM_QUESTIONS;

public class Reverse_k_nodes {
    private ListNode head;
    private int size;
    private ListNode tail;

    public Reverse_k_nodes(){
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

    public void Reverse_kgroup (int k){
        head = Reverse_kgroup(head , k);
    }

    
    private ListNode Reverse_kgroup(ListNode head, int k) {
        //check edge case : 
        if(head == null || head.next == null || k<2) return head;

        //create temp ; 
        ListNode temp = new ListNode(-1);
        temp.next = head;

        //Create the b_node and e_node
        ListNode b_node = temp;
        ListNode e_node = head;

        //while (true) loop until the enode becoms null . 
        while (true) {
            //Move enode k times ,also checked possibility of reversing k nodes .  
            for (int i = 0; i < k-1; i++) {
                if(e_node == null || e_node.next == null ) return temp.next;
                e_node = e_node.next;
            }
            
            //Node creation for reversal . 
            ListNode start = b_node.next;
            ListNode prev = start;
            ListNode curr = start.next;

            //end for reversing loop

            ListNode end = e_node.next;
            
            //Now reversing the nodes . 
            while(curr!=end){
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            //Stiching back the pointers . 
            b_node.next = prev;
            start.next = curr;
            
            //Update the b_node and e_node . 
            b_node = start;
            e_node = curr ; 
        }

    }
    
}
