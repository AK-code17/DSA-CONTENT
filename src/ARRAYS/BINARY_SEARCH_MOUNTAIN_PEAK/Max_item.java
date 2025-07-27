public class Max_item {
    public static void main(String[] args) {
        int[] arr = {1,3,23,9,18};
        // System.out.println(max(arr));
        System.out.println("The max element in the array is "+max(arr));
    
    }
    
    static int max(int[] arr){
        int max_ele = arr[0];
        for (int ele : arr) {
            if (ele>max_ele) {
                max_ele=ele;
            }
        }
        return max_ele;
    }
}
