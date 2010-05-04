/*
 * Bridge.java
 *
 * $Id$
 *
 * $Log$
 *
 */

/**
 * Bridge for CS3 Lab 6
 *
 * @author    Ryan Tenney
 *
 */
public class Bridge {

    /**
     * Maximum number of woolies allowed on the bridge
     */
    private static int MAX_ON_BRIDGE = 3;

    /**
     * The bridge's troll
     */
    private Troll myTroll = null;

    /**
     * The number of woolies on the bridge
     */
    private int numOnBridge = 0;


    /**
     * Constructor for the Bridge class
     */
    public Bridge() {
        myTroll = new Troll( this );
    }

    /**
     * Request permission to enter the bridge.
     */
    public synchronized void enter() {
        while ( numOnBridge == MAX_ON_BRIDGE ) {
            try {
                wait();
            } catch ( InterruptedException iex ) {
            }
        }
        numOnBridge++;
        notifyAll();
    }

    /**
     * Answer the troll assigned to guard this bridge.
     */
    public Troll getTroll() {
        return myTroll;
    }

    /**
     * Notify the bridge that a Woolie is leaving the bridge. This call
     * should be made by the bridge troll.
     */
    public synchronized void leave() {
        if ( numOnBridge > 0 ) {
            numOnBridge--;
        }
        notifyAll();
    }

}
