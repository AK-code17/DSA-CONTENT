import java.util.*;

public class Cycle_Sort {
    public static void main(String[] args) {
        int[] arr= {5,4,3,2,1};
        Cycle_Sort(arr);
        System.out.println(Arrays.toString(arr));
        
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
