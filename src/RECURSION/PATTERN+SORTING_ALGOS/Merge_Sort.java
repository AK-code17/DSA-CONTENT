import java.util.* ; 

public class Merge_Sort {
    public static void main(String[] args) {
        int[] arr = {8,4,3,12,5,6}  ;
        arr =  mergeSort(arr) ; 
        System.out.println(Arrays.toString(arr));
    }

    static int[] mergeSort(int[] arr ){
        if(arr.length == 1){
            return arr ;
        }

        int mid = arr.length/2 ; 

        int[] left = mergeSort(Arrays.copyOfRange(arr , 0 , mid)) ; 
        int[] right = mergeSort(Arrays.copyOfRange(arr , mid , arr.length)) ;  
        
        return merge(left , right) ; 
    
    
    }

    static int[] merge(int[] first , int[] second){
        int[] mix = new int[first.length+second.length] ; 

        //i an j pointers for the first and second arrays . 
        int i=0; 
        int j=0;
        int k=0;  //the current index of the mix array , to basically track the elements position . 

        while(i<first.length && j<second.length){
            if(first[i] < second[j]){
                mix[k++] = first[i++] ; 
            }
            else{
                mix[k++] = second[j++] ;  
            }

        }


        //it may be possible that one of the arrays is not complete . 
        //copy the remaining elements . 

        while(i < first.length){
            mix[k++] = first[i++] ; 
        }

        while(j < second.length){
            mix[k++] = second[j++] ; 
        }

        return mix ; 

    }
}
