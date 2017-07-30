package programming.hackersrank.graph;

import java.util.*;

/** Created by claudio on 7/29/17. */
public class Graph {
  private Map<Integer, Set<WeightedNeighbord>> adjacencies;

  public Graph(Set<Integer> nodes) {
    this.adjacencies = new HashMap();
    nodes.forEach(id -> this.adjacencies.put(id, new HashSet()));
  }

  public void addEdge(int origin, int destination, double weight) {
    this.adjacencies.get(origin).add(new WeightedNeighbord(weight, destination));
    this.adjacencies.get(destination).add(new WeightedNeighbord(weight, origin));
  }

  public Set<WeightedNeighbord> getNeighbors(int id) {
    return this.adjacencies.get(id);
  }

  public Set<Integer> getVertices() {
    return adjacencies.keySet();
  }
}

class WeightedNeighbord {
  public double weight;
  public int id;

  public WeightedNeighbord(double weight, int id) {
    this.weight = weight;
    this.id = id;
  }
}
