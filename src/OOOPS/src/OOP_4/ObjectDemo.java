package OOP_4;


public class ObjectDemo {
    int num ; 
    float n ; 

    //already covered these 2 .  
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public ObjectDemo (int num,float n){
        this.num = num ; 
        this.n = n ; 
    }

    //we will go in the deatil on how to create it in Hashmap lecture . 
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.num == ((ObjectDemo)obj).num; //casting obj to object demo since we need to access num , which is a variable  of ObjecDemo but we r passing Object class . 
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) {
        // ObjectDemo obj = new ObjectDemo(34);
        //we can get diff outputs based on different obj ; 
        // ObjectDemo obj2 = new ObjectDemo(34) ; 
        
        // System.out.println(obj.hashCode());
        //the output :  798154996 . 

        ObjectDemo obj_1 = new ObjectDemo(12,56.8f) ; 
        ObjectDemo obj_2 = new ObjectDemo(12,78.9f) ; 

        // if(obj1 == obj2){ //checking whether they r pointing to same obj or not . 
        //     System.out.println("obj1 is less than obj2");
        // }

        if(obj_1.equals(obj_2)){ //checking the contents of obj1 and obj2 . 
            System.out.println("obj1 is equalt to obj2");
        }

        System.out.println(obj_1.getClass().getName()); //gives the class name i.e OOP_4.ObjectDemo  , Also it will be stored in an Heap Area . 


        
    }



}
