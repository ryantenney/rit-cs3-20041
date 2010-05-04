/*
 * $Id: AndGate.java,v 1.5 2004/10/12 03:28:55 rwt5629 Exp $
 *
 * $Log: AndGate.java,v $
 * Revision 1.5  2004/10/12 03:28:55  rwt5629
 * with any luck, one of my last commitments
 *
 * Revision 1.4  2004/10/12 02:40:07  rwt5629
 * created Destination.java, also added cvs tracking to AndGate.java as well as made various other modifications to other files
 *
 */

/**
 * Class AndGate
 *
 * @author=Ryan Tenney
 */

public class AndGate extends Gate
{

    public AndGate( String ident, int inputs, Destination[] destinations )
    {
	super( ident, inputs, destinations );
	super.type = "and";
    }

    public boolean getOutput()
    {
	// stores cumulative output
	boolean sum = false;

	// assigns the first value;
	if ( super.inputPins[ 0 ] != null )
	    sum  = super.inputPins[ 0 ].getOutput();

	for( int i = 1; i < super.inputPins.length; i++ )
	{
	    // ands the inputs
	    if ( super.inputPins[ i ] != null )
		sum &= super.inputPins[ i ].getOutput();
	    else
		sum &= false;
	}

	return sum;
    }
}
