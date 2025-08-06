package IN_LECTURE_QUESTIONS.BFS_RELATED;

import BINARY_SEARCH_TREE.AVL;

/*
APPROACH : JUST SEE WHAT 'S NEEDED , simply every nodes at each level are connected via next pointers , so connect firstly : 

use simple BFS Apprroach , (refer the comment out code) : at each level we know its size , so for every i till i == size-2 connect node.next---> queue.peek() , if it reaches end we no need to make it next null , by default its null . 

APPROACH 2 : 
PURELY BASED ON OBSERVATION ..
Using Recursion as mentioned in the FOLLOW UP : just pass (node , node.left , node.right) : 
check if left==null , if yes then simply return , 
if its not null then its sure that it has a right sibling , connect siblings , left.next--> right . 

now the right .next -->first cousin , ie, left child of its parent's sibling , 
if(node.next !=null) then there has to be children , so right.next = node.next.left ;  

After this make a call to left and right childs as parent nodes ..

APPROACH_3 : Space Complextity O(1) ...
Simply we need to traverse each level and make connections and move to next level , 
so to move to next level , we will use Node left_most = leftmost.left ; if u see it diagrammatically then leftmost.left will keep on giving the starting node of each level , so since now we got the first node at each level just traverse that level and add connections , so Node curr = leftmost; and make its left-->right and right--> curr.next.left , 

so done made its connections , now move curr = curr.next ,  till its null . 
A PROPER DRY RUN WILL HELP MORE BETTER TO UNDERSTAND IT..

*/

public class LTC_116_Connect_Next_Right_Pointer {
    
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}
    
        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
    
    // Approach_1 : General BFS SOLN ..
    public Node brute_queue(Node node) {
        if(node == null) return node; 
        if(node == null) return node;

        Queue<Node> q = new LinkedList<>() ; 
        q.offer(node);

        while(!q.isEmpty()){
            int size = q.size() ; 
            for(int i=0 ; i<size ; i++){
                Node curr_node = q.poll() ;
                if(i != size-1) curr_node.next = q.peek() ;
                //left
                if(curr_node.left != null) q.add(curr_node.left);
                //right
                if(curr_node.right != null) q.add(curr_node.right);
            }
        }

        return node;        
    }

    // Approach 2 : 
    static Node better_recursive(Node root , Node left , Node right){
        if(left == null) return left;

        left.next = right;

        if(node.next != null){
            right.next = root.next.left;
        }

        //left call..
        better_recursive(left , left.left , left.right);
        //right call..
        better_recursive(right,right.left , right.right);

        return root;

    }

    //Approach_3 : 
    static Node optimal_iterative(Node root){
        // APPROACH_3 : Space Complexity O(1) : 

        Node leftmost = root;
        // Outer Loop for traversing each level..
        while(leftmost.left !=null){
            //Inner loop is for traversing each at each level
            Node curr = leftmost ; 
            while(curr != null){
                curr.left.next = curr.right;
                if(curr.next != null){
                    curr.right.next = curr.next.left;
                }
                curr = curr.next;
            }

            leftmost = leftmost.left;
        }

        return root;
    }
}
