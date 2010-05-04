/* ChatWindow.java
 *
 * $Id$
 * 
 * $Log$
 */

import java.awt.*;
import javax.swing.*;

/**
 *
 */
public class ChatWindow extends JFrame {

    /**
     * Class Objects
     */
    private ChatSession session = null;

    /**
     * Swing Objects
     */
    private JPanel jBody = null;
    private JTextArea jSend = null;
    private JTextArea jReceived = null;

    /**
     * Initializes ChatWindow class, passing the ChatSession to listen to
     */
    public ChatWindow( ChatSession session ) {
        super();
        setSize( 400, 250 );

        this.session = session;

        jBody = new JPanel( new GridLayout( 2, 1, 0, 0 ) );
        jReceived = new JTextArea();
        jSend = new JTextArea();

        jBody.add( jReceived );
        jBody.add( jSend );

        this.add( jBody );

        this.show();
    }
}
