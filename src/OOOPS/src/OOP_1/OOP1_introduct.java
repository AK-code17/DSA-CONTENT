package OOP_1;

import java.util.*;

public class OOP1_introduct {
    public static void main(String[] args) {
        //data of 5 students . 
        int[] rno = new int[5] ; 
        String[] name = new String[5] ; 
        float[] marks = new float[5] ; 

        Student[] students = new Student[5] ; 

        /*Since the student class is having rno,name & marks then what does the Students arr will be consisiting of ???
         The by default value of arrays is null. 
        
        // System.out.println(Arrays.toString(students));
        
        Then we will be trying to create a new Object and print its default values . 

        Now modifyoing or putting values*/

        Student Aru = new Student(17,"Aryan Budhe" ,9.5f) ; 
        /*But what if we are calling Aru.greeting() , when we have not created the Student() constructor ?? , 

        Then what will be printed?
        */
        // Aru.changeName("Komal");
        Aru.Greeting(); //simply null since the name is not given so it will put the name as default  i.e(null)

        // Hi null Have a good day .

        

        /*Aru.rno = 17 ;
        Aru.name = "Aryan Kiran Budhe" ; 
        Aru.marks = 9.5f; 

        So instead of adding values one by one we can directly make an constructor in the class  , it will put the values directly . */

        
        System.out.println(Aru.rno); //by def. value of rno(int) is 0 . 
        System.out.println(Aru.name); //by def. value of name(String) is null .
        System.out.println(Aru.marks); //by def. value of marks(float) is 0.0
        
        //So since we have added one more constructor Student(roll,naam,ank) , so whenever a new object is created and args are given then Student() functn. with 3 values , is called .  

        Student stud_2 = new Student(Aru) ; //while passing another object constructor with Student obj. is called . 
        // System.out.println(stud_2.name);

        Student rand_1 = new Student() ;
        System.out.println(rand_1.rno);
    
    }

}

//Since we want to store the all the data of a student inside one data type only then we will make a new Class . 
//Class is a named grp of props, and functions . 

class Student{  //A template basically . 
    int rno; 
    String name ; 
    float marks = 10.0f ;  //if we give any type a default value so that value will be instanced to every instance variables(object's) , though we try to provide new values in it .  Reason in the nb . 
    
    //simply putting name also go , but ?? this.name is a good use of explicit call , while only name is implicit , but in name anbiguity this.name is preferred . 

    void Greeting(){ 
        System.out.println("Hi "+this.name+" Have a good Day");
    }


    //to change the name 
    void changeName(String newName){
        this.name = newName ; 
    }
    

    /*so what is this , The this keyword is used to refer to the current object, ensuring you're setting the instance variables (not just local variables).

    So , whenever we are calling a function , this keyword refers to the current object and set its values .*/ 

    // Student(){
    //     this.rno=17;
    //     this.name = "Aryan Budhe" ; 
    //     this.marks = 9.5f ;
    // }

    //THIS IS HOW WE CALL A CONSTRUCTOR INSIDE A CONSTRUCTOR . 

    Student(Student other){
        this.rno = other.rno ; 
        this.name = other.name ; 
        this.marks = other.marks ; 
    }

    Student(){
        //this is how we call a constructor inside a constructor .
        //internally : new Student(7,"default person",100.0f)  ; 
        this(7,"default person",100.0f) ; 
    }


    //Since we can't put same argument name  , if we are putting then we have to mention this.name otherwise we can also use name also .  in every object . 

    Student(int roll , String naam , float ank){
        //if ur argument name and the Class variable name are same use of .
        // this. is must else no need but its a good practice . 

        this.rno = roll ; 
        this.name = naam ; 
        this.marks = ank ; 

    }

}
