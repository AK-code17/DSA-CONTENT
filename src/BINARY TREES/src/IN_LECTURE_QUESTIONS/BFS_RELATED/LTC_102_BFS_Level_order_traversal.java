package IN_LECTURE_QUESTIONS;

import java.util.*;

// import javax.swing.tree.TreeNode;

public class LTC_102_BFS_Level_order_traversal {
    
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


    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        
        //base case: or edge case u can say..
        if(root == null) return ans;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int list_size = queue.size();
            List<Integer> list = new ArrayList<>(list_size);
            for (int i = 0; i < list_size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if(node.left !=null) queue.offer(node.left);
                if(node.right !=null)queue.offer(node.right);
            }

            ans.addFirst(list);
        } 

        return ans; 
    
    }


    public TreeNode successor(TreeNode root , int key) {
        //base case: or edge case u can say..
        if(root == null) return root;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int list_size = queue.size();
            TreeNode node = queue.poll();
            if(node.left !=null) queue.offer(node.left);
            if(node.right !=null)queue.offer(node.right);
            if(node.val == key) return queue.poll();
        }

        return null; 
    
    }
}
