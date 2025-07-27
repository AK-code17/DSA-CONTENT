public class Comparison {
    public static void main(String[] args) {
        String a = "Aryan";
        String b = "Aryan";
        String c = "Aryan";

        //== 
        // System.out.println(a==b); // ==  CHECKS WHETHER THE OBJS ARE POINTING TO THE SAME VARIABLE OR NOT . 

        String name1 = new String("Aryan");
        String name2 = new String("Aryan");
    
        // System.out.println(name1==name2);

        // System.out.println(name1.equals(name2));   //.equals ONLY CHECKS WHETHER THE VALUES OF THE OBJS ARE SAME OR NOT . 
    
        //For traversal we cannot use name1[0] .like this as we do in python we must use : 

        System.out.println("Elements of the string are : ");
        for (int i = 0; i < name1.length(); i++) {
            System.out.println(name1.charAt(i));  //we use .charAt(index) for getting the elements at a particular index . 
        }
    
    
    
    
    
    }
}
