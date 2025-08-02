package BINARY_SEARCH_TREE;

public class AVL {
    public class Node {
        private int value;
        private Node left;
        private Node right;
        private int height;
        
        public Node(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    private Node root;
    
    public AVL() {
    }
    
    public int height() {
        return height(root);
    }
    
    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }
    
    public void insert(int value) {
        root = insert(value, root);
    }
    
    private Node insert(int value, Node node) {
        // Base case: create new node
        if (node == null) {
            node = new Node(value);
            return node;
        }
        
        // Insert in left subtree
        if (value < node.value) {
            node.left = insert(value, node.left);
        }
        
        // Insert in right subtree
        if (value > node.value) {
            node.right = insert(value, node.right);
        }
        
        // If value == node.value, we don't insert (no duplicates)
        // But we still update height and check for rotations
        
        // Update height of current node
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        
        // Check if rotations are needed and perform them , else rotate(node) will return the same node . 
        return rotate(node);
    }
    
    private Node rotate(Node node) {
    
        // if this is > 1 it means we have more no. of nodes in the left , hence make it balanced
        if (height(node.left) - height(node.right) > 1) {
            // Left heavy case
            
            // Check if it's Left-Left case or Left-Right case
            //in the height(node.left) we have 2 options either its left child has more heigth then, its LL
            if (height(node.left.left) - height(node.left.right) > 0) {
                // Left-Left case: do right rotation
                return rightRotate(node);
            }
            
            //else node.left.right has more num on nodes then its L-R
            if (height(node.left.left) - height(node.left.right) < 0) {
                // Left-Right case: first left rotate on left child, then right rotate
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        
        // if this is > 1 it means we have more no. of nodes in the right , hence make it balanced
        if (height(node.left) - height(node.right) < -1) {
            // Right heavy case
            
            // Check if it's Right-Right case or Right-Left case
            if (height(node.right.left) - height(node.right.right) < 0) {
                // Right-Right case: do left rotation
                return leftRotate(node);
            }
            
            if (height(node.right.left) - height(node.right.right) > 0) {
                // Right-Left case: first right rotate on right child, then left rotate
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        
        // If balanced, return the same node
        return node;
    }
    
    //right Rotate and LeftRotate are pretty simpple and starightforward similar to what we used to do in Linked Lists..
    public Node rightRotate(Node parent) {
        Node child = parent.left;
        Node right_child = child.right;
        
        // Perform rotation
        parent.left = right_child;
        child.right = parent;
        
        // Update heights (order matters: update parent first, then child)
        parent.height = Math.max(height(parent.left), height(parent.right)) + 1;
        child.height = Math.max(height(child.left), height(child.right)) + 1;
        
        // Return new root of this subtree
        return child;
    }
    
    public Node leftRotate(Node parent) {
        Node child = parent.right;
        Node left_child = child.left;
        
        // Perform rotation
        parent.right = left_child;
        child.left = parent;
        
        // Update heights (order matters: update parent first, then parent)
        parent.height = Math.max(height(parent.left), height(parent.right)) + 1;
        child.height = Math.max(height(child.left), height(child.right)) + 1;
        
        // Return new root of this subtree
        return child;
    }
    
    public boolean balanced() {
        return balanced(root);
    }
    
    private boolean balanced(Node node) {
        if (node == null) {
            return true;
        }
        return Math.abs(height(node.left) - height(node.right)) <= 1 
               && balanced(node.left) 
               && balanced(node.right);
    }
    
    public void display() {
        display(this.root, "Root Node: ");
    }
    
    private void display(Node node, String details) {
        if (node == null) {
            return;
        }
        System.out.println(details + node.value);
        display(node.left, "Left child of " + node.value + " : ");
        display(node.right, "Right child of " + node.value + " : ");
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
}