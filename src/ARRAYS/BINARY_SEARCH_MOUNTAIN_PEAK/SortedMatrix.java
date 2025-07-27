import java.util.Arrays;

public class SortedMatrix {
    public static void main(String[] args) {
        int[][] arr ={{1,2,3,4},
                     {5,6,7,8},
                     {9,10,11,12},
                     {13,14,15,16}
        };
        System.out.println(Arrays.toString(search(arr, 9)));
        
    }

    static int[] binarySearch(int[][] matrix, int row , int cStart ,int cEnd ,  int target){  //bcz after deleting the rows when we want to do binsearch in 4 parts of array  , we will enter a row and also enter from where to where we want to search . 
        while (cStart<=cEnd) {
            int mid= cStart+(cEnd-cStart)/2;   // here mid is nothing  but the column . 
            if (matrix[row][mid]==target) {
                return new int[] {row,mid};
            }
            if (matrix[row][mid]<target) {
                cStart=mid+1;

            }
            else{
                cEnd=mid-1;

            }
        }
        return  new int[]{-1,-1};
    }

    static int[] search(int[][] matrix , int target){
        //Already defininig the rows and cols because it will be helpful for further use  . 
        
        int rows = matrix.length;
        int cols = matrix[0].length;  //be cautious matrix may be empty 

        if (rows==1) {
            return binarySearch(matrix, 0, 0, cols-1, target);  //here only provide the indices or start index . do not provide here direct values ofc. 
        }

        //initilaizing the row start and row end . to take a coln and perform BS on it. 
        //Here we are going column wise . 

        int rStart = 0;
        int rEnd = rows-1;  //is nothing but indicating the index of the last row  . 
        int cMid = cols/2;   
        

        while (rStart < (rEnd-1)) {   //while this is true it wil have more than 2 rows
            int rmid  = rStart+(rEnd-rStart)/2;
            if (matrix[rmid][cMid]==target) {
                return new int[] {rmid,cMid};
            }
            if (matrix[rmid][cMid]>target) {
                rEnd = rmid;   
            }
            else{
                rStart= rmid;
            }
        }

        //now we have 2 rows .
        //check whether the target is in the coln of 2 rows . x
        if (matrix[rStart][cMid]==target) {
            return new int[] {rStart,cMid};
        }
        if (matrix[rStart+1][cMid]==target) {
            return new int[] {rStart+1,cMid};
        }

        
        //If target not in the above case then check in the remaining 4 parts of the array . 
        
        //otherwise search in 1 st half
        if (target<=matrix[rStart][cMid-1]) {
           return binarySearch(matrix, rStart, 0, cMid-1, target);
        }

        //otherwise search in 2nd half 
        if ((target>=matrix[rStart][cMid+1]) && (target<=matrix[rStart][cols-1])) {
            return binarySearch(matrix, rStart, cMid+1,cols-1, target);
        }
        //otherwise search in 3rd half
        if (target<=matrix[rStart+1][cMid-1]) {
            return binarySearch(matrix, rStart+1, 0, cMid-1, target);
        }
        //otherwise search in 4th half
        else {
            return binarySearch(matrix, rStart+1, cMid+1,cols-1, target);
        }

    }
}
