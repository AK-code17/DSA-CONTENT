import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LTC_Combinesum {
    public static void main(String[] args) {
        int []candidates = {2,3,5} ; 
        Arrays.sort(candidates) ; 
        List<Integer> list = new ArrayList<>() ; 
        List<List<Integer>> ans = combine_sum(list, candidates , 8) ;
        for(List<Integer> dhachu: ans){
            System.out.println(dhachu);
        }
    }

    static List<List<Integer>> combine_sum(List<Integer> list,int[] nums , int target){
        if(target == 0){
            List<List<Integer>> tempo = new ArrayList<>() ; 
            tempo.add(list) ; 
            return tempo  ; 
        }

        List<List<Integer>> internal = new ArrayList<>() ; 

        for(int i=0 ; i<nums.length ; i++){
            if(nums[i] <= target){
                List<Integer> andar = new ArrayList<>(list) ;
                andar.add(nums[i]) ; 
                internal.addAll(combine_sum(andar , nums , target-nums[i])) ;
            } 
            else{
                break ; 
            }
        }

        return internal ; 
    }
}
