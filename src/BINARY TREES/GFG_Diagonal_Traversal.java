package BINARY TREES;
import java.util.*;

public class GFG_Diagonal_Traversal {

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
}
