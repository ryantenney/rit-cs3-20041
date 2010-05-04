/*
 * BitOutputStream.java
 *
 * Version:
 *      $Log: BitOutputStream.java,v $
 *      Revision 1.1  2004/10/18 20:34:30  ptt
 *      Initial revision
 *
 */

import java.io.*;

/**
 * This class implements a stream which writes bits and integers
 * in addition to the normal byte oriented writes provided by
 * OutputStreams.
 *
 * @author     Paul Tymann
 */

public class BitOutputStream extends FilterOutputStream {

    /**
     *  The number of bytes in a Java int
     */

    public static final int BYTES_IN_INT = 4;

    private int curByte;            // A 'bit' buffer (holds 8 bits)
    private int bitMask = 0x100;    // The position in the buffer to write to

    /**
     * Create a BitOutputStream that writes to the given OutputStream.
     *
     * @param    out    OutputStream to write to.
     */

    public BitOutputStream( OutputStream out ) {
	super( out );
    }

    /**
     * Writes pending bits, closes this output stream and releases 
     * any system resources associated with this stream.
     *
     * @exception    IOException     if an I/O error occurs.
     */
    
    public void close() throws IOException {
	if ( bitMask != 0x100 )  {
	    write ( curByte );
        }
	
	super.close();
    }

    /**
     * Write b.length bytes from the specified byte array to this 
     * output stream. The general contract for write(b) is that it 
     * should have exactly the same effect as the call 
     * write(b, 0, b.length).  Any buffered bits are lost.
     *
     * @param    b    the data.
     *
     * @exception     IOException    if an I/O error occurs.
     */

    public void write( byte[] b ) throws IOException {
	// Reset the bit buffer

	curByte = 0;
	bitMask = 0x100;

	super.write( b );
    }

    /**
     * Write len bytes from the specified byte array starting at offset
     * off to this output stream. The general contract for 
     * write(b, off, len) is that some of the bytes in the array b are 
     * written to the output stream in order; element b[off] is the first 
     * byte written and b[off+len-1] is the last byte written by this 
     * operation. 
     *
     * If b is null, a NullPointerException is thrown. 
     *
     * If off is negative, or len is negative, or off+len is greater than 
     * the length of the array b, then an IndexOutOfBoundsException is thrown.
     *
     * Any buffered bits are lost after a write() operation.
     *
     * @param    b    the data.
     * @param    off  the start offset in the data.
     * @param    len  the number of bytes to write.
     *
     * @exception    IOException    if an I/O error occurs. In particular, 
     *                              an IOException is thrown if the output
     *                              stream is closed.
     */

    public void write( byte[] b, int off, int len ) throws IOException {
	// Reset the bit buffer

	curByte = 0;
	bitMask = 0x100;

	super.write( b, off, len );
    }

    /**
     * Write the specified byte to this output stream. The general contract
     * for write is that one byte is written to the output stream. The byte 
     * to be written is the eight low-order bits of the argument b. The 24
     * high-order bits of b are ignored. 
     *
     * Any buffered bits are lost after a write() operation.
     *
     * @param    b    the byte.
     *
     * @exception    IOException    if an I/O error occurs. In particular, 
     *                              an IOException may be thrown if the
     *                              output stream has been closed.
     */

    public void write( int b ) throws IOException {
	// Reset the bit buffer

	curByte = 0;
	bitMask = 0x100;

	super.write( b );
    }

    /**
     * Write the specified bit to this output stream. The general contract
     * for write is that one bit is written to the output stream. The bit 
     * is the least significant bit of the argument b. The 31 high-order 
     * bits of b are ignored. 
     *
     * @param    b    the bit.
     *
     * @exception    IOException    if an I/O error occurs. In particular, 
     *                              an IOException may be thrown if the
     *                              output stream has been closed.
     */

    public void writeBit( int b ) throws IOException {
	// Get ready to write the next bit into the buffer.  If the
	// 1 shifts off the end, the buffer is full and will be written
	// to the output stream

	if ( ( bitMask = bitMask >> 1 ) == 0 ) {
	    write( curByte );

	    curByte = 0;
	    bitMask = 0x80;
	}

	// If the LSB is set, set the appropriate bit in the buffer

	if ( ( b & 1 ) == 1 ) {
            curByte = curByte | bitMask;
        }
    }

    /**
     * Write the specified int to this output stream. The general contract
     * for write is that four bytes are written to the output stream.  The
     * bytes in the int are written to the stream in big endian order - most
     * significant byte first.
     *
     * Any buffered bits in the bit buffer will be lost.
     *
     * @param    val    the integer.
     *
     * @exception    IOException    if an I/O error occurs. In particular, 
     *                              an IOException may be thrown if the
     *                              output stream has been closed.
     */

    public void writeUnsignedInt( int val ) throws IOException {
	// Reset the bit buffer

	curByte = 0;
	bitMask = 0x100;

	// Used to strip off the written bytes

	int mask = 0x00ffffff;

	for ( int i = 24; i > 0; i = i - 8, mask = mask >> 8 ) {
	    write( val >> i );
	    val = val & mask;
	}

	// Write the last byte to the stream.

	write( val );
    }

} // BitOutputStream
