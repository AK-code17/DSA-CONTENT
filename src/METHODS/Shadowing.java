public class Shadowing {
    static int x=90;   //available to everything inside its' block or scope . 
    public static void main(String[] args) {
        System.out.println(x); //will give 90
        int x = 40; 
        System.out.println("New x delcared : "+x);  //will give 40 . 
        fun(x);

    }

    static void fun(int x){
        System.out.println(" Fun() output ar. passed from psvm . "+x);  //since the x  declared in the psvm has it;s local scope . and we did not provide any arg. to fun() , hence the x value will be of 90 . 
    }
}
