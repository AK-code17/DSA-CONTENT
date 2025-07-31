package BINARY_SEARCH_TREE;

public class BST {
    public class Node{
        private int value;
        private int height;
        private Node left;
        private Node right;
        
        public Node (int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }

    }

    private Node root;

    public BST(){

    }

    public int height(Node node){
        if(node == null) return -1;
        return node.height;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public void insert(int value){
        root = insert(root,value);
    }

    public void insert_multiple(int[] nums){
        for(int ele : nums){
            insert(ele);
        }
    }
    

    private Node insert(Node node, int value) {
        if(node == null){
            node = new Node(value);
            return node;
        }

        else if(value < node.value){
            node.left = insert(node.left,value);
        }

        else{
            node.right = insert(node.right, value);
        }
        
        // since a new node is added hence the height is increased by 1 . ..
        node.height = Math.max(height(node.left), height(node.right));

        return node;
    }


    public boolean balanced(){
        return balanced(root);
    }

    private boolean balanced(Node node) {
        if(node == null) return true;
        int diff  = Math.abs(height(node.left) - height(node.right));
        return (diff <=1) && balanced(node.left) && balanced(node.right);
    }

    public void display(){
        display(this.root , "Root node : ");
    }

    private void display(Node node, String details){
        if (node == null) {
            return;
        }

        System.out.println(details + node.value);


        display(node.left, "is Left child of " + node.getValue() + " : ");
        display(node.right, "is Right child of " + node.getValue() + " : ");
    }

}
