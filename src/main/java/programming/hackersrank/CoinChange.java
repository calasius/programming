package programming.hackersrank;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Created by claudio on 7/14/17.
 */
public class CoinChange {

    public static void findSolution(int number, int[] coins) {

        List<List<List<Integer>>> partialSolutions = new ArrayList<>();
        List<Integer> firstSolution = new ArrayList<>();
        firstSolution.add(1);
        List<List<Integer>> solution = new ArrayList<>();
        solution.add(firstSolution);

        for (int i = 2; i <= number; i++) {
            List<List<Integer>> actual = new ArrayList<>();
            List<List<Integer>> previous = partialSolutions.get(i-1);
            for (List<Integer> sol : previous) {

            }
        }

    }

    public static void main(String ... args) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("src/main/resources/hackersRankTests/coinChangeTest1"));

        int number = scanner.nextInt();
        int coinAmount = scanner.nextInt();
        int[] coins = new int[coinAmount];
        IntStream.range(0,coinAmount).forEach(index -> coins[index] = scanner.nextInt());

        findSolution(number, coins);

    }
}
