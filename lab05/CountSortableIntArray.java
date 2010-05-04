/*
 * CountSortableIntArray.java
 *
 * Version:
 *     $Id: CountSortableIntArray.java,v 1.1 2004/10/05 10:19:49 cs2 Exp 
ptt $
 *
 * Revisions:
 *     $Log: CountSortableIntArray.java,v $
 *     Revision 1.1  2004/10/05 10:19:49  cs2
 *     Initial revision
 *
 */

import java.util.*;

/**
 * A subclass of SortableIntArray that sorts integers using a
 * selection sort.

 * @author     Paul Tymann
 */

public class CountSortableIntArray extends SortableIntArray {

    private int[] count;
    private int position;

    /**
     * Create a CountSortableIntArray of the given size.
     *
     * @param    size    the size of the collection.
     */

    public CountSortableIntArray( int size ) {
	super( size );
	secondArray = new int[ size ];
	rangeSize = size;
    }

    /**
     * Sort the integers in the collection using a counting sort.
     */

    public void sort() {
	position = 0;
	count = new int[ Arrays.max( nums ) ];

	for ( int i = 0; i < rangeSize; i++ ) {
	    count[ nums [ i ] ]++;
	    operations += 3;
	}

	for ( int y = 0; y < count.length; y++ ) {
	    Arrays.fill( nums, position, position 
		+ count[nums[i]], y );
	    position += count;
	    operations += nums[ i ] + 3;
	}
    }

} // CountSortableIntArray
