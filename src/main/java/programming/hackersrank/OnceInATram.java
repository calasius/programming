package programming.hackersrank;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Created by claudio on 7/18/17.
 */
public class OnceInATram {

    static String onceInATram(int x) {

        int sum_izq = getSum(x);
        int res = minLucky(x, sum_izq);
        LuckyIterator iterator = new LuckyIterator(res, sum_izq);
        while(iterator.hasNext() && res <= x) {
            res = iterator.next();
        }

        if (res <= x) {
            int izq = (x / 1000) +1;
            res = minLucky(izq*1000, getSum(izq*1000));
        }

        return ""+res;

    }

    private static int minLucky(int x, int sum_izq) {

        int d3 = Math.min(9,sum_izq);
        int d2 = Math.min(9,sum_izq-d3);
        int d1 = Math.min(9,sum_izq-d3-d2);
        return  (x / 1000)*1000 + d1*100+d2*10+d3;
    }

    private static int getSum(int x) {
        int[] digits = getDigits(x);
        return IntStream.range(0,3).map(i -> digits[i]).sum();
    }

    private static int[] getDigits(int x) {
        String number = Integer.toString(x);
        int[] digits = new int[6];

        for (int i = 0; i < 6; i++) {
            digits[i] = number.charAt(i)-48;
        }
        return digits;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        String result = onceInATram(x);
        System.out.println(result);
    }

    static class LuckyIterator {
        int sum_izq;
        int n;
        int d1;
        int d2;
        int d3;

        public LuckyIterator(int n, int sum_izq) {
            this.sum_izq = sum_izq;
            this.n = n;
            d1 = n % 1000 / 100;
            d2 = n % 100 / 10;
            d3 = n % 10;
        }

        public int next() {
            if (d2 < 9 && d3 > 0) {
                d2++;
                d3--;
            } else if (d1 < 9 && d2 > 0) {
                d1++;
                d3 = Math.min(9, sum_izq - d1);
                d2 = Math.min(9, sum_izq -d1 -d3);
            }
            return (n / 1000)*1000 + d1*100 + d2*10 +d3;
        }

        public boolean hasNext() {
            if (d2 < 9 && d3 > 0) {
                return true;
            } else if (d1 < 9 && d2 > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}
