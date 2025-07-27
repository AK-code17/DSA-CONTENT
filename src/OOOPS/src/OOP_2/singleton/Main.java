package OOP_2.singleton;
import OOP_4.A;

public class Main {
    public static void main(String[] args) {
        Singleton obj = Singleton.getInstance() ; 
        
        Singleton obj_2 = Singleton.getInstance() ; 

        Singleton obj_3 = Singleton.getInstance() ;
        
        // all 3 ref. variables are pointing to just one object . 

        // A a = new A(17,"Aru");
        A a = new A(17,"Aru");
        System.out.println(a.getNum());
        // int n = a.num; this will not work as the protected will only work in the subclass . 
        System.out.println(n);

    }
}
