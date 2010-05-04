/*
 * ChatListener.java
 *
 * Version:
 *    $Id: ChatListener.java,v 1.1 2005/01/24 19:56:21 vcss232 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: ChatListener.java,v $
 *    Revision 1.1  2005/01/24 19:56:21  vcss232
 *    Initial revision
 *
 */

import java.util.Set;

/**
 * A listener for the chat server.  Defines two methods.  One method
 * is used to pass periodic user registration information to the
 * client.  The second is used to notify the client when a chat request
 * has been received.
 *
 * @author Paul Tymann (ptt@cs.rit.edu)
 */

public interface ChatListener {

    /**
     * Invoked by the server when it receives registration information
     * from the name server.
     *
     * @param users a set containing the names of the registered users 
     *              (stored as Java <code>String</code> values).
     */
    public void registeredUsers( Set<String> users );

    /**
     * Invoked by the server when a chat request is received.  At least 
     * one listener must invoke the <code>accept</code> method on the 
     * <code>session</code> object, or else the request is considered to 
     * have been refused.  
     * <p>
     * The server will wait until all listeners 
     * have finished handling this event before determining if the 
     * request has been refused.  If a listener blocks (e.g., pending a 
     * user's response), then the server object will wait until it is 
     * done blocking and finishes handling the event before proceeding 
     * to the next listener (or deciding if the request has been 
     * refused).
     *
     * @param session the parameters for the requested chat session.
     * @see ChatSession#accept
     */
    public void chatRequest( ChatSession session );

} // ChatListener
