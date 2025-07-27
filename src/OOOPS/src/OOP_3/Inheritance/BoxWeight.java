package OOP_3.Inheritance;

public class BoxWeight extends Box {
    double weight ; 

    public BoxWeight(){
        this.weight = -1;
    }

    BoxWeight (BoxWeight other){
        super(other) ; 
        weight = other.weight ; 
    }

    public BoxWeight(double l , double w ,double h ,double weight){
        super(l, w, h) ; //what's this ? this calls the parent class constructor . 
        //used to initialise the values present in the parent class . 
        
        this.weight = weight ; 
    }

    static void greeting(){
        System.out.println("Hey I am in Balak jhopdi . bakwass!!");
    }
}
