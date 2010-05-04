/*
 * $Id: OrGate.java,v 1.6 2004/10/12 03:28:57 rwt5629 Exp $
 *
 * $Log: OrGate.java,v $
 * Revision 1.6  2004/10/12 03:28:57  rwt5629
 * with any luck, one of my last commitments
 *
 * Revision 1.5  2004/10/12 02:40:14  rwt5629
 * created Destination.java, also added cvs tracking to AndGate.java as well as made various other modifications to other files
 *
 * Revision 1.4  2004/10/11 23:40:48  rwt5629
 * just another commitment
 *
 * Revision 1.3  2004/10/11 02:19:11  rwt5629
 * updated all the headers to actually reflect CVS commitments
 *
 */

/**
 * Class OrGate
 *
 * @author=Ryan Tenney
 */

public class OrGate extends Gate
{

    public OrGate( String ident, int inputs, Destination[] destinations )
    {
	super( ident, inputs, destinations );
	super.type = "or";
    }

    public boolean getOutput()
    {
	// stores the cumulative output
	boolean sum = false;

	// assigns the first input to sum
	if ( super.inputPins[ 0 ] != null )
	    sum = super.inputPins[ 0 ].getOutput();	

	for(int i = 1; i < inputPins.length; i++)
	{
	    // ors the inputs
	    if ( super.inputPins[ i ] != null )
		sum |= super.inputPins[ i ].getOutput();
	    // unassigned input pins are by default false
	    // a false pin does not change the return of an OR
	}

	return sum;
    }
}
