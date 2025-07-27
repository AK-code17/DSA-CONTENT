package CirularSLL;

public class CLL {
    private int size;
    private Node head;
    private Node tail;

    public CLL(){
        this.size = 0;
    }

    private class Node{
        private int val;
        private Node next;

        public Node(int val){
            this.val = val;
        }

        public Node(int val , Node next){
            this.val = val;
            this.next = next;
        }
    }

    public void insertFirst(int val){
        Node node = new Node(val);

        if(head == null){
            head = node;
            tail = node;
            size++;
            return;
        }

        tail.next = node;
        node.next = head;
        head = node;
        size++;

    }

    public void display(){

        Node temp = head;
        if(temp != null){
            do{
                System.out.print(temp.val + "->");
                temp = temp.next;
            }
            while(temp != head);
        }

        System.out.println("HEAD(" + temp.val + ")");

    }

    public void delete_val(int val){
        Node temp = head;
        if(temp == null) return;
        
        if(temp.val == val){
            head = head.next;
            tail.next = head;
            size--;
            return;
        }
        
        do { 
            if(temp.next.val == val){
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
        } while (temp!= head);

        size--;
    
    }

}
