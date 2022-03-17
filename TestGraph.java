

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for class Project.
 *
 */
public class TestGraph {

    Point[] pts = {new Point(1,2), new Point(3,4),
            new Point(5,6), new Point(7,8)};


    @Test
    public void testGraphConstructor () {
        Graph g = new Graph();

        assertThat (g.numVertices(), equalTo(0));
        assertFalse (g.iterator().hasNext());
        for (int i = 0; i < pts.length; ++i)
        {
            assertFalse (g.hasVertex(pts[i]));
        }
    }


    @Test
    public void testAddVertex() {
        Graph g = new Graph();
        g.addVertex(pts[0]);
        g.addVertex(pts[2]);
        assertThat (g.numVertices(), equalTo(2));
        assertTrue (g.iterator().hasNext());
        for (int i = 0; i < pts.length; ++i)
        {
            assertThat(g.hasVertex(pts[i]), equalTo(i % 2 == 0));
            if (i % 2 == 0)
            {
                int n = g.numVertices();
                assertThat (g.getVertex(pts[i]).getName(), equalTo(pts[i]));
                assertThat (g.addVertex(pts[i]).getName(), equalTo(pts[i]));
                assertThat (g.numVertices(), equalTo(n));
            }
            else
            {
                int n = g.numVertices();
                assertThat (g.addVertex(pts[i]).getName(), equalTo(pts[i]));
                assertThat (g.getVertex(pts[i]).getName(), equalTo(pts[i]));
                assertThat (g.numVertices(), equalTo(n+1));
            }
        }
    }

}
