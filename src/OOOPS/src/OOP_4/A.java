package OOP_4;

public class A {
    protected int num ; 
    String name ; 
    int[] arr ; 
    
    //basically when an variable(here num) is written as private then it cannot be accessed outside the class , but whatif we do need to access it ?
    // then we make a get funtion in order to return it , since this function is public this definitely can be accessed . 
    
    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num = num ; 
    }

    public A(int num , String name){
        this.num = num ; 
        this.name = name ; 
        this.arr = new int[num] ; 
    }
}
