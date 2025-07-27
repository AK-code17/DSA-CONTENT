import java.util.*;
public class Multidimensional_Arrays {
    public static void main(String[] args) {
        /*
        
            1 2 3
            4 5 6
            7 8 9
         */
        
         int[][] arr= new int[3][3]; // new int[row][column]
        //  System.out.println(arr.length); // gives the no. of rows
         Scanner in = new Scanner(System.in);

        //  int[][] arr2D = {
        //             {1,2,3,}, // oth index
        //             {4,5}, // 1 st index
        //             {6,7,8,9} //2nd index
        //  };

         //input in 2D array

        for (int row = 0; row < arr.length; row++) {
            // arr[row]=in.nextInt();
            for (int column = 0; column < arr[row].length; column++) {
                // System.out.println("New element in the same row");
                arr[row][column]=in.nextInt();
            }
            
            // System.out.println("New row starting");
        }

        /*for (int row = 0; row < arr.length; row++) {
            // arr[row]=in.nextInt();
            for (int column = 0; column < arr[row].length; column++) {
                // System.out.println("New element in the same row");
                System.out.print(" [");
                System.out.print(arr[row][column]);
                System.out.print("]");
            }
            
            System.out.println();
        }*/

        for (int row = 0; row < arr.length; row++) {
            // arr[row]=in.nextInt();
            System.out.println(Arrays.toString(arr[row])); // here row is not array it's index so arr[row] is array as an element . 
        }

        for (int[] row : arr) { // here every element itself is an array that's why [] is mentioned which was not in 1D enhanced array . 
            System.out.println(Arrays.toString(row)); // bcz row is an array itself . 
        }

        

    }


}
