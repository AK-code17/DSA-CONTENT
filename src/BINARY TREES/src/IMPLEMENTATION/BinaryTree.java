package IMPLEMENTATION;

import java.util.Scanner;

public class BinaryTree {

    private static class Node {
        int value;
        Node left, right;
        Node(int value) { 
            this.value = value; 
        }
    }

    private Node root;

    public void populate(Scanner s) {
        System.out.print("Root: ");
        root = new Node(s.nextInt());
        s.nextLine(); // consume newline
        populate(s, root);
    }


    private void populate(Scanner s, Node n) {
        System.out.print("Left of " + n.value + "? ");
        if (s.nextLine().equals("y")) {
            System.out.print("Value: ");
            n.left = new Node(s.nextInt());
            s.nextLine(); // consume newline
            populate(s, n.left);
        }

        System.out.print("Right of " + n.value + "? ");
        if (s.nextLine().equals("y")) {
            System.out.print("Value: ");
            n.right = new Node(s.nextInt());
            s.nextLine(); // consume newline
            populate(s, n.right);
        }
    }

    
    public void prettyDisplay() {
        prettyDisplay(root, "", true);
    }

    private void prettyDisplay(Node node, String indent, boolean isLeft) {
        if (node == null) return;

        prettyDisplay(node.right, indent + (isLeft ? "|       " : "        "), false);

        System.out.println(indent + (isLeft ? "|------>" : "|------>") + node.value);

        prettyDisplay(node.left, indent + (isLeft ? "|       " : "        "), true);
    }

    
    public void display() {
        display(root, "");
    }

    private void display(Node n, String indent) {
        if (n == null) return;
        System.out.println(indent + n.value);
        display(n.left, indent + "\t");
        display(n.right, indent + "\t");
    }

    
}