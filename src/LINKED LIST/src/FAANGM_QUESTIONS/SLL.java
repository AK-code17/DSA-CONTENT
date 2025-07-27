package FAANGM_QUESTIONS;

public class SLL {
    private Node head;
    private Node tail;
    private int size; 
    
    public SLL(){
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

    public void insertIndex(int ind , int val){  
        if(ind==0) {
            insertFirst(val);
            return;
        }

        if(ind==size){
            insertLast(val);
            return;
        }

        // Node node = new Node(val);
        Node temp = head;

        for(int i=1 ; i<ind ; i++){
            temp = temp.next;
        }

        // //Approach 1 :
        // Node curr = temp ;  // curr -> temp
        // temp = temp.next;   // temp -> next of temp . 

        // curr.next = node;   //curr -> node
        // node.next = temp;   //node -> temp
        // size++;

        //Approach 2 using the made constructor . 
        Node node2 = new Node(45,temp.next);
        temp.next = node2;
        size++;        

    }

    // Q2. LINEKD LIST REMOVE DUPLICATES . 
    public void remove_dupl(){
        remove_dupl(head.next, head);
    }
    
    private void remove_dupl(Node nd1, Node nd2) {
        if (nd1 == null) {
            nd2.next = null;
            return;
        }

        if (nd1.val != nd2.val) {
            nd2.next = nd1;
            remove_dupl(nd1.next, nd1);  //no need to do nd2=nd1;
        } 
        else {
            remove_dupl(nd1.next, nd2);  //also no need to do nd1.next after all , since we can pass it directly . 
        }
    
    }

    public void create_cycle(int ind){
        Node temp = head;
        for (int i = 1; i < ind; i++) {
            temp = temp.next;
        }

        tail.next = temp.next;
    }
}
