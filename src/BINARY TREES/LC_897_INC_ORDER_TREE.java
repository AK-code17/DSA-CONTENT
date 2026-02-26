package BINARY TREES;

public class LC_897_INC_ORDER_TREE {
    /**
 * PROBLEM: INCREASING ORDER SEARCH TREE
 * * GOAL: 
 * Rearrange the Binary Search Tree (BST) into a "right-skewed" tree (like a Singly Linked List) 
 * such that the nodes follow the In-order sequence. The final tree must have no left children.
 * * INTUITION: 
 * While a simple In-order traversal using a List or recursion takes O(H) or O(N) space, 
 * Morris Traversal allows us to achieve O(1) space. By "threading" the predecessor's 
 * right pointer to the current node, we can navigate the tree without a stack. 
 * Since the requirement is to rearrange the tree, we proactively prune the left 
 * pointers (curr.left = null) as we traverse, transforming the BST into a linked 
 * list in-place while recycling existing nodes.
 */

    
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
 

    public TreeNode increasingBST(TreeNode root) {
        return Morris_Traversal(root);
    }

    private TreeNode Morris_Traversal(TreeNode root){
        TreeNode curr = root;

        TreeNode dummy = new TreeNode(-1);
        TreeNode modified_root = dummy;

        while(curr != null){
            if(curr.left == null){

                // if at any time curr.left is null connect the dummy to curr node and move it..
                // dummy will be always pointing to prev node in IN-ORDER BST.. 
                // CHECK USING PEN N PAPER...
                dummy.right = curr;
                dummy = dummy.right;
                curr = curr.right;
                
            }

            // if element is there on the left , so go to its right most value and connect that to curr node , so that we can easily reach back to the curr node after the traversal , and we do not need to retain or store the curr value..
            else{
                TreeNode right_most = curr.left;
                while(right_most.right != null && right_most.right != curr){
                    right_most = right_most.right;
                }

                if(right_most.right == null){
                    // connect to curr node;
                    right_most.right = curr;
                    // first shift it to its left and then //|
                    curr = curr.left;                   //   |
                                                        //   ^
                    // BREAK THE CONNECTION FROM CURR TO CURR.LEFT , BECAUSE WE NEED TO FORM THE TREE INTO SINGLY LL CONTAINING ONLY RIGHT POINTER , AND IF WE DONOT REMOVE IT THEN , IT WILL BE A CYCLE...
                    right_most.right.left = null;
                }
                
            }
        }

        return modified_root.right;
    }
}
