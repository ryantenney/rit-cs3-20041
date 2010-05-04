/*
 * EntryComparator.java
 * 
 * $Id: EntryComparator.java,v 1.2 2005/01/05 13:32:40 rwt5629 Exp rwt5629 $
 *
 * $Log: EntryComparator.java,v $
 * Revision 1.2  2005/01/05 13:32:40  rwt5629
 * finished first half of 3rd activity
 *
 * Revision 1.1  2005/01/05 13:29:01  rwt5629
 * Initial revision
 *
 *
 */

import java.util.*;

/**
 * Class EntryComparator compares two TollREcord objects based on the exit
 * number where they entered the thruway.
 *
 * @author    Ryan Tenney
 */
public class EntryComparator implements Comparator {

    public EntryComparator() {}

    public int compare( Object o1, Object o2 ) throws ClassCastException {
	Integer t1, t2;
        t1 = new Integer( ((TollRecord)o1).getEntry() );
        t2 = new Integer( ((TollRecord)o2).getEntry() );
        return t1.compareTo( t2 );
    }

}
