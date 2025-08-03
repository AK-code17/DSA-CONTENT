package BINARY_SEARCH_TREE;

public class Segment_Trees {

    private static class Node{
        int data;//sum
        int start;
        int end;
        Node left;
        Node right;

        public Node(int start , int end){
            this.start = start;
            this.end = end;
        }
    }

    Node root;

    public Segment_Trees(int[] arr){
        //create a tree using this arr..
        this.root = constructTree(arr,0,arr.length-1); 
    }

    private Node constructTree(int[] arr, int start, int end) {
        
        //base case: when it reaches at end..
        if(start == end){
            //leaf node
            Node leaf = new Node(start, end);
            leaf.data = arr[start];
            return leaf;
        }

        //Create node..
        Node node = new Node(start, end);
        
        //first move and form left and right nodess.
        int mid = (start+end)/2;
        node.left = constructTree(arr, start, mid);
        node.right = constructTree(arr, mid+1, end);
        
        //then put the data into it , since now left and right are coming back , they will have values . 
        node.data = node.left.data + node.right.data;
        
        return node;
    }

    public void display(){
        display(this.root);
    }

    private void display(Node node){
        String str = "";

        //for left node...
        if(node.left != null){
            str = str + "Interval=[" + node.left.start + "-" + node.left.end + "] and data: " + node.left.data + " => ";
        }else{
            str = str + "No left child" ; 
        }

        //for current node
        str = str + "Interval=[" + node.start + "-" + node.end + "] and data: "+ node.data + " <= "; 
        
        //for right node...
        if(node.right != null){
            str = str + "Interval=[" + node.right.start + "-" + node.right.end + "] and data: " + node.right.data;
        }else{
            str = str + "No right child" ; 
        }


        System.out.println(str+'\n');

        //call recursion 
        if(node.left != null) display(node.left);
        if(node.right != null ) display(node.right);
    }


    //QUery_Code : for addition here , for any other operation u can use that ..
    public int query(int q_start , int q_end){
        return this.query(this.root, q_start,q_end);
    }

    private int query(Node node, int q_start, int q_end) {
        //Case 1: where the range lies inside the query...
        if(node.start >= q_start && node.end <= q_end){
            return node.data; //then just return the data..
        }

        //Case 2 : where the indices are lying outside my query..
        else if(node.start > q_end  || q_start >node.end) return 0;

        //Case 3 : when range is overlapping with the query then we can make left and right calls ,since we are unsure about range and a part of our ans lies in that query..
        else {

            return query(node.left, q_start, q_end) + query(node.right, q_start, q_end);
        }
        
    }

    //UPDATION : 

    public void update(int index , int val){
        
        this.root.data = update(root,index,val);
    }

    private int update(Node node , int ind , int val){
        //base case: i.e, when it reaches the leaf node..
        if(ind == node.start && ind == node.end){
            node.data = val;
            return val;
        }

        //else if the index lies between node.start and node.end , then do left and right call..
        if(node.start <= ind && node.end >= ind){
            int left_ans = update(node.left,ind,val);
            int right_ans = update(node.right,ind,val);

            node.data = left_ans + right_ans;
            return node.data;
        }

        //or else its out of bound hence just return the current node.data ; 
        return node.data;
    }
}
