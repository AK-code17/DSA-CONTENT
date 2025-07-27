public class Interview_Questions {
    public static void main(String[] args) {
        int[]arr={2,3,5,9,14,16,18};
        int target = 15;
        // System.out.println("The Ceiling of "+target+" is "+Ceiling(arr, target));
        System.out.println("The Floor of "+target+" is "+Floor(arr, target));
    }

    static int Ceiling(int[]arr,int target){
        int start=0;
        int end = arr.length-1;
        
        //With Binary Search . 
        while (start<=end) {
            
            int mid = start+(end-start)/2;
            if (arr[mid]==target) {
                return arr[mid];
            }
            if (target<arr[mid]) {
                end=mid-1;
            }
            else{
                start=mid+1;
            }
            // if (arr[mid]>target && arr[mid]<arr[mid+1]) {
            //     return arr[mid];
            // }
        }
        
        
        //Answer through brute force or Linear Search / 
        // for (int i : arr) {
        //     if (i==target) {
        //         return i;
        //     }
        //     if (i>target) {
        //         return i;
        //     }
        // }

        return arr[start];

    }


    static int Floor(int[]arr,int target){
        int start=0;
        int end = arr.length-1;
        
        //With Binary Search . 
        while (start<=end) {
            
            int mid = start+(end-start)/2;
            if (arr[mid]==target) {
                return arr[mid];
            }
            if (target<arr[mid]) {
                end=mid-1;
            }
            else{
                start=mid+1;
            }
            // if (arr[mid]<target && arr[mid]>arr[mid+1]) {
            //     return arr[mid];
            // }
        }
        

        //Answer through brute force or Linear Search / 
        // for (int i : arr) {
        //     if (i==target) {
        //         return i;
        //     }
        //     if (i>target) {
        //         return i;
        //     }
        // }

        return arr[end];

    }



}
