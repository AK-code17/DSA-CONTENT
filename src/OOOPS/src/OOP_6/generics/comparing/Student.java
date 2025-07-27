package OOP_6.generics.comparing;

import java.util.Arrays;

public class Student{
    int rollno ; 
    float marks ; 

    public Student(int rno , float mks){
        this.rollno = rno ;
        this.marks = mks ;
    }

    @Override
    public String toString() {
        return rolllno+"";
    }

    // @Override //this method is overriding methods of Comparable interface .
    
    // /*Also one important thing is that the compareTo works on the principle of
    //  -ve,+ve and zero ,so we need to return in this form only , not we can return 
    //  any number , the the sorting doesn't happen . */
      
    // public int compareTo(Student o) {
    //     return (int) (o.rollno-this.rollno);
    // }

}
