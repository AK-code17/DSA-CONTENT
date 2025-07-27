public class Lin_search_Algo {
    
    static int linearsearch(int[]arr, int target){
        if (arr.length==0) {
            return -1;
        }

        //run a for loop

        for (int index = 0; index < arr.length; index++) {
            //check for element at every index if it is == target .
            if (arr[index]==target) {
                return index;
            }
        }

        //this line will execute if none of the above return statement executed . 
        return -1;

    }
    
    public static void main(String[] args) {
         int[] nums = {23,45,1,2,8,19,-3,16,-11,28};
         int target = 69;
         int ans = linearsearch(nums,target);
         System.out.println(ans);
    }

    // search in the array: return the index if the item found . 
    //otherwise if item nto found return -1 . 
    
}
