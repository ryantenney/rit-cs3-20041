/*
 * $Id: Destination.java,v 1.3 2004/10/12 14:55:16 rwt5629 Exp $
 *
 * $Log: Destination.java,v $
 * Revision 1.3  2004/10/12 14:55:16  rwt5629
 * nearly finished, a problem with the 2^0 bit still remains, but its after the deadline so this is moot anyways.
 *
 * Revision 1.2  2004/10/12 03:28:57  rwt5629
 * with any luck, one of my last commitments
 *
 * Revision 1.1  2004/10/12 02:40:08  rwt5629
 * created Destination.java, also added cvs tracking to AndGate.java as well as made various other modifications to other files
 *
 */

/**
 * @author=Ryan Tenney
 *
 *
 */

import java.util.*;

public class Destination
{

    private Gate fromGate, toGate;
    private String toName;
    private int toPin;

    public Destination( Gate from, String to, int pin )
    {
	this.fromGate = from;
	this.toName = to;
	this.toPin = pin;
    }

    public Destination( Gate from, Gate to, int pin )
    {
	this.fromGate = from;
	this.toGate = to;
	this.toPin = pin;
    }

    public Destination( Gate from, String toNameAndPin )
    {
	this.fromGate = from;
	
	if ( toNameAndPin.substring( 0, 3 ) == "OUT" ) {	
	    this.toName = toNameAndPin;
	}
	else {
	    int dash = toNameAndPin.indexOf( '-' );
	    this.toName = toNameAndPin.substring( 0, dash );
	    this.toPin = Integer.parseInt( 
		toNameAndPin.substring( dash + 1) );
	}
    }

    public void connect()
    {
	this.toGate.connectPin( this.fromGate, this.toPin );
    }

    public void connect( Map nameGateMap )
    {
	this.toGate = (Gate)nameGateMap.get( this.toName );
	this.connect();
    }

    public Gate getFrom()
    {
	return this.fromGate;
    }

    public void setFrom( Gate from )
    {
	this.fromGate = from;
    }

    public Gate getTo()
    {
	return this.toGate;
    }

    public void setTo( Gate to )
    {
	this.toGate = to;
    }

    public String getToName()
    {
	return this.toName;
    }

    public void setToName( String to )
    {
	this.toName = to;
    }

    public int getPin()
    {
	return this.toPin;
    }

    public void setPin( int pin )
    {
	this.toPin = pin;
    }

}
