package HEAPS;

import java.util.*;

public class PQ {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(31);
        pq.offer(14);
        pq.offer(59);
        pq.offer(900);

        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }
}
