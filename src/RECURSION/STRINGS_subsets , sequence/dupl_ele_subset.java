import java.util.*;

public class dupl_ele_subset {
    public static void main(String[] args) {
        int[] nums = {1,2,3,3} ; 
        List<List<Integer>> ans = subset_dupl(nums) ; 
        for(List<Integer> sub: ans){
            System.out.println(sub);
        }
    }

    static List<List<Integer>> subset_dupl(int[] arr){
        List<List<Integer>> outer = new ArrayList<>() ; 

        outer.add(new ArrayList<>()) ;  
        

        
        for (int j=0 ; j<arr.length ; j++) {
            int n = outer.size() ; //storing the number of elements . 
            int min = n;    
            if(j>0 && arr[j] == arr[j-1]){
                min  = (int) (Math.pow(2, j-1)) ; 
            }

            int start = n - min ; 

            for (int i = start ; i < n; i++) {
                
                List<Integer> internal = new ArrayList<>(outer.get(i)); 
                
                internal.add(arr[j]) ; 
                outer.add(internal) ;
            } 
        }

        return outer ; 

    }

    static List<List<Integer>> subset_dupl_gpt(int[] arr) {
        Arrays.sort(arr); // Sort the array to handle duplicates
        List<List<Integer>> outer = new ArrayList<>();
        outer.add(new ArrayList<>());

        int start = 0, end = 0;

        for (int j = 0; j < arr.length; j++) {
            start = 0;

            // If the current element is the same as the previous one, only use subsets added in the previous step
            if (j > 0 && arr[j] == arr[j - 1]) {
                start = end + 1;
            }

            end = outer.size() - 1;
            int n = outer.size();

            for (int i = start; i < n; i++) {
                List<Integer> internal = new ArrayList<>(outer.get(i));
                internal.add(arr[j]);
                outer.add(internal);
            }
        }

        return outer;
    }
}
