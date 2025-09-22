package HEAPS;

import java.util.*;

class Heap<T extends Comparable<T>> {

    private ArrayList<T> list;
    private int root;

    public Heap(){
        list = new ArrayList<>();
        root = 0;
    }

    private void swap(int first , int second){
        T temp = list.get(first);
        list.set(first, list.get(second));
        list.set(second, temp);
    }

    private int parent(int index){
        return (index-1)/2;
    }

    private int left(int index){
        return index*2+1;
    }

    private int right(int index){
        return index*2+2;
    }

    public void insert_list(T[] arr){
        for(T ele : arr){
            this.insert(ele);
        }
    }

    public void insert(T value){
        list.add(value);
        upheap(list.size()-1);
    }

    private void upheap(int child) {
        if(child == 0) return;
        int parent = parent(child);
        
        if(list.get(child).compareTo(list.get(parent)) < 0){
            swap(child, parent);
            upheap(parent);
        }
    } 

    public T remove() throws Exception{
        if(list.isEmpty()) throw new Exception("Removing from an empty heap!!");

        T temp = list.get(0);
        
        T last = list.remove(list.size()-1); 
        
        if(!list.isEmpty()){
            list.set(0, last);
            downheap(0);
        }
        return temp;
    }

    private void downheap(int parent) {
        int min = parent;
        int left = left(parent);
        int right = right(parent);

        if (left < list.size() && list.get(parent).compareTo(list.get(left)) > 0) {
            min = left;
        }

        if (right < list.size() && list.get(parent).compareTo(list.get(right)) > 0) {
            min = right;
        }

        if(min != parent){
            swap(parent, min);
            downheap(min);
        }
        
    }

    public ArrayList<T> heapSort() throws Exception{
        ArrayList<T> ans = new ArrayList<>();
        
        while (!list.isEmpty()) {
            ans.add(this.remove());
        }

        return ans;
    }

}