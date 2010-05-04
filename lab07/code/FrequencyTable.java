/*
 * FrequencyTable.java
 *
 * Version:
 *     $Id: FrequencyTable.java,v 1.1 2004/10/18 20:34:30 ptt Exp ptt $
 *
 * Revisions:
 *     $Log: FrequencyTable.java,v $
 *     Revision 1.1  2004/10/18 20:34:30  ptt
 *     Initial revision
 *
 */

import java.util.*;
import java.io.*;

/**
 * Tables capable of counting the number of times a particular character
 * appears in a text file.
 *
 * @author     Paul Tymann
 */

public class FrequencyTable {
    // A hashtable is used to maintain the counts.  Keys are the
    // character being counted and the values are the number of times
    // the character appears.

    private Map counts;

    /**
     * Create an empty frequency table.
     */

    public FrequencyTable() {
	counts = new TreeMap();
    }

    /**
     * Remove all entries from the frequency table.
     */

    public void clear() {
	// The Map clear() method is not used since it is an optional
	// method and the implementation class may not support it.

	counts = new HashMap();
    }

    /**
     * Read a frequency table from a bit stream.  Each row is read
     * in consecutive order from the file.  The rows are assumed to 
     * be in the format <i>ciiii</i>, where <i>c</i> is a character 
     * and <i>iiii</i> is an integer representing the number of times 
     * the character appeared in the file.  The end of the table is 
     * marked by a 0 byte.  An IOException is thrown if an error 
     * occurs while reading the table from the file.
     *
     * @param    BitInputStream    the stream to read the table from.
     *
     * @exception    IOException    if an I/O error occurs.
     */

    public void read( BitInputStream in ) throws IOException {
	int ch;

	// Read until the end of the table ...

	while ( ( ch = in.read() ) != -1 && ch != 0 ) {

	    // Get the frequency

	    int freq = in.readUnsignedInt();

	    // Add the information to the map

	    counts.put( 
                new Character( ( char )ch ), 
                new Integer( freq ) );
	}
    }

    /**
     * Write a frequency table to a bit stream.  Each row is written
     * in consecutive order to the file.  The rows are written in the
     * format <i>ciiii</i>, where <i>c</i> is a character and <i>iiii</i> 
     * is an integer representing the number of times the character
     * appeared in the file.  The end of the table is marked by a 0 byte.
     * An IOException is thrown if an error occurs while writing the table
     * to the file.
     *
     * @param    BitOutputStream    the stream to write the table to.
     *
     * @exception    IOException    if an I/O error occurs.
     */

    public void write( BitOutputStream out ) throws IOException {
	// Need to step through the entries in the map

	Iterator i = counts.entrySet().iterator();

	while ( i.hasNext() ) {
	    Map.Entry cur = ( Map.Entry ) i.next();

	    // Convert the current entry, character and frequency,
	    // to primitive types and write them to the stream

	    char ch = ( ( Character ) cur.getKey() ).charValue();
	    int count = ( ( Integer ) cur.getValue() ).intValue();

	    out.write( ch );
	    out.writeUnsignedInt( count );
	}

	// The end of the frequency table is marked with a '0'

	out.write( 0 );
    }

    /**
     * Add one to the frequency of the specified character.  If the
     * character is already in the table, its frequency will be incremented
     * by one, otherwise a new entry is added to the table with a frequency
     * of one.
     *
     * @param    char    the character whose frequency is to be incremented.
     */

    public void add( char ch ) {
	Integer freq;

	Character key = new Character( ch );

	if ( ( freq = ( Integer )counts.get( key ) ) == null ) {
	    freq = new Integer( 1 );
	} else {
	    freq = new Integer( freq.intValue() + 1 );
        }
	counts.put( key, freq );
    }

    /**
     * Return the frequency of the specified character.  If the character
     * is not found in the table, 0 will be returned.
     *
     * @param    ch    the character to look up.
     *
     * @return        the frequency of the specified character or 0 if the
     *                 character is not in the table.
     */

    public int getFrequency( char ch ) {
	Integer freq;
	int retval = 0;

	Character key = new Character( ch );

	if ( ( freq = ( Integer )counts.get( key ) ) != null ) {
	    retval = freq.intValue();
        }
	return retval;
    }

    /**
     * Return the sum of all the frequencies in the table.  This should be
     * the same as the total number of characters that appear in the file
     * described by this table.
     *
     * @return    the number of characters recorded in the table.
     */

    public int charsInFile() {
	int retval = 0;

	Iterator i = counts.values().iterator();

	// Step through the frequencies, summing as we go

	while ( i.hasNext() ) {
	    retval = retval + ( ( Integer ) i.next() ).intValue();
        }

	return retval;
    }

    /**
     * Return a set containing all of the keys in the table.
     *
     * @return    a set containing all of the keys in the table.
     */

    public Set chars() {
	return counts.keySet();
    }

    /**
     * Print the contents of the table to standard output.
     */

    public void print() {
	// Need to step through the entries in the map

	Iterator i = counts.entrySet().iterator();

	while ( i.hasNext() ) {
	    Map.Entry cur = ( Map.Entry )i.next();

	    System.out.println( printChar( ( Character ) cur.getKey() )
				+ '\t' 
				+ ( ( Integer ) cur.getValue() ).intValue() );
	}
    }

    // Print the characters in a "pretty" format.  In particular write
    // non-printable characters in a readable form.

    private String printChar( Character ch ) {
	if ( !Character.isISOControl( ch.charValue() ) ) {
	    return new String( "'" + ch + "'" );
	} else {
	    switch ( ch.charValue() ) {
	    case '\b':
		return "'\b'";
	    case '\f':
		return "'\\f'";
	    case '\n':
		return "'\\n'";
	    case '\r':
		return "'\\r'";
	    case '\t':
		return "'\\t'";
	    default:
		return "'\\"  
                        + Integer.toOctalString( (int)ch.charValue() ) + "'";
	    }
        }
    }

} // FrequencyTable
