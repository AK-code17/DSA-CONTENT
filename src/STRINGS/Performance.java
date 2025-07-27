public class Performance {
    public static void main(String[] args) {
        String series = "";
        for (int i = 0; i < 26; i++) {
            char ch = (char)('a'+i);
            // System.out.println(ch);
            series+=ch+" ";
        }
        System.out.println(series);
        
        //Above soln has O(N^2) complexity since after each iteration new obj is created and then the string is modified and also all the old objs has no ref variables . 

        

        
    
    
    
    
    }
    
}
