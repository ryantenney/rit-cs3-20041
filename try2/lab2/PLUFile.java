/*
 * PLUFile.java
 *
 * Version:
 *    $Id: PLUFile.java,v 1.2 2004/12/10 15:46:06 rwt5629 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: PLUFile.java,v $
 *    Revision 1.2  2004/12/10 15:46:06  rwt5629
 *    finished
 *
 *    Revision 1.1  2004/12/10 15:23:53  rwt5629
 *    Initial revision
 *
 */

import java.util.*;
import java.io.*;

/**
 * A price look up file that can be used by a cash register to determine
 * the amount owed by a customer.
 *
 * @author     Paul Tymann
 */

public class PLUFile {

    // The items will be stored in a map

    Map pluFile;

    /**
     * Create a new PLU file that contains no items.
     */

    public PLUFile() {
        pluFile = new TreeMap();
    }

    /**
     * Create a new PLU file that contains the objects in the
     * specified file.
     *
     * @param fileName the file that contains the store item information
     * @return a PLUFile that contains the store items in the specified file
     * @exception IOException if an I/O error occurs while processing the file
     * @exception FileNotFoundException if the specified file cannot be opened
     */
    
    public static PLUFile createFromFile( String fileName )
	throws IOException, FileNotFoundException {

	// open the input file

	BufferedReader in = new BufferedReader( new FileReader( fileName ) );

	// The PLUFile that will eventually be returned

	PLUFile items = new PLUFile();
	String data;

	// Read the items from the file and add them to the PLUFile

	while ( ( data = in.readLine() ) != null ) {
	    items.add( new StoreItem( data ) );
        }

	// Close the input and return

	in.close();

	return items;
    }

    /**
     * Return a reference to the StoreItem that has the specified
     * UPC code.  A null reference is returned if no such item
     * exists.
     *
     * @param upc the UPC code for the item being retrieved
     * @return a reference to a StoreItem that has the specified
     *         UPC code, or null if such an item does not exist
     */

    public StoreItem lookup( String upc ) {
        StoreItem out;
        if ( !pluFile.containsKey( upc ) ) {
            out = null;
        } else {
            out = (StoreItem)pluFile.get( upc );
        }
        return out;
    }

    /**
     * Place the specified StoreItem in the PLU file.  If the PLU file
     * already contains a StoreItem with the same UPC code, the old
     * StoreItem is replaced.
     *
     * @param item the StoreItem to add to the file
     */
    
    public void add( StoreItem item ) {
        pluFile.put( item.getUPC(), item );
    }

    /**
     * Return a List containing the StoreItems in the PLU file
     * sorted in ascending order by UPC code.
     *
     * @return a list containing the StoreItems sorted by UPC code
     */

    public List toList() {
        List values = null;

        values = new ArrayList( pluFile.values() );

	return values;
    }

    /**
     * A simple program to test the ability of the PLUFile to load
     * items from a file.  The command line argument is the name of a
     * file that contains store items information.  This file will
     * be loaded into a PLUFile.  The contents of the resulting file
     * will then be displayed on standard output.
     *
     * @param args command line arguments (name of store item data file)
     */

    public static void main( String args[] ) {

	// Usage check

	if ( args.length != 1 ) {
	    System.err.println( "Usage:  java PLUFile <item file>" );
	    System.exit( 1 );
	}

	// Try to load the file

	PLUFile items = null;

	try {
	    items = PLUFile.createFromFile( args[ 0 ] );

	    // Print the file using an iterator

	    Iterator i = items.toList().iterator();

	    while ( i.hasNext() ) {
	        System.out.println( i.next() );
	    }
        }
	catch ( Exception e ) {
	    System.err.println( e.getMessage() );
	}
    }

} // PLUFile
