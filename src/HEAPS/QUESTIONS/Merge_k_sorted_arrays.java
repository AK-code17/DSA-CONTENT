package HEAPS.QUESTIONS;

public class Merge_k_sorted_arrays {
    public static ArrayList<Integer> mergeKSortedArrays(ArrayList<ArrayList<Integer>> kArrays, int k)
	{
        // this class will help us to get the next element..
		class Element{

			int val;
			int list;
			int ind;

			Element(int val , int list , int ind){
				this.val = val;
				this.list = list;
				this.ind = ind;
			}

		}

		// Write your code here.
		ArrayList<Integer> ans = new ArrayList<>();
		
		PriorityQueue<Element> pq = new PriorityQueue<>((a,b)-> a.val - b.val);
		
        // firstly add all the first elements of the lists..
		for(int i = 0 ; i<k ; i++){
			ArrayList<Integer> list = kArrays.get(i);
			if(list.size() > 0){
				pq.offer(new Element(list.get(0),i,0));
			}
		}

		while(!pq.isEmpty()){
			Element curr = pq.poll();
			ans.add(curr.val);
			int ind = curr.ind;

			ArrayList<Integer> list = kArrays.get(curr.list);
            // if the next ind is valid then add the element...
			if(ind + 1 < list.size()){
				int list_ind = curr.list;
				int val = list.get(ind+1);
				pq.offer(new Element(val,list_ind,ind+1));
			}
		}

		return ans;

	}
}
