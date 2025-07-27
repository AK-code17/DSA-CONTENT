package OOP_6.exceptionHandling;

public class MyException extends Exception{ //of course it will extend Exception , my exception includes exception also . 
    
    public MyException(String msg){
        super(msg);
    }

}
