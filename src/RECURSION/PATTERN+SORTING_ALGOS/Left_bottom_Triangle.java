public class Left_bottom_Triangle {

    public static void main(String[] args) {
        Normal_Triangle(4, 0);
    }
    //Left side trianle  . 
    static void Left_bottom_Triangle(int row  , int coln){
        //Checking the base contiton
        if (row==0) {
            return ;
        }
        if (coln < row) {
            System.out.print("*");
            Left_bottom_Triangle(row, coln+1);
        }
        else {
            System.out.println();
            Left_bottom_Triangle(row-1, 0);
        }

    }

    //Normal Triangle

    static void Normal_Triangle(int row  , int coln){
        //Checking the base contiton
        if (row==0) {
            return ;
        }
        if (coln==row) {
            Normal_Triangle(row-1, 0);
            System.out.println();
        }
        else {
            Normal_Triangle(row, coln+1);
            System.out.print("*");
        }
    }

}