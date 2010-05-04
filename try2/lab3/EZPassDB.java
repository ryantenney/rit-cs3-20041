/*
 * EZPassDB.java
 *
 * Version:
 *    $Id: EZPassDB.java,v 1.2 2005/01/05 08:11:38 rwt5629 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: EZPassDB.java,v $
 *    Revision 1.2  2005/01/05 08:11:38  rwt5629
 *    finished with part 2 of lab 3
 *
 *    Revision 1.1  2004/09/10 01:50:51  cs3
 *    Initial revision
 *
 */

import java.util.*;

/**
 * The computer end of the EZ pass system.
 *
 * Every time a car passes by a scanner at a Thruway Exit (either for 
 * entry or exit) the scanner generates a string that contains the 
 * vehicle's tag and the Exit the vehicle is at.  This information is 
 * passed to the log() method that records the event.
 *
 * <p>
 * The DB maintains two collections.  The first collection holds the
 * TollRecords for the trips that have not yet been completed (i.e.
 * the vehicle is still on the Thruway).  The second collection holds
 * the TollRecords for all completed trips.  Note that a vehicle can
 * only appear once in the first collection and may appear several times
 * in the second collection.
 *
 * @author     Paul Tymann
 * @author     Ryan Tenney
 */

public class EZPassDB {

    // A map is used to hold the TollRecords for the incomplete trips 
    // (the vehicle tag is used as the key) and a list is used to hold 
    // the TollRecords for the completed trips.

    private Map open;
    private List completed;

    // Ctors

    /**
     * Create a new EZ pass database.
     */

    public EZPassDB() {
        open = new HashMap();
        completed = new ArrayList();
    }

    /**
     * This method is called whenever a scanner detects that a vehicle
     * has entered or has left the Thruway.  Note that the parameters
     * do not indicate if the vehicle is entering or leaving the Thruway,
     * it is up to the database to determine which event has occurred.
     * If either parameter is invalid, the method will terminate without
     * changing the state of this object.
     *
     * @param tag the tag of the vehicle that has passed the scanner.
     *
     * @param exit the Exit at which the scanner is located
     */

    public void log( String tag, int exit ) {
        try {
            if ( open.containsKey( tag ) ) {
                TollRecord tr = (TollRecord)open.get( tag );
                tr.setExit( exit );
                open.remove( tag );
                completed.add( tr );
            } else {
                TollRecord tr = new TollRecord( tag );
                tr.setEntry( exit );
                open.put( tag, tr );
            }
        } catch ( TollRecordException trex ) {
            
        }
    }

    /**
     * Returns a list of open trips (vehicles that have entered the
     * Thruway but have not yet exited).  The List contains the 
     * TollRecords of the vehicles currently on the Thruway sorted in 
     * ascending order by the Exit number where they entered the Thruway.
     *
     * @return a list containing all vehicles currently on the Thruway sorted
     *         in ascending order by entry Exit number.
     */

    public List openTrips() {
        return new ArrayList( open.values() );
    }

    /**
     * Return a list of all completed trips.  The list will contain the
     * TollRecords of the the people who have completed trips on the
     * Thruway.  The list will be sorted in ascending order by vehicle
     * tag and, if there is a tie,
     * the Exit number where the vehicle entered the Thruway.
     *
     * @return a list containing the completed trips sorted in ascending
     *         order by tag and entry Exit number.
     */
    
    public List charges() {
        Collections.sort( completed, new TagEntryComparator() );
        return completed;
    }

    /**
     * Remove all of the completed TollRecords from the system.
     */

    public void purgeChargeList() {
        completed.clear();
    }

} // EZPassDB
