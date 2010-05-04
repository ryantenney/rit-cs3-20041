/*
 * NetworkChatServer.java
 *
 * Version:
 *    $Id: NetworkChatServer.java,v 1.1 2005/01/24 19:56:22 vcss232 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: NetworkChatServer.java,v $
 *    Revision 1.1  2005/01/24 19:56:22  vcss232
 *    Initial revision
 *
 */

import NameService.NameEntry;
import NameService.NSRegisterPak;
import NameService.NSTicketPak;
import NameService.NSUpdatePak;
import NameService.Ticket;
import NameService.TicketException;

import java.io.IOException;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * A network implementation of a chat server.  Every server has a port
 * where it will accept incoming connections.  Actual chat sessions are
 * carried out on separate connections.  This server uses a NameService 
 * to record handles and port values for registered servers.
 *
 * @author Paul Tymann (ptt@cs.rit.edu)
 */

public class NetworkChatServer implements ChatServer, Observer {

    // Default location of NameService
    public static final String DEFAULT_NS_HOST = "holly.cs.rit.edu";
    public static final int DEFAULT_NS_PORT = 1337;

    // Time to wait for accept
    public static final int CONNECT_TIMEOUT = 60 * 1000;

    /**
     * A ChatSocketObserver waits for connections to be made to the
     * command socket.  When a request comes in, a connection is
     * established and a chat session object is returned to describe
     * the connection.
     */
    private class ChatSocketObserver implements Runnable {
        public void run() {
            Socket client = null;

            while( true ) {
                try {
                    // Wait for a connection
                    client = sock.accept();

                    if ( enabled ) {
                        // Create a session for the connection
                        NetworkChatSession session = 
                            new NetworkChatSession( myHandle, client );

                        // Notify listeners
                        for ( Iterator<ChatListener> i = listeners.iterator(); 
                              i.hasNext(); ) {

                            (i.next()).chatRequest( session );
                        }

                        // See if any of the listeners was willing to 
                        // accept the request to chat.
                        if ( ! session.wasAccepted() ) {
                            // Nope.  Close the connection, so that the 
                            // remote end will be able to tell that they 
                            // won't have anyone to talk to....
                            client.close();
                        }
                    }
                    else {
                        // We aren't accepting *any* requests at this time
                        client.close();
                    }
                }
                catch ( IOException e ) {
                    // Not much we can do - just ignore the connection
                }
            }
        }
    }

    private List<ChatListener> listeners;      // Registered listeners

    private InetAddress nsHost;  // Location of the name service
    private int nsPort;
    private List<NameEntry> names;

    private ServerSocket sock;   // Command connection

    private Ticket myTicket;     // Ticket for this user
    private String myHandle;     // Handle for this user

    private boolean enabled;     // Is the server accepting requests

    /**
     * Create a new chat server that uses the default host and port
     * for the name service.
     *
     * @throws java.io.IOException if an IO error occurs while communicating
     *                     with the name service.
     */
    public NetworkChatServer() throws java.io.IOException {
        this( DEFAULT_NS_HOST, DEFAULT_NS_PORT );
    }

    /**
     * Create a new chat server that uses the specified host and port
     * for the name service.
     *
     * @param host   name/address of the name server being targeted
     * @param port   port number on the name server to be connected to
     * 
     * @throws java.net.UnknownHostException if the host name is invalid.
     * @throws java.io.IOException if an IO error occurs while communicating
     *                     with the name service.
     */
    public NetworkChatServer( String host, int port ) 
        throws java.net.UnknownHostException, java.io.IOException {

        // List to hold registered listeners
        listeners = new ArrayList();

        // Obtain addressing information for the server
        nsHost = InetAddress.getByName( host );
        nsPort = port;

        // User must enable the server
        enabled = false;

        // Get the socket that will be used to accept requests
        sock = new ServerSocket();
        sock.bind( new InetSocketAddress( 0 ) );

        // Start the thread to watch the socket
        Thread connectThread = new Thread( new ChatSocketObserver() );
        connectThread.setDaemon( true );
        connectThread.start();
    }

    /**
     * Either enable or disable the server's ability to accept
     * incoming chat requests.
     *
     * @param enable if true the server will accept incoming requests.
     */
    public void setEnabled( boolean enable ) {
        enabled = enable;
    }

    /**
     * Determine whether or not the server is accepting incoming
     * chat requests.
     *
     * @return true if the server is accepting requests and false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Register a new user with the chat server.
     *
     * @param handle the handle by which this user will be identified.
     *
     * @throws ChatException if the handle is not unique or the user
     *         cannot be registered.
     */
    public void register( String handle ) throws ChatException {

        if ( myTicket != null ) {
            throw new ChatException( "Already registered" );
        }

        myHandle = handle;

        try {
            NSRegisterPak out = null;
            NSTicketPak in = null;

            // Create a new ticket.  Will throw an exception if there
            // is a problem
            myTicket = 
                new Ticket( nsHost, nsPort, handle, sock.getLocalPort() );

            // Tell the ticket to notify this object when it receives
            // registration updates
            myTicket.addObserver( this );
        }
        catch ( TicketException e ) {
            throw new ChatException( e.getMessage() );
        }
        catch ( IOException e ) {
            throw new ChatException( "Communication error" );
        }
    }

    /**
     * Initiate a chat session with a registered user.
     *
     * @param handle the handle of the user to chat with.
     *
     * @return an object that contains information about the new chat session, 
	 *         or null if the other user did not accept the request.
     *
     * @throws ChatException if the handle is invalid, or if an internal 
	 *         error occurs.
     */
    public ChatSession chat( String handle ) throws ChatException {
        NameEntry name = findName( handle );
        ChatSession retVal = null;

        // Make both we and the destination are registered

        if ( myTicket == null ) {
            throw new ChatException( "Not registered" );
        }

        if ( name == null ) {
            throw new ChatException( "Unknown user" );
        }

        // Attempt to establish the session
        try {
            retVal = 
                new NetworkChatSession( myHandle, 
                                        name.getHost(), 
                                        name.getPort() );
        }
        catch ( SocketTimeoutException e ) {
            throw new ChatException( "Request time out" );
        }
        catch ( IOException e ) {
            throw new ChatException( e.getMessage() );
        }
		catch( NetworkChatSession.ChatRequestDeniedException crde ) {
			try { retVal.close(); } catch( Exception e ) {}
			retVal = null;
		}

        return retVal;
    }

    /**
     * Add a listener to the set of listeners for this object.  Listeners
     * will receive periodic updates of the registered user list and
     * will be notified when a chat request is received.
     *
     * @param client the listener to add.
     */
    public void addChatListener( ChatListener client ) {
        listeners.add( client );
    }

    /**
     * Remove a listener from the set of registered listeners for this
     * object.  Equality is determined on a shallow (i.e., by reference)
     * basis.
     *
     * @param client the listener to remove.
     */
    public void removeChatListener( ChatListener client ) {
        boolean removed = false;

        for ( Iterator<ChatListener> i = listeners.iterator(); i.hasNext() && !removed; ) {
            if ( i.next() == client ) {
                i.remove();
                removed = true; 
            }
        }
    }

    /**
     * This method will be invoked everytime that an update packet
     * is received by the ticket manager.  It will notify registered
     * listeners that the update has been received.  Notification is
     * done in a separate thread
     */
    public void update( Observable o, Object data ) {
        Set<String> handles = new HashSet<String>();

        names = ((NSUpdatePak)data).getNames();

        // Step through the registered listeners and notify
        // them that new registration information is available

        if ( listeners.size() != 0 ) {
            // First create a set contianing only the names of the
            // registered names
            for ( Iterator<NameEntry> i = names.iterator(); i.hasNext(); ) {
                handles.add( i.next().getName() );
            }

            // Make it unmodifiable so that we can pass the same
            // reference to all listeners
            handles = Collections.unmodifiableSet( handles );

            // Notify the listeners
            for ( Iterator<ChatListener> i = listeners.iterator(); i.hasNext(); ) {
                i.next().registeredUsers( handles );
            }
        }
    }

    /**
     * Find the name associated with the given handle.
     *
     * @param handle the handle to look for.
     *
     * @return the name if the name is in the table or null.
     */
    private NameEntry findName( String handle ) {
        NameEntry retVal = null;

        // Make sure we have received an update
        if ( names != null ) {
            // Look throughy the names for a match
            for ( Iterator<NameEntry> i = names.iterator(); 
                  i.hasNext() && retVal == null; ) {

                NameEntry cur = (NameEntry)i.next();
                if ( cur.getName().equals( handle ) ) {
                    retVal = cur;
                }
            }
        }

        return retVal;
    }

} // NetworkChatServer
