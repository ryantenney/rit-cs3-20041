/*
 * $Id: NorGate.java,v 1.7 2004/10/12 02:40:12 rwt5629 Exp $
 *
 * $Log: NorGate.java,v $
 * Revision 1.7  2004/10/12 02:40:12  rwt5629
 * created Destination.java, also added cvs tracking to AndGate.java as well as made various other modifications to other files
 *
 * Revision 1.6  2004/10/11 23:40:47  rwt5629
 * just another commitment
 *
 * Revision 1.5  2004/10/11 02:19:11  rwt5629
 * updated all the headers to actually reflect CVS commitments
 *
 */

/**
 * Class NorGate
 *
 * @author=Ryan Tenney
 */

public class NorGate extends OrGate
{

    public NorGate( String ident, int inputs, Destination[] destinations )
    {
	super( ident, inputs, destinations );
	type = "nor";
    }

    public boolean getOutput()
    {
	return !super.getOutput();
    }
}
