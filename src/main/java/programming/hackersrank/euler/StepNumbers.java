package programming.hackersrank.euler;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by calasius on 7/26/17.
 */
public class StepNumbers {

    public static Pair<Integer, Integer> indexStepNumber(String number) {
        boolean step = true;
        while (step) {

        }
        return null;
    }

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        String number = scanner.nextLine();
        System.out.println(Integer.valueOf("" + number.charAt(0)));
    }
}

class Pair<A, B> {
    public A left;
    public B right;

    public Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }
}
