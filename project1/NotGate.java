/*
 * $Id: NotGate.java,v 1.5 2004/10/12 03:28:57 rwt5629 Exp $
 *
 * $Log: NotGate.java,v $
 * Revision 1.5  2004/10/12 03:28:57  rwt5629
 * with any luck, one of my last commitments
 *
 * Revision 1.4  2004/10/12 02:40:12  rwt5629
 * created Destination.java, also added cvs tracking to AndGate.java as well as made various other modifications to other files
 *
 * Revision 1.3  2004/10/11 02:19:11  rwt5629
 * updated all the headers to actually reflect CVS commitments
 *
 */

/**
 * Class NotGate
 *
 * @author=Ryan Tenney
 */

public class NotGate extends Gate
{

    public NotGate( String ident, Destination[] destinations )
    {
	super( ident, 1, destinations );
	super.type = "not";
    }

    public boolean getOutput()
    {
	if ( super.inputPins[ 0 ] != null)
	    return !super.inputPins[ 0 ].getOutput();
	else
	    return false;
    }

    public void connect( Gate input )
    {
	super.connectPin( input, 0 );
    }
}
