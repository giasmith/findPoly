import java.util.HashMap;
import java.util.Iterator;


/**
 * A graph formed from line segments connected at shared endpoints.
 * 
 * @author zeil
 *
 */
public class Graph implements Iterable<Vertex>{

    private HashMap<Point, Vertex> vertices;

    /**
     * Create a new, empty graph.
     */
    public Graph()
    {
        vertices = new HashMap<>();
    }

    /**
     * If this graph already has a vertex with the given name,
     * returns that vertex. If not, creates a new vertex with
     * that name and returns it.
     * 
     * @param name unique identifier for a vertex
     * @return a vertex with that identifier as its name
     */
    public Vertex addVertex(Point name) {
        Vertex v = vertices.get(name);
        if (v == null)
        {
            v = new Vertex(name);
            vertices.put(name, v);
        }
        return v;
    }

    
    /**
     * Indicates whether the graph contains the vertex with the given name. 
     * 
     * @param name unique identifier for a vertex
     * @return true iff the vertex exists.
     */
    public boolean hasVertex(Point name) {
        return vertices.get(name) != null;
    }

    /**
     * Returns the vertex with the given name. 
     * 
     * @param name unique identifier for a vertex
     * @return a vertex with that identifier as its name.
     */
    public Vertex getVertex(Point name) {
        return vertices.get(name);
    }
    
    /**
     * The number of vertices in the graph.
     * @return the number of vertices.
     */
    public int numVertices() {return vertices.size();}

    /**
     * Iterator over the vertices in the graph.
     */
    @Override
    public Iterator<Vertex> iterator() {
        return vertices.values().iterator();
    }



}
