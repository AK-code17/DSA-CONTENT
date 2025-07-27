import java.util.*;

public class Bin_search_Recurrsion {
    
    public static void main(String[] args) {
        int[] nums = {0,2,3,4};
        System.out.println("Enter the number to search");
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        int ans = Bin_search_Recurrsion(nums, n, 0, nums.length-1);
        System.out.println("Element is present at index : "+ ans);
    }

    static int Bin_search_Recurrsion(int [] nums ,int target , int start , int end){
        if(start>end){
            return -1;           //Also missed this imp , Checking the base condition here . 
        }
        
        int mid = start+(end-start)/2;
        
        if(nums[mid]==target){
            return mid;              
        }
        else if(nums[mid]>target){
            return Bin_search_Recurrsion(nums, target, start, mid-1);  //here missed the return part while writing the c0de . 
        }
        else{
            return Bin_search_Recurrsion(nums, target, mid+1, end);     //same here also . 
        }
        

    }
}
