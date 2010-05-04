/*
 * ChatAdapter.java
 *
 * Version:
 *    $Id: ChatAdapter.java,v 1.1 2005/01/24 19:56:21 vcss232 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: ChatAdapter.java,v $
 *    Revision 1.1  2005/01/24 19:56:21  vcss232
 *    Initial revision
 *
 */

import java.util.Set;

/**
 * An adapter class, used to simplify the task of defining listeners for 
 * the chat server.  Defines trivial (empty) versions of the two methods 
 * from the ChatListener interface.
 *
 * @author Matt Healy (mjh@cs.rit.edu)
 */

public class ChatAdapter implements ChatListener {

    /**
     * Invoked by the server when it receives registration information
     * from the name server.
     *
     * @param users a set containing the names of the registered users.
     */
    public void registeredUsers( Set users )
    {}

    /**
     * Invoked by the server when a chat request is received.
     *
     * @param session the parameters for the requested chat session.
     */
    public void chatRequest( ChatSession session )
    {}

} // ChatListener
