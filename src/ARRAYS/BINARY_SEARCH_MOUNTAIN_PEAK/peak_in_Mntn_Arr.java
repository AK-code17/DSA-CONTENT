public class peak_in_Mntn_Arr {
    public static void main(String[] args) {
        int[] arr = {3,5,3,2,0};
        // System.out.println("The max element in the array is "+peak_in_Mntn_Arr(arr));
        int peak_index = peakIndexInMountainArray(arr);
        System.out.println("The peak index of mountain array is "+peak_index);
    
    }
    
    static int peakIndexInMountainArray(int[] arr){
        int start=0;
        int end=arr.length-1;
        int peak=0;

        while (end>=start) {
            //find the mid element
            // int mid = (start+end)/2; //might be possible that the (start + end) exceeds the range of int in java . 
            int mid = start+(end-start)/2;
            if (arr[mid]<arr[mid+1]) {
                start=mid+1;
            }
            else if (arr[mid]<arr[mid-1]) {
                end=mid-1;
            }
            else{
                //possible answer
                return mid;
            }
            
        }
        return -1;

    }
}
