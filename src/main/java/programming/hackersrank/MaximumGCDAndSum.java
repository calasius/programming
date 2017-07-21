package programming.hackersrank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/** Created by claudio on 7/18/17. */
public class MaximumGCDAndSum {

  private static int gcd(int n1, int n2) {
    while (n1 * n2 != 0) {
      if (n1 >= n2) n1 = n1 % n2;
      else n2 = n2 % n1;
    }
    return (n1 + n2);
  }

  public static void main(String[] args) throws FileNotFoundException {
    Scanner in = new Scanner(new File("src/main/resources/hackersRankTests/MaximumGCDAndSumTest4"));
    int n = in.nextInt();
    Integer[] A = new Integer[n];
    for (int A_i = 0; A_i < n; A_i++) {
      A[A_i] = in.nextInt();
    }
    Integer[] B = new Integer[n];
    for (int B_i = 0; B_i < n; B_i++) {
      B[B_i] = in.nextInt();
    }
    int res = maximumGcdAndSum1(A, B);
    System.out.println(res);
  }

  static int maximumGcdAndSum1(Integer[] A, Integer[] B) {

      if (A.length == 1) {
        return A[0]+B[0];
      }

    Map<Integer, Map.Entry<Integer, Integer>> numbersWithDivisors = new HashMap();

    for (int i = 0; i < A.length; i++) {
      numbersWithDivisors.put(A[i], new AbstractMap.SimpleEntry<Integer, Integer>(A[i], -A[i]));
      for (int j = 2; j <= Math.sqrt(A[i]) + 1; j++) {
        if (A[i] != j && A[i] % j == 0) {
          numbersWithDivisors.put(j, new AbstractMap.SimpleEntry<Integer, Integer>(A[i], -A[i]));
          numbersWithDivisors.put(
                  A[i] / j, new AbstractMap.SimpleEntry<Integer, Integer>(A[i], -A[i]));
        }
      }
    }

    int maxDiv = 1;
    for (int i = 0; i < B.length; i++) {
      Map.Entry<Integer, Integer> e = numbersWithDivisors.get(B[i]);
      if (e != null && e.getValue() < B[i]) {
        e.setValue(B[i]);
        maxDiv = Math.max(maxDiv, B[i]);
      }

      int divisor = maxDiv+1;
      while(divisor <= Math.sqrt(B[i]) + 1) {
        if (B[i] != divisor && B[i] % divisor == 0) {
          e = numbersWithDivisors.get(divisor);
          if (e != null && e.getValue() < B[i]) {
            e.setValue(B[i]);
            maxDiv = Math.max(maxDiv, divisor);
          }
          e = numbersWithDivisors.get(B[i] / divisor);
          if (e != null && e.getValue() < B[i]) {
            e.setValue(B[i]);
            maxDiv = Math.max(maxDiv, B[i] / divisor);
          }
          divisor = maxDiv+1;
        } else {
          divisor ++;
        }
      }
    }

    Integer maxGcd =
            numbersWithDivisors
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().getKey().intValue() + e.getValue().getValue() > 0)
                    .map(e -> e.getKey().intValue())
                    .max(Integer::compareTo)
                    .get();

    return numbersWithDivisors.get(maxGcd).getKey() + numbersWithDivisors.get(maxGcd).getValue();
  }

  private static int updateMaxDivisor(int max, int j) {
    if (j > max) {
      max = j;
    }
    return max;
  }
}