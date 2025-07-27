package FAANGM_QUESTIONS;

public class Main2 {
    public static void main(String[] args) {
        // BubbleSort list = new BubbleSort();
        // Reverse_LL list2 = new Reverse_LL();
        Reverse_LL2 list3 = new Reverse_LL2();
        list3.insertFirst(1);
        list3.insertLast(2);
        // list3.insertLast(6);
        // list3.insertLast(5);
        list3.insertLast(3);
        list3.insertLast(4);
        list3.insertLast(5);
        // list3.insertLast(8);

        // System.out.println(list.getsize());

    
        list3.display();

        // list.Bubble_Sort();
        // System.out.println(list2.Reverse_LL());

        list3.Reverse_Left_right(2,4);
        

        list3.display();


    }
}
