import java.util.Arrays;

public class Quick_Sort {
    public static void main(String[] args) {
        int[] arr = {4,8,3,12,5,6}  ;
        Quick_Sort(arr , 0 , arr.length-1) ; 
        System.out.println(Arrays.toString(arr));
    }

    static void Quick_Sort(int[] nums , int low , int hi ){
        if(low >= hi ) return ; 
        
        int s = low ; 
        int e = hi ; 

        int mid = s + (e-s)/2 ;
        int pivot = nums[mid] ; //basically , a reference point , having the left part smaller than it and right part bigger than self . 

        while(s <= e){

            //also a reason why if its alreasy sorted it won't sort .  , s
            while (nums[s] < pivot) {
                s++ ;  //hame value small chahiye left mai , jab tak hai increment . 
            }

            while (nums[e] > pivot) {
                e-- ; //jaha value small hogyi  , toh start ke element ke saath swap krdo . 
            }

            if(s <= e ){   //again checking the base cond. bcz , it might be possible that start and end may cross each other , indicating the pivot is already at its place  . 
                int temp = nums[e] ; 
                nums[e] = nums[s] ; 
                nums[s] = temp ; 
                s++ ; 
                e-- ; 
            }

        }

        //now my pivot is at correct index , now sort the 2 halves . 

        Quick_Sort(nums, low, e);
        Quick_Sort(nums, s, hi);

        
    }
}
