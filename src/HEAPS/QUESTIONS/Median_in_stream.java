package HEAPS.QUESTIONS;

public class Median_in_stream {
    class MedianFinder {
    //defined min and max heaps..
    PriorityQueue<Integer> max_h;
    PriorityQueue<Integer> min_h;

    public MedianFinder() {
        //initialized min and max heaps...
        max_h = new PriorityQueue<>(Collections.reverseOrder());
        min_h = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
    
        if(max_h.isEmpty()){
            max_h.offer(num);
            return;
        }
        
        //by checking the peek we ensure that the smaller nums remain in the max_h and bigger ones in the min_h.. since we want the max from smaller part and similarly the min from the larger part...
        if(num > max_h.peek()){
            min_h.offer(num);
        }

        else{
            max_h.offer(num);
        }

        int diff = max_h.size() - min_h.size();
        // our objective to do the below operation(s) to ensure that height of max - min ==0 || 1 atmost..

        //if min_> max_h then balance them  / make the max_h bigger..
        if(diff == -1 ){
            max_h.offer(min_h.poll());
            return;
        }

        // if the max_h exceeds min_h by 2 , then also balance is needed. 
        if(diff == 2){
            // shift ele from max_h to min_h..
            min_h.offer(max_h.poll());
        }
        
    }
    
    public double findMedian() {
        
        //we implemented the balance part in the add num , so that we can get the median in the constant time ..
        //if size is same add both  / 2..
        if(max_h.size() - min_h.size() == 0){
            return (double)(max_h.peek() + min_h.peek()) / 2;
        }

        // else max_h will be > min_h , so return its peek().. 
        return (double)max_h.peek();

    }
}
}
