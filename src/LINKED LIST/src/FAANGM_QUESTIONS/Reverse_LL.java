package FAANGM_QUESTIONS;

public class Reverse_LL {
    private ListNode head;
    private int size;
    private ListNode tail;

    public Reverse_LL(){
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

    public int Reverse_LL(){
        // ListNode temp = head;

        // head = rev_LL(temp,temp.next,temp,temp);
        head = rev_LL2_rec(null, head,head.next);
        return head.val;
    }

    //APPROACH 1 . 
    //this method of doing reversal I got through the by seeing an keem observation that the strict sorting of decreasing order of linked list is the main working of reversing so I done that . 
    private ListNode rev_LL(ListNode f , ListNode s , ListNode prev , ListNode temp){
        if(s == null) return temp;

        if(f==temp){
            temp=s;
            prev.next = temp.next;
            
        }

        // else if(s==tail){
        //     prev.next=null;
        //     temp=s;
        //     tail=prev;
        // } 

        else{
            prev.next = s.next;
            temp=s;

        }

        s=s.next;
        temp.next=f;
        f=temp;

        return rev_LL(f, s, prev,temp);

    }

    //APPROACH 2 : 
    //Just by looking what is happening after submission of 100% on Approach 2  . 

    public ListNode rev_LL2_itr(ListNode prev , ListNode curr){
        while(curr != null){
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        return prev;
    }

    public ListNode rev_LL2_rec(ListNode prev , ListNode curr , ListNode temp){
        if(curr == null) return prev;
        temp = curr.next;
        curr.next = prev;
        return rev_LL2_rec(curr, temp,temp);
    }



}
