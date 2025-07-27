package SinglyLL;

public class LL {
    //making below private inorder to keep them secure them from any mmodification . 
    
    private Node head;
    private Node tail;
    private int size; 
    
    public LL(){
        this.size = 0;
    }

    public void insertFirst(int val){
        Node node = new Node(val);
        node.next = head ;
        head=node;

        if(tail==null) tail = head;
        size++;

    }

    public void displayLL(LL list){
        Node temp = list.head;
        while(temp != null){
            System.out.print(temp.val + "->");
            temp = temp.next;
        }
        // System.out.println("Done displaying the elements");
        System.out.println();
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

    // ******************DELETION*****************

    public int deleteFirst(LL list){
        if(head==null) return -1;    
        
        int val = list.head.val;
        list.head = list.head.next;
        if(list.head == null) list.tail = null; //this is when there is only single item . 
        size--;
        return val;
    
    }

    public int deleteLast(LL list){
        if(list.head==null) return -1;

        Node temp = list.head;
        for (int i = 1; i < list.size-1 ; i++) {
            temp = temp.next;
        }

        int val = list.tail.val;
        list.tail = temp;
        tail.next = null;  //temp.next = null; both are correct since now tail and temp are pointing
                           //  to same ref but tail may be used . 
        size--;
        return val;
    }

    public int deleteInd(int ind,LL list){
        if(ind<0 || ind>=list.size) return -1;
        
        if(ind==0){
            return deleteFirst(list);
        }

        if(ind==list.size-1){
            return deleteLast(list);
        }

        Node temp = list.head;
        for(int i=0 ; i<ind-1 ; i++){
            temp = temp.next;
        }

        int val = temp.next.val;
        temp.next = temp.next.next;
        size--;
        return val;
    }

    //INSERTION USING RECURSION . 

    public void insert_Rec(int val , int index){
        Node temp = head;
        insertRec(val, index, temp, 0);
    }

    private void insertRec(int val , int index,Node temp,int i){
        
        // if(i > index || temp==null){
        //     System.out.println("Unable to add , index out of bounds");
        //     return;
        // }

        // //CASE- I : Insert at first. 
        // if(index == 0){
        //     insertFirst(val);
        //     return;
        // }

        //CASE-III : Insert at point
        if(i ==index-1 || i==0){
            Node curr = new Node(val);
            curr.next = temp.next;
            temp.next = curr;
            return;
        }

        // //CASE-II : Insert at last.
        // if(temp.next == null){
        //     insertLast(val);
        //     return;
        // }
        
        insertRec(val, index, temp, i+1);

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


}
