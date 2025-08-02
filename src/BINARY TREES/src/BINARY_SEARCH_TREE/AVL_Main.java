package BINARY_SEARCH_TREE;

public class AVL_Main {
    public static void main(String[] args) {
        AVL tree = new AVL();
        for (int i = 0; i < 1000; i++) {
            tree.insert(i);
        }

        System.out.println("Height of tree "+tree.height());
        System.out.println("Is balanced: " + tree.balanced());
    }
}
