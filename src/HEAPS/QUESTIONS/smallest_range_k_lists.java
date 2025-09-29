package HEAPS.QUESTIONS;

public class smallest_range_k_lists {
    /*

    Approach : "See what I learnt is just find a range covering all elements ie initially get all the first
    elements or the smallest ones from all K lists , then get max and min from them , and like then to shorten
    the range we could dec the max or inc the min , but since the max is the smaller ele of that particular
    list , we can't dec that , but for min there are elements further remaining , and yes also if no further
    ele's are remaining for the smaller ele's list then return that range , because then range will not have
    ele from this list , ok and keep doing this and track if current max-min < diff(already) and keep updating 
    them .. "

 */

    class Element {
        int val, list, ind;
        
        Element(int val, int list, int ind) {
            this.val = val;
            this.list = list;
            this.ind = ind;
        }
    }
    
    public int[] smallestRange(List<List<Integer>> nums) {
        if(nums.size() == 0) return new int[]{};
        
        PriorityQueue<Element> pq = new PriorityQueue<>((a,b) -> a.val - b.val);
        int max = Integer.MIN_VALUE;
        int n = nums.size();
        
        // Push the first element of each list into the min-heap
        for(int i = 0; i < n; i++) {
            List<Integer> list = nums.get(i);
            if(list.size() > 0) {
                int val = list.get(0);
                pq.offer(new Element(val, i, 0));
                max = Math.max(max, val);
            }
        }
        
        int diff = Integer.MAX_VALUE;
        int[] result = new int[2];
        
        while(!pq.isEmpty()) {
            Element curr = pq.poll();
            int min = curr.val;
            
            // if we found more shorter range just update the min,max;;
            if(max - min < diff) {
                diff = max - min;
                result[0] = min;
                result[1] = max;
            }
            
            // If we've reached the end of a list , then break since no more increase is possible..
            if(curr.ind + 1 >= nums.get(curr.list).size()) {
                break;
            }
            
            // Push next element from the same list
            int nextVal = nums.get(curr.list).get(curr.ind + 1);
            pq.offer(new Element(nextVal, curr.list, curr.ind + 1));
            max = Math.max(max, nextVal);
        }
        
        return result;
    }

}
