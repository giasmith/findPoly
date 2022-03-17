import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * Driver class for finding figures and polygons from a list of line segments.
 * 
 * @author zeil
 *
 */
public class FindPoly {



    /**
     * Read a set gf line segments and form a graph indicating
     * which points are joined by a line segment.
     * @param bin   A buffered input source.
     * return g graph of connected points
     * @throws IOException if input cannot be processed
     */
    final Graph read (BufferedReader bin) throws IOException
    {
        Graph g = new Graph();
        String line = bin.readLine();
        while (line != null) {
            String[] segmentParts = line.split(";");
            for (String segmentStr: segmentParts) {
                if (segmentStr.length() > 0) {
                    int k = segmentStr.indexOf("),(");
                    String name1 = segmentStr.substring(0, k+1);
                    Point p1 = Point.read(name1);
                    String name2 = segmentStr.substring(k+2);
                    Point p2 = Point.read(name2);
                    Vertex v1 = g.addVertex(p1);
                    Vertex v2 = g.addVertex(p2);
                    v1.addAdjacent(v2);
                    v2.addAdjacent(v1);
                }
            }
            line = bin.readLine();
        }
        return g;
    }

    
    /**
     * Read a set if line segments and count the number of figures
     * and polygons.
     * @param bin   A buffered input source.
     * @throws IOException if input cannot be processed
     */
    final void solve (BufferedReader bin) throws IOException
    {
        Graph g = read(bin);
        List<Figure> figures = collectFigures(g);
        int nf = figures.size();
        int np = 0;
        for (Figure fig: figures) {
            if (isPolygon(fig, g)) {
                ++np;
            }
        }
//        int np = countPolygons(g);
        System.out.println("" + nf + " " + np);
    }

    private boolean isPolygon(Figure fig, Graph g) {
        if (fig.size() >= 3) {
            Vertex[] path = new Vertex[fig.size()+1];
            Iterator<Point> iPoints = fig.iterator();
            Vertex v0 = g.getVertex(iPoints.next());
            path[0] = v0;
            path[fig.size()] = null;
            v0.setCovered(true);
            // Try to trace our way around the figure up until
            // the last step.
            for (int k = 0; k < fig.size()-1; ++k) {
                //System.err.println("path is " + Arrays.asList(path));
                Vertex v = path[k];
                path[k+1] = null;
                if (v.getNumAdjacent() == 2) {
                    for (Point pw: v) {
                        Vertex w = g.getVertex(pw);
                        if (!w.isCovered()) {
                            path[k+1] = w;
                            w.setCovered(true);
                            break;
                        }
                    }
                }
                if (path[k+1] == null)
                    break;
            }
            //System.err.println("path is " + Arrays.asList(path));
            if (path[fig.size()-1] != null) {
                // Now see if we can reconnect to our starting point.
                Vertex v = path[fig.size()-1];
                if (v.getNumAdjacent() == 2) {
                    for (Point pw: v) {
                        Vertex w = g.getVertex(pw);
                        if (w == v0) {
                            path[fig.size()] = w;
                            return true;
                        }
                    }
                }                    
            }
        } 
        return false;
    }


    /**
     * Count the number of polygons in a graph. Assumes that all
     * figures have been detected and that the size of each figure is
     * already known. 
     * 
     * @param g  a graph made up of multiple line segments conencted at
     *           their shared endpoints.
     * @return  the number of polynomials
     */
    /*
    private int countPolygons(Graph g) {
        int nPoly = 0;
        
        for (Vertex v0: g) {
            if (v0.find() == v0 && v0.getFigureSize() >= 3) {
                int figSize = v0.getFigureSize();
                Vertex[] path = new Vertex[figSize+1];
                path[0] = v0;
                path[v0.getFigureSize()] = null;
                v0.setCovered(true);
                // Try to trace our way around the figure up until
                // the last step.
                for (int k = 0; k < figSize-1; ++k) {
                    //System.err.println("path is " + Arrays.asList(path));
                    Vertex v = path[k];
                    path[k+1] = null;
                    if (v.getNumAdjacent() == 2) {
                        for (Vertex w: v) {
                            if (!w.isCovered()) {
                                path[k+1] = w;
                                w.setCovered(true);
                                break;
                            }
                        }
                    }
                    if (path[k+1] == null)
                        break;
                }
                //System.err.println("path is " + Arrays.asList(path));
                if (path[figSize-1] != null) {
                    // Now see if we can reconnect to our starting point.
                    Vertex v = path[figSize-1];
                    if (v.getNumAdjacent() == 2) {
                        for (Point w: v) {
                            if (w == v0) {
                                path[figSize] = w;
                                ++nPoly;
                                break;
                            }
                        }
                    }                    
                }
            }
        }
        
        return nPoly;
    }
    */

    /**
     * Count the number of connected figures in the graph, marking each
     * with its size.
     *  
     * @param g a graph made up of multiple line segments conencted at
     *           their shared endpoints.
     * @return the number of connected components (figures) in the graph.
     */
    /*
    private int countFigures(Graph g) {
        for (Vertex v: g) {
            for (Vertex w: v) {
                v.union(w);
            }
        }
        int nFigures = 0;
        for (Vertex v: g) {
            Vertex fig = v.find();
            if (fig == v) {
                ++nFigures;
            }
            fig.setFigureSize(fig.getFigureSize() + v.getNumAdjacent());
        }
        for (Vertex v: g) {
            if (v.find() == v) {
                v.setFigureSize(v.getFigureSize() / 2);
                //System.err.println("Figure " + v + " is size " + v.figureSize);
            }
        }
        return nFigures;
    }
    */

    /**
     * Count the number of connected figures in the graph, marking each
     * with its size.
     *  
     * @param g a graph made up of multiple line segments conencted at
     *           their shared endpoints.
     * @return the number of connected components (figures) in the graph.
     */
    private List<Figure> collectFigures(Graph g) {
        for (Vertex v: g) {
            v.setFigure(new Figure(v.getName()));
        }
        for (Vertex v: g) {
            Figure vFig = v.getFigure();
            for (Point pw: v) {
                Vertex w = g.getVertex(pw);
                Figure wFig = w.getFigure();
                vFig.merge(wFig);
                for (Point p: vFig)
                {
                    g.getVertex(p).setFigure(vFig);
                }
            }
        }
        ArrayList<Figure> results = new ArrayList<>();
        for (Vertex v: g) {
            Figure fig = v.getFigure();
            if (fig.signature() != null && fig.signature().equals(v.getName()))
                results.add(fig);
        }
        return results;
    }

    
    
    /**
     * Main function for findPoly program. Will take input
     * either from a file named on the command line or, if no
     * command parameter is supplied, from standard input.
     * 
     * @param args  command line arguments
     * @throws IOException 
     */
    public static void main (String[] argv) throws IOException
    {
        BufferedReader in;
        if (argv.length > 0)
        {
            in = new BufferedReader(new FileReader(argv[0]));
        }
        else {
            in = new BufferedReader (new InputStreamReader(System.in));
        }
        new FindPoly().solve (in);
    }


}
