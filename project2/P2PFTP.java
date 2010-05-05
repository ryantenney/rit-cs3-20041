import java.io.*;

/**
 * Main class for the P2PFTP program that includes the main function
 */
public class P2PFTP
{

	/**
	 * Public Static Void Main method that invokes by default
	 *
	 * @param    args    command line arguments
	 */
	public static void main( String[] args )
	{
		if ( args.length != 3 ) {
			System.err.println( "incorrect number of arguments" );
			System.err.println( "correct argument string : [root] "
					+ "[server] [port]" );
			System.exit( 0 );
		}

		String workspace = args[ 0 ];
		String host = args[ 1 ];
		int port = -1;

		try {
			port = Integer.parseInt( args[ 2 ] );
		}
		catch ( NumberFormatException nfex ) {
			System.err.println( "[port] : number format exception" );
			System.exit( 0 );
		}

		File root = new File( workspace );

		Manager man = new Manager( host, port, 0, "343guiltySpark", 
				root.getAbsolutePath(), 45167, true );

		Gooey gui = new Gooey( man );

		gui.show();
	}

	/** 
	 * Default constructor with no arguments
	 */
	public P2PFTP()
	{

	}

}
