package FAANGM_QUESTIONS;

public class CycleQuestions {
    private Node head;
    private Node tail;
    private int size; 
    
    public CycleQuestions(){
        this.size = 0;
    }

    private class Node{
        private int val;
        private Node next;
        
        //when giving val to node , hence made public . 
        public Node(int value){
            this.val = value;
        }

        //same here , user modification by setters . 
        public Node(int val , Node next ){
            this.val = val;
            this.next = next;
        }

    
    }

    public void insertFirst(int val){
        Node node = new Node(val);
        node.next = head ;
        head=node;

        if(tail==null) tail = head;
        size++;

    }

    public void displayLL(){
        Node temp = head;
        do { 
            System.out.print(temp.val + "->");
            temp = temp.next;
        } while (temp != tail);

        System.out.print(temp.val + "->");
        if(temp.next != null ){
            System.out.print(temp.next.val);
        }
        else{
            System.out.print("ant");
        }
    }

    public void insertLast(int val){
        if(tail==null){
            insertFirst(val); //if tail=null hence only single item need to be added . 
            return;
        }
        
        //otherwise , make new node and do accordingly .
        Node node = new Node(val);
        tail.next = node;
        tail=node ;
        size++;

    }

    public void create_cycle(int ind){
        Node temp = head;
        for (int i = 1; i < ind; i++) {
            temp = temp.next;
        }

        tail.next = temp.next;
    }

    //check cycle , using HARE and TURTLE  , or FSTTTT OR Slowwwwwww pointer . 
    
    public boolean check_Cycle() {
        return check_cycle(head);
    }

    private boolean check_cycle(Node head){
        Node fast = head;
        Node slow = head;
        //conditions
        while(fast!=null && fast.next!=null){
            //made changes and checked later bcz , both at first are pointing to head , if will fail out . 
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast) return true ; //if they meet , which will be eventually be happening if it's an cycle... 

        }       

        return false; //if it's out of loop then , one of fast / fast.next is null hence return false ; 
    }

    //Q-Length of Linked List Cycle . 
    
    // By observation I saw that the no. of times the loop has worked to catch the fast and slow pointer to
    // be at one place..
    
    public int len_Cycle() {
        return len_Cycle(head);
    }

    private int len_Cycle(Node head){
        int len=1;
        Node fast = head;
        Node slow = head;
        //conditions
        while(fast!=null && fast.next!=null){ 
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast)  return len;
            // {   //Another method to do it is to keep the fast there and move slow to next until its == fast . 
            //     int length = 0;
            //     do { 
            //         slow = slow.next;
            //         length++;
            //     } while (slow != fast);
            //     return length;
            // }
            

            len++;
        }       

        return 0; //if it's out of loop then , then no cycle , length=0 ; 
    }

    // Q- 6 finding the start of the cycle ..

    private Node chck_cycle(Node head){
        Node fast = head;
        Node slow = head;
        //conditions
        while(fast!=null && fast.next!=null){
            //made changes and checked later bcz , both at first are pointing to head , if will fail out . 
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast) return fast ; //if they meet , which will be eventually be happening if it's an cycle... 

        }       

        return null; //if it's out of loop then , one of fast / fast.next is null hence return false ; 
    }

    public int  start_node_cycle(){
        return start_cycle().val;
    }

    private Node start_cycle(){
        Node fast = chck_cycle(head);

        if(fast != null) {
            Node t1 = fast.next ; 
            Node t2 = head; 
            while(true){
                if(t1.next == t2){
                    return t2;
                }
                if(t2 == t1){
                    t1 = t1.next;
                    t2 = head;
                }

                else{
                    t2 = t2.next;
                }
            }
        }

        return fast;

    }
    
}
