import java.util.*;

public class Selection_Sort {
    
    public static void main(String[] args) {
        int[] arr = {3,1,5,4,2};
        System.out.println(Arrays.toString(arr));
        Selection_Sort(arr, arr.length-1, 0);
        System.out.println(Arrays.toString(arr));
    }

    static void Selection_Sort(int[] nums , int row  , int coln){
        //Checking the base contiton
        if (row==0) {
            return ;
        }
        if (coln < row) {
            Selection_Sort(nums , row, coln+1);
            int min_index = getminInd(nums , coln, row);
            swap(nums, coln, min_index);
        }
        else {
            Selection_Sort(nums , row-1, 0);
        }
    }


    static int getminInd(int[]arr,int start, int end){
        int min = start;
        for (int j = start; j <=end; j++) {
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
