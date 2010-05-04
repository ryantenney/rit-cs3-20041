/* 
 * ExpressionTreeException.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

/**
 * A class used to indicate an error that may be generated from the
 * expression tree class.
 *
 * @author     Paul Tymann
 */

public class ExpressionTreeException extends Exception {
    
    /**
     * Create a new ExpressionTreeException with the specified string
     * as its message.
     *
     * @param  s  the message string for this exception
     */

    public ExpressionTreeException( String s ) {
	super( s );
    }

} // ExpressionTreeException
