public class FindMin {
    public static void main(String[] args) {
        int[] arr = {18,12,-7,3,14,28};
        // System.out.println(max(arr));
        System.out.println("The min element in the array is "+min(arr));
    
    }
    
    static int min(int[] arr){
        int min_ele = arr[0];
        for (int ele : arr) {
            if (ele<min_ele) {
                min_ele=ele;
            }
        }
        return min_ele;
    }
}
