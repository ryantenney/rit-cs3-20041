/*
 * Woolie.java
 *
 * $Id: Woolie.java,v 2.0 2004/01/20 22:06:14 icss235 Exp $
 *
 * Revisions:
 *   $Log: Woolie.java,v $
 *   Revision 2.0  2004/01/20 22:06:14  icss235
 *   *** empty log message ***
 *
 */

/**
 * Woolie - simulates a woolie crossing a bridge
 * <p>
 * Each woolie object is constructed with a name, length of time it
 * takes the woolie to cross the bridge, and a destination city.  The
 * objects, which extend the Thread class, execute as individual threads.
 *
 * Before crossing the bridge, a woolie will ask a troll for
 * permission to cross.  Once the troll grants permission, the
 * woolie crosses the bridge.  Once on the other side, the woolie will
 * notify the bridge (not the troll) that it has left the bridge.
 *
 * @author	James Heliotis
 * @author      Jim Vallino
 */
public class Woolie extends Thread {
    
    /**
     * Construct a new Woolie object that can be started in a separate
     * thread.  The constructor will simply initializes all of the 
     * instance fields.
     *
     * @param       n    the name of this Woolie
     * @param       c    the number of seconds it takes the Woolie to cross 
     *                   the bridge
     * @param       d    the city the Woolie is heading to
     * @param       b    the bridge the Woolie is crossing
     *
     * Preconditions:<ul>
     * <li>d = &quote;Sicstine&quote; or &quote;Merctran&quote;
     * <li>c >= 0
     * <li>n,b != null
     * </ul>
     */
    public Woolie( String n, int c, String d, Bridge b ) {
	super( n );
	crossingTime = c;
	destination = d;
        theBridge = b;
        bridgeGuard = theBridge.getTroll();
    }
    
    /**
     * This method handles the Woolie's crossing of the bridge.  There
     * are several messages that must be displayed to describe the Woolie's
     * progress crossing the bridge.  <I>Note: In all the following
     * messages</I>
     * &quot;<code>name</code>&quot; <I>is the name of the Woolie.</I>
     *
     * <ul>
     * <li>When the Woolie thread starts the message<br>
     * <blockquote><code>name has arrived at the bridge.</code></blockquote>
     * is displayed.
     * <li>When the Woolie starts crossing the bridge, at time 0, the message
     * <br>
     * <blockquote><code>name is starting to cross.</code></blockquote>
     * is displayed.
     * <li>For every one second interval, beyond time 0, that the Woolie is on 
     * the bridge a message<br>
     * <blockquote><code>name x seconds.</code></blockquote>
     * should be printed where &quot;<code>x</code>&quot;
     * is the number of seconds that the Woolie has been on the bridge.
     * <li>When the Woolie reaches its destination display the
     * message<br>
     * <blockquote><code>name leaves at city.</code></blockquote>
     * where &quot;<code>city</code>&quot; is the Woolie's destination.
     * </ul>
     */

    public void run() {

        // The Woolie has started to cross the bridge

	// System.out.println( getName() + " has arrived at the bridge.");

        // Get permission to cross from the troll

        bridgeGuard.enterBridgePlease();

        // Simulate the time on the bridge

	for ( int time = 0; time < crossingTime; time++ ) {
            // Take care of output

	    if( time == 0 )
		System.out.println( getName() + " is starting to cross." );
	    else
		System.out.println( "\t" + getName() + ' ' + time + " seconds." );

            // Let time pass

	    try {
		sleep(1000);
	    }
	    catch( InterruptedException e ) {}
	}

        // Tell the bridge we have crossed

        bridgeGuard.leave();

        // Finished crossing

	System.out.println( getName() + " leaves at " + destination + "." );
    }

    /**
     * The number of seconds require to cross
     */
    private int crossingTime;

    /**
     * The name of the destination of this woolie
     */
    private String destination;

    /**
     * The bridge the woolie will cross
     */
    private Bridge theBridge;

    /**
     * The troll guarding the bridge
     */
    private Troll bridgeGuard;
}
