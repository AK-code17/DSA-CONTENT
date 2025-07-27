package OOP_5_AbstractDemo.INTERFACES.extendDemo2;

public interface B {
    void greet() ;
    // void fun() ;  
    // default void fun(){
    //     System.out.println("I am in B");
    // }

    /*It can have default functions with another name , but cannot have it with the same name , as the there are issue(Otherwise, the compiler will throw an error because it won't know which default implementation to use.) in overriding default methods with the same name. */
}
