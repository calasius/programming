package programming.hackersrank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/** Created by claudio on 7/18/17. */
public class MaximumGCDAndSum {

  private static List<Integer> primes = sieve();

  private static List<Integer> sieve() {
    int N = 1000;
    int[] lp = new int[N + 1];
    List<Integer> pr = new ArrayList();
    for (int i = 2; i <= N; ++i) {
      if (lp[i] == 0) {
        lp[i] = i;
        pr.add(i);
      }
      for (int j = 0; j < pr.size() && pr.get(j) <= lp[i] && i * pr.get(j) <= N; ++j)
        lp[i * pr.get(j)] = pr.get(j);
    }
    return pr;
  }

  private static int[] divisors(int number, int minDivisor) {
    int n = number;
    List<Pair> primeFactors = new ArrayList<>();
    for (int i = 0; i < primes.size() && primes.get(i) <= Math.sqrt(n); i++) {
      int p = primes.get(i);
      int pot = 0;
      while (number % p == 0) {
        number /= p;
        pot++;
      }
      if (pot > 0) primeFactors.add(new Pair(p, pot));
    }

    if (number > 1) primeFactors.add(new Pair(number, 1));

    int divisorsCount = 1;
    for (Pair p : primeFactors) {
      divisorsCount *= p.y + 1;
    }

    int[] divs = new int[divisorsCount];
    divs[0] = 1;

    int count = 1;
    for (int stack_level = 0; stack_level < primeFactors.size(); ++stack_level) {
      int count_so_far = count;
      int prime = primeFactors.get(stack_level).x;
      int exponent = primeFactors.get(stack_level).y;
      int multiplier = 1;

      for (int j = 0; j < exponent; ++j) {
        multiplier *= prime;
        for (int i = 0; i < count_so_far; ++i) {
          divs[count++] = divs[i] * multiplier;
        }
      }
    }

    return divs;
  }

  public static void main(String[] args) throws FileNotFoundException {
    divisors(1000, 0);
    Scanner in = new Scanner(new File("src/main/resources/hackersRankTests/MaximumGCDAndSumTest5"));
    int n = in.nextInt();
    Integer[] A = new Integer[n];
    int maxA = 0;
    for (int A_i = 0; A_i < n; A_i++) {
      A[A_i] = in.nextInt();
      if (A[A_i] > maxA) {
        maxA = A[A_i];
      }
    }
    Integer[] B = new Integer[n];
    int maxB = 0;
    for (int B_i = 0; B_i < n; B_i++) {
      B[B_i] = in.nextInt();
      if (B[B_i] > maxB) {
        maxB = B[B_i];
      }
    }
    int res = maximumGcdAndSum1(A, B, maxA, maxB);
    System.out.println(res);
  }

  static int maximumGcdAndSum1(Integer[] A, Integer[] B, int maxA, int maxB) {

    if (A.length == 1) {
      return A[0] + B[0];
    }

    Map<Integer, Map.Entry<Integer, Integer>> numbersWithDivisors = new HashMap();

    for (int i = 0; i < A.length; i++) {
      int[] divisors = divisors(A[i], 0);
      for (Integer div : divisors) {
        if (numbersWithDivisors.containsKey(div)) {
          int key = numbersWithDivisors.get(div).getKey();
          if (A[i] > key) {
            numbersWithDivisors.put(div, new AbstractMap.SimpleEntry(A[i], -A[i]));
          }
        } else {
          numbersWithDivisors.put(div, new AbstractMap.SimpleEntry(A[i], -A[i]));
        }
      }
    }

    int maxGcd = 0;
    for (int i = 0; i < B.length; i++) {
      if (B[i] > maxGcd) {
        int[] divisors = divisors(B[i], maxGcd);
        for (Integer div : divisors) {
          if (div > maxGcd) {
            if (numbersWithDivisors.containsKey(div)) {
              int value = numbersWithDivisors.get(div).getValue();
              if (B[i] > value) {
                if (div > maxGcd) maxGcd = div;
                numbersWithDivisors.get(div).setValue(B[i]);
              }
            }
          }
        }
      }
    }

    if (maxGcd > 0) {
      return numbersWithDivisors.get(maxGcd).getKey() + numbersWithDivisors.get(maxGcd).getValue();
    } else {
      return maxA + maxB;
    }
  }

  static class Pair {
    public int x;
    public int y;

    public Pair(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}
