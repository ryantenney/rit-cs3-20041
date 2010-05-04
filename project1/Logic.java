/*
 * $Id: Logic.java,v 1.10 2004/10/12 14:55:16 rwt5629 Exp $
 *
 * $Log: Logic.java,v $
 * Revision 1.10  2004/10/12 14:55:16  rwt5629
 * nearly finished, a problem with the 2^0 bit still remains, but its after the deadline so this is moot anyways.
 *
 * Revision 1.9  2004/10/12 03:57:07  rwt5629
 * slkdfjslD
 * a
 *
 * Revision 1.8  2004/10/12 02:40:09  rwt5629
 * created Destination.java, also added cvs tracking to AndGate.java as well as made various other modifications to other files
 *
 * Revision 1.6  2004/10/11 02:19:11  rwt5629
 * updated all the headers to actually reflect CVS commitments
 *
 */

/**
 * Class Logic
 *
 * @author=Ryan Tenney
 */

import java.util.*;
import java.io.*;

public class Logic
{

    private int inputs, outputs, numInputs, numOutputs, numGates;
    private char outputMode = 'b';
    private boolean verboseOutput = false;
    private String filePath;
    private String[] fileData;
    private BufferedReader inFile;

    private Circuit gateSim;

    public Logic( String path, char outputMode, boolean verbose )
    {
	this.filePath = path;
	this.outputMode = outputMode;
	this.verboseOutput = verbose;
	
//	try {
	    readFile( this.filePath );
	    digest();
//	}
//	catch ( Exception ex ) {
//	    System.out.println( ex.toString() );
//	    System.out.println( "An error occurred during file io" );
//	}
    }

    private void readFile(String path)
    {
	try {
	    int line = 0;
	    String lastLine;
	    ArrayList file = new ArrayList();

	    inFile = new BufferedReader(new FileReader(path));
	    do {
		lastLine = inFile.readLine();
		file.add( lastLine );
		file.ensureCapacity( file.size() + 1 );
	    } while ( lastLine != null );
	    file.remove( file.size() - 1 );
	    fileData = new String[ file.size() ];
	    file.toArray( fileData );
	    inFile.close();
	}
	catch ( FileNotFoundException ex ) {
	    System.out.println( "File Not Found Exception" );
	}
	catch ( IOException ex ) {
	    System.out.println( "IO Exception" );
	}
	catch ( Exception ex ) {
	    System.out.println( "Unknown Exception" );
	}
    }

    private void digest()
    {
	for ( int i = 0; i < fileData.length; i++ )
	{
	    String token = fileData[ i ].substring( 0, 4 );
	    if ( token.equals("EXTE") ) {
		// EXTERNAL x y
		String[] external = fileData[ i ].split( "\\s" );
		numInputs = Integer.parseInt( external[ 1 ] );
		numOutputs = Integer.parseInt( external[ 2 ] );
	    }
	    else if ( token.equals("GATE") ) {
		// GATES x
		String[] gates = fileData[ i ].split( "\\s" );
		numGates = Integer.parseInt( gates[ 1 ] );
	    }
	    else if ( token.equals("OPTS") ) {
		// OPTS -[v](bdh)
		
	    }
	    else {
		// gate-ident gate-type inputs destinations
		if ( (i > 1) && (gateSim == null) )
		    gateSim = new Circuit( numInputs, numOutputs );
		
		Gate added = gateSim.addGate( fileData[ i ] );
	    }
	}
	
	gateSim.makeConnections();
    }

    private void debug()
    {
	for ( int i = 0; i < fileData.length; i++ )
	    System.out.println( fileData[ i ] );
    }

    public void truthTable()
    {
	int[] outputs = new int[ (int)Math.pow( 2, numInputs ) ];
	int inputSizer = (int)Math.pow( 2, numInputs + 1 );
	int outputSizer = (int)Math.pow( 2, numOutputs + 1 );

	for ( int i = 0; i < (int)Math.pow( 2, numInputs ); i++ )
	{
	    outputs[ i ] = gateSim.evaluate( i );

	    String binIn, binOut;
	    binIn = Integer.toBinaryString( i + inputSizer);
	    binIn = binIn.substring( binIn.length() - numInputs );
	    binOut = Integer.toBinaryString( outputs[ i ] + outputSizer );
	    binOut = binOut.substring( binOut.length() - numOutputs );

	    System.out.println( binIn + " " + binOut );
	}
    }

    public static void main( String[] args )
    {
	char outputMode = 'd';
	boolean verbose = false;
	String path = null;

	for ( int x = 0; x < args.length; x++ ) {
	    if ( args[ x ].charAt( 0 ) == '-' ) {
		// is a command line switch?
		switch ( args[ x ].charAt( 1 ) ) {
		    case 'v':		// verbose mode switch
			verbose = true;
			break;
		    case 'b':		// binary mode switch
			outputMode = 'b';
			break;
		    case 'd':		// decimal mode switch
			outputMode = 'd';
			break;
		    case 'x':		// hexadecimal mode switch
			outputMode = 'h';
			break;
		    default:		// invalid parameter
			break;
		}
	    }
	    else {
		// is not a command line switch
		path = args[ x ];
	    }
	}

	if ( path != null ) {
	    run( path, outputMode, verbose );
	}
	else {
	    // just exit i guess...
	}
    }

    private static void run( String path, char output, boolean verbose )
    {
	Logic l = new Logic( path, output, verbose );
	l.truthTable();
    }
}
