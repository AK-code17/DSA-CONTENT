import java.util.*;

public class Subset_Array {
    public static void main(String[] args) {
        int[] nums = {1,2,3,3} ; 
        List<List<Integer>> ans = subset(nums) ; 
        for(List<Integer> sub: ans){
            System.out.println(sub);
        }
    }

    static List<List<Integer>> subset(int[] arr){
        List<List<Integer>> outer = new ArrayList<>() ; 

        outer.add(new ArrayList<>()) ; //bcz in the dry run we saw that the internal list that we will be creating will have the same no. of subsets that is equal to the size of outer . 
        
        for (int ele : arr) {
            int n = outer.size() ; //storing the number of elements . 
            for (int i = 0; i < n; i++) {
                List<Integer> internal = new ArrayList<>(outer.get(i)); 
                /*it basically copies the subset from starting and add ele , into it and then add it to outer . 

                That's what we do na , in subset take the prev subsets and add our element into it . 
                here one imp point is that we are copying the subset and then adding ele. so we cannot directly say internal = outer.get(i) , since it will refer to the one in the outer and it will modify it .*/
                 
                internal.add(ele) ; 
                outer.add(internal) ;  
            } 
        }

        return outer ; 

    }

}
