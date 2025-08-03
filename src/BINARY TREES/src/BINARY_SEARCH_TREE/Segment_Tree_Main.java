package BINARY_SEARCH_TREE;

public class Segment_Tree_Main {
    public static void main(String[] args) {
        int[] arr = {3,8,6,7,-2,-8,4,9};
        Segment_Trees tree = new Segment_Trees(arr);
        // tree.display();

        System.out.println(tree.query(1, 6));
    }
}
