package OOP_3.Inheritance;

public class Box {
    double l;
    double w;
    double h;
    double weight;

    Box(){
        super();
        this.l = -1;
        this.w = -1;
        this.h = -1;
    }

    //if its a cube . 

    Box(double side){
        this.l = side ;
        this.w = side ; 
        this.h = side ;  
    }

    Box(Box box){
        this.l = box.l ; 
        this.l = box.l ; 
        this.l = box.l ; 
    }

    Box(double l , double w , double h){
        this.l = l;
        this.w = w;
        this.h = h;
    }

    static void greeting(){
        System.out.println("Hey I am in Pappa house . whoooa!!");
    }

}
