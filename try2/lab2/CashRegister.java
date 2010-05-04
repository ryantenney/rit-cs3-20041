/*
 * CashRegister.java
 *
 * Version:
 *    $Id: CashRegister.java,v 1.2 2004/12/10 15:57:23 rwt5629 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: CashRegister.java,v $
 *    Revision 1.2  2004/12/10 15:57:23  rwt5629
 *    doin some more work
 *
 *    Revision 1.1  2004/12/10 15:46:52  rwt5629
 *    Initial revision
 *
 */

import java.util.*;
import java.io.*;
import java.text.*;

/**
 * A program that simulates a simple cash register that might be used
 * at a supermarket.  The program takes a single command line argument
 * which is the name of the file that contains the data that will be
 * used to build a PLU file.  Once the file is built, the program reads
 * UPC codes from the command line until it encounters a blank line of
 * input.  These codes represent the items being purchased.  The program
 * will then attempt to read more UPC codes which represent the coupons
 * being redeemed.  The output from the program is a receipt showing
 * the items purchased and the coupons that were accepted for redemption.
 *
 * @author     Paul Tymann
 */

public class CashRegister {
    private static final double COUPON_DISC = .25;  // Coupon discount rate
    private static final double TAX_RATE = .08;     // Sales tax rate

    /**
     * The main method.
     *
     * @param args the command line arguments (name of PLU file to read).
     */

    public static void main( String args[] ) {

	// Check for the correct number of command line arguments.

	if ( args.length != 1 ) {
	    System.err.println( "Usage:  java CashRegister <item file>" );
	    System.exit( 1 );
	}

	// Create a PLUFile object that contains the items in the
        // File specified on the command line.  If any exceptions
        // are generated during the creation of the PLUFile object,
        // the message associated with the exception must be printed
        // to standard error, and the program must terminate
        // immediately.

        PLUFile items = PLUFile.createFromFile( args[ 0 ] );

        // Create two collections:  one to hold the purchases and the
        // other to hold the coupons.  Remember that the order in
        // which the purchases and the coupons are printed is
        // determined by the type of collection they are stored in

        Set purchases = new HashSet();
	Set coupons = new HashSet();

	// Read UPCs for purchases and coupons from the keyboard
        // using the method readUPCs().  The format of the input 
        // is the UPC codes for the purchases, followed by a blank 
        // line, followed by the UPC codes for the coupons.  The 
        // coupon section is optional, but the blank line that
	// follows the purchases will always be present.

	// The UPC codes and coupons come from standard input

        BufferedReader in = new BufferedReader( 
	            new InputStreamReader( System.in ) );

        readUPCs( in, items, purchases );

        // readUPCs() reads all of the coupons, now we must determine
        // which coupons to accept (i.e. which coupons should stay
        // in the collection).  Coupons will be accepted only if the 
        // corresponding item has been purchased.  Duplicate coupons 
        // are not accepted.

        // INSERT YOUR CODE HERE

	// Now print the receipt

        // INSERT YOUR CODE HERE
    }

    /**
     * Print the receipt given a collection containing the items
     * purchased and a collection that contains the coupons that
     * have been accepted.  The purchases and coupons will be
     * printed in the order given by the collection they are contained
     * in.  The format of the receipt is shown below:
     *
     * <blockquote>
     * <pre>
     * <tt>
     * ****** Java-Mart ******
     *
     * Items purchased:  XXX
     *
     * description      x.xx T
     * description      x.xx T
     * description      x.xx
     * ...
     *
     * $$$$$$ Coupons $$$$$$$$
     *
     * description      x.xx-
     * description      x.xx-
     *
     * Savings          x.xx
     * $$$$$$$$$$$$$$$$$$$$$$$
     *
     * *** Tax          x.xx
     * *** Balance      x.xx
     *
     * ****** Thank You ******
     *
     * </tt>
     * </pre>
     * </blockquote>
     *
     * @param purchases collection containing the items that were purchased
     * @param coupons collection containing the coupons that were accepted
     */
    
    public static void printReceipt( Collection purchases, 
				      Collection coupons ) {

	// Totals shown on the receipt

	double total = 0;
	double tax = 0;
	double savings = 0;

        // Working variables

        StoreItem cur = null;

	// Print the receipt header

	System.out.println( "****** Java-Mart ******" );
	System.out.println();
	System.out.println( "Items purchased:  " + purchases.size() );
	System.out.println();

	// Print the purchases on the receipt

	for ( Iterator i = purchases.iterator(); i.hasNext(); ) {
	    cur = ( StoreItem )i.next();

	    // Update total spent

	    total += cur.getPrice();

	    // Print description and price

	    System.out.print( cur.getDescription() );
	    System.out.print( rightJustify( cur.getPrice() ) );

	    // Print 'T' if the item is taxable and update the total tax

	    if ( cur.getTaxable() ) {
		tax += cur.getPrice() * TAX_RATE;
		System.out.print( " T" );
	    }

	    // Newline at the end of the line

	    System.out.println();
	}

	// Now handle the coupons if there are any

	if ( coupons.size() != 0 ) {
	    // Print out the coupon header

	    System.out.println();
	    System.out.println( "$$$$$$ Coupons $$$$$$$$" );
	    System.out.println();

	    // Process each of the coupons that have been accepted

	    for ( Iterator i = coupons.iterator(); i.hasNext(); ) {
		cur = ( StoreItem )i.next();

		// Determine the amount of the coupon and update savings

		double couponAmount = cur.getPrice() * COUPON_DISC;
		savings += couponAmount;

		// Print out the coupon information

		System.out.print( cur.getDescription() );
		System.out.println( rightJustify( couponAmount ) + '-' );
	    }

	    // Coupon footer

	    System.out.println();
	    System.out.println( "Savings     " + rightJustify( savings ) );
	    System.out.println( "$$$$$$$$$$$$$$$$$$$$$$$" );
	}

	// Determine balance owed

	double balance = total + tax - savings;

	// Print out the receipt footer

	System.out.println();
	System.out.println( "*** Tax     " + rightJustify( tax ) );
	System.out.println( "*** Balance " + rightJustify( balance ) );
	System.out.println();
	System.out.println( "****** Thank You ******" );
    }	

    /**
     * Return a string that contains the characters in the
     * specified double parameter right justified.
     *
     * @param amount the value to format
     * @return a string containing the value right justified
     */
    
    public static String rightJustify( double amount ) {
	// General format used for numbers on the receipt
	
	DecimalFormat numFormat = new DecimalFormat( "####0.00" );

	String fmt = "        " + numFormat.format( amount );

	return fmt.substring( fmt.length() - 8 );
    }

    /**
     * Read a series of UPC codes from the specified BufferedReader.
     * Each UPC code read will then be used to perform a lookup() on
     * the PLUFile items.  If the UPC is found in the PLUFile, the
     * corresponding item will be added to the collection UPCs.  Reading
     * stops when a blank line is read or the EOF is reached.
     *
     * @param in stream the UPC codes will be read from
     * @param items the PLU file used to verify the UPC codes
     * @param upcs the collection to add validated UPC codes to
     * @exception IOException if an I/O error occurs while reading UPCS codes
     */
    
    public static void readUPCs( BufferedReader in,
				  PLUFile items, 
				  Collection upcs ) throws IOException {

	// Working variables

	String upc = null;
        StoreItem cur = null;

	// Read lines until a blank line is read or we hit EOF

	while ( ( upc = in.readLine() ) != null && upc.length() > 0 ) {
	    // Look the item up in the PLU file

	    cur = items.lookup( upc );

	    // If it is in the PLU file, add it to the collection

	    if ( cur != null ) upcs.add( cur );
	} 
    }

} // CashRegister
