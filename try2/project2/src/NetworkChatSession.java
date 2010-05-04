/*
 * NetworkChatSession.java
 *
 * Version:
 *    $Id: NetworkChatSession.java,v 1.1 2005/01/24 19:56:22 vcss232 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: NetworkChatSession.java,v $
 *    Revision 1.1  2005/01/24 19:56:22  vcss232
 *    Initial revision
 *
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Date;

/**
 * This class holds information regarding a chat session.
 *
 * @author Paul Tymann (ptt@cs.rit.edu)
 * @author Matt Healy (mjh@cs.rit.edu)
 */
public class NetworkChatSession implements ChatSession {
    final static private boolean SUPPORT_ACCEPT_PROTOCOL = true;

    private String local;       // Handle of user on local host
    private String remote;      // Handle of user on remote host

    private Writer out;         // Stream to remote user
    private Reader in;          // Stream from remote user

    private Socket connection;  // Socket used to communicate
    private Date initiated;     // The time this session was started

    /**
     * Actively establish a new chat session with the user on the specified
     * host.
     *
     * @param handle the handle of the user establishing the connection.
     * @param host the remote host for the chat session
     * @param port the port where the remote server is accepting requests.
     *
     * @throws UnknownHostException if the host name is invalid.
     * @throws IOException if an error occurs during setup.
	 * @throws ChatRequestDeniedException if the remote user did not accept 
	 *                                    the chat request.
     */
    public NetworkChatSession( String handle, String host, int port )
        throws UnknownHostException, IOException, ChatRequestDeniedException 
	{
        local = handle;
        initiated = new Date();

        // Contact remote chat server
        connection = new Socket( host, port );

        // Create streams
        out = new OutputStreamWriter( connection.getOutputStream() );
        in = new InputStreamReader( connection.getInputStream() );

        // Send handle
        out.write( local + '\n' );
        out.flush();

        // Receive their handle
        remote = readLine();
        
        if ( SUPPORT_ACCEPT_PROTOCOL )
        {
            // If we got here, then the remote side must've accepted the 
            // request.  Regardless, we don't want to have anything happen 
            // if the user calls "accept()" on this object, since we 
            // *initiated* the chat session!
            acceptedRequest = true;
        }
    }

    /**
     * Establish a chat session with the user on the other end of the
     * specified socket.
     *
     * @param handle the handle of the user creating this session.
     * @param sock the socket used to communicate with the user.
     */
    public NetworkChatSession( String handle, Socket sock ) 
        throws IOException {

        connection = sock;
        local = handle;
        initiated = new Date();

        // Create streams
        out = new OutputStreamWriter( connection.getOutputStream() );
        in = new InputStreamReader( connection.getInputStream() );

        // Receive their handle
		try {
	        remote = readLine();
		} catch ( ChatRequestDeniedException e ) {
			// Internal failure: just report it as an IOException
			throw new IOException( "Failed to get handle" );
		}

        if ( ! SUPPORT_ACCEPT_PROTOCOL )
        {
            // Send handle
            out.write( local + '\n' );
            out.flush();
        }
        else
        {
            // WE WILL NOT WRITE OUT OUR HANDLE UNTIL THE REQUEST IS 
            // ACCEPTED BY A ChatListener OBJECT.
        }
    }

    /** Manages "has this request been accepted?" state. */
    private boolean acceptedRequest = false;

    /** Invoked by a ChatListener during execution of chatRequest()
      * to indicate that the request will be handled by the
      * listener.  If it is not invoked, then the ChatServer managing 
      * the chat protocol will assume that the "request to chat" has 
      * been declined, and the user at the other end of the connection 
      * will be appropriately notified.
      */
    public synchronized void accept() throws ChatException {
        if ( ! SUPPORT_ACCEPT_PROTOCOL ) {
            throw new UnsupportedOperationException(
                    "Accept protocol is disabled in this build"
                );
        }
        
        if ( acceptedRequest )
            return;

        acceptedRequest = true;
        try {
            // Send handle
            out.write( local + '\n' );
            out.flush();
        } catch( Exception e ) {
            throw new ChatException( "Remote end must've cancelled!", e );
        }
    }

    /** Invoked by NetworkChatServer after allowing all listeners a chance
      * to accept a chat request.  If it returns false, the ChatServer 
      * will simply close() the session, which will cause an I/O exception
      * on the initiating side, which will then be translated into a 
      * ChatException (thus fulfilling the requirement for such an 
      * exception when the request isn't accepted). <p />
      *
      * Note that this is <b>not</b> a method from ChatSession, but is 
      * simply a utility function of the NetworkChatSession type.
      */
    public boolean wasAccepted() {
        return acceptedRequest;
    }

    /**
     * Return the handle of the local user.
     *
     * @return the local handle.
     */
    public String localHandle() {
        return local;
    }

    /**
     * Return the handle of the remote user.
     *
     * @return the remote handle.
     */
    public String remoteHandle() {
        return remote;
    }

    /**
     * Return the time that the request for this chat session
     * was received.
     *
     * @return the time the request for this session was received.
     */
    public Date getRequestTime() {
        return initiated;
    }

    /**
     * Return a writer that can be used to transmit text data to the
     * user at the other end of the connection.
     *
     * @return a stream used to send information to the user.
     */
    public Writer getWriter() {
        return out;
    }

    /**
     * Return a reader that can be used to receive text data from the
     * user at the other end of the connection.
     *
     * @return a stream used to receive information from the user.
     */
    public Reader getReader() {
        return in;
    }

    /**
     * Close this session.
     */
    public void close() {
        try {
            // System.out.println( "Closing session's connection" );
            connection.close();
        }
        catch ( IOException e ) {}
        // System.out.println( "Done closing NetworkChatSession" );
    }

    /**
     * Return a string representation of this chat session.
     *
     * @return a string representation of this chat session.
     */
    public String toString() {
        return 
            "< local=" + local +
            ", remote=" + remote +
            ", initiated=" + initiated +
            ", socket=" + connection +
            " >";
    }
    
    /** Character returned by a Reader when the end of a stream is reached. */
    private static final int EOF_CHAR = -1;
    
    /**
     * Read a line from the reader connected to the socket.
     *
     * @return the next line of input from the reader.
     *
     * @throws IOException if an I/O error occurs, or if the end of the 
     *                     stream is reached unexpectedly.
	 * @throws ChatRequestDeniedException if the remote user did not accept 
	 *                                    the chat request.
     */
    private String readLine() throws IOException, ChatRequestDeniedException  {
        StringBuffer input = new StringBuffer();
        int ch = 0;

        while ( ( ch = in.read() ) != '\n' && (ch != EOF_CHAR) ) {
            input.append( (char)ch );
        }
        
		// See if it looks like the other side simply disconnected (which 
		// means that they declined our request)
        if ( input.length() == 0 && ch == EOF_CHAR ) {
            throw new ChatRequestDeniedException();
        }
		
        if ( ch == EOF_CHAR ) {
			throw new IOException( "Unexpected EOF encountered!" );
		}
		
        return input.toString();
    }
	
	/** Defines a custom exception type used when an attempt to connect to 
	  * a remote user fails because the remote user does not accept the 
	  * request.
	  */
	public class ChatRequestDeniedException extends Exception
	{
		/** Default constructor. */
		public ChatRequestDeniedException()
		{}
	}
	
} // NetworkChatSession
