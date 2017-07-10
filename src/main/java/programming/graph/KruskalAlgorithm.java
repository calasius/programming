package programming.graph;

import com.google.common.collect.Interner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.math3.geometry.partitioning.utilities.OrderedTuple;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by claudio on 7/5/17.
 */
public class KruskalAlgorithm {

    public static void main(String ... args) {
        Pair<Set<Integer>,List<Edge>> g = createGraph();

        UF<Integer> ds = new UF(g.first);

        Set<Edge> tree = Sets.newHashSet();
        g.second.forEach(edge -> {
            if (ds.connected(edge.getX(),edge.getY())) {
                tree.add(edge);
                ds.union(edge.getX(),edge.getY());
            }
        });
    }

    private static Pair createGraph() {

        List<Edge> edges = Lists.newArrayList();

        edges.add(new Edge(1, 2,1));
        edges.add(new Edge(1, 3,3));
        edges.add(new Edge(2, 3,4));
        edges.add(new Edge(3, 4,5));
        edges.add(new Edge(2, 4,6));
        edges.add(new Edge(2, 5,7));
        edges.add(new Edge(4, 5,2));

        edges.sort(Comparator.comparing(e -> Integer.valueOf(e.getWeight())));
        Set<Integer> nodes = Sets.newHashSet(0,1,2,3,4,5,6,7,8,9);
        return new Pair(nodes, edges);
    }
}

class Edge {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWeight() {
        return weight;
    }

    private int weight;

    public Edge(int x, int y, int weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }
}
