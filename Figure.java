import java.util.Iterator;

/**
 * A collection of points connected by line segments.
 * 
 * @author zeil
 *
 */
public class Figure implements Iterable<Point>, Cloneable {
    private class LLNode {
        Point data;
        LLNode next;
        
        public LLNode (Point p, LLNode nxt) {
            data = p;
            next = nxt;
        }
    }
    
    private LLNode first;
    private LLNode last;
    private int theSize;
    
    private class Iter implements Iterator<Point> {
        private LLNode current;

        public Iter(LLNode frst) {
            current = frst;
        }
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Point next() {
            Point p = current.data;
            current = current.next;
            return p;
        }
    }
    
    /**
     * Create a figure containing no points.
     */
    public Figure () {
        first = last = null;
        theSize = 0;
    }
    
    /**
     * Create a figure containing a single point.
     * @param p  the initial point in the figure
     */
    public Figure (Point p) {
        first = last = new LLNode(p, null);
        theSize = 1;
    }
    
    /**
     * Size of the figure.
     * @return the number of points in the figure
     */
    public int size() {return theSize;}
    
    /**
     * Combine two figures. All of the points in f are added to this
     * figure and removed from f.   The signature of this figure is unchanged.
     * f becomes empty and its signature is null. 
     * 
     * Special cases: If this figure and f have the same signature, they are
     *    not changed in any way.
     *    If this figure was empty, it becomes equal to f.
     *    If f was empty, this figure is unchanged.
     * 
     * Note: this operation can be done in O(1) time.
     * 
     * @param f another figure
     */
    public void merge (Figure f) {
		//*** Insert your code here.
    }
    
    /**
     * Two figures are equal if they have the same signature.
     */
    public boolean equals (Object obj) {
        if (obj instanceof Figure) {
            Figure other = (Figure)obj;
            if (theSize != other.theSize)
                return false;
            LLNode it0 = first;
            LLNode it1 = other.first;
            while (it0 != null && it1 != null) {
                if (!it0.data.equals(it1.data)) {
                    return false;
                }
                it0 = it0.next;
                it1 = it1.next;
            }
            return it0 == null && it1 == null;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("[");
        boolean firstTime = true;
        for (Point p: this) {
            if (!firstTime) {
                buffer.append(", ");
            }
            firstTime = false;
            buffer.append(p.toString());
        }
        buffer.append("]");
        return buffer.toString();
    }

    @Override
    /**
     * Provide access to the points within the figure.
     */
    public Iterator<Point> iterator() {
        return new Iter(first);
    }

    /**
     * The signature of a figure is the first point that was used to create
     * the initial figure, or null if all points have been removed
     * from the figure.
     *  
     * @return the signature point.
     */
    public Point signature() {
        if (first == null)
            return null;
        else
            return first.data;
    }
    
    @Override
    public Figure clone() {
		//*** Insert your code here
    }
}
