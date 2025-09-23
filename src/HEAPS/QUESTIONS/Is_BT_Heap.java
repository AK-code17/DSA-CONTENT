package HEAPS.QUESTIONS;


public class Is_BT_Heap{
    boolean isHeap(Node tree) {
        // code here
        // Write your code here.

		return isCBT(tree) && is_maxH(tree);
    }
    
    boolean isCBT(Node root){
        Queue<Node> q = new LinkedList<>();
		q.offer(root);
		Node prev = root;

		while(!q.isEmpty()){
            Node curr = q.poll();
            if(prev == null && curr!= null) return false;
            
            if(curr != null){
                q.offer(curr.left);
                q.offer(curr.right);   
            }
            
            prev = curr;
            
		}

		return true;
    }
    
    boolean is_maxH(Node root){
        if(root == null) return true;
        
        // left check..
        if(root.left != null && root.left.data>root.data) return false;
        
        // right check..
        if(root.right != null && root.right.data>root.data) return false; 
        
        return is_maxH(root.left) && is_maxH(root.right);
        
    }
}
