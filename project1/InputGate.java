/*
 * $Id: InputGate.java,v 1.5 2004/10/12 02:40:09 rwt5629 Exp $
 *
 * $Log: InputGate.java,v $
 * Revision 1.5  2004/10/12 02:40:09  rwt5629
 * created Destination.java, also added cvs tracking to AndGate.java as well as made various other modifications to other files
 *
 * Revision 1.4  2004/10/11 03:28:10  rwt5629
 * another commitment
 *
 * Revision 1.3  2004/10/11 02:19:11  rwt5629
 * updated all the headers to actually reflect CVS commitments
 *
 */

/**
 * Class InputGate
 *
 * @author=Ryan Tenney
 */

public class InputGate extends Gate
{

    private boolean value = false;

    public InputGate( String ident, Destination[] destinations )
    {
	super( ident, 0, destinations );
	super.type = "in";
    }

    public void setOutput(boolean input)
    {
	this.value = input;
    }

    public boolean getOutput()
    {
	return this.value;
    }
}
