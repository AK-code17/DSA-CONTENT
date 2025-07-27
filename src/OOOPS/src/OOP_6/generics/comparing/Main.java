package OOP_6.generics.comparing;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Student aryan = new Student(26,89.4f) ; 
        Student komal = new Student(7, 99.5f) ; 
        Student suraj = new Student(45,95.6f) ;
        Student harsh = new Student(39,71.9f) ;
        Student pritesh = new Student(36,78.9f) ;

        Student[] list = {aryan,komal,suraj,harsh,pritesh};

        System.out.println(Arrays.toString(list));  //see while hovering its showing object class 
        // implementation , but overriding method is called at runtime .
        
        Arrays.sort(list, (o1,o2) -> -(int)(o1.marks-o2.marks)); /*so here;s the fun1!! , at sort its sorting based on marks , but why?? 
        because internally the sort works on the compareTo operation and in our compare to we have 
        intialised marks , if we change it to rollno , it will do that . and the fun is, when we 
        remove the compareTo method and hence also remove the implements of Comaparable then , its 
        shows error , since now there is not any parameter defined or intialised based upon which it 
        can compare .. mazzzzaaa .*/ 
        
        System.out.println(Arrays.toString(list));


        // if(aryan.compareTo(komal) < 0){
        //     System.out.println("Komal is smarter than Aryan by "+aryan.compareTo(komal));
        // }
 
    }

}
