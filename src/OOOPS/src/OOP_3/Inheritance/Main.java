package OOP_3.Inheritance;

public class Main {
    public static void main(String[] args) {
        // Box box1 = new Box(4.6,7.9,9.9) ;
        // Box box2 = new Box(box1) ;  

        // System.out.println(box1.l + " "+ box1.h + " " + box1.w);
        
        // BoxWeight box3 = new BoxWeight() ;
        // BoxWeight box4 = new BoxWeight(2,3,4,8) ;
        
        // System.out.println(box3.h + " " + box3.weight);

        // Box box5 = new BoxWeight(2,3,4,8);
        // System.out.println(box5.weight); //why but since the constructor is of BoxWeight then it must be able to use it ?

        /*there are many variables in both parent and child clasees
         * you are give naccess to variables that are in the ref type i.e BoxWeight
         * hence , you should have access to weight variable
         * this also means ,that the ones you are trying to access should ebe initialised
         * but here.when the obj itself is of type parent class,  how eill you call the child class' variable
         * this is why error .*/
        
        // BoxWeight box6 = new Box(2,3,4);
        // System.out.println(box6); 



        // the main thing above was that learing about Static since the Overriding depend upon objects hence they can be overriden , methods are static and static does not depend upon object hence they cannot be overriden . 
        // Box box = new BoxWeight();
        // box.greeting(); //you can inherit , but u can't override . 

        
        


    }
}
