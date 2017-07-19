package programming.hackersrank;

import java.util.*;

/** Created by claudio on 7/18/17. */
public class MaximumGCDAndSum {

  static int maximumGcdAndSum(Integer[] A, Integer[] B) {
    // Complete this function

    Arrays.sort(A, Collections.reverseOrder());
    Arrays.sort(B, Collections.reverseOrder());
    Queue<Element> heap = new PriorityQueue();

    Element result = new Element(0, 0, A[0] + B[0], gcd(A[0], B[0]));
    int j = 0;
    int i = 0;

    while (i < A.length && j < A.length) {
      heap.add(new Element(i + 1, j, A[i + 1] + B[j], gcd(A[i + 1], B[j])));
      Element next = heap.peek();
      while (A[i] + B[j] >= next.sum) {
        int gcd = gcd(A[i], B[j]);
        if (gcd > result.gcd) {
          result = new Element(i, j, A[i] + B[j], gcd);
        }
        j++;
      }
    }

    return 0;
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    Integer[] A = new Integer[n];
    for (int A_i = 0; A_i < n; A_i++) {
      A[A_i] = in.nextInt();
    }
    Integer[] B = new Integer[n];
    for (int B_i = 0; B_i < n; B_i++) {
      B[B_i] = in.nextInt();
    }
    int res = maximumGcdAndSum(A, B);
    System.out.println(res);
  }

  private static int gcd(int n1, int n2) {
    while (n1 * n2 != 0) {
      if (n1 >= n2) n1 = n1 % n2;
      else n2 = n2 % n1;
    }
    return (n1 + n2);
  }

  static class Element implements Comparable<Element> {
    public int i;
    public int j;
    public Integer sum;
    public int gcd;

    public Element(int i, int j, int sum, int gcd) {
      this.i = i;
      this.j = j;
      this.sum = sum;
      this.gcd = gcd;
    }

    @Override
    public int compareTo(Element o) {
      return this.sum.compareTo(o.sum);
    }
  }
}
