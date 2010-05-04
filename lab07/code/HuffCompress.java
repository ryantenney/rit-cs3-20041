/*
 * HuffCompress.java
 *
 * Version:
 *    $Id: HuffCompress.java,v 1.1 2004/10/18 20:34:29 ptt Exp ptt $
 *
 * Revisions:
 *    $Log: HuffCompress.java,v $
 *    Revision 1.1  2004/10/18 20:34:29  ptt
 *    Initial revision
 *
 */

import java.io.*;

/**
 * This program will compress a file using Huffman encoding.
 *
 * @author     Paul Tymann
 */

import java.io.*;

public class HuffCompress {

    /*
     * This is the main method. It reads a file, builds a frequency
     * table, and writes the encoded file back out.
     *
     * @param     args    command line arguments, two file names
     *
     */ 
    public static void main( String args[] ) {
	// Usage check

	if ( args.length != 2 ) {
	    System.err.println( "Usage:  java HuffCompress in-file out-file" );
	    System.exit( 1 );
	}

	// Open the input file

	BufferedReader in = null;

	try {
	    in = new BufferedReader( new FileReader( args[0] ) );
	}
	catch ( FileNotFoundException e ) {
	    System.err.println( "HuffCompress:  unable to open " + args[ 0 ] );
	    System.exit( 1 );
	}

	try {

	    // Build the frequency table

	    FrequencyTable freq = new FrequencyTable();
	    int ch;

	    while ( ( ch = in.read() ) != -1 ) {
		freq.add( ( char )ch );
            }

	    // Reset the file back to the beginning

	    in.close();
	    in = new BufferedReader( new FileReader( args[ 0 ] ) );

	    // Now write the file using a HuffmanWriter
	    
	    HuffmanWriter out = new HuffmanWriter( 
                    new FileOutputStream( args[ 1 ] ), freq );

	    while ( ( ch = in.read() ) != -1 ) {
		out.write( ( char ) ch );
            }

	    // Close the streams

	    in.close();
	    out.close();
	}
	catch ( FileNotFoundException e ) {
	    System.err.println( "HuffCompress:  unable to open " + args[ 1 ] );
	    System.exit( 1 );
	}
	catch ( IOException e ) {
	    System.err.println( "HuffCompress:  I/O error" );
	    System.exit( 1 );
	}
    }

} // HuffCompress
