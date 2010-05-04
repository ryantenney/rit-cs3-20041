/*
 * $Id: OutputGate.java,v 1.6 2004/10/12 14:55:16 rwt5629 Exp $
 *
 * $Log: OutputGate.java,v $
 * Revision 1.6  2004/10/12 14:55:16  rwt5629
 * nearly finished, a problem with the 2^0 bit still remains, but its after the deadline so this is moot anyways.
 *
 * Revision 1.5  2004/10/12 03:28:57  rwt5629
 * with any luck, one of my last commitments
 *
 * Revision 1.4  2004/10/12 02:40:15  rwt5629
 * created Destination.java, also added cvs tracking to AndGate.java as well as made various other modifications to other files
 *
 * Revision 1.3  2004/10/11 23:40:48  rwt5629
 * just another commitment
 *
 * Revision 1.2  2004/10/11 02:19:11  rwt5629
 * updated all the headers to actually reflect CVS commitments
 *
 */

/**
 * Class OutputGate
 *
 * @author=Ryan Tenney
 */

import java.util.*;

public class OutputGate extends Gate
{

    public OutputGate( String ident, int inputs )
    {
	super( ident, inputs, null );
	super.type = "out";
    }

    public int getOutputAsInt()
    {
	int output = 0;
	for ( int i = 1; i < inputPins.length; i++ )
	{
	    if ( inputPins[ i ].getOutput() )
	    {
		output += (int)Math.pow( 2, i );
	    }
	}
	return output;
    }

    public void procDestinations( Map gateMap )
    {
	return;	// output gate has no destinations to process
    }

    public void connectPin( Gate input, int pin )
    {
	super.connectPin( input, pin );
    }
}
