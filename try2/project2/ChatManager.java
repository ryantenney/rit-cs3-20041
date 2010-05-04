/*
 *
 *
 *
 *
 */

imports java.util.*;

/**
 *
 *
 *
 */
public class ChatManager implements ChatListener {

    /**
     * Objects
     */
    private Set<String> users = null;
    private Set<String> pending = null;
    private Map<String,ChatSession> chats = null;
    private ChatServer server = null;

    /**
     *
     */
    public ChatManager( String handle ) {
        users = new HashSet<String>();
        pending = new HashSet<String>();
        chats = new HashMap<String,ChatSession>();

        server = new NetworkChatServer();
        server.addChatListener( this );

        server.register( handle );
    }

    /**
     *
     */
    public ChatSession chat( String handle ) {
        
    }

    /**
     * Invoked by the server when a chat request is received.
     */
    public void chatRequest( ChatSession session ) {
        chats.put( session );
        pending.put( session.remoteHandle() );
    }

    /**
     * Invoked by the server when it receives registration information 
     * from the name server.
     */
    public void registeredUsers( Set<String> users ) {
        
    }
}
