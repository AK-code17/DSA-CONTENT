package DoublyLL;

public class Main {
    public static void main(String[] args) {
        DLL list = new DLL();
        list.insertFirst(3, list);
        list.insertFirst(2, list);
        list.insertFirst(8, list);
        list.insertFirst(17, list);
        list.insertLast(1, list);
        list.display(list);

        list.insertNode_withnode(23, 11 , list);
        

        list.display(list);
    }
}
