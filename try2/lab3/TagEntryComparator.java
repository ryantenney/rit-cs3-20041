/*
 * TagEntryComparator.java
 * 
 * $Id: TagEntryComparator.java,v 1.1 2005/01/06 04:57:11 rwt5629 Exp rwt5629 $
 *
 * $Log: TagEntryComparator.java,v $
 * Revision 1.1  2005/01/06 04:57:11  rwt5629
 * Initial revision
 *
 *
 */

import java.util.*;

/**
 * Class Tag EntryComparator compares two TollRecord objects based on 
 * the vehicle tag and exit number where they entered the thruway.
 *
 * @author    Ryan Tenney
 */
public class TagEntryComparator implements Comparator {

    public TagEntryComparator() {}

    public int compare( Object o1, Object o2 ) throws ClassCastException {
        int retVal;
        TollRecord tr1, tr2;
	tr1 = (TollRecord)o1; tr2 = (TollRecord)o2;
        if ( !tr1.getTag().equals( tr2.getTag() ) ) {
            retVal = tr1.getTag().compareTo( tr2.getTag() );
        } else {
            Integer i1, i2;
            i1 = new Integer( tr1.getEntry() );
            i2 = new Integer( tr2.getEntry() );
            retVal = i1.compareTo( i2 );
        }
        return retVal;
    }

}
