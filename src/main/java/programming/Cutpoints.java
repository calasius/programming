package programming;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.BlockCutpointGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.event.VertexSetListener;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by claudio on 12/29/16.
 */
public class Cutpoints {

    public static void main(String... args) {
        UndirectedGraph<Integer, DefaultEdge> g = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(6);

        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 5);
        g.addEdge(5, 2);
        g.addEdge(2, 4);
        g.addEdge(4, 1);
        g.addEdge(6, 1);

        Map<Integer, List<Set<Integer>>> connectedComponentsByCutpoint = connectedComponentsByCutpoint(g);

    }

    private static Map<Integer, List<Set<Integer>>> connectedComponentsByCutpoint(UndirectedGraph<Integer, DefaultEdge> g) {

        Map<Integer, List<Set<Integer>>> connectedComponentsByCutpoint = Maps.newHashMap();

        BlockCutpointGraph<Integer, DefaultEdge> cutpointGraph = new BlockCutpointGraph<Integer, DefaultEdge>(g);

        Set<Integer> cutpoints = cutpointGraph.getCutpoints();

        ConnectivityInspector<Integer, DefaultEdge> connectivityInspector = new ConnectivityInspector<Integer, DefaultEdge>(
            g);

        Map<Integer, Set<Integer>> neighborsByCutpoint = Maps.newHashMap();
        NeighborIndex<Integer, DefaultEdge> neighborIndex = new NeighborIndex<Integer, DefaultEdge>(g);

        for (Integer vertex : cutpoints) {
            neighborsByCutpoint.put(vertex, neighborIndex.neighborsOf(vertex));
        }


        for (Integer vertex : cutpoints) {
            if (hasSingleMemberConnectedComponent(neighborIndex, vertex)) {
                connectedComponentsByCutpoint.put(vertex, singleMemberConnectedComponents(neighborIndex, vertex, g));
            } else {
                g.removeVertex(vertex);
                connectedComponentsByCutpoint.put(vertex, connectivityInspector.connectedSets());
                reconstrucGraph(vertex, neighborsByCutpoint.get(vertex), g);
            }
        }

        return connectedComponentsByCutpoint;
    }

    private static void reconstrucGraph(Integer vertex, Set<Integer> neighbors, UndirectedGraph<Integer, DefaultEdge> g) {
        g.addVertex(vertex);
        for (Integer neighbor : neighbors) {
            g.addEdge(vertex, neighbor);
        }
    }

    private static boolean hasSingleMemberConnectedComponent(NeighborIndex<Integer, DefaultEdge> neighborIndex,
        Integer cutpoint) {
        for (Integer neighbor : neighborIndex.neighborsOf(cutpoint)) {
            if (neighborIndex.neighborsOf(neighbor).size() == 1) {
                return true;
            }
        }
        return false;
    }

    private static List<Set<Integer>> singleMemberConnectedComponents(NeighborIndex<Integer, DefaultEdge> neighborIndex,
        Integer cutpoint, UndirectedGraph<Integer, DefaultEdge> g) {

        List<Set<Integer>> connectedComponents = Lists.newArrayList();
        Set<Integer> singleMembers = Sets.newHashSet();

        for (Integer neighbor : neighborIndex.neighborsOf(cutpoint)) {
            if (neighborIndex.neighborsOf(neighbor).size() == 1) {
                singleMembers.add(neighbor);
                connectedComponents.add(Sets.newHashSet(neighbor));
            }
        }

        Set<Integer> otherComponent = Sets.newHashSet(g.vertexSet());
        otherComponent.removeAll(singleMembers);
        otherComponent.remove(cutpoint);
        connectedComponents.add(otherComponent);

        return connectedComponents;
    }
}
