package OOP_5_AbstractDemo.INTERFACES;

public class Main {
    public static void main(String[] args) {
        /*Engine gadi = new Car();

        // gadi.acc() ; 
        gadi.start();
        gadi.stop();

        ///Now making an object of Media's instance . 
        Media car_awaz = new Car();

        car_awaz.start();
        car_awaz.stop();*/

        NiceCar car = new NiceCar();

        car.start();
        car.startMusic();
        car.upgradeEngine();
        car.start();

    }
}
