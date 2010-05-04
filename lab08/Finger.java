import java.io.*;
import java.net.*;

public class Finger {

    public static void main( String[] args ) {

	if ( args.length == 0 ) {
	    // no arguments
	    // finger localhost
	    
	}
	else if ( args.length == 1 ) {
	    // one argument
	    // split for '@' symbol
	    String[] arg = args[ 0 ].split( "@" );
	    String host;
	    
	    if ( arg.length == 1 ) {
		finger( arg[ 0 ], "localhost" );
	    }
	    else if ( arg.length == 2 ) {
		finger( arg[ 0 ], arg[ 1 ] );
	    }
	    else {
		// do nothing really...
	    }
	}
	else {
	    // two or more arguments
	    // do not parse?
	}
    }

    private static void finger( String user, String host )
    {
	// FREE EVALUATION UNITS
	// REAL TRUE PORT!
	Socket realtrueport = null;
	PrintWriter out = null;
	BufferedReader in = null;

	try {
	    realtrueport = new Socket( host, 79 );
	    out = new PrintWriter( realtrueport.getOutputStream(), true );
	    in = new BufferedReader( new InputStreamReader(
		realtrueport.getInputStream() ) );

	    out.println( user );

	    String fromServer = null;

	    while ( (fromServer = in.readLine() ) != null) {
		System.out.println( fromServer );
	    }

	    out.close();
	    in.close();
	    realtrueport.close();
	}
	catch ( UnknownHostException uhex ) {
	    System.err.println( "Finger:  invalid machine name" );
	    System.exit( 1 );
	}
	catch ( IOException ioex ) {
	    System.err.println( "Finger:  invalid machine name" );
	    System.exit( 1 );
	}
    }
}
