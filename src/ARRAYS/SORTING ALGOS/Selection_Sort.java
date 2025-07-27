import java.util.*;

public class Selection_Sort {
    public static void main(String[] args) {
        int[] arr= {-3,1,0,-42,2};
        Selection_Sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    static void Selection_Sort(int[]arr){
          // Assume the first element is the minimum initially . 
        for (int i = 0; i < arr.length; i++) {  
            //find the min item in the remaining array and swap with correct index . 
            int start_ind = i;  //for the 1st pass the start_ind is the lst position , for the 2nd pass  , start_ind= 2nd start_ind position . 
            int min_ind = getminInd(arr,start_ind,arr.length-1); 
            swap(arr,min_ind,start_ind);
        }
    }

    static int getminInd(int[]arr,int start,int end){
        int min = start;
        for (int j = start; j <= end; j++) {
            if (arr[min]>arr[j]) {
                min=j;
            }
        }
        return min;
    }

    static void swap(int[]arr,int first , int second){
        int temp=arr[first];
        arr[first]=arr[second];
        arr[second]=temp;
    }
}
