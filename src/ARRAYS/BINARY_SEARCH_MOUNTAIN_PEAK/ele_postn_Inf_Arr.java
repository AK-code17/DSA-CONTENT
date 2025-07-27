public class ele_postn_Inf_Arr {     //Position of an element in an infinite array . 
    public static void main(String[] args) {
        int[] arr={3,5,7,9,10,90,100,130,140,160,170};
        int target =10;
        System.out.println(ans(arr, target));
    }

    static int ans(int[]arr , int target){
        //first find the range . 
        //first start with the box of start 2 . 
        int start =0;
        int end = 1;

        //condition for the target to lie in the range . 
        while (target>arr[end]) {
            start= end+1;
            //double the box value . 
            //end = previous end + sizeofbox*2 . 
            end = end+start*2;
        }
        return binarysearch(arr, target, start, end);
    }

    static int binarysearch(int[]arr , int target,int start , int end){
        

        while (end>=start) {
            //find the mid element
            // int mid = (start+end)/2; //might be possible that the (start + end) exceeds the range of int in java . 
            int mid = start+(end-start)/2;
            if (target>arr[mid]) {
                start=mid+1;
            }
            else if (target<arr[mid]) {
                end=mid-1;
            }
            else{
                return mid;
            }
        }
        return -1;

    } 
}
