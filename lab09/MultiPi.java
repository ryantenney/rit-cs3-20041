/**
 * Compute the ratio of the area of a circle to the area
 * of its bounding box.
 *
 * @author James Heliotis
 */

public class MultiPi extends Thread {

	/**
	 * Diameter of circle (and width and height of bounding box)
	 */
	private final static double d = 1.0;

	/**
	 * Radius of circle
	 */
	private final static double r = d / 2.0;

	/**
	 * Square of radius of circle
	 */
	private final static double rSq = r * r;

	/**
	 * Initialize the parameters and in/out counter.
	 *
	 * @param numSteps the total number of random coordinates
	 *        to be generated 
	 */
	public MultiPi( int numSteps ) {
		steps = numSteps;
		in = 0;
	}

	/**
	 * Generate random coordinates and increment the
	 * in/out counter if the coordinates fall inside
	 * the circle.
	 */
	public void run() {
		double x;
		double y;
		for ( int i=0; i<steps; ++i ) {
			x = rand.nextDouble() * d;
			y = rand.nextDouble() * d;
			if ( inCircle( x, y ) ) {
				in += 1;
			}
		}
	}

	/**
	 * Compute the ratio of points inside to total points.
	 *
	 * @return in/out counter divided by number of steps
	 */
	public double getRatio() {
		return (double)in / (double)steps;
	}

	/**
	 * Does the given point fall inside the circle?
	 *
	 * @param x x-coordinate of point
	 * @param y y-coordinate of point
	 * @return true iff (x,y) is inside the circle.
	 */
	private boolean inCircle( double x, double y ) {
		return ( (x-r)*(x-r) + (y-r)*(y-r) ) <= rSq;
	}

	/**
	 * a random number generator for the points
	 */
	private final static java.util.Random rand = new java.util.Random();

	/**
	 * the number of random point coordinates to generate
	 */
	private int steps;

	/**
	 * the in/out counter
	 */
	private int in;
}
