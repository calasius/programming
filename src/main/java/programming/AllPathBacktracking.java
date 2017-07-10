package programming;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.BlockCutpointGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

/**
 * Created by claudio on 12/28/16.
 */
public class AllPathBacktracking
    extends Backtracking<UndirectedGraph<Integer, DefaultEdge>, Integer> {
    private NeighborIndex<Integer, DefaultEdge> neighborIndex;
    private Map<Integer, List<Set<Integer>>> connectedComponentsByCutpoint;
    private boolean boost;

    public AllPathBacktracking(UndirectedGraph<Integer, DefaultEdge> structure, Integer input, boolean boost) {
        super(structure, input);
        this.neighborIndex = new NeighborIndex<Integer, DefaultEdge>(structure);
        this.connectedComponentsByCutpoint = connectedComponentsByCutpoint(this.structure);
        this.boost = boost;
    }

    @Override
    protected void unmakeLastMove(List<Integer> partialSolution) {
        partialSolution.remove(partialSolution.size() - 1);
    }

    @Override
    protected void makeMove(Integer element, List<Integer> partialSolution) {
        partialSolution.add(element);
    }

    @Override
    protected List<Integer> constructCandidates(List<Integer> partialSolution) {
        Set<Integer> inSol = Sets.newHashSet(partialSolution);
        Integer lastElement = partialSolution.get(partialSolution.size() - 1);

        // Is lastElement is a cutpoint then fisout if destination vertex is in conneted component if doesn't return []
        boolean isCutpoint = this.connectedComponentsByCutpoint.containsKey(lastElement);
        List<Integer> candidates = Lists.newArrayList();
        List<Integer> neighbors = this.neighborIndex.neighborListOf(lastElement);
        for (Integer vertex : neighbors) {
            if (!inSol.contains(vertex)) {
                if (isCutpoint && this.boost) {
                    Set<Integer> component = findoutComponentOf(vertex, this.connectedComponentsByCutpoint.get(lastElement));
                    if (component.contains(this.input)) {
                        candidates.add(vertex);
                    }
                } else {
                    candidates.add(vertex);
                }
            }
        }
        return candidates;
    }

    private Set<Integer> findoutComponentOf(Integer vertex, List<Set<Integer>> components) {
        for (Set<Integer> component : components) {
            if (component.contains(vertex)) {
                return component;
            }
        }
        return null;
    }

    @Override
    protected boolean isASolution(List<Integer> partialSolution) {
        return partialSolution.get(partialSolution.size() - 1) == this.input;
    }

    @Override
    protected List<Integer> initialPartialSolution() {
        return Lists.newArrayList(7);
    }

    public static void main(String... args) {

        /*for (int i = 5; i < 13; i++) {
            UndirectedGraph<Integer, DefaultEdge> g = createCompleteGraph(i);
            Long begin = System.currentTimeMillis();
            Backtracking<UndirectedGraph<Integer, DefaultEdge>, Integer> backtracking = new AllPathBacktracking(g, 4);
            List<List<Integer>> solutions = backtracking.backtrack();
            Long end = System.currentTimeMillis();
            System.out.print(i + "," + solutions.size() + "," + (end - begin));
            System.out.println();
        }*/

        for (int i = 5; i < 20; i++) {
            UndirectedGraph<Integer, DefaultEdge> g = createTree(i, 3);
            Long begin = System.currentTimeMillis();
            Backtracking<UndirectedGraph<Integer, DefaultEdge>, Integer> backtracking = new AllPathBacktracking(g, 9, false);
            List<List<Integer>> solutions = backtracking.backtrack();
            Long end = System.currentTimeMillis();

            System.out.println("Sin boost " + i + " = " + (end - begin));

            g = createTree(i, 3);
            begin = System.currentTimeMillis();
            backtracking = new AllPathBacktracking(g, 9, true);
            solutions = backtracking.backtrack();
            end = System.currentTimeMillis();

            System.out.println("Con boost " + i + " = " + (end - begin));
        }

    }

    private static UndirectedGraph<Integer, DefaultEdge> createCompleteGraph(int n) {
        UndirectedGraph<Integer, DefaultEdge> g = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);

        for (int i = 0; i < n; i++) {
            g.addVertex(i);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                g.addEdge(i, j);
            }
        }

        return g;
    }

    private static UndirectedGraph<Integer, DefaultEdge> createTree(int levels, int leafAmount) {
        UndirectedGraph<Integer, DefaultEdge> g = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
        // root
        g.addVertex(0);
        Set<Integer> leaves = Sets.newHashSet();
        for (int i = 1; i <= leafAmount; i++) {
            g.addVertex(i);
            g.addEdge(0, i);
            leaves.add(i);
        }

        return createTreeAux(g, leaves, levels - 1, leafAmount, 1);

    }

    private static UndirectedGraph<Integer, DefaultEdge> createTreeAux(UndirectedGraph<Integer, DefaultEdge> g, Set<Integer> leaves, int levels, int leafAmount, int actualLevel) {
        if (levels == 0) return g;
        Integer resto = 3 * ((actualLevel * (actualLevel - 1)) / 2) + 1;
        for (Integer leaf : leaves) {
            Set<Integer> newleaves = Sets.newHashSet();
            for (int i = 0; i < leafAmount - 1; i++) {
                Integer vertexNumber = calculateVertexNumber(leaf, actualLevel, leafAmount, i, resto);
                g.addVertex(vertexNumber);
                g.addEdge(leaf, vertexNumber);
                newleaves.add(vertexNumber);
            }
            g = createTreeAux(g, newleaves, levels - 1, leafAmount, actualLevel + 1);
        }
        return g;
    }

    private static Integer calculateVertexNumber(Integer leaf, Integer actualLevel, Integer leafAmount, Integer i, Integer resto) {
        return leaf + (actualLevel * leafAmount) + (leaf - resto) + i;
    }

    private static Map<Integer, List<Set<Integer>>> connectedComponentsByCutpoint(UndirectedGraph<Integer, DefaultEdge> g) {

        Map<Integer, List<Set<Integer>>> connectedComponentsByCutpoint = Maps.newHashMap();

        BlockCutpointGraph<Integer, DefaultEdge> cutpointGraph = new BlockCutpointGraph<Integer, DefaultEdge>(g);

        Set<Integer> cutpoints = cutpointGraph.getCutpoints();

        Map<Integer, Set<Integer>> neighborsByCutpoint = Maps.newHashMap();
        NeighborIndex<Integer, DefaultEdge> neighborIndex = new NeighborIndex<Integer, DefaultEdge>(g);

        for (Integer vertex : cutpoints) {
            neighborsByCutpoint.put(vertex, neighborIndex.neighborsOf(vertex));
        }


        Iterator<Integer> it = cutpoints.iterator();
        for (int i = 0; i < cutpoints.size() / 20; i++) {
            Integer vertex = it.next();
            if (hasSingleMemberConnectedComponent(neighborIndex, vertex)) {
                connectedComponentsByCutpoint.put(vertex, singleMemberConnectedComponents(neighborIndex, vertex, g));
            } else {
                Random r = new Random(System.currentTimeMillis());
                Float p = r.nextFloat();
                if (p > 0.9) {
                    g.removeVertex(vertex);
                    ConnectivityInspector<Integer, DefaultEdge> connectivityInspector = new ConnectivityInspector<Integer, DefaultEdge>(g);
                    connectedComponentsByCutpoint.put(vertex, connectivityInspector.connectedSets());
                    reconstrucGraph(vertex, neighborsByCutpoint.get(vertex), g);
                }
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

    private static List<Set<Integer>> singleMemberConnectedComponents(NeighborIndex<Integer, DefaultEdge> neighborIndex, Integer cutpoint, UndirectedGraph<Integer, DefaultEdge> g) {

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
