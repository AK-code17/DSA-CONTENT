package FAANGM_QUESTIONS;

public class Main {
    public static void main(String[] args) {
        CycleQuestions list = new CycleQuestions();
        list.insertFirst(-1);
        list.insertLast(10);
        list.insertLast(1);
        list.insertLast(2);
        list.insertLast(3);
        list.insertLast(4);        
    
        list.create_cycle(2);

        list.displayLL();

        System.out.println("The result of this linked list to be cycle is "+ list.check_Cycle());
        System.out.println("The length of this linked list is "+ list.len_Cycle());
        System.out.println("The starting node of the cycle is "+ list.start_node_cycle());

    }
}
