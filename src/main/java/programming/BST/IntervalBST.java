package programming.BST; /******************************************************************************
                          * Compilation: javac IntervalBST.java Execution: java IntervalBST < input.txt Dependencies:
                          * StdIn.java StdOut.java Data files: http://algs4.cs.princeton.edu/33balanced/tinyST.txt
                          * 
                          * A symbol table implemented using a left-leaning red-black BST. This is the 2-3 version.
                          *
                          * Note: commented out assertions because DrJava now enables assertions by default.
                          *
                          * % more tinyST.txt S E A R C H E X A M P L E
                          * 
                          * % java IntervalBST < tinyST.txt A 8 C 4 E 12 H 5 L 11 M 9 P 10 R 3 S 0 X 7
                          *
                          ******************************************************************************/

import java.util.*;

/**
 *  The {@code BST} class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides ordered methods for finding the <em>minimum</em>,
 *  <em>maximum</em>, <em>floor</em>, and <em>ceiling</em>.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a left-leaning red-black BST. It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *  The <em>put</em>, <em>contains</em>, <em>remove</em>, <em>minimum</em>,
 *  <em>maximum</em>, <em>ceiling</em>, and <em>floor</em> operations each take
 *  logarithmic time in the worst case, if the tree becomes unbalanced.
 *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/33balanced">Section 3.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

public class IntervalBST {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root; // root of the BST

    // BST helper node data type

    public static class Interval
        implements Comparable<Interval> {

        private Double lo;
        private Double hi;
        private Integer id;

        public Interval(Double lo, Double hi) {
            this(lo, hi, 0);
        }

        public Interval(Double lo, Double hi, Integer id) {
            if (lo > hi) throw new IllegalArgumentException("lo must be lower than hi");
            this.lo = lo;
            this.hi = hi;
            this.id = id;
        }

        public int compareTo(Interval o) {
            return this.lo.compareTo(o.lo);
        }

        boolean intersects(Interval interval) {
            return interval.lo >= this.lo && interval.hi <= this.hi;
        }

        public String toString() {
            return String.format("[%s, %s]", this.lo.toString(), this.hi.toString());
        }

        public Integer getId() {
            return this.id;
        }
    }


    private class Node {
        private Interval interval; // key
        private Node left, right; // links to left and right subtrees
        private boolean color; // color of parent link
        private int size; // subtree count
        private Double max;

        public Node(Interval interval, boolean color, int size) {
            this.interval = interval;
            this.color = color;
            this.size = size;
            this.max = this.interval.hi;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public IntervalBST() {
    }

    /***************************************************************************
    *  Node helper methods.
    ***************************************************************************/
    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }


    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }


    /***************************************************************************
    *  Standard BST search.
    ***************************************************************************/

    /**
     * Returns the value associated with the given key.
     * @param interval the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public List<Interval> intersectIntervals(Interval interval) {
        if (interval == null) throw new IllegalArgumentException("argument to get() is null");
        return intersectIntervals(root, interval);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private List<Interval> intersectIntervals(Node x, Interval interval) {

        List<Interval> response = new ArrayList<Interval>();
        while (x != null) {
            if (x.interval.intersects(interval)) response.add(x.interval);

            if (x.left == null)
                x = x.right;
            else if (x.left.max < interval.lo)
                x = x.right;
            else
                x = x.left;
        }

        return response;
    }

    /***************************************************************************
    *  Red-black tree insertion.
    ***************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param interval the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Interval interval) {
        if (interval == null) throw new IllegalArgumentException("first argument to put() is null");
        root = put(root, interval);
        root.color = BLACK;
        // assert check();
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, Interval interval) {
        if (h == null) return new Node(interval, RED, 1);
        h.max = Math.max(h.max, interval.hi);
        int cmp = interval.compareTo(h.interval);
        if (cmp <= 0)
            h.left = put(h.left, interval);
        else if (cmp > 0) h.right = put(h.right, interval);

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }

    /***************************************************************************
    *  Red-black tree deletion.
    ***************************************************************************/

    /**
     * Removes the smallest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) {
        if (h.left == null) return null;

        if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    /***************************************************************************
    *  Red-black tree helper functions.
    ***************************************************************************/

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) && isRed(h.left) && isRed(h.right))
        // || (isRed(h) && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }


    /***************************************************************************
    *  Utility functions.
    ***************************************************************************/

    /**
     * Returns the height of the BST (for debugging).
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private boolean is23() {
        return is23(root);
    }

    private boolean is23(Node x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        if (x != root && isRed(x) && isRed(x.left)) return false;
        return is23(x.left) && is23(x.right);
    }

    // do all paths from root to leaf have same number of black edges?
    private boolean isBalanced() {
        int black = 0; // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    // does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        if (x == null) return black == 0;
        if (!isRed(x)) black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    public static void main(String... args) {
        IntervalBST intervalBST = new IntervalBST();
        intervalBST.put(new Interval(1.0, 3.0, 1));
        intervalBST.put(new Interval(2.0, 4.0, 2));
        intervalBST.put(new Interval(5.0, 7.0, 3));

        System.out.println(intervalBST.intersectIntervals(new Interval(1.3, 1.3, 0)));
    }
}
