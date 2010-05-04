/*
 * ChatSession.java
 *
 * Version:
 *    $Id: ChatSession.java,v 1.1 2005/01/24 19:56:22 vcss232 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: ChatSession.java,v $
 *    Revision 1.1  2005/01/24 19:56:22  vcss232
 *    Initial revision
 *
 */

import java.io.Reader;
import java.io.Writer;

import java.util.Date;

/**
 * This interface defines what information needs to be maintained
 * regarding a chat session.
 *
 * @author Paul Tymann (ptt@cs.rit.edu)
 */
public interface ChatSession {
    /**
     * Return the handle of the local user.
     *
     * @return the local handle.
     */
    public String localHandle();

    /**
     * Return the handle of the remote user.
     *
     * @return the remote handle.
     */
    public String remoteHandle();

    /**
     * Return the time that the request for this chat session
     * was received.
     *
     * @return the time the request for this session was received.
     */
    public Date getRequestTime();

    /**
     * Return a writer that can be used to transmit text data to the
     * user at the other end of the connection.
     *
     * @return a stream used to send information to the user.
     */
    public Writer getWriter();

    /**
     * Return a reader that can be used to receive text data from the
     * user at the other end of the connection.
     *
     * @return a stream used to receive information from the user.
     */
    public Reader getReader();

    /** Invoked by a ChatListener during execution of chatRequest()
      * to indicate that the request will be handled by the
      * listener.  
      * <p>
      * If this method is not invoked by the receiver, then the ChatServer 
      * providing the session will assume that the "request to chat" has 
      * been declined, and the user at the other end of the connection 
      * will be appropriately notified.
      * </p>
      */
    void accept() throws ChatException;

    /**
     * Close any resources associated with this session.
     */
    public void close();

} // ChatSession
