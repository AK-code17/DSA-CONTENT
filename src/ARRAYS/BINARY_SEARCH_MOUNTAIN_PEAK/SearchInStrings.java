import java.util.Arrays;

public class SearchInStrings {
    public static void main(String[] args) {
        String name = "Aryan";
        char target = 'n';
        System.out.println(Arrays.toString(name.toCharArray()));
        System.out.println(Search(name, target));
        System.out.println(Search2(name, target));
    }

    
    static boolean Search2(String str , char target){
        if (str.length()==0) {
            return false;
        }
        for (char element : str.toCharArray()) {
            if (element==target) {
                return true;
            }
        }
        return false;
    }
    
    
    static boolean Search(String str , char target){
        if (str.length()==0) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i)==target) {
                return true;
            }

        }
        return false;

    }
}
