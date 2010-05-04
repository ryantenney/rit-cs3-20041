/*
 * $Id: NandGate.java,v 1.6 2004/10/12 02:40:11 rwt5629 Exp $
 *
 * $Log: NandGate.java,v $
 * Revision 1.6  2004/10/12 02:40:11  rwt5629
 * created Destination.java, also added cvs tracking to AndGate.java as well as made various other modifications to other files
 *
 * Revision 1.5  2004/10/11 02:19:11  rwt5629
 * updated all the headers to actually reflect CVS commitments
 *
 */

/**
 * Class NandGate
 *
 * @author=Ryan Tenney
 */

public class NandGate extends AndGate
{

    public NandGate( String ident, int inputs, Destination[] destinations )
    {
	super( ident, inputs, destinations );
	super.type = "nand";
    }

    public boolean getOutput()
    {
	return !super.getOutput();
    }
}
