package programming.hackersrank;

/**
 * Created by claudio on 7/13/17.
 */
public class BuildPalindrome {

    public static boolean isPalindrome(String s) {
        int l = s.length();
        for (int i = 0; i < l/2; i++) {
            if (s.charAt(i) != s.charAt(l-i-1))
                return false;
        }
        return true;
    }

    public static void main(String ... args) {
        System.out.println(isPalindrome("abanaba"));
    }
}
