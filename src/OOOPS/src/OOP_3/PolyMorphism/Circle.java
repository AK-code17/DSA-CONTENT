package OOP_3.PolyMorphism;

public class Circle extends Shapes{
    
    // this will run when obj of Circle is created
    // hence it is overrriding the parent method
    // @Override //this is called annotation  (basically a check for Overriding) and its creation we will learn in the INterfaces lecture . 

    void area(){
        System.out.println("I am in Circle");
    }
}