package OOP_5_AbstractDemo.INTERFACES;

public class Car implements Engine, Brake, Media{ //the order in which u write interfaces matter while its order in the class , but it doesn't matter actually  .

    @Override
    public void brake() {
        System.out.println("I brake like an normal car");
    }
    
    @Override
    public void start() {
        System.out.println("I am engine, start like an normal car");
    }

    @Override
    public void stop() {
        System.out.println("I am engine, stop like an normal car");
    }

    @Override
    public void acc() {
        System.out.println("I accelerate like an normal car");
    }
    
}
