/*
 * $Id: Circuit.java,v 1.8 2004/10/12 14:55:16 rwt5629 Exp $
 * 
 * $Log: Circuit.java,v $
 * Revision 1.8  2004/10/12 14:55:16  rwt5629
 * nearly finished, a problem with the 2^0 bit still remains, but its after the deadline so this is moot anyways.
 *
 * Revision 1.7  2004/10/12 03:57:07  rwt5629
 * slkdfjslD
 * a
 *
 * Revision 1.6  2004/10/12 03:28:56  rwt5629
 * with any luck, one of my last commitments
 *
 * Revision 1.5  2004/10/11 23:40:47  rwt5629
 * just another commitment
 *
 * Revision 1.4  2004/10/11 03:28:09  rwt5629
 * another commitment
 *
 * Revision 1.3  2004/10/11 02:08:59  rwt5629
 * testing cvs logging
 *
 *
 */

/**
 * Class Circuit
 *
 * @author=Ryan Tenney
 */

import java.util.*;

public class Circuit
{
	
    private int ext_inputs, ext_outputs;
    private InputGate[] inputGates;
    private OutputGate outputGate;
    private Map gateMap;

    public Circuit(int numInputs, int numOutputs)
    {
	ext_inputs = numInputs;
	ext_outputs = numOutputs;
	inputGates = new InputGate[ ext_inputs ];
	outputGate = new OutputGate( "OUT", ext_outputs );
	gateMap = new HashMap();
	gateMap.put( "OUT", outputGate );
    }

    public Gate addGate(String gateDeclaration)
    {
	Gate newGate;
	String[] tokenized = gateDeclaration.split("\\s");
	String ident, type;
	int inputs;
	Destination[] destinations;

	ident = tokenized[ 0 ];
	type = tokenized[ 1 ];
	inputs = Integer.parseInt( tokenized[ 2 ] );

	ArrayList destBuild = new ArrayList();
	for ( int i = 3; i < tokenized.length; i++ )
	{
	    destBuild.add( new Destination( null, tokenized[ i ] ) );
	}

	destinations = new Destination[ destBuild.size() ];
	destBuild.toArray( destinations );

	if ( type.equals( "and" ) ) {
	    newGate = new AndGate( ident, inputs, destinations );
	}
	else if ( type.equals( "or" ) ) {
	    newGate = new OrGate( ident, inputs, destinations );
	}
	else if ( type.equals( "not" ) ) {
	    newGate = new NotGate( ident, destinations );
	}
	else if ( type.equals( "nand" ) ) {
	    newGate = new NandGate( ident, inputs, destinations );
	}
	else if ( type.equals( "nor" ) ) {
	    newGate = new NorGate( ident, inputs, destinations );
	}
	else if ( type.equals( "in" ) ) {
	    newGate = new InputGate( ident, destinations );
	    int i = Integer.parseInt( ident.substring( 3 ) );
	    inputGates[ i ] = (InputGate)newGate;
	}
	else {
	    // shouldnt occur
	    return null;
	}

	gateMap.put( ident, newGate );
	return newGate;
    }

    public void makeConnections()
    {
	Object[] allGates = gateMap.values().toArray();
	for ( int i = 0; i < allGates.length; i++ )
	{
	    Gate thisGate = (Gate)allGates[ i ];
	    thisGate.procDestinations( gateMap );
	}
    }

    public int evaluate(int input)
    {
	Object[] keys = gateMap.keySet().toArray();

	for ( int i = 0; i < ext_inputs; i++ )
	{
	    boolean in = ( input & (int)Math.pow( 2, i ) ) > 0;
	    inputGates[ i ].setOutput( in );
	}

	return outputGate.getOutputAsInt();
    }
}
