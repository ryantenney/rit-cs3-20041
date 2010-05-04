import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class implements a 100meter race track that Dr. Tiger and
 * his friends can race on.
 *
 * @version   $Id: RaceTrack.java,v 1.1 2000/04/05 15:38:54 cs3 Exp ptt $
 *
 * @author    HP bischof
 * @author    Paul Tymann
 *
 * Revisions:
 *      $Log: RaceTrack.java,v $
 *      Revision 1.1  2000/04/05 15:38:54  cs3
 *      Initial revision
 *
 */

public class RaceTrack extends JFrame {
    private static final int SCREEN_WIDTH = 480;
    private static final int TITLEBAR_WIDTH = 25;

    private static final int RACER_HEIGHT = 50;  // Height used by each racer
    private static final int RACER_GAP = 10;     // Gap between racers
    private static final int RACER_DELTA = 5;    // Delta used by oneStep()

    private Image tiger;
    private boolean noWinner = true;

    private int numRacers;
    private int[] position;

    // Used for creating the starting line

    static private Integer line = new Integer(0);
    private int lineUp = 0;

    // The Tigers will be placed inside a panel so that we do not
    // have to worry about the title bar and other frame adornments.

    private class TigerPanel extends JPanel {

	/**
	 * Create a new tiger panel.
	 */

	public TigerPanel() {
	    setBackground( Color.white );
	}

	/**
	 * Draws the tiger panel using the graphics context provided as an
	 * argument.  The tigers are drawn at their positions starting from
	 * the top of the screen working down.
	 *
	 * @param g the graphics context to use to draw the panel.
	 */

	public void paint( Graphics g ) {
	    for ( int i=0; i< numRacers; i++ ) {
		// Figure out where the tiger is

		int y = i * RACER_HEIGHT;
		int tigerLength = tiger.getWidth( this );

		// Erase the area where the tiger has been

		g.setColor( Color.white );

		g.fillRect( 0,
			    y,
			    position[ i ],
			    RACER_HEIGHT - RACER_GAP );

		// Now draw the tiger

		g.drawImage( tiger, position[ i ], y, this );

		// Check for a winner!

		if ( noWinner && position[ i ] + tigerLength > SCREEN_WIDTH ) {
		    System.out.println( "The winner is Tiger number " + i );
		    noWinner = false;
		}
	    }
	}
    }

    /**
     * Initialize the race track for the given number of runners.
     *
     * @param numRacers the number of racers on the track.
     */

    public RaceTrack( int numRacers ) {
	super( "The Great Race" );

	// Initialize attributes

	this.numRacers = numRacers;
	position = new int[ numRacers ];

	// Take care of the graphic for the tiger

	tiger = Toolkit.getDefaultToolkit().getImage( "rit_tiger_small.gif" );

	// Make sure the image is loaded before doing anything else

	MediaTracker tracker = new MediaTracker( this );
	tracker.addImage( tiger, 0 );

	try {
	    tracker.waitForID( 0 );
	}
	catch ( InterruptedException e ) {}

	// Scale the image to a "resonable size"

	tiger = tiger.getScaledInstance( -1, 
					 RACER_HEIGHT - RACER_GAP, 
					 Image.SCALE_SMOOTH );

	// Wait for the scaling request to finish before proceeding

	tracker.addImage( tiger, 1 );

	try {
	    tracker.waitForID( 1 );
	}
	catch ( InterruptedException e ) {}

	// Build the frame

        addWindowListener( new WindowAdapter() {
	    public void windowClosing( WindowEvent e ) {
	        System.exit( 0 );
	    }
        });

	setContentPane( new TigerPanel() );

	setBackground( Color.white );
       	setSize( SCREEN_WIDTH, ( numRacers * RACER_HEIGHT ) + TITLEBAR_WIDTH );
        setVisible(true);
    }

    /**
     * Advance the given racer forward by 1 meter.
     *
     * @param tiger the runner to advance
     *
     * @exception ArrayIndexOutOfBounds if this racer doesn't exist.
     */

    public void oneStep( int tiger ) throws ArrayIndexOutOfBoundsException {
	if ( tiger < 0 || tiger > numRacers )
	    throw new ArrayIndexOutOfBoundsException();
	    
	if ( noWinner ) 
	    position[ tiger ] = position[ tiger ] + RACER_DELTA;
	    
	// Schedule a paint so the motion can be seen

	repaint();
    }

    /**
     * This method will force all the runners to wait until everyone
     * is ready.  This way they all start at the same time.  This method
     * will not return until all of the racers have lined up to race.
     */

    public void lineUp() {
	// Everybody will wait on the static lock line

	synchronized ( line )	{

	    // Increment the count of the number of tigers that are
	    // ready

	    lineUp ++;

	    // When the last tiger lines up, notify the others that it
	    // is time to race.  Otherwise the tiger simply waits.

	    if ( lineUp >= numRacers )
		line.notifyAll();
	    else
		try {
		    line.wait();
		} catch ( Exception e ) {}
	}
    }


    /**
     * Draw a tiger panel on the screen and move the tigers across the
     * panel using a loop.  The purpose of this method is to provide a
     * way in which a user of this class can see what the race track looks
     * like before using the class.
     *
     * @param args[] a value of type 'String'
     */
    
    public static void main( String args[] ) {

	// Create a track with three tigers

	RaceTrack x = new RaceTrack( 3 );

	// Now move them across the screen

	try {
	    for ( int i=0; i<200; i++ ) {
		for ( int j=0; j<3; j++ )
		    x.oneStep( j );
		Thread.currentThread().sleep(500);
	    }
	}
	catch ( Exception e ) {};
    }

} // RaceTrack
