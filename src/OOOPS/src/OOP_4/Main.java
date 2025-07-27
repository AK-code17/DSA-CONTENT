package OOP_4;

public class Main {
    public static void main(String[] args) {
        A obj = new A(10,"Kunal");
        /*need to do a few things
        1. access the data members
        2. modify the data members

        below we can though modify arraylist or like that , but we cannot actually modify the actual array . 
        we are just working with the reference , because all the methods of ArrayList are marked as final or private. */
        
        // ArrayList<Integer> list = new ArrayList<>(23) ; 
        // list.DEFAULT_CAPACITY;

        obj.getNum(); //it will return the num though it's marked as private before . 
        int n = obj.num;        

    }
}
