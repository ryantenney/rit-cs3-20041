/**
 * Driver program for computing Pi using a Monte Carlo approach.
 *
 * @author Ryan Tenney
 */
public class ComputePi {

	/**
	 * number of iterations to perform
	 */
	private static int iter, threads;

	/**
	 * Run the program. Create multiple instances of MonoPi
	 * that will compute the ratio of the circle's area to
	 * the square's. This approximates Pi/4. Print Pi.
	 *
	 * @param args an array containing two strings. Both strings
         *        represent integers. The first is the number of
         *        iterations that should be performed. The second is the 
         *        number of threads that are to perform the calculation.
	 */
	public static void main( String[] args ) {
		if ( args.length != 2 ) {
		    System.err.println(
		        "Usage: ComputePi #iterations #threads" );
		    System.exit( 1 );
		}
		try {
		    iter = Integer.parseInt( args[ 0 ] );
                    threads = Integer.parseInt( args [ 0 ] );

		    MultiPi mp = new MultiPi( iter );
                    mp.start();
                    mp.join();
		    double inOut = mp.getRatio();
		    System.out.println( "Pi ~= " + inOut * 4 );

		} catch( NumberFormatException nfex ) {
		    System.err.println(
		        "Usage: ComputePi #iterations #threads" );
		    System.err.println( "(arguments must be " +
		        "integers.)" );
		    System.exit( 1 );
                } catch ( InterruptedException iex ) {
                    
                }
	}

}
