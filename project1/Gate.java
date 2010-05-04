/*
 * $Id: Gate.java,v 1.8 2004/10/12 14:55:16 rwt5629 Exp $
 *
 * $Log: Gate.java,v $
 * Revision 1.8  2004/10/12 14:55:16  rwt5629
 * nearly finished, a problem with the 2^0 bit still remains, but its after the deadline so this is moot anyways.
 *
 * Revision 1.7  2004/10/12 03:28:57  rwt5629
 * with any luck, one of my last commitments
 *
 * Revision 1.6  2004/10/12 02:40:09  rwt5629
 * created Destination.java, also added cvs tracking to AndGate.java as well as made various other modifications to other files
 *
 * Revision 1.5  2004/10/11 23:40:47  rwt5629
 * just another commitment
 *
 * Revision 1.4  2004/10/11 02:19:10  rwt5629
 * updated all the headers to actually reflect CVS commitments
 *
 */

/**
 * Class Gate
 *
 * @author=Ryan Tenney
 */

import java.util.*;

public class Gate
{
    protected String ident;
    protected int inputs;
    protected String type;
    protected Destination[] destinations;
    protected Gate[] inputPins;

    private Map gateMap;

    public Gate( String ident, int inputs, Destination[] destinations )
    {
	this.ident = ident;
	this.inputs = inputs;
	// this.type is written by the subclasses
	this.destinations = destinations;
	this.inputPins = new Gate[ inputs ];

    }

    public void procDestinations( Map gateMap )
    {
	this.gateMap = gateMap;

	for ( int i = 0; i < this.destinations.length; i++ )
	{
	    this.destinations[ i ].setFrom( this );
	    this.destinations[ i ].connect( gateMap );
	}
    }

    public boolean getOutput()
    {
	// method must be overridden by subclasses
	return false;
    }

    public void connectPin( Gate input, int pin )
    {
	this.inputPins[ pin ] = input;
    }

    public String getIdent()
    {
	return this.ident;
    }
}
