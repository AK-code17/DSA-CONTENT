package OOP_5_AbstractDemo.INTERFACES;


/*Here a doubt may occur that why we did not implement here the Engine and other interfaces inorder to call , but see the same error of overriding will occur since It will confuse Compiler to decide which to use and all that . Our main moto to make diff obj was only to resolve this conflict .  */
public class NiceCar {
    private Engine engine ; 
    private Media player = new CDPlayer();

    public NiceCar(){
        engine = new PowerEngine();
    }

    public NiceCar(Engine engine) {
        this.engine = engine;
    }

    public void start(){
        engine.start();
    }

    public void stop(){
        engine.start();
    }

    //Now we do not need to write engine.start()/stop() as we have created an diff obj , Ab stop ne sirf gana rukega , gaadi nahi . 
    public void startMusic(){
        player.start();
    }

    public void stopMusic(){
        player.stop();
    }

    public void upgradeEngine(){
        this.engine = new ElectricEngine() ; 
    }
    
}
