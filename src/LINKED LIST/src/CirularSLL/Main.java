package CirularSLL;

public class Main {
    public static void main(String[] args) {
        CLL list = new CLL();
        list.insertFirst(23);
        list.insertFirst(3);
        list.insertFirst(19);
        list.insertFirst(75);

        list.display();

        list.delete_val(19);

        list.display();
    }
}
