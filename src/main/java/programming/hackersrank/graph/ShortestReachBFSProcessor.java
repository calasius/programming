package programming.hackersrank.graph;

import java.util.*;
import java.util.stream.Collectors;

/** Created by claudio on 7/29/17. */
public class ShortestReachBFSProcessor extends BFSProcessor {

  private Map<Integer, Double> distancies;
  private int start;

  public ShortestReachBFSProcessor(Graph g, int start) {
    distancies = new HashMap();
    this.g = g;
    this.start = start;
  }

  @Override
  public void processVertexEarly(int v) {}

  @Override
  public void processVertexLate(int v) {}

  @Override
  public void processEdge(int v, WeightedNeighbord u) {}

  @Override
  public void processTreeEdge(int v, WeightedNeighbord u) {
    distancies.put(u.id, u.weight + distancies.getOrDefault(v, 0.0));
  }

  public List<Integer> getDistancies() {
    List<Integer> vertices = new ArrayList();
    vertices.addAll(g.getVertices());
    Collections.sort(vertices);
    vertices.remove(Integer.valueOf(start));
    return vertices
        .stream()
        .map(v -> this.distancies.getOrDefault(v, -1.0).intValue())
        .collect(Collectors.toList());
  }
}
