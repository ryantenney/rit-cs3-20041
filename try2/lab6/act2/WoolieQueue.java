/*
 * WoolieQueue.java
 * $Id: WoolieQueue.java,v 2.2 2004/01/21 20:05:08 icss235 Exp $
 *
 * Revisions:
 *	$Log: WoolieQueue.java,v $
 *	Revision 2.2  2004/01/21 20:05:08  icss235
 *	Reversed order from LIFO to FIFO (jeh)
 *
 *	Revision 2.1  2004/01/21 16:43:43  icss235
 *	Simplified design. (jeh,swm)
 *
 *	Revision 2.0  2004/01/20 22:06:59  icss235
 *	*** empty log message ***
 *
 */

/**
 * A multithread-safe wrapper around a linked list to make a queue.
 *
 * @author James Heliotis
 */
public class WoolieQueue {

	/**
	 * Constructor. Does nothing in this version.
	 */
	public WoolieQueue() {
	}

	/**
	 * Insert a woolie into the queue.
	 *
	 * @param caller the Woolie to be inserted.
	 */
	public synchronized void insert( Woolie caller ) {
		woolies.addFirst( caller );
		notifyAll();
	}

	/**
	 * Inspect the first woolie in the queue.
	 * This method never blocks.
	 *
	 * @return the Woolie that has been in the queue for
	 *         the longest time, or null if the queue is
	 *         empty.
	 */
	public synchronized Woolie peek() {
		Woolie result = null;
		try {
			result = (Woolie)woolies.getLast();
		}
		catch( java.util.NoSuchElementException nse ) {
		}
		return result;
	}

	/**
	 * Remove a woolie from the queue.
	 * This call blocks while the queue is empty.
	 *
	 * @return the Woolie that has been in the queue for
	 *         the longest time.
	 */
	public synchronized Woolie remove() {
		while ( woolies.isEmpty() ) {
			try {
				wait();
			}
			catch( InterruptedException ie ) {}
		}
		return (Woolie)woolies.removeLast();
	}

	/**
	 * The underlying data structure
	 */
	private java.util.LinkedList woolies = new java.util.LinkedList();

}
