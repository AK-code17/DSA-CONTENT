package HEAPS;

import java.util.ArrayList;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws Exception {
        // yaha  likhh...
        Heap<Integer> heap = new Heap<>();
        Integer[] arr = {34,45,22,89,76};
        heap.insert_list(arr);

        // System.out.println(heap.remove());

        ArrayList<Integer> sorted = heap.heapSort();
        System.out.println(sorted);

        System.out.println(heap.remove() );

    }
}