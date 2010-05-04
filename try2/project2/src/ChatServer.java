/*
 * ChatServer.java
 *
 * Version:
 *    $Id: ChatServer.java,v 1.1 2005/01/24 19:56:21 vcss232 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: ChatServer.java,v $
 *    Revision 1.1  2005/01/24 19:56:21  vcss232
 *    Initial revision
 *
 */

/**
 * This interface defines the behavior provided by a chat server.
 *
 * @author Paul Tymann (ptt@cs.rit.edu)
 */

public interface ChatServer {
    /**
     * Either enable or disable the server's ability to accept
     * incoming chat requests.
     *
     * @param enable if true the server will accept incoming requests.
     */
    public void setEnabled( boolean enable );

    /**
     * Determine whether or not the server is accepting incoming
     * chat requests.
     *
     * @return true if the server is accepting requests and false otherwise.
     */
    public boolean isEnabled();

    /**
     * Register a new user with the chat server.  The server will not
     * accept requests until a user has registered.  Only one handle
     * can be registered.
     *
     * @param handle the handle by which this user will be identified.
     *
     * @throws ChatException if the handle is not unique or the user
     *         cannot be registered.
     */
    public void register( String handle ) throws ChatException;

    /**
     * Initiate a chat session with a registered user.
     * 
     * <p>
     * <em>Note:</em> specifying the user's own handle is supported, and 
     * will result in a chat request being sent to this application's 
     * registered listeners.  (This will allow the user to chat with 
     * themselves, if the client application supports it.)
     * </p>
     *
     * @param handle the handle of the user to chat with.
     *
     * @return an object that contains information about the new chat session, 
     *         or null if none of the other user's listeners accepted the 
     *         request.
     * 
     * @throws ChatException if the handle is invalid, or if an internal 
     *         error occurs.
     *
     * @see #addChatListener
     * @see ChatSession#accept
     */
    public ChatSession chat( String handle ) throws ChatException;

    /**
     * Add a listener to the set of listeners for this object.  Listeners
     * will receive periodic updates of the registered user list and
     * will be notified when a chat request is received.
     *
     * @param client the listener to add.
     */
    public void addChatListener( ChatListener client );

    /**
     * Remove a listener from the set of registered listeners for this
     * object.  Equality is determined on a shallow (i.e., by reference)
     * basis.
     *
     * @param client the listener to remove.
     */
    public void removeChatListener( ChatListener client );

} // ChatServer
