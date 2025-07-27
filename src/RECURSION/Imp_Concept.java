public class Imp_Concept {
    
    public static void main(String[] args) {
        System.out.println(Concept(5));
    }

    static int Concept(int n){
        if (n==0) {
            return 0;
        }
        System.out.println(n);
        // return Concept(n--); //here n-- is first passing the value 5 then returning  , instead first decrement then pass . 
        return Concept(--n);   //here use --n , to subtract then pass the value . 
    }
}
