import java.util.*;

public class ARRAYS {
    /*public static void main(String[] args) {
        //Q store a roll number
        int a =19;

        // ??Q  :Store a person;s name
        String name= "Aryan Kiran budhe";

        //Syntax

        //datatype[] variable_name = new datatype[size];
        //store 5 roll nos;
            // int [] rnos = new int[5];
            // // or directly
            // int rnos_2[]={2,3,4,5};
        int[]ros; //declaration of arryas , ros is getting defined in the stack;
        ros=new int[5]; //Actually here object is being created in the memory (heap)
        System.out.println(ros[1]);*/
        
        // System.out.println(Arr[0]);
        

        
        static void change(int [] arr){
            arr[1]=99;
        }
        
        
        public  static void main(String[] args) {
           
            // Array of primitives . 

            Scanner sc= new Scanner (System.in); 
            


            int[] arr =new int[5];
            arr[0]=23;
            arr[1]=45;
            arr[2]=4;
            arr[3]=5;
            arr[4]=453;

            // System.out.println(arr[3]);

            //input using for loop

            // for (int i = 0; i < arr.length; i++) {
            //     arr[i]=sc.nextInt();
            // }

            // for (int i = 0; i < arr.length; i++) {
            //     System.out.print(arr[i]+" ");
            // }

            // for(int num : arr){ //for every element in array , print the element . 
            //     System.out.print(num+" "); // here  num represents element of array
            // }

            //Best method to print all the elements of the arrays .  
            // System.out.println(Arrays.toString(arr));

            //ARRAYS OF OBJECTS

            String[] str = new String[5];
            // for (int i = 0; i < str.length; i++) {
            //     str[i]= sc.next();            
            // }

            // System.out.println(Arrays.toString(str));

            //modify 
            // str[0]="komal";

            // System.out.println(Arrays.toString(str));

            int[] nums = {3,4,5,12};
            // System.out.println(Arrays.toString(nums));
            // change(nums);
            // System.out.println(Arrays.toString(nums));
            
            
            // System.out.println();

            String Arr[]=new String[4];

            

            

            
        }
}

