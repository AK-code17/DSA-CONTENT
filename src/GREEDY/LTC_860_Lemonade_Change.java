package GREEDY;

public class LTC_860_Lemonade_Change {
    int n = bills.length;

        if(n == 0 ) return true;

        int ind = 0;
        int cnt_5 = 0;
        int cnt_10 = 0;
        
        while (ind < n){
            int cost = bills[ind]; //5..

            // no need to give change , increment cnt++;
            if(cost == 5) cnt_5++;

            else if(cost == 10){
                // first increment ++;
                cnt_10++;
                // check if $5 bill exists..
                if(cnt_5 > 0) cnt_5--;
                else return false;
            }

            else{
                
                // first try to spend $10 since , its only usable in $20 change , but saving here 2 * $5 bills , will help to sell atleast 2 more lemonades..
                if(cnt_10 >= 1 && cnt_5 >= 2) {
                    cnt_10--;
                    cnt_5 = cnt_5-2;
                }
                
                // this condtn will hit as backup , if $10 isn't there..
                else if(cnt_5 >= 3){
                    cnt_5 = cnt_5-3;
                }

                // if both condth not fulfilled then "false" is only option..
                else{
                    return false;
                }

            }

            ind++;

        }

        // if came out of loop hence all elements traversed , and successful change transaction happened.. 
        return true;
    }
}
