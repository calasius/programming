package programming.hackersrank.graph;

import java.util.*;

/** Created by claudio on 7/29/17. */
public abstract class BFSProcessor {

  protected Graph g;

  public void bfs(int start) {
    Queue<Integer> queue = new LinkedList();
    Set<Integer> discovered = new HashSet();
    Set<Integer> processed = new HashSet();

    queue.add(start);
    discovered.add(start);

    while (!queue.isEmpty()) {
      int v = queue.poll();
      processVertexEarly(v);
      processed.add(v);
      Set<WeightedNeighbord> neighbords = g.getNeighbors(v);
      for (WeightedNeighbord neighbord : neighbords) {
        if (!processed.contains(neighbord.id)) processEdge(v, neighbord);
        if (!discovered.contains(neighbord.id)) {
          queue.add(neighbord.id);
          discovered.add(neighbord.id);
          processTreeEdge(v, neighbord);
        }
      }
      processVertexLate(v);
    }
  }

  public abstract void processVertexEarly(int v);

  public abstract void processVertexLate(int v);

  public abstract void processEdge(int v, WeightedNeighbord u);

  public abstract void processTreeEdge(int v, WeightedNeighbord u);
}
