package programming.hackersrank.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

/** Created by claudio on 7/29/17. */
public class ShortestReach {

  public static void main(String... args) throws FileNotFoundException {
    Scanner scanner =
        new Scanner(new File("src/main/resources/hackersRankTests/graph/shortestReach1"));

    int queries = scanner.nextInt();

    for (int i = 0; i < queries; ++i) {
      int nodeAmount = scanner.nextInt();
      int edgesAmount = scanner.nextInt();

      Set<Integer> vertices =
          IntStream.range(1, nodeAmount + 1).collect(HashSet::new, HashSet::add, HashSet::addAll);
      Graph g = new Graph(vertices);

      for (int j = 0; j < edgesAmount; ++j) {
        int origin = scanner.nextInt();
        int dest = scanner.nextInt();
        g.addEdge(origin, dest, 6.0);
      }

      int start = scanner.nextInt();
      ShortestReachBFSProcessor bfsProcessor = new ShortestReachBFSProcessor(g, start);
      bfsProcessor.bfs(start);
      listToSout(bfsProcessor.getDistancies());
      System.out.println();
    }
  }

  public static void listToSout(List<Integer> distancies) {
    distancies.forEach(d -> System.out.print(d + " "));
  }
}
