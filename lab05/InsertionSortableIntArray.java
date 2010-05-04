/*
 * InsertionSortableIntArray.java
 *
 * Version:
 *     $Id: InsertionSortableIntArray.java,v 1.1 2003/04/18 14:58:07 cs2 Exp ptt $
 *
 * Revisions:
 *     $Log: InsertionSortableIntArray.java,v $
 *     Revision 1.1  2003/04/18 14:58:07  cs2
 *     Initial revision
 *
 */

/**
 * A subclass of SortableIntArray that sorts integers using an
 * insertion sort.

 * @author     Paul Tymann
 */

public class InsertionSortableIntArray extends SortableIntArray {

    /**
     * Create an InsertionSortableIntArray of the given size.
     *
     * @param    size    the size of the collection.
     */

    public InsertionSortableIntArray( int size ) {
	super( size );
    }

    /**
     * Sort the integers in the collection using an insertion sort.
     */

    public void sort() {

        // i marks the beginning of the unsorted portion of the array
        // (elements in postions 0..i-1 are in sorted order).

	for ( int i = 1; i < getSize(); i++) {
            // Insert the first element in the unsorted portion of the
            // array into the sorted portion.  The process works from
            // right to left in the sorted array.

	    for ( int j = i - 1;
                    j >= 0 && getElementAt( j + 1 ) < getElementAt( j ); 
                    j-- ) { 
		operations++; 

                // since we have not found the correct location for the
                // new element, move the elements in the sorted portion of
                // the array one to the right.

		int tmp = getElementAt( j );
		setElementAt( j , getElementAt( j + 1 ) );
		setElementAt( j + 1 , tmp );
	    }
        }
    }

} // InsertionSortableIntArray
