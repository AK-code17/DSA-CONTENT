package OOP_2.static_key;

public class Human {
    int umr ; 
    String naam ; 
    int pagar ;
    boolean vivah ; 
    //we understood in the notes how the static is working .
    static long jan_sankhya ;  

    

    static void message(){
        System.out.println("Hello world");
        // System.out.println(this.umr); not allowed since its static so how will it access non-static . 
    }
    
    public Human(int umr , String naam , int pagar , boolean vivah){ //making it public so we can use it in another file . 
        this.umr = umr ; 
        this.naam = naam ; 
        this.pagar = pagar ; 
        this.vivah = vivah ; 
        Human.jan_sankhya += 1 ;     // though obj. is also can be done , but we clearly understood why this cannot be used since its static  , independent of the object hence directly related to class( here Human) so use Human.  ,
        System.out.println("Hi");
        
        message(); //use it when u are calling to static classes inside the class only . 
        Human.message(); //but if calling from another class using class_name is the convention . 
    
    }

}
