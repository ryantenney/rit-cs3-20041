/*
 * BitInputStream.java
 *
 * Version:
 *      $Id: BitInputStream.java,v 1.1 2004/10/18 20:34:29 ptt Exp ptt $
 *
 * Revisions:
 *      $Log: BitInputStream.java,v $
 *      Revision 1.1  2004/10/18 20:34:29  ptt
 *      Initial revision
 *
 */

import java.io.*;

/**
 * This class implements a stream which allows one to read bits and
 * integers in addtion to the normal byte oriented reads provided by
 * InputStream.
 *
 * @author     Paul Tymann
 *
 */

public class BitInputStream extends FilterInputStream {

    /**
     * The number of bytes in a Java int
     */

    public static final int BYTES_IN_INT = 4;

    private int curByte;        // The current byte we are reading from
    private int bitMask = 0;    // The position in the byte we are looking at

    /**
     * Create a BitInputStream that reads from the given InputStream.
     *
     * @param    in    InputStream that supplies data to this stream.
     */

    public BitInputStream( InputStream in ) {
	super( in );
    }

    /**
     * BitInputStreams do not support marking.
     */

    public boolean markSupported() {
	return false;
    }

    /**
     * Read the next byte of data from the input stream. The value byte
     * is returned as an int in the range 0 to 255. If no byte is available
     * because the end of the stream has been reached, the value -1 is 
     * returned. This method blocks until input data is available, the 
     * end of the stream is detected, or an exception is thrown.
     *
     * Bytes in a BitInputStream always start on byte boundaries, therefore
     * any bits that have not been read from the last byte will be discarded.
     *
     * @return       the next byte of data, or -1 if the end of the stream 
     *               is reached.
     *
     * @exception    IOException    if an I/O error occurs.
     */

    public int read() throws IOException {
	// Restart any future bit reads

	bitMask = 0;

	return super.read();
    }

    /**
     * Read some number of bytes from the input stream and store them 
     * into the buffer array b. The number of bytes actually read is 
     * returned as an integer. This method blocks until input data is 
     * available, end of file is detected, or an exception is thrown. 
     *
     * Bytes in a BitInputStream always start on byte boundaries, therefore
     * any bits that have not been read from the last byte will be discarded.
     *
     * @param    b    the buffer into which the data is read.
     *
     * @return        the total number of bytes read into the buffer, 
     *                or -1 is there is no more data because the end of 
     *                the stream has been reached.
     *
     * @exception     IOException    if an I/O error occurs.
     */

    public int read( byte[] b ) throws IOException {
	// Restart any future bit reads 

	bitMask = 0;

	return super.read( b );
    }

    /**
     * Read up to len bytes of data from the input stream into an array 
     * of bytes. An attempt is made to read as many as len bytes, but a 
     * smaller number may be read, possibly zero. The number of bytes 
     * actually read is returned as an integer. 
     *
     * @param    b     the buffer into which the data is read.
     * @param    off   the start offset in array b at which the data 
                       is written.
     * @param    len   the maximum number of bytes to read.
     *
     * @return         the total number of bytes read into the buffer, or 
     *                 -1 if there is no more data because the end of the 
     *                 stream has been reached.
     *
     * @exception      IOException    if an I/O error occurs.
     */

    public int read( byte[] b, int off, int len ) throws IOException {
	// Restart any future bit reads

	bitMask = 0;

        return super.read( b, off, len );
    }

    /**
     * Read the next bit of data from the input stream. The bit value
     * is returned as an int in the range 0 to 1. If no bit is available
     * because the end of the stream has been reached, the value -1 is 
     * returned. This method blocks until input data is available, the 
     * end of the stream is detected, or an exception is thrown.
     *
     * Note that non-bit reads always start on a byte boundary.  A non-bit
     * read will flush bits until it reaches a byte boundary.
     *
     * @return       the next bit of data, or -1 if the end of the stream 
     *               is reached.
     *
     * @exception    IOException    if an I/O error occurs.
     */

    public int readBit() throws IOException {
	// If all the bits in the current byte have been
	// read, read the next byte in the file.

	if ( ( bitMask = bitMask >> 1 ) == 0 ) {
	    int i;

	    if ( ( i = read() ) == -1 ) return -1;

	    curByte = ( byte )i;
	    bitMask = 0x80;
	}

	return ( ( curByte & bitMask ) != 0 ) ? 1 : 0;
    }

    /**
     * Read the next integer from the input stream. Integers consist
     * of 4 consecutive bytes read in big endian order - read the most
     * significant byte first.  If no integer is 
     * available, less than 4 bytes remain in the stream, or the end
     * of the stream has been reached, the value -1 is returned. This 
     * method blocks until input data is available, the end of the 
     * stream is detected, or an exception is thrown.
     *
     * Integers start on byte boundaries.  Any unread bits before the
     * next byte will be flushed.
     *
     * @return       the next integer of data, or -1 if the end of the stream 
     *               is reached.
     *
     * @exception    IOException    if an I/O error occurs.
     */

    public int readUnsignedInt() throws IOException {
	// Restart any future bit reads

	bitMask = 0;

	// Bytes will go in here and then will be converted to an int

	byte bytes[] = new byte[ BYTES_IN_INT ];

	// Read in the bytes that make up the integer

	if ( read( bytes, 0, BYTES_IN_INT ) != BYTES_IN_INT )
	    return -1;

	int retval = 0;

	// Pack the bytes into an int

	for ( int i = 0; i < BYTES_IN_INT; i++ ) {
	    retval = ( retval << 8 ) | ( 0xff & bytes[ i ] );
	}

	return retval;
    }

} // BitInputStream
