// package BINARY TREES;
import java.util.*;

public class GFG_Diagonal_Traversal {

    // APPROACH 1: RECURSIVE AND HASHMAP... (BETTER)

/**
 * PROBLEM: DIAGONAL TRAVERSAL OF BINARY TREE
 * * GOAL: 
 * Group and collect tree nodes that lie on the same diagonal. A diagonal is formed 
 * by a continuous path of right children; a left child marks the start of a new diagonal.
 * * INTUITION: 
 * We use a "Slope" or "Distance" ID to identify diagonals. When we move to a 
 * right child, we stay on the same slope (ID stays same). When we move to a 
 * left child, we move to a new diagonal (slope increases by 1). By using a Map 
 * to store these slope IDs, we can group nodes and then print them in sequence.
 */
    class Node{
        int data;
        Node left;
        Node right;

        Node(int val){
            this.data = val;
        }
    }
    public ArrayList<Integer> diagonal(Node root) {
        // add your code here.
        
        // Use TreeMap to ensure slopes are processed in numerical order (0, 1, 2...)
        Map<Integer,List<Integer>> map = new TreeMap<>();
        ArrayList<Integer> ans = new ArrayList<>();
        
        find_diagonals(root,0,map);
        
        // get the total cnt of diagonals...
        int diagonals = map.size();
        
        // Keep adding the list of digonals.. sequentially..
        for(int slope=0 ; slope<diagonals ; slope++){
            // Ensure we only try to get if the slope exists in the map
            if(map.containsKey(slope)) {
                ans.addAll(map.get(slope));    
            }
        }
        
        return ans;
    }
    
    private void find_diagonals(Node curr_node , int slope , Map<Integer,List<Integer>> map){
        
        if(curr_node == null) return;
        
        // If List is absent it creates one && add the curr node to its slope list
        map.computeIfAbsent(slope , k -> new ArrayList<>()).add(curr_node.data);
        
        // move to left sub-tree because in the condition given its asked to print the left nodes first...
        // slope + 1 , because the left child creates a new diagonal..
        find_diagonals(curr_node.left , slope+1 , map);
        
        // keep adding the right child to the same slope..
        find_diagonals(curr_node.right , slope , map);
        
    }

    // APPROACH 2 : ITERATIVE (OPTIMISED)..
    /**
     * PROBLEM: DIAGONAL TRAVERSAL (OPTIMIZED QUEUE)
     * * GOAL: 
     * Achieve the most memory-efficient and fastest diagonal traversal by eliminating 
     * auxiliary data structures like Maps or wrapper classes.
     * * INTUITION: 
     * We recognize that a diagonal is simply a "right-sliding" path. We use a Queue to 
     * keep track of the "seeds" (left children) for the next diagonals. By processing 
     * the current node's right path completely before moving to the next item in the 
     * queue, we naturally maintain the top-to-bottom, top-right to bottom-left order 
     * required for the output.
     */

    public ArrayList<Integer> diagonal_optimised(Node root) {
        // add your code here.
        ArrayList<Integer> ans = new ArrayList<>();
        if (root == null) return ans;
        
        Deque<Node> que = new ArrayDeque<>();

        que.offerLast(root);
        
        while(!que.isEmpty()){
            Node curr_node = que.pollFirst();
            
            // Keep going to right because all the nodes in same slope lies on node.right..
            while(curr_node != null){
                ans.add(curr_node.data);
                // if left is not null add it because it will help for the next 
                // diagonal to traverse.. and also the left subtree comes before 
                // the right sub-tree...
                if(curr_node.left != null) que.offerLast(curr_node.left);
                
                curr_node = curr_node.right;
            }
        }

        return ans;
    }
}