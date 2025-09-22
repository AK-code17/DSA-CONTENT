package HEAPS;

import java.util.*;
// import java.util.Arrays;

class Main {
    public static void main(String[] args) throws Exception {
        // yaha  likhh...
        Heap<Integer> heap = new Heap<>();
        // Integer[] arr = {34,45,22,89,76};
        // heap.insert_list(arr);

        // System.out.println(heap.remove());

        // ArrayList<Integer> sorted = heap.heapSort();
        // System.out.println(sorted);

        // System.out.println(heap.remove() );

        ArrayList<Integer> data = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("How many items to insert in list");
        int n = sc.nextInt();
        while(n-->0){
            data.add(sc.nextInt());
        }
        
        // heap.heapify_algo(data,false);
        // System.out.println("Here is your MIN_HEAP : "+ data);
        heap.heapSort(data, true);
        System.out.println("Here is your sorted heap : "+data);
    }
}