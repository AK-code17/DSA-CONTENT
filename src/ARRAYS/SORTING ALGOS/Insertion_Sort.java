import java.util.Arrays;

public class Insertion_Sort {
    public static void main(String[] args) {
        int[] arr= {-3,1,0,-42,2};
        Insertion_Sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    static void Insertion_Sort(int[] arr){
        for (int i = 0; i <= arr.length-2; i++) { //arr.length-2 bcs when i==4 j will be i+1 i.e 5 so it will be out of bound ans also till i==3 the array will be sorted till the it position . 
            for (int j = i+1; j > 0; j--) {
               if (arr[j]<arr[j-1]){
                //swap the lowest with the largest . 
                int temp=arr[j-1];
                arr[j-1]=arr[j];
                arr[j]=temp;   
               }
               else{
                break;
               }
            }
        }
    }
}
