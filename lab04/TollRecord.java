/*
 * TollRecord.java
 *
 * Version:
 *    $Id: TollRecord.java,v 1.7 2004/10/04 03:56:18 rwt5629 Exp $
 *
 * Revisions:
 *    $Log: TollRecord.java,v $
 *    Revision 1.7  2004/10/04 03:56:18  rwt5629
 *    complete for activity 2
 *
 *    Revision 1.3  2004/09/28 21:48:17  rwt5629
 *    Yet another routine checkin
 *
 *    Revision 1.2  2004/09/28 15:01:12  rwt5629
 *    I have no comments concerning this checkin.
 *
 *    Revision 1.1.1.1  2004/09/28 14:48:47  rwt5629
 *    i have no comment
 *
 *    Revision 1.1  2004/09/27 10:28:01  ptt
 *    Initial revision
 *
 */

import java.io.*;

/**
 * A class that can be used to create objects that record trips made
 * by vehicles on the thruway.  A toll record records the tag of the
 * vehicle and the exits at which the vehicle entered and left the
 * thruway.  TollRecord objects can compute the toll that must
 * be paid by the driver of the vehicle.  The toll is $1.25 for every
 * exit the driver passes on the Thruway (not counting the exit at
 * which they entered).
 *
 * @author     Paul Tymann
 */

public class TollRecord {

    /**
     * In this simplified version of the Thruway toll system, a customer
     * is charged $1.25 for every exit that they pass while on the
     * Thruway.  For example, if a customer enters the Thruway at exit
     * 20 and leaves the Thruway at exit 25, they will be charged $6.25
     * (a total of 5 exits).
     */
    public static final double TOLL_RATE = 1.25;

    /**
     * The maximum number of exits on the simulated thruway.  Exit
     * numbers start at 1 and end at MAX_EXIT.
     */
    public static final int MAX_EXIT = 50;

    /**
     * Constant (-1) to be used to indicate that an entry point or exit point
     * has not been initialized.
     */
    public static final int NOT_RECORDED = -1;

    // Instance variables

    private String tag;                // Unique identification for the vehicle
    private int entry = NOT_RECORDED;  // Entry point
    private int exit = NOT_RECORDED;   // Exit point

    /**
     * Create a new TollRecord given the tag of the vehicle.
     *
     * @param tag a unique identifier for the vehicle that this toll
     *            record represents.
     */
    public TollRecord( String tag ) {
	    this.tag = tag;
    }

    /**
     * Record the exit number at which this vehicle entered the
     * thruway.  The exit number may not be less than one or greater
     * than MAX_EXIT.  If the exit number is invalid a TollRecordException
     * will be thrown that contains the message "Invalid entry point".
     *
     * @param entry the exit at which the vehicle entered the thruway.
     *
     * @exception TollRecordException if the exit number is invalid.
     */
    public void setEntry( int entry ) throws TollRecordException {
        if ( entry > MAX_EXIT || entry < 1 ) {
            throw new TollRecordException( "Invalid entry point" );
        } else {
            this.entry = entry;
        }
    }

    /**
     * Record the exit number at which this vehicle left the
     * thruway.  The exit number may not be less than one or greater
     * than MAX_EXIT.  If the exit number is invalid a TollRecordException
     * will be thrown that contains the message "Invalid exit point".
     *
     * @param exit the exit at which the vehicle left the thruway.
     *
     * @exception TollRecordException if the exit number is invalid: an 
     *            invalid exit number, the entry point has not been set yet
     *            or the exit number equals the entry number.
     */
    public void setExit( int exit ) throws TollRecordException {
        if ( exit > MAX_EXIT || exit < 1 ) {
            throw new TollRecordException( "Invalid exit point" );
        } else {
            this.exit = exit;
        }
    }

    /**
     * Return the vehicle tag associated with this TollRecord.
     *
     * @return the vehcile tag associated with this TollRecord.
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * Return the exit at which this vehicle entered the thruway.
     * If the entry point has not been set, a -1 will be be returned.
     *
     * @return the exit number where this vehicle entered the thruway,
     *         or a -1 if the entry point has not been recorded.
     */
    public int getEntry() {
        return entry;
    }

    /**
     * Return the exit at which this vehicle left the thruway.
     * If the exit point has not been set, a -1 will be be returned.
     *
     * @return the exit number where this vehicle left the thruway,
     *         or a -1 if the exit point has not been recorded.
     */
    public int getExit() {
        return exit;
    }

    /**
     * Compute the toll paid by this vehicle.  If the toll cannot
     * be computed because either one of the ends points has not been
     * recorded a TollRecordException will be thrown that contains the
     * message "Incomplete toll record".
     *
     * @return the toll to be paid by this vehicle.
     *
     * @exception TollRecordException if the toll cannot be computed.
     */
    public double getToll() throws TollRecordException {
        if ( entry == NOT_RECORDED || exit == NOT_RECORDED ) {
	        throw new TollRecordException( "Incomplete toll record" );
	    } else {
	        return Math.abs( entry - exit ) * TOLL_RATE;
    	}
    }

    // Class Object stuff

    /**
     * Indicates whether some other object is "equal to" this one. 
     *
     * @param o a reference to the object to compare this one with.
     *
     * @return true if this object is the same as the obj argument; 
     *         false otherwise.
     */
    public boolean equals( Object o ) {
        TollRecord compare;
        if ( o instanceof TollRecord ) {
            compare = ( TollRecord ) o;
            return ( entry == compare.getEntry() && exit == compare.getExit()
                && tag == compare.getTag() );
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of this object.  The format
     * of the string returned by this method is given below:
     * <p>
     * <blockquote>
     * <pre>Vehicle ID:  XXXXXX Entry = XX Exit = XX</pre>
     * </blockquote>
     *
     * @return a string representation of this object.
     */
    
    public String toString() {
        return "Vehicle ID:  " + tag + " Entry = " 
            + Integer.toString( entry )
            + " Exit = " + Integer.toString( exit );
    }

    /**
     * Returns a hash code value for the object.
     * (The main reason this is here is that Java expects
     * hashCode() to be redefined if equals() is
     * redefined. One must make sure that<br/>
     * a.equals(b) => * a.hashCode() == b.hashCode().)
     *
     * @return a hash code for this object.
     */
    
    public int hashCode() {
        return ( ( tag.hashCode() * 10000 ) + ( entry * 100 ) + ( exit ) );
    }

    /**
     * A simple test program for the class.  Reads lines of the form:
     * <p>
     * <blockquote>
     * <pre>TTTTTTNNXX</pre>
     * </blockquote>
     *
     * from the keyboard and creates a TollRecord object based on the
     * information. The toll that will be charged is then displayed.
     * Note two things:<ul>
     * <li>The data format contains no spaces and the lengths of the
     *     fields are fixed: 6 for the Tag, 2 for the eNtry point, and
     *     2 for the eXit point.</li>
     * <li>This format does <em>not</em> match that of the general
     *     simulator used in this lab.</li>
     * </ul>
     *
     * @param args command line arguments (ignored)
     */
    
    public static void main( String args[] ) {

	try {
	    // Input comes from standard input

	    BufferedReader in = 
		new BufferedReader( new InputStreamReader( System.in ) );

	    String input = null;

	    // Read lines from the keyboard, create a toll record, set
	    // the entry and exit points, and then print the toll that
	    // will be charged.

	    while ( ( input = in.readLine() ) != null ) {
		String tag = input.substring( 0, 6 );
		int entry = Integer.parseInt( input.substring( 6, 8 ) );
		int exit = Integer.parseInt( input.substring( 8 ) );

		TollRecord rec = new TollRecord( tag );
		rec.setEntry( entry );
		rec.setExit( exit );

		System.out.println( rec + " Toll = $" + rec.getToll() );
	    }
	}
	catch ( Exception e ) {
	    System.out.println( "TollRecord:  " + e );
	    System.exit( 1 );
	}
    }

} // TollRecord
