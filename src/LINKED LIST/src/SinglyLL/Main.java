package SinglyLL;

import org.w3c.dom.Node;

public class Main {
    public static void main(String[] args) {
        LL list = new LL();
        list.insertFirst(3);
        list.insertFirst(2);
        list.insertFirst(8);
        list.insertFirst(17);
        list.insertLast(99);
        list.insertIndex(5, 100);
        list.insertIndex(4, 45);
        // System.out.println(list.deleteFirst());
        list.insert_Rec(11, 1);
        list.displayLL(list);
        // System.out.println(list.deleteLast(list) + " is the deleted item");
        // System.out.println(list.deleteInd(1, list) + " is the deleted item at ind "+1);

        
        // list.displayLL(list);
    }
}
