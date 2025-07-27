package OOP_1;

public class Wrapper_eg {
    public static void main(String[] args) {
        // int a = 10;
        // int b = 20 ;

        Integer a = 10;
        Integer b = 20 ;

        Integer num = 45 ; /*now the num is a object rather than just being a primitive data type . 
        
        Since num being a object its feature we can access by the same (.)  */
        
        swap(a, b);

        // System.out.println(a+" and "+b);
        //no change in variable sine pass by values is there not pass by reference . 
        final int bonus = 2 ; 
        // bonus=3; error because final keyword makes the variable to be not modified . 


        final A Aru = new A("Aryan Budhe") ; 
        Aru.name = "other name" ; 
        
        //when a non primitive is final , u caanot reasign it.. . 

        A obj ; 
        
        for (int i = 0; i < 1000; i++) {
            obj = new A("Random name") ; 
        }
    }

    static void swap(int a  , int b){
        int temp =a ; 
        a= b ; 
        b= temp ; 
    }

    /*But while we are passing the objects its reference is being passed , so the changes will be reflected in the main object , ok 
    
    But why its not changing the value .?? bcz of the final keyword . 
    */
    
    

    static void swap(Integer a  , Integer b){
        Integer temp = a  ; 
        a = b ; 
        b = temp ; 
    }


}

class A{

    final int num = 10 ; //it needed to be 
    String name;

    public A(String name){
        this.name = name ; 
        System.out.println(name);
    }


    //Its called when a object is being destroyed .
    //Manual destruction of an obj. is not allowed in JAVA , but ya some sort of actions we can do while destroying an obj . 

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        // super.finalize();
        System.out.println("Mujhe apne dil mai jagah dene ke liye dhanyawad");
    }
}
