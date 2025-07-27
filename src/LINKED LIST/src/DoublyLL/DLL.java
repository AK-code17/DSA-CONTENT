package DoublyLL;

// import SinglyLL.LL;

public class DLL {  

    private Node head;
    private Node tail;
    private int size;


    public DLL(){
        this.size=0;
    }

    //the node will contain next and prev , since these both the properties are bounded with node . 
    class Node{
        int val;
        Node next;
        Node prev;


        public Node(int val){
            this.val = val;
        }

        public Node(int val , Node prev , Node next){  //due to Doubly Linked List , have given 2 arguments in the function , 
            this.val =val;
            this.prev = prev;
            this.next = next;
        }

    }

    public void insertFirst(int val,DLL list){
        Node node = new Node(val);
        node.next = list.head;
        node.prev = null;
        if(head!=null){
            list.head.prev = node;
        }
        list.head = node;
        if(tail==null) tail=head;

        size++;
    }
    
    //here while displaying we also added the part for the rever_display
    //When TAIL ISN'T GIVEN . 

    public void display(DLL list){
        Node temp = list.head;
        Node last = null;

        while(temp != null){
            System.out.print(temp.val + "->");
            last = temp; //now when its the last time of for loop , the last -> temp(tail) . 
            temp = temp.next;
        }
        // System.out.println("Done displaying the elements");
        System.out.println("ANTT");

        // while(last !=null){
        //     System.out.print(last.val + " -> ");
        //     last = last.prev ; //same logic as in approach2 , just the issue of tail is being resolved by making the last to node . 
        // }

        // System.out.println("AARAMB");
    }

    // TAIL IS GIVEN 
    public void display_Rev(DLL list){
 
        Node temp = list.tail;
        while(temp != null){
            System.out.print(temp.val + "->");
            temp = temp.prev;
        }
        // System.out.println("Done displaying the elements");
        System.out.println("AARAMB");
    }

    public void insertLast(int val,DLL list){

        if(head==null){
            insertFirst(val, list) ;
            return ; 
        }

        Node temp = head;
        while(temp.next != null){
            temp = temp.next;
        }

        Node node = new Node(val);
        temp.next = node ; 
        node.prev = temp;
        node.next = null;

        size++;

    }

    public void insertNode_withnode(int after , int val , DLL list){

        //create new node(list also) . 
        if(list.head == null) {
            insertFirst(val, list);
            return;
        }

        Node prev_node = head;
        
        //added prev_node!=null to ensure unnecessary further movement . 
        while( prev_node != null &&  prev_node.val != after ){
            prev_node = prev_node.next;
        }

        //if after loop prev_node == null , means value not found hence add at last . 
        if(prev_node == null) {
            insertLast(val, list);
            return;
        }


        //othwerwise do all . 
        Node new_node = new Node(val);
        new_node.prev = prev_node;
        new_node.next = prev_node.next;
        prev_node.next = new_node;
        if(new_node.next != null) new_node.next.prev = new_node;
        
        size++;
    }

}
