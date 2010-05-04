/*
 * ThruwaySimulator.java
 *
 * Version:
 *    $Id: ThruwaySimulator.java,v 1.1 2004/09/10 01:50:50 cs3 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: ThruwaySimulator.java,v $
 *    Revision 1.1  2004/09/10 01:50:50  cs3
 *    Initial revision
 *
 */

import java.io.*;
import java.util.*;

/**
 * Run the EZ pass simulation.  This program reads a series of simulated
 * EZ pass scan inputs from a data file and logs that information with an
 * EZPassDB object.  After all of the scans have been processed, two reports
 * are printed.  The first lists all the vehicles that are still on the
 * Thruway.  The report lists the vehicles in sorted order based on the
 * exit number where they entered the Thruway.  The second report lists
 * billing information for each vehicle that completed a trip on the 
 * Thruway.  This report is sorted in order by tag and then the exit
 * number where the vehcile entered the Thruway.
 *
 * @author     Paul Tymann
 */

public class ThruwaySimulator {

    // The EZPass database used in this simulation

    private static EZPassDB ezCPU = new EZPassDB();

    /**
     * Main entry point for the simulation.
     *
     * @param args command line arguments (name of data file)
     */
    
    public static void main( String args[] ) {
        String curRecord = null;
        int exit = 0;

	// Handle command line arguments

	if ( args.length != 1 ) {
	    System.err.println( "Usage:  java ThruwaySimulator data-file" );
	    System.exit( 1 );
	}

	// Attempt to open the data file and process the input.
	// Data records are of the form TTTTTTEE where TTTTTT is
	// the tag and EE is the exit number.  Any malformed input,
	// or I/O error will cause this program to terminate.

	try {
	    BufferedReader in =
		new BufferedReader( new FileReader( args[ 0 ] ) );

	    // Read records from the file, extract the appropriate information
	    // and then log the information with the EZPassDB object.

	    while ( ( curRecord = in.readLine() ) != null ) {

		// Vehicle tag is in positions 0 through 5 inclusive.
		// The exit number is the last two characters in the string

		exit = Integer.parseInt( curRecord.substring( 6 ) );

		ezCPU.log( curRecord.substring( 0, 6 ), exit );
	    }

	    in.close();
	}
	catch ( Exception e ) {
	    System.err.println( "ThruwaySimulator:  " + e.getMessage() );
	    System.exit( 1 );
	}

	// Print the report listing the vehicles still on the Thruway

	printIncompleteList();

	// Print the billing information

	printBills();
    }

    /**
     * Print out a report listing the vehicles that are still on the
     * Thruway.  The vehicles on the listing will be printed in
     * order based on the exit where they entered the Thruway.
     */
    
    private static void printIncompleteList() {
        int lastExit = 0;
        List incomplete = null;
        Iterator i = null;
        TollRecord curRecord= null;

	// Report heading

	System.out.println( "Incomplete Trips listed by exit number" );
	System.out.println( "--------------------------------------" );

	// Last exit printed (used to produce line breaks)

	lastExit = TollRecord.MAX_EXIT + 1;

	// Get the open list and prepare to iterate through it

	incomplete = ezCPU.openTrips();
	i = incomplete.iterator();

	// For each TollRecord in the list, print out the Vehicle tag

	while ( i.hasNext() ) {
	    curRecord = ( TollRecord )i.next();

	    // If this is a different exit, print a new exit header

	    if ( curRecord.getEntry() != lastExit ) {
		lastExit = curRecord.getEntry();
		System.out.println();
		System.out.println( "Exit:  " + lastExit );
	    }

	    // Print the tag

	    System.out.println( "    " + curRecord.getTag() );
	}

	// Summary line for the report

	System.out.println( "\nTotal vehicles:  " + incomplete.size() );
	System.out.println();
    }

    /**
     * Print out a billing report for the vehicles that completed trips
     * on the Thruway.  The report lists the trips first by vehicle tag
     * and then by the exit where the vehicle entered the Thruway.  The
     * toll that was charged for each trip plus the total toll due is
     * printed for each unique vehicle.
     */
    
    private static void printBills() {
        TollRecord curRecord = null;

	// Report header

	System.out.println( "Billing information" );
	System.out.println( "-------------------" );

	// Last tag printed (used to produce line breaks) and
	// total toll due from this tag

	String lastTag = "";
	double custTotal = 0;
	double grandTotal = 0;

	// Get the completed list and prepare to iterate through it

	List completed = ezCPU.charges();
	Iterator i = completed.iterator();

	// For each completed trip print the entry and exit points and
	// the amount of toll charged for the trip.  Every time a
	// new vehicle is encountered, the total toll owed by the previous
	// vehicle is printed followed by a short heading for the new
	// vehicle

	while ( i.hasNext() ) {
	    curRecord = ( TollRecord )i.next();

	    // If this is a different tag, print total, followed
	    // by a new header

	    if ( !curRecord.getTag().equals( lastTag ) ) {

		// We don't need to print summary information
		// for the previous vehicle if this is the first
		// one

		if ( !lastTag.equals( "" ) ) {
		    printCustSummary( custTotal );
		}

		// Reset counters for the new vehicle

		lastTag = curRecord.getTag();
		custTotal = 0;

		// Print out a little vehicle header

		System.out.println( "Tag:  " + lastTag );
	    }

	    // Print the trip information.  If something goes wrong
	    // while trying to collect the information, the
	    // information contained in this record will not be printed

	    try {
		System.out.println( "  From " + curRecord.getEntry() +
				    " to " + curRecord.getExit() +
				    " Toll:  $" + curRecord.getToll() );

		custTotal += curRecord.getToll();
                grandTotal += curRecord.getToll();
	    }
	    catch ( TollRecordException e ) {
		System.err.println( "ThruwaySimulator:  internal error" );
		System.exit( 1 );
	    }
	}

	// Print the report summary

        if ( !lastTag.equals( "" ) ) {
            printCustSummary( custTotal );

            System.out.println( "\nTotal Due:  $" + grandTotal );
            System.out.println();
        }
    }

    /**
     * Print the summary information for a single customer.
     *
     * @param total the total owed by the current customer
     */

    private static void printCustSummary( double total ) {
        System.out.println();
        System.out.println( "  Total due:  $" + total );
        System.out.println();
    }

} // ThruwaySimulator
