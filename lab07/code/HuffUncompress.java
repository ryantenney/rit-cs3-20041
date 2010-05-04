/*
 * HuffUncompress.java
 *
 * Version:
 *    $Id: HuffUncompress.java,v 1.1 2004/10/18 20:34:30 ptt Exp ptt $
 *
 * Revision:
 *    $Log: HuffUncompress.java,v $
 *    Revision 1.1  2004/10/18 20:34:30  ptt
 *    Initial revision
 *
 */

/**
 * This program uncompresses a Huffman encoded file.
 *
 * @author     Paul Tymann
 */

import java.io.*;

public class HuffUncompress {

    /*
     * main method accepts a Huffman compressed file, uncompresses it
     * and writes it back out.
     *
     * @param    args   command line arguments
     *                  name of file to read in and to write out
     */
    public static void main( String args[] ) {
	// Usage check

	if ( args.length != 2 ) {
	    System.err.println( "Usage:  java HuffUncompress infile outfile" );
	    System.exit( 1 );
	}

	// Open the input

	HuffmanReader in = null;

	try {
	    in = new HuffmanReader( new FileInputStream( args[0] ) );
	}
	catch ( FileNotFoundException e ) {
	    System.err.println( "HuffUnCompress:  unable to open " + args[0] );
	    System.exit( 1 );
	}
	catch ( IOException e ) {
	    System.err.println( "HuffUncompress:  I/O error" );
	    System.exit( 1 );
	}

	BufferedWriter out = null;

	try {
	    // Open the output file

	    out = new BufferedWriter( new FileWriter( args[1] ) );

	    int ch;

	    // Write decoded characters to the output file.

	    while ( ( ch = in.read() ) != -1 ) {
		out.write( ( char )ch );
            }

	    // Close the streams

	    in.close();
	    out.close();
	}
	catch ( FileNotFoundException e ) {
	    System.err.println( "HuffUncompress:  unable to open " + args[1] );
	    System.exit( 1 );
	}
	catch ( IOException e ) {
	    System.err.println( "HuffUncompress:  I/O error" );
	    System.exit( 1 );
	}
    }
} // HuffUncompress
