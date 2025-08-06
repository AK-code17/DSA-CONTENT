package IN_LECTURE_QUESTIONS.BFS_RELATED;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LTC_103_ZIG_ZAG {
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

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();

        if(root == null) return ans;

        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        boolean rev = false;
        deque.add(root);

        while(!deque.isEmpty()){   
            List<Integer> list = new ArrayList<>();
            int list_size = deque.size();
            for(int i=0  ;i<list_size ; i++){
                if(!rev){
                    TreeNode node = deque.pollFirst();
                    list.add(node.val);
                    if(node.left!=null) deque.offer(node.left);
                    if(node.right!=null) deque.offer(node.right);                }
                
                else{
                    TreeNode node = deque.pollLast();
                    list.add(node.val);
                    if(node.right!=null) deque.offerFirst(node.right);
                    if(node.left!=null) deque.offerFirst(node.left);
                }
            }

            ans.add(list);

            rev = !rev;
        }

        return ans;

    }
}
