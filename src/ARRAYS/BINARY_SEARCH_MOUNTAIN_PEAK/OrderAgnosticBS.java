public class OrderAgnosticBS {
    public static void main(String[] args) {
        int[] arr= {-18,12,-4,0,2,3,4,15,16,18,22,45,89};
        int[] arr1={90,75,18,12,6,4,3,1};

        int target=22;
        int ans= OrderAgnosticBS(arr1, target);
        System.out.println(ans);
    }

    static int OrderAgnosticBS(int[]arr , int target){
        int start=0;
        int end=arr.length-1;
        
        //find whether the arrays is sorted ascending or descending . 

        boolean isAsc=arr[start]<arr[end];

        while (end>=start) {
            //find the mid element
            // int mid = (start+end)/2; //might be possible that the (start + end) exceeds the range of int in java . 
            int mid = start+(end-start)/2;
            if (arr[mid]==target) {
                return mid;
            }
            if (isAsc) {         // Asc condition is applied here, bcz if the mid == target , no need to check the condition so time will be saved . 
                if (target>arr[mid]) {
                    start=mid+1;
                }
                else if (target<arr[mid]) {
                    end=mid-1;
                }
            }
            
            else{
                //for array sorted in the descending format . 
                if (target>arr[mid]) {    //just reverse the direction of the arrow  . 
                    end=mid-1;
                    
                }
                else if (target<arr[mid]) {   //just reverse the direction of the arrow . 
                    start=mid+1;
                }
            }
        }
        return -1;
    }
}
