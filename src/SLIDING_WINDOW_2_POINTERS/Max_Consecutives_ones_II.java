public class Max_Consecutives_ones_II  {
  public static void main(String args[]) {
      int nums[] = { 1,0,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1 };
      int ans = for_maxConsecutiveOnes(nums);
      System.out.println("The maximum  consecutive 1's by flipping only single 0 bit :  " + ans);
  }

  //using while loop
  public static int while_maxConsecutiveOnes(int[] nums){
    int start = 0;
    int end = 0;
    int zero_cnt = 0;
    int max = 0;

    while(end < nums.length){
      
      if(nums[end] ==0 ) zero_cnt++;
      
      if(zero_cnt > 1){
        if(nums[start] == 0) zero_cnt--;
        start++;
      }

      //this is the else part where the zero_cnt < 2 or ele == 1 . 
      end++;
      max = Math.max(max,end-start);
    }

    ///here also no need to return Math.max(max,end-start) , as it gets updated at end of every iteration . 
    return max;
  }

  //using for loop
  public static int for_maxConsecutiveOnes(int[] nums){
    int start = 0;
    int zero_cnt = 0;
    int max = 0;

    for(int end=0 ; end<nums.length ; end++){

      if(nums[end] ==0 ) zero_cnt++;

      if(zero_cnt > 1){
        if(nums[start] == 0) zero_cnt--;
        start++;
      }
      
      max = Math.max(max,end-start +1);  //end-start+1 , because here we are not appending the end before the next loop , it will get automatically while moving forward .
    
    }

    ///here also no need to return Math.max(max,end-start) , as it gets updated at end of every iteration .
    return max;
  
  }

}