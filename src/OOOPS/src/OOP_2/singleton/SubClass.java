package OOP_2.singleton;
import OOP_4.A;

public class SubClass extends A{
    
    public SubClass(int num , String name){
        super(num,name) ; 
        // System.out.println(n);
    }

    public static void main(String[] args) {
        SubClass obj = new SubClass(17  , "Aru Budhe") ; 
        int n = obj.num;
        System.out.println(n);
    }

}

//if we made an subclass of A having protected variable , then it can be used by its grandchildre 

class SubSubClass extends SubClass{
    public SubSubClass(int num , String name){
        super(num,name) ; 
        // System.out.println(n);
    }

    public static void main(String[] args) {
        SubSubClass obj = new SubSubClass(17  , "Aru Budhe") ; 
        int n = obj.num;
        System.out.println(n);
    }
}


class SubClass2 extends A{
    
    public SubClass2(int num , String name){
        super(num,name) ; 
        // System.out.println(n);
    }

    public static void main(String[] args) {
        SubClass2 obj = new SubClass2(17  , "Aru Budhe") ; 
        int n = obj.num;
        System.out.println(n);
    }

}