package programming.hackersrank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/** Created by claudio on 7/14/17. */
public class CoinChange {

  public static long findSolution(int number, int[] coins) {

    Arrays.sort(coins);

    long[][] partialSolutions = new long[number + 1][coins.length + 1];

    for (int i = 0; i < coins.length; ++i) {
      if (coins[i] <= number) {
        partialSolutions[coins[i]][i] = 1L;
        partialSolutions[coins[i]][coins.length] = 1L;
      }
    }

    for (int i = 1; i <= number; i++) {
      long total = partialSolutions[i][coins.length];
      for (int j = 0; j < coins.length; j++) {
        if (i - coins[j] > 0) {
          final int diff = i - coins[j];
          long sum = 0L;
          for (int k = j; k < coins.length; k++) {
            sum += partialSolutions[diff][k];
          }
          partialSolutions[i][j] = sum;
          total += sum;
        }
      }
      partialSolutions[i][coins.length] = total;
    }

    return partialSolutions[number][coins.length];
  }

  public static void main(String... args) throws FileNotFoundException {

    Scanner scanner = new Scanner(new File("src/main/resources/hackersRankTests/CoinChangeTest1"));

    int number = scanner.nextInt();
    int coinAmount = scanner.nextInt();
    int[] coins = new int[coinAmount];
    IntStream.range(0, coinAmount).forEach(index -> coins[index] = scanner.nextInt());

    System.out.println(findSolution(number, coins));
  }
}
