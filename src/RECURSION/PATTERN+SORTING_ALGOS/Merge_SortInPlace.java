import java.util.Arrays;

public class Merge_SortInPlace {
    
    public static void main(String[] args) {
        int[] arr = {8,4,3,12,5,6}  ;
        mergeSortInPlace(arr , 0 , arr.length) ; 
        System.out.println(Arrays.toString(arr));
    }

    static void mergeSortInPlace(int[] arr  , int s , int e){
        if(e-s == 1){  //agr diff. 1 ka  hai , it means that ki single index hi bacha hai so return . 
            return;
        }

        int mid = s + (e-s)/2 ; 

        mergeSortInPlace(arr , s , mid) ; //har part apna recursion tree banega and solve hoga . 
        mergeSortInPlace(arr , mid , e) ;
        //Imp point : sabka individual sorteing ke badd unke left and right correspoinding unke baad solve  hoaga , accor, to the recusion tree .   
        
        mergeInPlace(arr, s, mid , e ) ; 
    
    
    }

    static void mergeInPlace(int[] arr , int s , int mid , int e){
      int[] mix = new int[e-s] ; //firstly I need to make the array sortted using the given indices  , then I will append them accordibglt in thr main array .  

        //i an j pointers for the first and second arrays . 
        int i=s; 
        int j=mid;
        int k=0;  //the current index of the mix array , to basically track the elements position . 

        while(i<mid && j<e){   //yaha i<mid isisliye liya kyuki array 2 halves mai hai or vo alredy sorted hai then , mujhe bas mid tak jaake values comapre krni hai and unko temp arr i,e mix usme add krna hai . 
            if(arr[i] < arr[j]){
                mix[k++] = arr[i++] ; 
            }
            else{
                mix[k++] = arr[j++] ;  
            }

        }


        //it may be possible that one of the arrays is not complete . 
        //copy the remaining elements . 

        while(i < mid){
            mix[k++] = arr[i++] ; 
        }

        while(j < e){
            mix[k++] = arr[j++] ; 
        }

        //then ab start ke hisab se mai respective values put krunga , jisse ki mujhe overlappinf ka issue na aaye . 
        for (int l=0 ; l<mix.length ; l++){
            arr[s+l] = mix[l] ; 
        }

        

    }

}
