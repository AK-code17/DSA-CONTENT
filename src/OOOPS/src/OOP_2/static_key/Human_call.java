package OOP_2.static_key;

public class Human_call {
    //reason why the main is static is written in notes . 
    // public static void main(String[] args) {
    //     //Since both the Classes i.e (Human and Human_call are present inside the same folder so no need to import the class , but need to import the folder(static)key) .
    //     // Human Aru = new Human(19, "Aryan Kiran Budhe", 150, false) ; 
    //     // Human Rasmalai = new Human(19, "Rasmalai", 15000, false) ; 

    //     // System.out.println(Human.jan_sankhya);  //here also obj.jan_sankhya is working but class_name is the convention . 
    //     // System.out.println(Human.jan_sankhya);

    //     // greeting();
    //     fun() ; 
    // }
    
    //if something we defined as static it can definitely be accessed in static . 
    static void fun(){
        System.out.println("Hello world");
        // greeting(); similarly here as something which itself is static then how can it call a non-static funtion . 

        //you need to necessarily make an ref to the method  . 
        Human_call obj = new Human_call() ; 
        obj.greeting() ; 
    }

    void fun2(){
        greeting();
    }
    
    /*non-static funtions requires some instance inorder to call them . 
    we know that something which is not static it belongs to some object , so without any ref object we cannot use nonstatic method . */
    public void greeting(){
        System.out.println("Hello world");
        // fun(); //but we can def. allow static members .   
    }

    


}





