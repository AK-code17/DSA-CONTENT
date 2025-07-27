import java.util.Arrays;

public class Adj_Peak_2D {
    
    public static void main(String[] args) {
        int[][] mat  = {{2,1},
                   {3,4},
                    };

        int[] ans = findPeakGrid(mat);
        System.out.println(Arrays.toString(ans));

    }

    static int[] findPeakGrid(int[][] mat) {
        int row = 0;
        int coln = mat[row].length-1;

        boolean check = true;
        while(row<mat.length && coln>=0){
            if(row==0){
                if( mat[row][coln] < mat[row+1][coln] ){
                    row++;
                    check=false;
                }

                if( mat[row][coln] < mat[row][coln-1] ){
                    coln--;
                    check=false;
                }

            }

            else if(row<mat.length-1){
                if( mat[row][coln] < mat[row+1][coln] ){
                    row++;
                    check=false;
                    
                }

                if( mat[row][coln] < mat[row][coln-1] ){
                    coln--;
                    check=false;
                }

            }

            else{
                if( mat[row][coln] < mat[row][coln+1] ){
                    coln++;
                    check=false;
                    
                }

                if( mat[row][coln] < mat[row][coln-1] ){
                    coln--;
                    check=false;
                }

            }

            if(check){
                return new int[] {row,coln};
            }

        }

        return new int[]{-1,-1};
        

    }
}
