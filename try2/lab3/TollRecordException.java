/*
 * TollRecordException.java
 *
 * Version:
 *    $Id: TollRecordException.java,v 1.2 2005/01/05 05:52:00 rwt5629 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: TollRecordException.java,v $
 *    Revision 1.2  2005/01/05 05:52:00  rwt5629
 *    yay for being done with part one
 *
 *    Revision 1.1  2004/09/10 01:50:51  cs3
 *    Initial revision
 *
 */

import java.lang.*;

/**
 * An exception used to indicate errors by the TollRecord class.
 *
 * @author     Paul Tymann
 * @author     Ryan Tenney
 */

public class TollRecordException extends Exception {

    /**
     * Create a new TollRecordException that contains the specified
     * message.
     *
     * @param message the message recorded by the exception.
     */
    
    public TollRecordException( String message ) {
        super( message );
    }

} // TollRecordException
