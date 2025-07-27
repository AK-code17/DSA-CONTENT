package OOP_5_AbstractDemo.INTERFACES.extendDemo2;

public interface B2 {
    // void greet() ;  
    default void fun(){
        System.out.println("I am in B");
    }
}
