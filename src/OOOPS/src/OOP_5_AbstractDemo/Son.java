package OOP_5_AbstractDemo;

public class Son extends Parent {

    public Son(int age){
        super(age) ; 
    }
    
    @Override
    void career() {
        System.out.println("I am going to be a Athlete");    
    }

    void normal() {
        System.out.println("I am normal in Son");;
    }

    @Override
    void partner() {
        System.out.println("I love Classical Music");
    }
}
