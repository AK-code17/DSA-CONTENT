public class StringExample {
    public static void main(String[] args) {
        System.out.println(myGreet("Kashish"));
    }

    static String myGreet(String name) {
        // /xception("Unimplemented method 'myGreet'");
        String message = "Hello "+name;
        return message;
    
    }

    static String greet(){
        String greeting = "How are you ";
        
        return greeting;
    }


}
