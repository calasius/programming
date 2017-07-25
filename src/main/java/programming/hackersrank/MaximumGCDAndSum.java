package programming.hackersrank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by claudio on 7/18/17.
 */
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
        long ti = System.currentTimeMillis();
        Scanner in = new Scanner(new File("src/main/resources/hackersRankTests/test1"));
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
        long tf = System.currentTimeMillis();
        System.out.println(tf - ti);
        System.out.println(res);

    }

    static int maximumGcdAndSum1(Integer[] A, Integer[] B, int maxA, int maxB) {

        if (A.length == 1) {
            return A[0] + B[0];
        }

        int[] divsA = new int[Math.max(maxA, maxB) + 1];
        int[] divsB = new int[Math.max(maxA, maxB) + 1];
        Map<Integer, Map.Entry<Integer, Integer>> numbersWithDivisors = new HashMap();

        for (int i = 0; i < A.length; i++) {
            DivisorIterator iterator = new DivisorIterator(A[i]);
            //int[] divisors = divisors(A[i], 0);
            while (iterator.hasNext()) {
                //for (Integer div : divisors) {
                int div = iterator.nextDivisor();
                if (A[i] > divsA[div]) {
                    divsA[div] = A[i];
                }
            }
        }

        int maxGcd = 0;
        for (int i = 0; i < B.length; i++) {
            if (B[i] > maxGcd) {
                DivisorIterator iterator = new DivisorIterator(B[i]);
                //int[] divisors = divisors(B[i], 0);
                while (iterator.hasNext()) {
                    //for (Integer div : divisors) {
                    int div = iterator.nextDivisor();
                    if (B[i] > divsB[div] && divsA[div] > 0) {
                        if (div > maxGcd) maxGcd = div;
                        divsB[div] = B[i];
                    }
                }
            }
        }

        return divsA[maxGcd] + divsB[maxGcd];
    }

    public static int[] divisorsIterator(int number) {
        DivisorIterator iterator = new DivisorIterator(number);
        int[] divisors = new int[iterator.divisorsCount];
        int i = 0;
        while (iterator.hasNext()) {
            divisors[i] = iterator.nextDivisor();
            i++;
        }
        return divisors;
    }

    static class Pair {
        public int x;
        public int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class DivisorIterator {

        private List<Pair> primeFactors;
        private int divisorsCount;
        private int count = 1;
        private int count_so_far = count;
        private int stack_level = 0;
        private int exponent;
        private int prime;
        private int next = 1;
        private int i = -1;
        private int j = 0;
        private int[] divs;
        private int multiplier;

        public DivisorIterator(int number) {
            int n = number;
            primeFactors = new ArrayList<>();
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

            divisorsCount = 1;
            for (Pair p : primeFactors) {
                divisorsCount *= p.y + 1;
            }

            exponent = primeFactors.size() > 0 ? primeFactors.get(stack_level).y : 0;
            prime = primeFactors.size() > 0 ? primeFactors.get(stack_level).x : 0;
            divs = new int[divisorsCount];
            divs[0] = 1;
            multiplier = prime;
        }

        public boolean hasNext() {
            return count <= divisorsCount;
        }

        public int nextDivisor() {
            if (divisorsCount == 1) {
                count++;
                return 1;
            }
            int res = next;

            if (++i < count_so_far) {
                divs[count] = divs[i] * multiplier;
                next = divs[count];
                ++count;
            } else if (++j < exponent) {
                multiplier *= prime;
                i = 0;
                divs[count] = divs[i] * multiplier;
                next = divs[count];
                ++count;
            } else if (++stack_level < primeFactors.size()) {
                count_so_far = count;
                prime = primeFactors.get(stack_level).x;
                exponent = primeFactors.get(stack_level).y;
                multiplier = 1;
                multiplier *= prime;
                i = 0;
                j = 0;
                divs[count] = divs[i] * multiplier;
                next = divs[count];
                ++count;
            } else {
                ++count;
            }
            return res;
        }
    }
}
