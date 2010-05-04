/*
 * $Id: GateDescriptor.java,v 1.5 2004/10/11 23:40:47 rwt5629 Exp $
 *
 * $Log: GateDescriptor.java,v $
 * Revision 1.5  2004/10/11 23:40:47  rwt5629
 * just another commitment
 *
 * Revision 1.4  2004/10/11 03:28:10  rwt5629
 * another commitment
 *
 * Revision 1.3  2004/10/11 02:19:10  rwt5629
 * updated all the headers to actually reflect CVS commitments
 *
 */

/**
 * Class GateDescriptor
 *
 * @author=Ryan Tenney
 */

public class GateDescriptor
{

    private String ident, type;
    private int numInputs;
    private String[] inputs;

    public GateDescriptor( String gateDeclaration )
    {
	
    }

    public GateDescriptor( String ident, String type, int inputs, 
	String[] destinations )
    {

    }

    public static GateDescriptor parse( String gateDeclaration )
    {
	// gate-ident gate-type inputs destinations
	String[] tokenized = gateDeclaration.split("\\s");
	String ident = tokenized[ 0 ];
	String type = tokenized[ 1 ];
	int inputs = Integer.parseInt( tokenized[ 2 ] );

	String[] destinations = new String[ tokenized.length - 3 ];

	for ( int i = 3; i < tokenized.length; i++ )
	{
	    destinations[ i - 3 ] = tokenized [ i ];
	}
	return null;
    }

    public String getIdent()
    {
	return this.ident;
    }

    public void setIdent( String ident )
    {
	this.ident = ident;
    }

    public String getType()
    {
	return this.type;
    }

    public void setType( String type )
    {
	this.type = type;
    }

    public int getNumInputs()
    {
	return this.numInputs;
    }

    public void setNumInputs( int numInputs )
    {
	this.numInputs = numInputs;
    }

    public String[] getInputs()
    {
	return this.inputs;
    }

    public void setInputs( String[] inputs )
    {
	this.inputs = inputs;
    }

    // public void setInputs( GateDescriptor[] inputs )
    // {
	// this.destinations = destinations;
    // }
}
