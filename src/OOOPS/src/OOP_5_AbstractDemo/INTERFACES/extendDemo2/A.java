package OOP_5_AbstractDemo.INTERFACES.extendDemo2;

public interface A {

    /*static methods need a body , why? see since static are not able to override because its not dependednt upon object , but here in interface all the methods are abstract and we do not need there implementation , but since static cannot be overriden we have to give body to it.*/

    static void greeting(){
        System.out.println("Hello how are you I am in A");
    }

    default void fun(){
        System.out.println("I am in A");
    }
}
