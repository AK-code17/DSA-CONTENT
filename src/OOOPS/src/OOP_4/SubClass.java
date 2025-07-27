package OOP_4;

// package OOP_4.A;

public class SubClass extends A{
    
    public SubClass(int num , String name){
        super(num,name) ; 
        // System.out.println(n);
    }

    public static void main(String[] args) {
        SubClass obj = new SubClass(17  , "Aru Budhe") ; 
        int n = obj.num;
        // System.out.println(n);

        System.out.println(obj instanceof A); //gives true since it's an instance of the SubClass,which indeed  is an subclass of A . 

        //Interestingly it returns true to instance of Object, since its the root of Class . Here we again got an live example of Multi-level Inheritance . 
    }
}
