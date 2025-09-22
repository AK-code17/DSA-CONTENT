package HEAPS;

import java.util.*;

class Heap<T extends Comparable<T>> {

    private ArrayList<T> list;
    private int root;

    public Heap(){
        list = new ArrayList<>();
        root = 0;
    }

    private void swap(int first , int second , ArrayList<T> list){
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
            swap(child, parent,list);
            upheap(parent);
        }
    } 

    public T remove(ArrayList<T> list ,boolean is_min) throws Exception{
        if(list.isEmpty()) throw new Exception("Removing from an empty heap!!");

        T temp = list.get(0);
        
        T last = list.remove(list.size()-1); 
        
        if(!list.isEmpty()){
            list.set(0, last);
            // downheap(0);
            min_max_heap(0,list.size(), list, is_min);
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
            swap(parent, min,list);
            downheap(min);
        }
        
    }

    // specially created for the heapify algo , its downheap only but in input  ArrayList is given..
    private void min_max_heap(int parent ,int n, ArrayList<T> data , boolean is_min) {
        int min = parent;
        int max = parent;
        int left = left(parent);
        int right = right(parent);

        if(is_min){
            if (left < n && data.get(min).compareTo(data.get(left)) > 0) {
                min = left;
            }

            if (right < n && data.get(min).compareTo(data.get(right)) > 0) {
                min = right;
            }

            if(min != parent){
                swap(parent, min,data);
                min_max_heap(min,n,data,is_min);
            }
        }

        else{
            if (left < n && data.get(max).compareTo(data.get(left)) < 0) {
                max = left;
            }

            if (right < n && data.get(max).compareTo(data.get(right)) < 0) {
                max = right;
            }
            
            if(max != parent){
                swap(parent, max,data);
                min_max_heap(max,n,data,is_min);
            }
        }

        
    }

    public ArrayList<T> heapSort(boolean is_min) throws Exception{
        ArrayList<T> ans = new ArrayList<>();
        
        while (!list.isEmpty()) {
            ans.add(this.remove(this.list , is_min));
        }

        return ans;
    }

    // babbar heap sort..

    public void heapSort(ArrayList<T> data,boolean is_min){
        heapify_algo(data, is_min);
        
        // ArrayList<T> ans = new ArrayList<>();
        int n = data.size();

        for(int i = n-1 ; i>0 ; i--){
            swap(0, i, data);
            min_max_heap(0, i, data, is_min);
        }

        // return ans;
    }

    public void heapify_algo(ArrayList<T> data,boolean is_min){
        int n = data.size();
        for(int i = n/2-1 ; i>=0 ; i--){
            min_max_heap(i,n,data,is_min);
        }
    }

}