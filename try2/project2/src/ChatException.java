/*
 * ChatException.java
 *
 * Version:
 *    $Id: ChatException.java,v 1.1 2005/01/24 19:56:21 vcss232 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: ChatException.java,v $
 *    Revision 1.1  2005/01/24 19:56:21  vcss232
 *    Initial revision
 *
 */

/**
 * Used to record information about an exception within the
 * chat system.
 *
 * @author Paul Tymann (ptt@cs.rit.edu)
 */

public class ChatException extends Exception {

    /**
     * Create a new exception without a message.
     */
    public ChatException() {
        super();
    }

    /**
     * Create a new ChatException with the specified message.
     *
     * @param message the message to associate with the exception.
     */
    public ChatException( String message ) {
        super( message );
    }

    /**
     * Create a new chat exception with the specified message and
     * the specified cause.
     *
     * @param message the message for this exception.
     * @param cause the cause of this exception.
     * 
     * @see java.lang.Exception#Exception( String, Throwable )
     */
    public ChatException( String message, Throwable cause ) {
        super( message, cause );
    }

} // ChatException
