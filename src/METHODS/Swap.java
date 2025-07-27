public class Swap {
    public static void main(String[] args) {
        int a= 10;
        int b= 20;
        swap(a, b);
        System.out.println(a+" "+b);

        String name = "Aryan Kiran Budhe";
        changeName(name);
        System.out.println(name);
    }

    static void changeName(String name) {
        name = "Rasmalai";
    }

    static void swap(int a , int b){
        //swap numbers code
        int temp =a;
        a=b;
        b=temp;
    }
}
