import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * An (x,y) point with integer coordinates.
 * 
 * @author zeil
 *
 */
public class Point extends java.awt.Point {
    
	public Point (int x, int y) {
		super(x,y);
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * Read a point from a string in the form "(x,y)"
	 * @param ptstr string from which to read
	 * @return the Point
	 */
    public static Point read(String ptstr) {
        Scanner in = new Scanner(ptstr);
        Pattern notDigits = Pattern.compile("[^-0-9]+");
        in.useDelimiter(notDigits);
        //in.skip(notDigits);
        int x = in.nextInt();
        in.skip(notDigits);
        int y = in.nextInt();
        in.close();
        return new Point(x,y);
    }
}
