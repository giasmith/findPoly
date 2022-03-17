

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

/**
 * Unit test for class Project.
 *
 */
public class TestFigure {

    Point[] pts = {new Point(1,2), new Point(3,4), 
            new Point(5,6), new Point(7,8)};


    @Test
    public void testDefaultConstructor() {
        Figure fig1 = new Figure();
        assertThat (fig1.size(), equalTo(0));

        Figure fig2 = new Figure();

        Point p3 = new Point(245, 123);
        Figure fig3 = new Figure(p3);

        assertThat (fig1, equalTo(fig2));
        assertThat (fig1, not(equalTo(fig3)));

        String outs = fig1.toString();
        assertFalse (outs.contains("("));

        assertFalse (fig1.iterator().hasNext());

    }


    @Test
    public void testConstructor() {
        Point p1 = new Point(123,245);
        Figure fig1 = new Figure(p1);
        assertThat (fig1.size(), equalTo(1));

        Point p2 = (Point)p1.clone();
        Figure fig2 = new Figure(p2);

        Point p3 = new Point(245, 123);
        Figure fig3 = new Figure(p3);

        assertThat (fig1, equalTo(fig2));
        assertThat (fig1, not(equalTo(fig3)));

        String outs = fig1.toString();
        assertTrue (outs.contains("123"));
        assertTrue (outs.contains("245"));

        Iterator<Point> b = fig1.iterator();
        assertTrue (b.hasNext());
        assertThat(b.next(), equalTo(p1));
        assertFalse (b.hasNext());

        assertThat(fig1.signature(), equalTo(p1));
    }


    @Test
    public void testMerge()
    {
        Point[] pts = {new Point(1,2), new Point(3,4), 
                new Point(5,6), new Point(7,8)};

        Figure fig0 = new Figure(pts[0]);
        Figure fig1 = new Figure(pts[0]);
        Figure fig2 = new Figure(pts[1]);
        Figure fig3 = new Figure(pts[2]);
        Figure fig4 = new Figure(pts[3]);

        fig1.merge(fig2);
        assertThat (fig1, not(equalTo(fig0)));
        assertThat(fig1.size(), equalTo(2));
        assertThat(fig1.signature(), equalTo(pts[0]));

        assertThat(fig2.size(), equalTo(0));

        Iterator<Point> it = fig1.iterator();
        for (int i = 0; i < 2; ++i)
        {
            Point p = it.next();
            assertThat(p, equalTo(pts[i]));
        }
        assertFalse(it.hasNext());

        fig3.merge(fig4);

        fig1.merge(fig3);
        assertThat(fig1.size(), equalTo(4));
        assertThat(fig1.signature(), equalTo(pts[0]));

        it = fig1.iterator();
        for (int i = 0; i < 4; ++i)
        {
            Point p = it.next();
            assertThat(p, equalTo(pts[i]));
        }

        String outs = fig1.toString();
        assertTrue (outs.contains("1"));
        assertTrue (outs.contains("5"));
        assertTrue (outs.contains("),"));
    }

    @Test
    public void testMergeEmpty1()
    {

        Figure fig0 = new Figure(pts[0]);
        Figure fig1 = new Figure(pts[0]);
        Figure fig2 = new Figure();

        fig1.merge(fig2);
        assertThat (fig1, equalTo(fig0));
        assertThat(fig1.size(), equalTo(1));
        assertThat(fig1.signature(), equalTo(pts[0]));

        assertThat(fig2.size(), equalTo(0));

        Iterator<Point> it = fig1.iterator();
        for (int i = 0; i < 1; ++i)
        {
            assertTrue (it.hasNext());
            assertThat(it.next(), equalTo(pts[i]));
        }
        assertFalse (it.hasNext());

        String outs = fig1.toString();
        assertTrue (outs.contains("1,"));
        assertTrue (outs.contains("2"));
        assertFalse (outs.contains("),"));
    }

    @Test
    public void testMergeEmpty2()
    {
        Figure fig0 = new Figure(pts[0]);
        Figure fig1 = new Figure();
        Figure fig2 = new Figure(pts[0]);

        fig1.merge(fig2);
        assertThat (fig1, equalTo(fig0));
        assertThat(fig1.size(), equalTo(1));
        assertThat(fig1.signature(), equalTo(pts[0]));

        assertThat(fig2.size(), equalTo(0));

        Iterator<Point> it = fig1.iterator();
        for (int i = 0; i < 1; ++i)
        {
            assertTrue (it.hasNext());
            assertThat(it.next(), equalTo(pts[i]));
        }
        assertFalse (it.hasNext());

        String outs = fig1.toString();
        assertTrue (outs.contains("1,"));
        assertTrue (outs.contains("2"));
        assertFalse (outs.contains("),"));
    }


    @Test
    public void testFigCopy()
    {
        Figure fig0 = new Figure(pts[0]);
        Figure fig1 = new Figure(pts[0]);
        Figure fig2 = new Figure(pts[1]);

        fig1.merge(fig2);

        Figure fig3 = fig1.clone();
        Figure fig4 = fig2.clone();

        assertThat (fig3, equalTo(fig1));
        assertThat (fig4, equalTo(fig2));

        assertThat(fig3.size(), equalTo(fig1.size()));
        assertThat(fig3.signature(), equalTo(fig1.signature()));

        assertThat(fig4.size(), equalTo(fig2.size()));
        assertThat(fig4.signature(), equalTo(fig2.signature()));

        Iterator<Point> it1 = fig1.iterator();
        Iterator<Point> it3 = fig3.iterator();
        while (it1.hasNext() && it3.hasNext()) {
            assertThat(it1.next(), equalTo(it3.next()));
        }
        assertThat(it1.hasNext(), equalTo(it3.hasNext()));

        Iterator<Point> it2 = fig2.iterator();
        Iterator<Point> it4 = fig4.iterator();
        while (it2.hasNext() && it4.hasNext()) {
            assertThat(it2.next(), equalTo(it4.next()));
        }
        assertThat(it2.hasNext(), equalTo(it4.hasNext()));
    }

    @Test
    public void testFigDistinctCopy()
    {
        Figure fig0 = new Figure(pts[0]);
        Figure fig0a = new Figure(pts[2]);
        fig0.merge(fig0a);

        Figure fig1 = new Figure(pts[0]);
        Figure fig1a = new Figure(pts[2]);
        fig1.merge(fig1a);
        Figure fig2 = new Figure(pts[1]);

        Figure fig3 = fig1.clone();
        Figure fig4 = fig2.clone();

        assertThat (fig3, equalTo(fig1));

        fig3.merge(fig4);  // should not affect fig1

        assertThat (fig1, equalTo(fig0));

        assertThat(fig1.size(), equalTo(fig0.size()));
        assertThat(fig1.signature(), equalTo(fig0.signature()));

        Iterator<Point> it1 = fig1.iterator();
        Iterator<Point> it0 = fig0.iterator();
        while (it1.hasNext() && it0.hasNext()) {
            assertThat(it1.next(), equalTo(it0.next()));
        }
        assertThat(it1.hasNext(), equalTo(it0.hasNext()));
    }



	/**
     * Selective test runner
     * @param args  test names
     */
    public static void main(String... args) {
        for (String testName: args) {
            Request request = Request.method(TestFigure.class, testName);
            Result result = new JUnitCore().run(request);
            String resultStr = (result.wasSuccessful()) ? "OK" : "Failed";
            System.out.println(testName + ": " + resultStr);
        }
    }


	

}
