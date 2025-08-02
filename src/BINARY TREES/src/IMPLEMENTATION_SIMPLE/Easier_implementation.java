package IMPLEMENTATION_SIMPLE;

public class Easier_implementation {
    private static class Node {
        int value;
        Node left, right;
        Node(int value) { 
            this.value = value; 
        }
    }

    private Node root;

    public void setRoot(int val) {
        root = new Node(val);
    }

    public void insert(int parentVal, int childVal, boolean isLeft) {
        Node parent = findNode(root, parentVal);
        if (parent == null) {
            System.out.println("Parent not found: " + parentVal);
            return;
        }
        if (isLeft) {
            parent.left = new Node(childVal);
        } else {
            parent.right = new Node(childVal);
        }
    }

    private Node findNode(Node current, int value) {
        if (current == null) return null;
        if (current.value == value) return current;
        Node leftSearch = findNode(current.left, value);
        if (leftSearch != null) return leftSearch;
        return findNode(current.right, value);
    }


}
