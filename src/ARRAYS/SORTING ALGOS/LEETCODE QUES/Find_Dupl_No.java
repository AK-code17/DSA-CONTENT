public class Find_Dupl_No {
    public static void main(String[] args) {
        int[] nums={1};
        System.out.println(findDuplicate(nums));
    }

    //FULL self solved question 
    //https://leetcode.com/problems/find-the-duplicate-number/description/
    //AMAZON QUESTION . 

    
    
    static int findDuplicate(int[] nums){
        Cycle_Sort(nums);
        int dupl=-1;
        if(nums.length==0 || nums.length==1){
            return dupl;
        }

        for (int index = 0; index < nums.length; index++) {
            if ((nums[index]==nums[0]) && (nums[index]==nums[nums.length-1])) {
                return nums[index];
            }
            
            if (index!=nums[index]-1) {
                dupl=nums[index];
                return dupl;
            }
            
        }

        return dupl;
    }

    static void Cycle_Sort(int[]arr){
        int i=0;
        while (i<arr.length) {
            int correct_ind = arr[i]-1;  //to specify what;s the current index is . 
            if (arr[i]!=arr[correct_ind]) {
                swap(arr, i,correct_ind);
            }
            else{
                i++;
            }
        }
    }

    static void swap(int[]arr,int first , int second){
        int temp=arr[first];
        arr[first]=arr[second];
        arr[second]=temp;
    }
}
