package OOP_5_AbstractDemo;

public abstract class Parent {

    int age ; 
    final int value ; 

    public Parent(int age){
        this.age = age ;
        value = 0;

    }

    static void hello(){
        System.out.println("hey");
    }

    void normal(){
        System.out.println("this is a normal method");
    }

    abstract void career() ; 
    abstract void partner() ; 
}
