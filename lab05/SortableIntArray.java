/*
 * SortableIntArray.java
 *
 * Version:
 *     $Id: SortableIntArray.java,v 1.1 2003/04/18 14:58:06 cs2 Exp ptt $
 *
 * Revisions:
 *     $Log: SortableIntArray.java,v $
 *     Revision 1.1  2003/04/18 14:58:06  cs2
 *     Initial revision
 *
 */

/**
 * This class defines an abstract base class for a class
 * that maintains a collection of integers that can be sorted.
 *
 * @author     Paul Tymann
 */

public abstract class SortableIntArray {

    /**
     *  The array in which the integers are held.
     */

    private int[] nums;

    /**
     *  The array will contain integers between 0 and rangeSize - 1 (inclusive).
     */

    private int rangeSize;

    /**
     * A counter for the number of operations performed.
     */

    protected int operations; 

    /**
     * Create a sortable array of the specified size.
     *
     * @param    size    the size of the sortable array
     */

    public SortableIntArray( int size ) {
	nums = new int[ size ];
	operations = 0;
    }

    /** Accessor method for the count of operations performed during
     *  sorting.
     * 
     *  @return the number of operations performed
     */

    public int getOperations() {
   	return operations; 
    }

    /** 
     * Reset the operation counter to 0
     */

    public void resetOperations() {
        operations = 0; 
    }

    /**
     * Return the size of the sortable array. 
     *
     * @return   the size of the sortable array.
     */

    public int getSize() {
        return nums.length;
    }

    /**
     * Change the size of the sortable array.  Any elements currently
     * in the collection will be lost.
     *
     * @param    newSize    the new size of the sortable array.
     */

    public void setSize( int newSize ) {
        nums = new int[ newSize ];
    }

    /**
     * Return the size of the range of numbers in the sortable array.
     * The array contains integers between 0 and getRangeSize() - 1 (inclusive).
     *
     * @return   the size of the range of numbers in the sortable array.
     */

    public int getRangeSize() {
        return rangeSize;
    }

    /**
     * Fill the collection with random numbers in the range
     * 0 to max - 1. 
     *
     * @param    max    fill the collection with integers in the range 0
     *                  to max - 1.
     */

    public void fill( int max ) {
        rangeSize = max;

	for ( int i = 0; i < nums.length; i++ ) {
	    nums[ i ] = ( int )( Math.random() * max );
        }
    }

    /**
     * Change the integer at the specified position to the given value.
     *
     * @param    i      the position of the integer to change
     * @param    val    the new value of the integer
     */

    public void setElementAt( int i, int val ) {
        if ( val > rangeSize ) {
            rangeSize = val;
        }

	nums[ i ] = val;
    }

    /**
     * Retrieve the value of the integer stored at the specified position.
     *
     * @param    i    the position of the integer to retrieve
     *
     * @return   the value of the specified integer
     */

    public int getElementAt( int i ) {
	return nums[ i ];
    }

    /**
     * Sort the integers in the collection in ascending order.
     */

    public abstract void sort();

    /**
     * Print out the integers in the collection, in the order in
     * which they occur in the collection.
     */

    public void print() {
	for ( int i = 0; i < nums.length; i++ ) {
	    System.out.println( nums[ i ] );
        }
    }

} // SortableIntArray
