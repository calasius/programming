package programming.hackersrank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by calasius on 7/10/17.
 */
public class AngryChildren2 {

    public static void main(String ... args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/test1"));

        int N = scanner.nextInt();
        int K = scanner.nextInt();

        int[] numbers = new int[N];
        int[] diff = new int[N-K+1];

        for(int i = 0; i < N; i++) {
            numbers[i] = scanner.nextInt();
        }

        Arrays.sort(numbers);

        for (int i = 0; i < K; i++) {
            for (int j = i+1; j < K; j++) {
                diff[0] += numbers[j] - numbers[i];
            }
        }

        int res = diff[0];

        for (int i = 1; i < N - K ; i++) {
            diff[i] = diff[i-1];
            for (int j = i; j < K+i-1; j++) {
                diff[i] -= numbers[j] - numbers[K*(i-1)];
                diff[i] += numbers[K+i-1] - numbers[j];
            }
            if (diff[i] < res) {
                res = diff[i];
            }
        }

        System.out.println(res);
    }
}
