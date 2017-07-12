package programming.hackersrank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by calasius on 7/10/17.
 */
public class AngryChildren2 {

    public static void main(String ... args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/AngryChildren2/test1"));

        int N = scanner.nextInt();
        int K = scanner.nextInt();

        long[] numbers = new long[N];

        for(int i = 0; i < N; i++) {
            numbers[i] = scanner.nextLong();
        }

        Arrays.sort(numbers);

        findSolution1(N, K, numbers);
    }

    private static void findSolution(int n, int k, long[] numbers) {
        long[] diff = new long[n - k +1];
        for (int i = 0; i < k; i++) {
            for (int j = i+1; j < k; j++) {
                diff[0] += numbers[j] - numbers[i];
            }
        }

        long res = diff[0];

        for (int i = 1; i <= n - k; i++) {
            diff[i] = diff[i-1];
            for (int j = i; j < k +i-1; j++) {
                diff[i] -= (numbers[j] - numbers[i-1]);
                diff[i] += (numbers[k +i-1] - numbers[j]);
            }
            if (diff[i] < res) {
                res = diff[i];
            }
        }

        System.out.println(res);
    }

    private static void findSolution1(int n, int k, long[] numbers) {
        long[] diff = new long[n - k +1];
        for (int i = 0; i < k; i++) {
            for (int j = i+1; j < k; j++) {
                diff[0] += numbers[j] - numbers[i];
            }
        }

        long A = getA(k, numbers);
        long B = getB(k, numbers);
        long C = getC(k, numbers);
        long D = getD(k, numbers);

        long res = diff[0];

        for (int i = 1; i <= n - k; i++) {
            long vp = A;
            diff[i] = diff[i-1];
            diff[i] -= A;
            diff[i] += C;
            A -= (k-1)*(numbers[i] - numbers[i-1]);
            B += (numbers[k +i-1] - numbers[k +i-2]);
            A += B;
            B -= (numbers[i+1] - numbers[i]);

            long vn = C;
//            C -= (k-1)*(numbers[k +i-1] - numbers[k +i-2]);
            C -= D;
            if (i != n-k) {
                C += (k-1)*(numbers[k +i] - numbers[k +i-1]);
                D += (numbers[k +i] - numbers[k +i-1]);
            }
            D -= (numbers[i+1]-numbers[i]);
            long v = 0;
            long f = 0;

//            for (int j = i; j < k +i-1; j++) {
//                v += (numbers[j] - numbers[i-1]);
//                f += (numbers[k +i-1] - numbers[j]);
//                diff[i] += (numbers[k +i-1] - numbers[j]);
//            }
//            if (vp != v) {
//                throw new RuntimeException("A "+vp + " " + v + " " +i);
//            }
//            if (vn != f) {
//                throw new RuntimeException("C "+vn + " " + f + " " +i);
//            }
            if (diff[i] < res) {
                res = diff[i];
            }
        }

        System.out.println(res);
    }

    private static long getB(int k, long[] numbers) {
        long B = 0;
        for (int i = 1; i < k-1; i++) {
            B += (numbers[i+1]-numbers[i]);
        }
        return B;
    }

    private static long getA(int k, long[] numbers) {
        long A = 0;
        for (int i = 0; i < k-1; i++) {
            A += (k-i-1)*(numbers[i+1]-numbers[i]);
        }
        return A;
    }

    private static long getD(int k, long[] numbers) {
        long D = 0;
        for (int i = 1; i <= k-1; i++) {
            D += (numbers[i+1]-numbers[i]);
        }
        return D;
    }

    private static long getC(int k, long[] numbers) {
        long C = 0;
        for (int i = 1; i <= k-1; i++) {
            C += (k-i)*(numbers[k-i+1]-numbers[k-i]);
        }
        return C;
    }
}
