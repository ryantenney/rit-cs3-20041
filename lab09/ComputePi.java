/**
 * Driver program for computing Pi using a Monte Carlo approach.
 *
 * @author James Heliotis
 */
public class ComputePi {

	/**
	 * number of iterations to perform
	 */
	private static int iter;

	/**
	 * Run the program. Create a single instance of MonoPi
	 * that will compute the ratio of the circle's area to
	 * the square's. This approximates Pi/4. Print Pi.
	 *
	 * @param args an array containing a single string. That
	 *        string represents an integer which is the
	 *        number of iterations that should be performed.
	 */
	public static void main( String[] args ) {
		if ( args.length != 1 ) {
			System.err.println(
			    "Usage: ComputePi #iterations #threads" );
			System.exit( 1 );
		}
		try {
			iter = Integer.parseInt( args[ 0 ] );
		}
		catch( NumberFormatException nfe ) {
			System.err.println(
			    "Usage: ComputePi #iterations #threads" );
			System.err.println( "(#iterations and " +
			    "#threads must be integers.)" );
			System.exit( 1 );
		}
		MultiPi mp = new MultiPi( iter );
		mp.run();
		double inOut = mp.getRatio();
		System.out.println( "Pi ~= " + inOut * 4 );
	}
}
