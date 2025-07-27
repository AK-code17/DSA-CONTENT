import java.util.ArrayList;

public class Lin_Search {
    
    public static void main(String[] args) {
        int[] arr = {3,2,1,3,18,9,3};
        // System.out.println(Lin_Search(arr,59,0));
        ArrayList<Integer> ans = find_ind_all(arr, 3, 0,new ArrayList<>());
        System.out.println(ans);
    
    }
    //searching from the first ind . 
    static int Lin_Search(int[] arr , int target , int start){
        if (start==arr.length) {
            return -1;
        }
        if(arr[start]==target){
            return start;
        }
        return Lin_Search(arr,target,start+1);
    }

    //searching from the last ind . 
    static int find_ind_last(int[] arr , int target , int end){
        if (end==-1) {
            return -1;
        }
        if(arr[end]==target){
            return end;
        }
        return find_ind_last(arr,target,end-1);
    }

    //searching all the target indices and storing it in array list . 
    static ArrayList<Integer> find_ind_all(int[] arr , int target , int start  , ArrayList<Integer> list){
        if (start==arr.length) {
            return list;
        }
        if(arr[start]==target){
            list.add(start);
        }
        return find_ind_all(arr,target,start+1,list);
    }

    //Creating the ArrayLost inside the function itself .
    
    static ArrayList<Integer> find_ind_all2(int[] arr , int target , int index){
        
        ArrayList<Integer> list = new ArrayList<>();
        if (index==arr.length) {
            return list;
        }
        //this will contain the ans for that function call only . 
        if(arr[index]==target){
            list.add(index);
        }
        //last call tak jayega  then return hote hote , wapas aayega . 
        ArrayList<Integer> ansFromBelowCalls =  find_ind_all2(arr,target,index+1);

        //adding all the ans we got from the below calls  . 
        list.addAll(ansFromBelowCalls);

        return list;
    }



}
