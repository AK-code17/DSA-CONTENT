package OOP_2.singleton;

public class Singleton {
    //since we want to create only one obj. , then we must call the constructor once , since its only responsible for creating objs.

    private Singleton(){ //we can use this constrcutor in this class only . 
        
    }

    private static Singleton instance; //since the same variable is being refrenced . 

    public static Singleton getInstance(){  //public definitely as other objs can call this , static since the method calling may be of static type only  , 
        //since we are accessing the getInstance via the class , static is mandatory . 
        // check whether only single obj is presetn . 
        if(instance == null){ //if no obj. is created . 
            instance = new Singleton();
        }

        return instance ; 
    }
}
