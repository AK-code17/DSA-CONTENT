package OOP_6.generics;

import java.util.ArrayList;
import java.util.Arrays;

//here T can be Number or subclasses of Number . 
public class WildcardExample<T extends Number> {
    private Object[] data;
    private static int DEFAULT_SIZE = 10;
    private int size=0;  //size is actually acting as an index value . 

    public WildcardExample(){
        this.data = new Object[DEFAULT_SIZE];
    }

    //example showing the difference between the <> and <?> , it includes Numnber's subclasses also . 
    public void getList(ArrayList<? extends Number> list){
        //do something . 
    }

    public void add(T num){
        if(isFull()){
            resize();  //if full resize or else add element . 
        }

        data[size++] = num;
    }

    public boolean isFull(){
        return size == data.length; 
    }
    
    //we need to make it private since we do not wany anyone to resize it directlt , it should be done indriectly while adding an element.
    private void resize() {
        Object[] temp = new Object[2*data.length];   //or also we can use here the default size variable also . 
        for (int i = 0; i < data.length; i++) {
            temp[i]= data[i];
        }

        data = temp;

        DEFAULT_SIZE *= 2 ;
    }

    public T remove() {
        /*here we are considering remove funtion to be removing the last index , as we do in Python 
        but in ArrayList in java its actually we provide the index ,but again the shifting of the index
         will make it a bit complex .*/ 

        return (T)data[--size];
    }
    
    public T get(int index){
        // if(index<0 || index>=data.length) return -1;

        return (T)data[index];
    }

    public int size(){
        return size;
    }

    public void set(int index , T value){
        data[index] = value;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(data)+" List size "+size + " List Length "+DEFAULT_SIZE;
    }
    
    public static void main(String[] args) {
        // ArrayList list = new ArrayList();

        WildcardExample<Integer> list3 = new WildcardExample();

        // CustomGenArrayList list = new CustomArrayList();
        list3.add(6);
        list3.add(9);

        for (int i = 0; i < 14; i++) {
            list3.add(3*i);
        }


        System.out.println(list3.toString());

    }
}
