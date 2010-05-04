/*
 * HuffmanReader.java
 *
 * Version:
 *    $Id: HuffmanReader.java,v 1.1 2004/10/18 20:34:30 ptt Exp ptt $
 *
 * Revision:
 *    $Log: HuffmanReader.java,v $
 *    Revision 1.1  2004/10/18 20:34:30  ptt
 *    Initial revision
 *
 */
 
import java.io.*;

/**
 * A Reader capable of decoding Huffman encoded bit streams.
 *
 * @author     Paul Tymann
 */

public class HuffmanReader extends Reader {

    // The stream the huffman coded bits are originating from

    private BitInputStream bin;

    // Since the characters are written as bits, it is no longer the
    // case that the number of bytes in the files is equal to the number
    // of characters.  The frequency table will be used to determine the
    // number of characters in the file and this variable will record the
    // number of characters that still need to be read from the file.

    private int charsToRead = 0;

    // The root of the Huffman tree to be used in decoding.

    private HuffmanTree root = null;

    /**
     * Create a Huffman reader where the object 'lock' is used to synchronize
     * operations on this stream. For efficiency, a character-stream object 
     * may use an object other than itself to protect critical sections.
     *
     * @param lock used to synchronize operations on this stream.
     */

    public HuffmanReader( Object lock ) {
	super( lock );
    }

    /**
     * Create a Huffman reader that will read bits from the specified
     * input stream.  During construction the frequency table will be
     * read from the stream and the Huffman tree required for decoding
     * will be built.  Since the frequency table is being read from the
     * stream, this method may throw an IOException.
     *
     * @param    in    the InputStream the bits are coming from.
     *
     * @exception      IOException    if an I/O error occurs while reading
     *                                the frequency table.
     */

    public HuffmanReader( InputStream in ) throws IOException {
	// Reading actually has to appen through a bit stream.

	bin = new BitInputStream( in );

	// Extract the frequency table.

	FrequencyTable freq = new FrequencyTable();
	freq.read( bin );

	// Make the decoding tree.

	root = HuffmanTree.makeTree( freq );

	// Determine the number of characters (not the number of bits)
	// contained in this file.

	charsToRead = freq.charsInFile();
    }

    /**
     * Close the stream. Once a stream has been closed, further read(),
     * ready(), mark(), or reset() invocations will throw an IOException. 
     * Closing a previously-closed stream, however, has no effect.
     *
     * @exception    IOException    If an I/O error occurs.
     */

    public void close() throws IOException {
	bin.close();
    }

    /**
     * HuffmanReaders do not support the mark() operation.
     */

    public boolean markSupported() {
	return false;
    }

    /**
     * Read the next character from the BitInputStream.  An IOException
     * will be thrown if an I/O error occurs.
     *
     * @return    the next character in the file or -1 if the
     * last character in the file has been read.
     *
     * @exception    IOException    If an I/O error occurs. 
     */

    public int read() throws IOException {
	int retVal = -1;

	// If there are any characters to read

	if ( charsToRead != 0 ) {

            // << INSERT YOUR CODE STARTING HERE >>

            // *READ* the comments.  They outline the algorithm that
            // you must implement to complete this method.
	    // Start traversing the Huffman tree at the root
	    HuffmanTree this1 = root;

	    // While the current node is not a leaf ....
	    while ( !this1.isLeaf() ) {
		int bit = bin.readBit();
                // Read the next bit from the bit stream.  If we have
                // hit the end of the stream (i.e., read() returns a
                // -1) it means that the file has been truncated.  Throw
                // an IOException (no error message) to indicate to the 
                // caller that the file is not in the correct format since
                // eof was found too soon.		

		// Now that we have a bit, if it is a 0, make the
		// current node the left subtree, otherwise make the
		// current node the right subtree.

		switch( bit ) {
		    case 0:
			this1 = this1.getLeft();
			break;

		    case 1:
			this1 = this1.getRight();
			break;

		    default:
			// will catch case -1
			// has reached EOF prematurely or an 
			// unrecognizable return
			throw new IOException();
		}

		// Go back to the top of the loop
	    }

	    // If the loop terminated, the current node contains the
	    // decoded character.  Set retVal to contain the decoded
	    // character.

	    retVal = this1.getChar();

	    // Decrement the charsToRead since we have extracted one more
	    // character from the file.

	    charsToRead--;

	}

	return retVal;
    }

    /**
     * Read characters into a portion of an array. This method will block 
     * until some input is available, an I/O error occurs, or the end of 
     * the stream is reached.
     *
     * @param    cbuf    Destination buffer.
     * @param    off     Offset at which to start storing characters.
     * @param    len     Maximum number of characters to read.
     * 
     * @return    The number of characters read, or -1 if the end of the 
     *            stream has been reached.
     *
     * @exception    IOException    If an I/O error occurs.
     */

    public int read( char[] cbuf, int off, int len ) throws IOException {
	int lastVal = 0;
	int i;
        int retVal;

	// Use read() to do the actual reading

	for ( i = off; i < len && lastVal > 0; i++ ) {
	    lastVal = read();
	    if ( lastVal > 0 ) cbuf[ i ] = ( char )lastVal;
	}

	// If the specified number of characters because we hit the
	// end of the stream, return a -1, otherwise return the
	// number of characters that were read.

	if ( ( i - off ) != 0 ) {
	    retVal = i - off;
        } else {
	    retVal = -1;
        }
        return retVal;
    }
	
} // HuffmanInputStream


