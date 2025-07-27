package FAANGM_QUESTIONS;

public class Main3 {
    public static void main(String[] args) {
        Reorder_LL list4 = new Reorder_LL();
        list4.insertFirst(1);
        list4.insertLast(2);
        list4.insertLast(3);
        list4.insertLast(4);
        list4.insertLast(5);

        list4.display();

        list4.reorderList();

        list4.display();
    
    }
}
