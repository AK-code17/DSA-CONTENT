package BINARY_SEARCH_TREE;

public class Traversals extends BST {
    //all the necessary are already inherited . 

    //PRE-ORDER..
    // FIRST NODE--> LEFT--> RIGHT ...
    public void Pre_Order(){
        System.out.println("PRINTING PRE_ORDER");
        System.out.print("{ ");
        preorder(getRoot());
        System.out.println(" }");
    }

    private void preorder(Node node) {
        //base case : 
        if( node == null)return;

        System.out.print(node.getValue()+",");
        
        preorder(node.getLeft());
        
        preorder(node.getRight());
    }

    //IN-ORDER..
    // FIRST LEFT-->NODE-->RIGHT ...
    public void In_Order(){
        System.out.println("PRINTING IN_ORDER");
        System.out.print("{ ");
        inorder(getRoot());
        System.out.println(" }");
    }

    private void inorder(Node node) {
        //base case : 
        if( node == null)return;

        inorder(node.getLeft());

        System.out.print(node.getValue()+",");
        
        inorder(node.getRight());
    }

    //POST-ORDER..
    // FIRST LEFT--> RIGHT--->NODE ...
    public void Post_Order(){
        System.out.println("PRINTING POST_ORDER");
        System.out.print("{ ");
        postorder(getRoot());
        System.out.println(" }");
    }

    private void postorder(Node node) {
        //base case : 
        if( node == null)return;

        postorder(node.getLeft());

        postorder(node.getRight());

        System.out.print(node.getValue()+",");
    }

}
