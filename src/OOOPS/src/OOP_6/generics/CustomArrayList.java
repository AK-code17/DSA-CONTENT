package OOP_6.generics;

import java.util.*;

public class CustomArrayList {


    private int[] data;
    private static int DEFAULT_SIZE = 10;
    private int size=0;  //size is actually acting as an index value . 

    public CustomArrayList(){
        this.data = new int[DEFAULT_SIZE];
    }

    public void add(int num){
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
        int[] temp = new int[2*data.length]; //or also we can use here the default size variable also . 
        for (int i = 0; i < data.length; i++) {
            temp[i]= data[i];
        }

        data = temp;

        DEFAULT_SIZE *= 2 ;
    }

    public int remove() {
        /*here we are considering remove funtion to be removing the last index , as we do in Python 
        but in ArrayList in java its actually we provide the index ,but again the shifting of the index
         will make it a bit complex .*/ 

        return data[--size];
    }
    
    public int get(int index){
        if(index<0 || index>=data.length) return -1;

        return data[index];
    }

    public int size(){
        return size;
    }

    public void set(int index , int value){
        data[index] = value;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(data)+" List size "+size + " List Length "+DEFAULT_SIZE;
    }
    
    public static void main(String[] args) {
        // ArrayList list = new ArrayList();

        CustomArrayList list = new CustomArrayList();
        list.add(6);
        list.add(9);

        for (int i = 0; i < 14; i++) {
            list.add(2*i);
        }

        System.out.println(list.toString());

    }
}
