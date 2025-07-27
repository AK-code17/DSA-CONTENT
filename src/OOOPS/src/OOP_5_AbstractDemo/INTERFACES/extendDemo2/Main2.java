package OOP_5_AbstractDemo.INTERFACES.extendDemo2;

public class Main2 implements A,B2{
    
    @Override
    public void fun() {
        // Explicitly call the default method of interface A
        A.super.fun();
        // Explicitly call the default method of interface B
        B2.super.fun();
    }

    public void callFun() {
        fun(); // Calls the overridden method in Main
    }

    public static void main(String[] args) {
        Main2 obj = new Main2();
        obj.callFun();
        obj.fun();
    }
}
