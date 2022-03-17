import java.util.ArrayList;
import java.util.Iterator;

/**
 * Vertices in a graph.
 * 
 * Each vertex has a name, a list of other vertices adjacent to it,
 * a boolean flag "covered" to aid in graph traversals, and
 * can be assigned membership in a "figure", a set within a partitioning
 * of all vertices.  Each set is identified by a "signature" vertex selected
 * arbitrarily from among the vertices in the set.
 * 
 * Initially, a newly created vertex is considered to be in a figure by itself,
 * and is the signature for its own figure set.
 * 
 * @author zeil
 */
public class Vertex implements Iterable<Point> {
    private Point id;
    private Vertex figure;
    private int figureSize;
    private ArrayList<Point> adjacent;
    private boolean covered;
    private Figure theFigure;

    /**
     * Create a new vertex with the given name, uncovered,
     * no adjacent vertices, in a figure by itself.
     * @param ident
     */
    public Vertex (Point ident) {
        id = new Point(ident.x, ident.y);
        figure = this;
        figureSize = 0;
        adjacent = new ArrayList<>();
        covered = false;
        theFigure = null;
    }

    /**
     * Add v to the adjacency list for this vertex.
     * @param v  Another vertex
     */
    public void addAdjacent(Vertex v) {
        adjacent.add(v.id);
    }
    
    /**
     * How many vertices are adjacent to this one?
     * @return number of adjacent vertices.
     */
    public int getNumAdjacent() {
        return adjacent.size();
    }
    
    /**
     * Get the signature of the figure to which this vertex belongs.
     * @return the signature
     */
    public Vertex find() {
        Vertex signature = figure;
        while (signature != signature.figure) {
            signature = signature.figure;
        }
        figure = signature;
        return signature;
    }
    
    /**
     * Combine (set union) the figures of this vertex and that
     * of the vertex w.
     * @param w another vertex
     */
    public void union (Vertex w) {
        Vertex sigv = find();
        Vertex sigw = w.find();
        sigw.figure = sigv;
    }
    
    /**
     * Compare two vertices for equality.
     */
    public boolean equals(Object ov)
    {
        Vertex v = (Vertex)ov;
        return v.id.equals(id);
    }

    /**
     * Hashcode for vertices.
     */
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Debugging output of a vertex.
     */
    public String toString() {
        return id.toString();
    }

    /**
     * @return the identifier of this vertex.
     */
    public Point getName() {
        return id;
    }

    /**
     * @return the number of vertices in the figure to which
     *    this vertex belongs.
     */
    public int getFigureSize() {
        return figureSize;
    }

    /**
     * Set the the number of vertices in the figure to which
     *    this vertex belongs.
     * @param figureSize the value to set the size to
     */
    public void setFigureSize(int figureSize) {
        this.figureSize = figureSize;
    }

    /**
     * Provides access to the list of vertices adjacent to this one.
     */
    @Override
    public Iterator<Point> iterator() {
        return adjacent.iterator();
    }

    /**
     * @return the covered flag
     */
    public boolean isCovered() {
        return covered;
    }

    /**
     * @param covered the covered value to set
     */
    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public void setFigure(Figure figure) {
        theFigure = figure;
    }

    public Figure getFigure() {
        return theFigure;
    }

}