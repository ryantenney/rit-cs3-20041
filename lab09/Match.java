/**
 * Compute the number of occurrences of a string within a set of files.
 *
 * @author James Heliotis
 */
public class Match {

	/**
	 * Process command line arguments and run the program.
	 * Start a Matcher object for each file in turn.
	 * Print out the total number of matches found.
	 *
	 * @param args an array of command line argument strings:<BR>
	 *     args[0] is the string for which to search;<BR>
	 *     the rest are names of files in which to search.
	 */
	public static void main( String[] args ) {
		if ( args.length < 1 ) {
			System.err.println(
			    "Usage: java Match string [ files... ]" );
			System.exit( 1 );
		}

		int count = 0;

		for ( int f = 1; f < args.length; ++f ) {
			Matcher matcher = new Matcher( args[ 0 ], args[ f ] );
			matcher.run();
			count += matcher.getNumMatches();
		}

		System.out.println( "\nTotal number of matches is " + count );
	}

}
