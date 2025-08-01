package BINARY_SEARCH_TREE;

public class Main {
    public static void main(String[] args) {
        // BST tree = new BST();
        // int[] nums = {1,2,3,4,5,6,7,8,9,10};
        // tree.insert_multiple(nums);
        // tree.balanced();

        Traversals tree = new Traversals();
        int[] arr = {10,20,5,8,3,2};
        tree.insert_multiple(arr);
        // tree.display();
        tree.Pre_Order();
        tree.In_Order();
        tree.Post_Order();
    }
}
