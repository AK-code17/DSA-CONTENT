package OOP_5_AbstractDemo;

public class Main {
    public static void main(String[] args) {
        Son son  = new Son(20) ; 
        son.career();

        Parent.hello() ;

        son.normal(); //normal in son overrides normal of Parent if commented out it's own normal . 

        // Son.hello() ; always use static with the original parent class,though no issue but for avoiding confusion we use this . 


        Daughter daughter = new Daughter(22) ; 
        daughter.career();

    }
}
