import java.util.Arrays;

public class Bin_search_2DArray {
    public static void main(String[] args) {
        int[][]matrix={{10,20,30,40},
                        {15,25,35,45},
                        {28,29,37,49},
                        {33,34,38,50}};
        
        //For th above matrix , we will use different approach since its strictly sorted . It will work for this approach but time will be more .               

        int[][]arr={{1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,16}};
        int target=15;
        // int ans=Bin_search_2DArraysearch_2DArray(matrix, target);
        // if (ans==-1) {
        //     System.out.println("Element not found");
        // }
        // else{
        //     System.out.println("Element found");
        // }
    int[] ans = Bin_search_2DArray(arr, target);
    System.out.println("Element is present at ");
    System.out.println(Arrays.toString(ans));

    }

    //My method mot optimum but another way to search in 2D array . 

    // static int Bin_search_2DArraysearch_2DArray(int[][]matrix,int target){

    //     for (int[] row : matrix) {
    //         int ans=binarysearch(row, target);
    //         if (ans!=-1) {
    //             return ans;
    //         } 
    //     }
    //     return -1;
    // }

    // static int binarysearch(int[]arr , int target){
    //     int start=0;
    //     int end=arr.length-1;

    //     while (start<=end) {
    //         //find the mid element
    //         // int mid = (start+end)/2; //might be possible that the (start + end) exceeds the range of int in java . 
    //         int mid = start+(end-start)/2;
    //         if (target>arr[mid]) {
    //             start=mid+1;
    //         }
    //         else if (target<arr[mid]) {
    //             end=mid-1;
    //         }
    //         else{
    //             return mid;
    //         }
    //     }
    //     return -1;

    // }

    // OPTIMUM SOLUTION KUNAL METHOD 

    // if the matrix is sorted in row and column wise . 

    static int[] Bin_search_2DArray(int[][] matrix,int target ){
        // int m = matrix.length;
        int row = 0; //initializing row = 0 bcz the row will be increasing as the element will not found .
        int coln = matrix[row].length-1; //Similarly , the coln will be decreasing as the element will not found .
        //above, the matrix[row] is written bcz may be the length of every row may not be same . 

        while (row<matrix.length && coln>=0) {
            if (matrix[row][coln]==target) {
                return new int[] {row,coln};
            }
            if (matrix[row][coln]>target) {
                coln--;   //bcz if this particular element is less than the last element of the particular row hence , the column will be deleted , (all the elements of that column will be less than it) .It mean that we will now shift the upper bound i.e our column to  2nd last element of the row .   
            }
            else{
                row++;  //bcz if this particular element is smaaler than the target , hence all the elements before it will be smaller than this(asc . sorted) .    
                // We will shift to 2nd last element of the next row , only if the target is not found in the row also .
            }

        }
        return new int[]{-1,-1};



    }

    //if the matrix is sorted is strictly sorted i.e. start element of current row > last element of previous row






















}

