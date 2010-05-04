/*
 * StoreItem.java
 *
 * Version:
 *    $Id$
 *
 * Revsions:
 *    $Log$
 */

import java.lang.*;

/**
 * A class that can be used to represent items in a grocery store.
 * Store items are immutable.
 *
 * @author     Paul Tymann
 */

public class StoreItem implements Comparable {

    // Constants used to convert PLU text file records into
    // StoreItem objects

    private static final int UPC_START = 0;
    private static final int UPC_END = 11;

    private static final int DESC_START = 12;
    private static final int DESC_END = 23;

    private static final int TAX_START = 32;
    private static final int TAX_END = 32;

    private static final int DEPT_START = 38;
    private static final int DEPT_END = 40;

    private static final int PRICE_START = 41;
    private static final int PRICE_END = 46;

    // Data members

    private String upc;
    private String description;
    private int department;
    private double price;
    private boolean taxable;

    /**
     * Create a new store item from a string.  The string must be formatted
     * as shown in the table below:
     *
     * <blockquote>
     * <table border="1">
     * <tr>
     *   <td><b>Field</b></td>
     *   <td><b>Start</b></td>
     *   <td><b>End</b></td>
     *   <td><b>Type</b></td>
     * </tr>
     * <tr>
     *   <td>UPC</td>
     *   <td>0</td>
     *   <td>11</td>
     *   <td>Text</td>
     * </tr>
     * <tr>
     *   <td>Description</td>
     *   <td>12</td>
     *   <td>23</td>
     *   <td>Text</td>
     * </tr>
     * <tr>
     *   <td>Taxable</td>
     *   <td>32</td>
     *   <td>32</td>
     *   <td>Character ('Y' or 'N')</td>
     * </tr>
     * <tr>
     *   <td>Department</td>
     *   <td>38</td>
     *   <td>40</td>
     *   <td>Integer (0 - 9)</td>
     * </tr>
     * <tr>
     *   <td>Price</td>
     *   <td>41</td>
     *   <td>46</td>
     *   <td>Real</td>
     * </tr>
     * </table>
     * </blockquote>
     *
     * @param pluRec string to be converted to a store item.
     */
    
    public StoreItem( String pluRec ) {

	// Pull the simple text fields out of the string

	upc = pluRec.substring( UPC_START, UPC_END + 1 );
	description = pluRec.substring( DESC_START, DESC_END + 1 );
	taxable = pluRec.substring( TAX_START, TAX_END + 1 ).equals( "Y" );

	// Now get the numbers.  Instance variables will be set to zero
	// if the number fields are invalid

	try {
	    String dept = pluRec.substring( DEPT_START, DEPT_END + 1 ).trim();
	    department = Integer.parseInt( dept );
	}
	catch ( NumberFormatException e ) {}

	try {
	    String p = pluRec.substring( PRICE_START, PRICE_END + 1 ).trim();
	    price = Integer.parseInt( p ) / 100.0;
	}
	catch ( NumberFormatException e ) {}
    }

    /**
     * Create a new store item as specified by the parameters.
     *
     * @param upc the UPC for this item
     * @param description a short description of this item
     * @param department the department this item belongs to (0-9)
     * @param price the price of this item
     * @param taxable is the item taxable?
     */

    public StoreItem( String upc, 
		      String description, 
		      int department, 
		      double price, 
		      boolean taxable ) {

	this.upc = upc;
	this.description = description;

	// Make sure the numbers passed in make sense.  If they don't
	// leave the corresponding fields set to zero.

	if ( department >= 0 && department < 10 ) {
	    this.department = department;
        }

	if ( price >= 0 ) {
	    this.price = price;
        }

	this.taxable = taxable;
    }

    // Accessors

    /**
     * Return the UPC code for this item
     *
     * @return the UPC code for this item.
     */
    
    public String getUPC() { 
	return upc; 
    };

    /**
     * Return the short description of this item.
     *
     * @return the description of this item
     */
    
    public String getDescription() { 
	return description; 
    };

    /**
     * Return the department of this item.
     *
     * @return a value of type 'int'
     */
    
    public int getDepartment() { 
	return department; 
    };

    /**
     * Return the price of this item.
     *
     * @return the price of this item
     */
    
    public double getPrice() {
	return price; 
    };

    /**
     * Return true if this item is taxable and false otherwise.
     *
     * @return true if the item is taxable and false otherwise
     */

    public boolean getTaxable() { 
	return taxable; 
    };

    // Misc

    /**
     * Return a string representation of this item.
     *
     * @return a string representation of this item
     */

    public String toString() {
	return 
	    "[ \"" + upc + "\", \"" + 
	    description + "\", " + 
	    department + ", " +
	    taxable + ", " +
	    price + " ]";
    }

    /**
     * Returns a hash code for this StoreItem.  The hash code is
     * based on the UPC code.
     *
     * @return a hash code value for this object, equal to the hash code
     *         of the string that represents the UPC code assigned to this
     *         object
     */

    public int hashCode() {
	return upc.hashCode();
    }

    /**
     * Compares this object to the specified object. The result is true 
     * if and only if the argument is not null and is a StoreItem object
     * that has the same UPC code as this object.
     *
     * @param other the object to compare with
     * @return true if the objects are equal and false otherwise
     */
    
    public boolean equals( Object other ) {
        boolean retVal = false;

	if ( other instanceof StoreItem ) {
	    retVal = upc.equals( ( ( StoreItem ) other ).getUPC() );
        }

        return retVal;
    }

    /**
     * Compare two store items based on their UPC codes.
     *
     * @param other the StoreItem to compare with
     * @return the value 0 if the argument is a StoreItem that has the 
     *         same UPC code as this object; a value less than 0 if the 
     *         argument is a StoreItem whose UPC code is lexicographically 
     *         greater than this StoreItem's UPC code; or a value greater 
     *         than 0 if the argument is a StoreItem whose UPC code is 
     *         lexicographically less than this StoreItem's UPC code.
     */
    
    public int compareTo( Object other ) {
	return upc.compareTo( ( ( StoreItem ) other).getUPC() );
    }
	
} // StoreItem
