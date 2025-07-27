package OOP_5_AbstractDemo.INTERFACES.nested;

// import INTERFACES.nested.A.NestedInterface;

public class A {
    public interface NestedInterface{
        boolean isOdd(int num) ; 
    }
}

class B implements A.NestedInterface{
    
    @Override
    public boolean isOdd(int num) {
        return (num&1) == 1;
    }

}
