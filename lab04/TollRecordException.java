/*
 * TollRecordException.java
 *
 * Version:
 *    $Id: TollRecordException.java,v 1.5 2004/10/04 03:56:19 rwt5629 Exp $
 *
 * Revisions:
 *    $Log: TollRecordException.java,v $
 *    Revision 1.5  2004/10/04 03:56:19  rwt5629
 *    complete for activity 2
 *
 *    Revision 1.1.1.1  2004/09/28 14:48:47  rwt5629
 *    i have no comment
 *
 *    Revision 1.1  2004/09/27 10:28:01  ptt
 *    Initial revision
 *
 */

import java.lang.*;

/**
 * An exception used to indicate errors by the TollRecord class.
 *
 * @author     Paul Tymann
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
