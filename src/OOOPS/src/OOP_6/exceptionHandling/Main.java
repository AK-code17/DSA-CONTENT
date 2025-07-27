package OOP_6.exceptionHandling;

public class Main {
    public static void main(String[] args) {
        int a = 5;
        int b = 0;
        // int c = a/b ; at runtime the error is thrown . 

        try {
            // System.out.println(divide(a,b));
            //just throwing an exception . 
            // throw new Exception();

            String name = "Aru";
            if(name.equals("Aru")){
                throw new MyException("khush raho!!") ; 
            }
        }

        //here it goes order wise all the exceptions are cathced below , hence it won't move to arithemtic one , but for catching diff types
        // one need to mention it above this one . 
        
        catch(MyException mye){
            System.out.println(mye.getMessage());
        }
        catch (ArithmeticException err) {  //it catches if exceptn. happens . 
            System.out.println("arithmetic one");
        }

        catch (Exception err) {  //it catches if exceptn. happens . 
            System.out.println("exception one");
        }
        
        finally{
            System.out.println("It always gets done!!");
        }
    
    }

    static int divide(int a, int b){ //throws AE is an optional thing (it means it may throw an exception), since its and type of
                                                                // unchecked exception hence it is automatically checked.
        if (b == 0) {
            throw new ArithmeticException("bhai pls mat kar!"); // this is beyond the return type of the function since 
            // it interally does the catch thig and returns the msg passed .
        }
    
        return (a/b); 
    }
    
}
