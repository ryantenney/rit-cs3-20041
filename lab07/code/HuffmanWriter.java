/*
 * HuffmanWriter.java
 *
 * Version:
 *    $Id: HuffmanWriter.java,v 1.1 2004/10/18 20:34:29 ptt Exp ptt $
 *
 * Revisions:
 *    $Log: HuffmanWriter.java,v $
 *    Revision 1.1  2004/10/18 20:34:29  ptt
 *    Initial revision
 *
 */

import java.io.*;
import java.util.*;

/**
 * A Writer capable of writing Huffman encoded bit streams.
 *
 * In this version of the program the frequency table is stored at the
 * beginning of the file.  When the file is uncompressed this table will 
 * be used to build the required Huffman tree.  Note that since multiple 
 * Huffman trees can be generated from the same frequency table it is 
 * important that the compress and uncompress program build the Huffman 
 * trees in the same way.
 *
 * @author     Paul Tymann
 */

public class HuffmanWriter extends Writer {

    // The stream the huffman coded bits will be written to

    private BitOutputStream out;

    // The translation of characters to bit strings will be
    // done using a hashtable.  The keys in the hastable are the
    // characters in the file (derived from the frequency table),
    // the values are strings consisting of 0s and 1s that give
    // the sequence of bits to write for the key.

    private Map tTable = null;

    /**
     * Create a Huffman writer where the object lock is used to synchronize
     * operations on this stream. For efficiency, a character-stream object 
     * may use an object other than itself to protect critical sections.
     *
     * @param lock the object used to synchronize operations on this stream
     */

    public HuffmanWriter( Object lock ) {
	super( lock );
    }

    /**
     * Create a Huffman writer given a frequency table for the
     * characters to be written to the stream.  The constructor
     * uses the frequency table to create a Huffman tree using the
     * static method makeTree() from the HuffmanTree class.  The
     * resulting translation table is then used to encode this
     * stream.
     *
     * Before returning, the frquency table is written to the stream.
     * Since a write() operation is performed on a stream, an
     * IOException will be thrown if an I/O error occurs.
     *
     * @param    myStream   the stream to write the bits to.
     * @param    freqTable  the character frequencies.
     *
     * @exception    IOException    if an I/O error occurs.
     */

    public HuffmanWriter( OutputStream myStream, FrequencyTable freqTable ) 
	throws IOException {

	out = new BitOutputStream( myStream );

	// Make the tree and get the translation table.  Note that
	// once the translation table is retrieved it is no longer
	// necessary to keep a reference to the Huffman tree.

	HuffmanTree tree = HuffmanTree.makeTree( freqTable );
	tTable = tree.getTranslationTable();

	// Write out the frequency table.

	freqTable.write( this.out );
    }

    /**
     * Close the stream, flushing it first. Once a stream has been 
     * closed, further write() or flush() invocations will cause
     * an IOException to be thrown. Closing a previously-closed stream,
     * however, has no effect.
     *
     * @exception    IOException    If an I/O error occurs.
     */

    public void close() throws IOException {
	out.flush();
	out.close();
    }

    /**
     * Flush the stream. If the stream has saved any characters from 
     * the various write() methods in a buffer, write them immediately
     * to their intended destination. Then, if that destination is
     * another character or byte stream, flush it.  Thus one flush()
     * invocation will flush all the buffers in a chain of Writers
     * and OutputStreams.
     *
     * @exception    IOException    If an I/O error occurs.
     */

    public void flush() throws IOException {
	out.flush();
    }

    /**
     * Write a portion of an array of characters.
     *
     * @param    cbuf    Array of characters.
     * @param    off     Offset from which to start writing characters.
     * @param    len     Number of characters to write.
     *
     * @exception    IOException    If an I/O error occurs.
     */

    public void write( char cbuf[], int off, int len ) throws IOException {
	for ( int i = 0; i < len; i++, off++ ) {
	    this.write( cbuf[ off ] );
        }
    }

    /**
     * Writes the specified character, in encoded form, to the
     * bit stream.  The string that represents this character is
     * retrieved from the hashtable and used to write the
     * bits to the output stream.
     *
     * If the character is not found in the translation table, or
     * an I/O error occurs during writing, an IOException will
     * be thrown.
     *
     * @param    ch    the character to encode and write to the stream.
     *
     * @exception    IOException    If the character is not found in the
     *                              translation table, or an I/O error
     *                              occurs.
     */

    public void write( char ch ) throws IOException {
	if ( tTable == null ) {
            throw new IOException();
        }

	// Retrieve the bit string from the translation table

	String bits = ( String )tTable.get( new Character( ch ) );

	// If the character is not in the table, throw an
	// IOException.

	if ( bits == null ) {
            throw new IOException();
        }

	// Loop through the characters in the bit strings, writing
	// out the corresponding bits to the output stream.

	for ( int i = 0; i < bits.length(); i++ ) {
	    if ( bits.charAt( i ) == '0' ) {
		out.writeBit( 0 );
	    } else {
		out.writeBit( 1 );
            }
        }
    }

} // HuffmanWriter
