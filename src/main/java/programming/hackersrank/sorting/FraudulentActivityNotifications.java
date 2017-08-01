package programming.hackersrank.sorting;

import java.util.Arrays;
import java.util.Scanner;

/** Created by claudio on 8/1/17. */
public class FraudulentActivityNotifications {

  static int activityNotifications(int[] expenditure, int d) {
    if (d <= 5) {
      int notifications = 0;
      for (int i = 0; i < expenditure.length - d; ++i) {
        Arrays.sort(expenditure, i, i + d - 1);
        int mid_index = i + d / 2;
        double median = 0;
        if (d % 2 == 1) {
          median = expenditure[i + mid_index];
        } else {
          median = (expenditure[mid_index] + expenditure[mid_index + 1]) / (double) 2;
        }
        if (expenditure[i + d] > median) ++notifications;
        return notifications;
      }
    } else {

    }

    return 0;
  }

  public static int[] mediansOfMedians(int[] A, int pivot) {}

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int d = in.nextInt();
    int[] expenditure = new int[n];
    for (int expenditure_i = 0; expenditure_i < n; expenditure_i++) {
      expenditure[expenditure_i] = in.nextInt();
    }
    int result = activityNotifications(expenditure, d);
    System.out.println(result);
    in.close();
  }
}
