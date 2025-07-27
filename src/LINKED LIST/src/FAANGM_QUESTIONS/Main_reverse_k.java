package FAANGM_QUESTIONS;

public class Main_reverse_k {
    public static void main(String[] args) {
        Reverse_alternate_k list = new Reverse_alternate_k();
        list.insertFirst(1);
        list.insertLast(2);
        list.insertLast(3);
        list.insertLast(4);
        list.insertLast(5);
        // list.insertLast(6);
        // list.insertLast(7);
        // list.insertLast(8);
        // list.insertLast(9);

        list.display();

        list.Reverse_alternate_kgroup(3);

        list.display();
    }
}
