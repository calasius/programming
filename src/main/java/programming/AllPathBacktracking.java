package programming;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;
import java.util.Set;

/**
 * Created by claudio on 12/28/16.
 */
public class AllPathBacktracking
    extends Backtracking<UndirectedGraph<Integer, DefaultEdge>, Integer> {
    private NeighborIndex<Integer, DefaultEdge> neighborIndex;

    public AllPathBacktracking(UndirectedGraph<Integer, DefaultEdge> structure, Integer input) {
        super(structure, input);
        this.neighborIndex = new NeighborIndex<Integer, DefaultEdge>(structure);
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
        List<Integer> candidates = Lists.newArrayList();
        List<Integer> neighbors = this.neighborIndex.neighborListOf(lastElement);
        for (Integer vertex : neighbors) {
            if (!inSol.contains(vertex)) {
                candidates.add(vertex);
            }
        }
        return candidates;
    }

    @Override
    protected boolean isASolution(List<Integer> partialSolution) {
        return partialSolution.get(partialSolution.size() - 1) == this.input;
    }

    @Override
    protected List<Integer> initialPartialSolution() {
        return Lists.newArrayList(1);
    }

    public static void main(String... args) {

        for (int i = 5; i < 100; i++) {
            UndirectedGraph<Integer, DefaultEdge> g = createCompleteGraph(i);
            Long begin = System.currentTimeMillis();
            Backtracking<UndirectedGraph<Integer, DefaultEdge>, Integer> backtracking = new AllPathBacktracking(g, 4);
            List<List<Integer>> solutions = backtracking.backtrack();
            Long end = System.currentTimeMillis();
            System.out.print(i + "," + solutions.size() + "," + (end - begin));
            System.out.println();
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
}
