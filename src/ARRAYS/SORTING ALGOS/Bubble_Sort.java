import java.util.Arrays;

public class Bubble_sort {
    public static void main(String[] args) {
        int[] arr={3,1,5,4,2};
        Bubble_Sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    static void Bubble_Sort(int[]arr){
        boolean swapped;   //if the array is already sorted it will not check again  , like for the first pass if not swapped hence the array is sorted  , the swapped variable will not change  .
        //run the steps in n-1 types.
        for (int i = 0; i < arr.length; i++) {
            swapped=false;
            //for each step max item will come at the last respsective index . 
            for (int j = 1; j < arr.length-i; j++) {
                //swap if the item is smaller than the previous iutem . 
                if (arr[j]<arr[j-1]) {
                    //swap  the smalleest with the largest . 
                    int temp = arr[j];
                    arr[j]=arr[j-1];
                    arr[j-1]=temp;
                    swapped=true;
                }
            }
            //break since , the array is sorted hence stop the porgram . 
            if (!swapped) {  //if swapped == false  . 
                break;
            }
        }
    }

}
