package OOP_6.cloning;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Human H1 = new Human(19, "aru");
        // Human H2 = new Human(H1); //but it takes a lot by going to the deftn .and all . hence make the clone object  . 

        Human H3 = (Human) H1.clone();

        // System.out.println(H3.name + " "+ H3.age);  
        System.out.println(Arrays.toString(H1.arr) + " "+ H1.name);

        H3.arr[0] = 100;
        H3.name = "Komal";
        
        System.out.println(Arrays.toString(H3.arr)+ " "+ H3.name); 

        System.out.println(Arrays.toString(H1.arr)+" "+ H1.name);
    
    }

}
