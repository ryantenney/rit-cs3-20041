/*
 * SelectionSortableIntArray.java
 *
 * Version:
 *     $Id: SelectionSortableIntArray.java,v 1.1 2003/04/18 14:58:06 cs2 Exp ptt $
 *
 * Revisions:
 *     $Log: SelectionSortableIntArray.java,v $
 *     Revision 1.1  2003/04/18 14:58:06  cs2
 *     Initial revision
 *
 */

/**
 * A subclass of SortableIntArray that sorts integers using a
 * selection sort.

 * @author     Paul Tymann
 */

public class SelectionSortableIntArray extends SortableIntArray {

    /**
     * Create a SelectionSortableIntArray of the given size.
     *
     * @param    size    the size of the collection.
     */

    public SelectionSortableIntArray( int size ) {
	super( size );
    }

    /**
     * Sort the integers in the collection using a selection sort.
     */

    public void sort() {
	int min;

        // Selection sort

	for ( int i = 0; i < getSize() - 1; i++ ) {
	    min = i;

            // Look for the smallest element after the element at
            // position i

	    for ( int j = i + 1; j <= getSize() - 1; j++ ) {
                operations++;
		if ( getElementAt( j ) < getElementAt( min ) ) {
                    min = j;
                }
            }

            // Put the next smallest element in the correct spot
            // (i.e. swap it with the element at position i)

	    int tmp = getElementAt( min );
	    setElementAt( min, getElementAt( i ) );
	    setElementAt( i, tmp );
	}
    }

} // SelectionSortableIntArray
