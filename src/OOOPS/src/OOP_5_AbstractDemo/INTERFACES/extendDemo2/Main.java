package OOP_5_AbstractDemo.INTERFACES.extendDemo2;

public class Main implements A,B {

    @Override
    public void greet() {
        
    }

    public static void main(String[] args) {
        Main obj = new Main();
        A.greeting();
    }
    
}
