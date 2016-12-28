package programming;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * Created by claudio on 12/28/16.
 */
public abstract class Backtracking<Structure, Element> {

    protected Structure structure;
    protected Element input;

    public Backtracking(Structure structure, Element input) {
        this.structure = structure;
        this.input = input;
    }

    public List<List<Element>> backtrack() {
        List<List<Element>> solutions = new ArrayList<List<Element>>();
        List<Element> partialSolution = new ArrayList<Element>();
        Stack<CandidatesIterator> stackCandidates = new Stack<CandidatesIterator>();
        stackCandidates.add(new CandidatesIterator(initialPartialSolution()));
        CandidatesIterator currentIterator = stackCandidates.peek();

        while (!stackCandidates.isEmpty()) {
            if (currentIterator.hasNext()) {
                makeMove(currentIterator.next(), partialSolution);
                if (isASolution(partialSolution)) {
                    solutions.add(Lists.newArrayList(partialSolution));
                    unmakeLastMove(partialSolution);
                } else {
                    CandidatesIterator newIterator = new CandidatesIterator(constructCandidates(partialSolution));
                    currentIterator = newIterator;
                    stackCandidates.push(newIterator);
                }
            } else {
                stackCandidates.pop();
                if (stackCandidates.isEmpty()) break;
                currentIterator = stackCandidates.peek();
                unmakeLastMove(partialSolution);
            }
        }

        return solutions;
    }

    protected abstract void unmakeLastMove(List<Element> partialSolution);

    protected abstract void makeMove(Element element, List<Element> partialSolution);

    protected abstract List<Element> constructCandidates(List<Element> partialSolution);

    protected abstract boolean isASolution(List<Element> partialSolution);

    protected abstract List<Element> initialPartialSolution();

    private class CandidatesIterator {
        private List<Element> candidates;
        private Integer nextElement = 0;

        public CandidatesIterator(List<Element> candidates) {
            this.candidates = candidates;
        }

        public Element next() {
            Element next = candidates.get(this.nextElement);
            this.nextElement++;
            return next;
        }

        public boolean hasNext() {
            return this.nextElement < this.candidates.size();
        }
    }
}
