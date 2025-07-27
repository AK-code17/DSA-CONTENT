package OOP_7.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        //here the object is of Parent class i,e List and the reference is of child class . 
        // actually internally the list is an interface and implementation is in the ArrayList , linkedlist etc , which all it uses . 
        
        
        // List<Integer> list = new ArrayList<>();
        // List<Integer> list2 = new LinkedList<>();
        
        // list2.add(13);
        // list2.add(17);
        // list2.add(36);

        // System.out.println(list2);

        //vector also implements list . 

        List<Integer> vector = new Vector<>();
        vector.add(31);
        vector.add(71);
        vector.add(63);

        System.out.println(vector);
    }
}
