public class SearchInMountainArray {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,3,1};
        // System.out.println("The max element in the array is "+peak_in_Mntn_Arr(arr));
        int peak_index = peakIndexInMountainArray(arr);
        // System.out.println("The peak index of mountain array is "+peak_index);
        int target = 3;
        System.out.println(SearchInMountainArray(arr, target, peak_index));
        // System.out.println(result);
        


    }

    static int SearchInMountainArray(int [] arr, int target,int peak_index){
        int result = binarysearch(arr, target,0,peak_index);
        if (result!=-1) {
            return result;
        }
        return binarysearch(arr, target, peak_index+1, arr.length-1);
    }
    
    static int peakIndexInMountainArray(int[] arr){
        int start=0;
        int end=arr.length-1;
        // int peak=0;

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


    static int binarysearch(int[]arr , int target,int start ,int end){
        // int start=0;/
        // int end=peak_index;
        // int newmid= peakIndexInMountainArray(arr);

        while (end>=start) {
            //find the mid element
            int mid = (start+end)/2; //might be possible that the (start + end) exceeds the range of int in java . 
            // int mid = end;
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
