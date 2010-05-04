/*
 * Troll.java
 *
 * $Id: Troll.java,v 2.0 2004/01/20 22:06:14 icss235 Exp $
 *
 * Revisions:
 *	$Log: Troll.java,v $
 *	Revision 2.0  2004/01/20 22:06:14  icss235
 *	*** empty log message ***
 *
 */

/*
 * A troll to guard a bridge. The troll simply passes requests from
 * woolies to his bridge. It is a placeholder for a later exercise.
 * <p>
 * Note
 * <ul>
 * <li>The troll gets entry permission; the woolie itself tells
 *     the bridge it is off.</li>
 * <li>The troll runs an independent thread to pull things off
 *     its queue. This thread runs forever, so the Troll objects
 *     must be a daemon thread.</li>
 * <ul>
 */
public class Troll {

	/**
	 * Create a troll. (Called by its Bridge object.)
	 *
	 * @param b the Bridge object associated with this Troll object
	 */
	public Troll( Bridge b ) {
		myBridge = b;
	}

	/**
	 * Request permission to go on the bridge. This method is
	 * called by a Woolie object. It just calls Bridge.enter().
	 *
	 * Postcondition:<ul>
	 * <li>The woolie got permission and has entered the bridge.
	 * </ul>
	 */
	public void enterBridgePlease() {
		myBridge.enter();
	}

	/**
	 * Inform the troll that the caller (the woolie) is getting
	 * off the bridge.
	 */
	public void leave() {
		myBridge.leave();
	}

	/**
	 * The bridge that this troll guards. The Bridge object
	 * creates the troll object.
	 */
	private Bridge myBridge;

}
