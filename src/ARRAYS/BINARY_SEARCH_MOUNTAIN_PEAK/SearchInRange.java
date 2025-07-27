public class SearchInRange {
    
    static int search_range(int[]arr, int target,int start,int end ){
        if (arr.length==0) {
            return -1;
        }

        //run a for loop

        for (int i = start; i <= end; i++) {
            //check for element at every i if it is == target .
            if (arr[i]==target) {
                return i;
            }
        }

        //this line will execute if none of the above return statement executed . 
        return -1;

    }
    public static void main(String[] args) {
        int[] nums = {18,12,-7,3,14,28};
        int target=146;
        
        
        System.out.println(search_range(nums,target,1,4));

    }
}
