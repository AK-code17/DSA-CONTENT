package FAANGM_QUESTIONS;

public class BubbleSort {
    private ListNode head;
    private int size;
    private ListNode tail;

    public BubbleSort(){
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

    public void insertFirstB(int val){
        ListNode node = new ListNode(val);
        node.next = head ;
        head=node;

        if(tail==null) tail = head;
        size++;

    }

    public void insertLastB(int val){
        if(tail==null){
            insertFirstB(val); //if tail=null hence only single item need to be added . 
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

    public void Bubble_Sort(){
        bubble_sort(size-1 , 0);
    }

    private void bubble_sort(int r , int c){
        if(r==0) return;

        if(r==c){
            //move to next . 
            bubble_sort(r-1, 0);
            return;
        }

        //getting nodes at indices current and next . 
        ListNode fst = get_node(c);
        ListNode sec = get_node(c+1);

        if(fst.val > sec.val){
            //swapping happens . 

            // Case -I : where swap is at the head . 
            if (fst == head) {
                head=sec;
                fst.next = sec.next;
                sec.next = fst;
                
            }
            
            // Case -II : where swap is at the tail . 
            else if (sec == tail) {    
                ListNode prev = get_node(c-1);
                prev.next = sec;
                tail=fst;
                fst.next = null;
                sec.next = tail;
                
            }

            // Case -III : where swap is at the middle . 
            else{
                ListNode prev = get_node(c-1);
                prev.next = sec;
                fst.next = sec.next;
                sec.next = fst;
                
            }

        }
        
        bubble_sort(r, c+1); //(if sec.val > fst.val) or else after doing the work move to next. 

    }

    private ListNode get_node(int c) {
        ListNode temp = head;
        if(c==0) return temp ; 

        int i=0;
        while(i != c){
            temp = temp.next ; 
            i++;
        }

        return temp;
    }

}
