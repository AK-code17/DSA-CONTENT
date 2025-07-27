public class smll_lettr_than_trgt {
    public static void main(String[] args) {
        char[]arr={'c','f','j'};
        char target = 'd';
        // System.out.println("The Ceiling of "+target+" is "+Ceiling(arr, target));
        // System.out.println("The Greatestletter of "+target+" is "+nextGreatestLetter(arr, target));
 
    }

    static char nextGreatestLetter(char[]letters,char target){
        int start=0;
        int end = letters.length-1;
        
        //With Binary Search . 
        while (start<=end) {
            
            int mid = start+(end-start)/2;
            // if (letters[mid]==target) {
            //     return letters[mid];
            // }
            if (target<letters[mid]) {
                end=mid-1;
            }
            else{
                start=mid+1;
            }
        }
        return letters[start%letters.length];// important bcz we can actually have no need to write the if condition if the start is at end . 

        //It will always return the start untill start = arr.lenght-1 . 
        //If it's arr.length then ans would be 0 . That's what we want from it . 

    }
}
