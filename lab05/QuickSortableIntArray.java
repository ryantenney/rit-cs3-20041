/*
 * QuickSortableIntArray.java
 *
 * Version:
 *     $Id: QuickSortableIntArray.java,v 1.1 2003/04/18 14:58:07 cs2 Exp ptt $
 *
 * Revisions:
 *     $Log: QuickSortableIntArray.java,v $
 *     Revision 1.1  2003/04/18 14:58:07  cs2
 *     Initial revision
 *
 */

/**
 * A subclass of SortableIntArray that sorts integers using
 * Quicksort.
 *
 * @author     Paul Tymann
 */

public class QuickSortableIntArray extends SortableIntArray {

    /**
     * Create a QuickSortableIntArray of the given size.
     *
     * @param    size    the size of the collection.
     */

    public QuickSortableIntArray( int size ) {
        super( size );
    }

    /**
     * Sort the integers in the collection using Quicksort.
     */
    public void sort() {
        // The real work is done by the recursive method qsort().
        // This method is a wrapper around the the recursive method.  This
        // way the user has no idea that the sort is actually recursive.

	qsort( 0, getSize() - 1 );
    }

    /**
     * An implementation of quicksort.  The portion of the collection
     * starting at index lo and ending at index hi (inclusive) will
     * be sorted.
     *
     * The pivot value is the middle element in the portion
     * of the collection being sorted.
     *
     * @param    lo    the starting index for the sort.
     * @param    hi    the ending index for the sort.
     */
    private void qsort( int lo, int hi ) {
	int i, j, guessed_median, tmp;
 
        // Copies of end points for partitioning
	i = lo;
	j = hi;

        // The middle element will be used as the pivot (very easy
        // to implement).
	guessed_median = getElementAt( ( int ) ( ( lo + hi ) / 2 ) );

        // Partition the array.  Note that the pivot (i.e. the middle
        // element) may not stay in its current position.  When partitioning
        // is finished the array will look like this:
        //
        // "values < pivot" "values >= pivot"

        // The array is partitioned by establishing 2 points in the
        // array using the variables i and j.  All the values in the
        // array to the left of i are in the correct position and all of
        // the values to the right of j are in the correct position.
        // This means that the values between i and j (inclusive) are
        // possibly in the wrong portion of the array and need to be
        // checked.  Partitioning is completed when there are no more
        // elements between i and j (in others words as long as i is
        // less than j there are elements that need to be checked.

	while ( i < j ) {
            // i can be moved to the right as long as the values it
            // passes are less than the pivot.

	    while ( getElementAt( i ) < guessed_median ) {
		operations++; 
                i++;
            }

            // j can be moved to the left as long as the values it
            // passes are greater than the pivot.
	    while ( getElementAt( j ) > guessed_median ) {
		operations++; 
                j--;
            }

            // If i and j have passed each other (i.e. i is now greater
            // than j) the array is partitioned.  Otherwise we need to
            // swap the values at i and j to place them in the correct
            // portion of the array.
            // The condition for this if statement is correct; do not 
            // change it.
	    if ( i <= j )    {
		operations++; 

                // Swap the elements at index i and j

		tmp = getElementAt( i );
		setElementAt( i , getElementAt( j ) );
		setElementAt( j , tmp );

                // Now that the values are in the correct partition
                // of the array i can be moved one position to the
                // right and j can be moved one position to the left.
		i++;
		j--;
	    }
	}

        // Note that i is now at the first value in the partition that contains
        // a value >= the pivot, and j is at the last value in the partition
        // that contains a value <= the pivot.The method qsort() can now be 
        // used to sort the two partitions.  Note that a call to qsort() will
        // be made only if there is something in the partition to sort.
	if ( lo < j ) {
            qsort( lo, j );
        }

	if ( i < hi ) {
            qsort( i, hi );
        }
    }

} // QuickSortableIntArray
