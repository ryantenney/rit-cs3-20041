/**
 * Scan a file for a specific string.
 *
 * @author James Heliotis
 */
public class Matcher {

	/**
	 * Open a file and record the string for which to be searched.
	 *
	 * @param searchString the string for which to be searched
	 * @param filename the name of the file in which to search
	 */
	public Matcher( String searchString, String filename ) {
		this.searchString = searchString;
		try {
			inFile = new java.io.BufferedReader(
			           new java.io.FileReader( filename ) );
		}
		catch ( java.io.FileNotFoundException fnfe ) {
			System.err.println( fnfe );
			/* inFile remains null as a state indicator */
		}
	}

	/**
	 * Count the number of times the search string appears
	 * in the file.<BR>
	 * Precondition: initialized() 
	 */
	public void run() {
		String line = null;
		try {
			while ( ( line=inFile.readLine() ) != null ) {
				int found = line.indexOf( searchString );
				while ( found != -1 ) {
					count += 1;
					found = line.indexOf(
					         searchString, found+1 );
				}
			}
		}
		catch ( java.io.IOException ioe ) {
			System.err.println( ioe );
		}
	}

	/**
	 * How many times was the search string found in the file?
	 *
	 * @return the number of times found
	 */
	public int getNumMatches() {
		return count;
	}

	/**
	 * Has this object been properly initialized?
	 *
	 * @return whether the file was successfully opened in the
	 *         constructor
	 */
	public boolean initialized() {
		return inFile != null;
	}

	/**
	 * Clean things up when the garbage collector reclaims
	 * this object. Basically this means close the file.
	 */
	protected void finalize() {
		if ( initialized() ) {
			try {
				inFile.close();
			}
			catch( java.io.IOException ioe ) { }
		}
	}

	/**
	 * The stream for the file to be searched
	 */
	private java.io.BufferedReader inFile = null;

	/**
	 * The string for which to be searched.
	 */
	private String searchString = null;

	/**
	 * The number of times the string has been found so far.
	 */
	private int count = 0;

}
