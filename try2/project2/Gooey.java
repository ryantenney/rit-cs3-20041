/**
 * Gooey.java
 *
 * $Id$
 *
 * $Log$
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * Creates and manages the JChat User Interface
 *
 * @author    Ryan Tenney
 */
public class Gooey extends JFrame implements ActionListener
{

    /**
     * 
     */

    /**
     * Declares swing objects used in the GUI
     */
    private JPanel jBody;
    private JList jHandleList;
    private JScrollPane jScroll;
        
    /**
     * Declares menu objects
     */
    private JMenuBar jMenuBar = null;
        //private JMenu jServer = null;
            //private JMenuItem jServerStart = null;

    /**
     * Default constructor
     */
    public Gooey( String handle )
    {
        super( "jChat :: " + handle );
        
        setSize( 200, 500 );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            UIManager.setLookAndFeel( 
                    UIManager.getSystemLookAndFeelClassName() );
        }
        catch ( Exception ex ) {
            System.err.println( "LookAndFeel Error" );
        }
        
        jBody = new JPanel( new BorderLayout() );
        jHandleList = new JList();
        jScroll = new JScrollPane( jHandleList );
        
        //jMenuBar = new JMenuBar();
        //    jServer = new JMenu( "Server" );
        //    jMenuBar.add( jServer );
        //        jServerStart = jServer.add( "Start" );
        //        jServerStart.addActionListener( this );
        //        jServerStop = jServer.add( "Stop" );
        //        jServerStop.addActionListener( this );
        //        jServer.addSeparator();
        //        jServerOptions = jServer.add( "Options" );
        //        jServerOptions.addActionListener( this );
        //        jServer.addSeparator();
        //        jServerExit = jServer.add( "Exit" );
        //        jServerExit.addActionListener( this );
        //    jHelp = new JMenu( "Help" );
        //    jMenuBar.add( jHelp );
        //        jHelpTopics = jHelp.add( "Help Topics" );
        //        jHelpTopics.addActionListener( this );
        //        jHelp.addSeparator();
        //        jHelpAbout = jHelp.add( "About..." );
        //        jHelpAbout.addActionListener( this );
        
        Container c = getContentPane();
        
        //setJMenuBar( jMenuBar );
        
        // c.add( jSearchBox );
        
        jBody.add( jScroll );
        c.add( jBody );
        
    }

    public void actionPerformed( ActionEvent e ) {
/*
        if ( e.getSource() instanceof JMenuItem ) {
            
            if ( e.getSource() == jServerStart ) {
                manager.ftpServerStart();
            }
            else if ( e.getSource() == jServerStop ) {
                manager.ftpServerStop();
            }
            else if ( e.getSource() == jServerOptions ) {
                JOptionPane.showMessageDialog( this, 
                		"All options can be set via the command line",
						"Sever Options", JOptionPane.INFORMATION_MESSAGE );
            }
            else if ( e.getSource() == jServerExit ) {
                manager.shutdown();
                System.exit( 1 );
            }
            else if ( e.getSource() == jHelpTopics ) {
                System.out.println( "Help>HelpTopics" );
            }
            else if ( e.getSource() == jHelpAbout ) {
                JOptionPane.showMessageDialog( this,
                		"P2Pftp: ACS 4003-236 Final Project\r\n"
                		+ "Written by: Ryan Tenney\r\n"
                		+ "AIM s/n: ph0t0phobic\r\n"
						+ "DCE login: rwt5629\r\n\r\n"
						+ "Special thanks to:\r\n"
						+ "Kristin Evans for buying me a case of "
						+ "Sobe No Fear so I could finish this."
						, "About P2Pftp", JOptionPane.INFORMATION_MESSAGE );
            }
            else {
                System.out.println( "Unknown menu item actuated" );
            }
        }
        else if ( e.getSource() instanceof JButton ) {
            
            if ( e.getSource() == jSearchButton ) {
                System.out.println( "Search button pressed" );
            }
            else if ( e.getSource() == jListFiles ) {
            	NameClient.ServerEntry selected = 
            		(NameClient.ServerEntry)jServerList.getSelectedValue();
            	manager.getClient( selected.name ).retrFileList(
            			selected.name, this );
            }
            else if ( e.getSource() == jDownload ) {
            	int index = jTab.getSelectedIndex();
            	String workspace = jTab.getTitleAt( index );
            	JScrollPane scroll = (JScrollPane)jTab.getComponentAt( index );
            	JList files = (JList)scroll.getViewport().getView();
            	String file = (String)files.getSelectedValue();
            	 
            	manager.getClient( workspace ).getFile( file );
            }
            else if ( e.getSource() == jClose ) {
				int index = jTab.getSelectedIndex();
            	String workspace = jTab.getTitleAt( index );
            	if ( !workspace.equals( "local files" ) ) {
            		jTab.remove( index );
            	}
            }
            else {
                System.out.println( "Unknown button actuated" );
            }
        }
        else {
            
        }
*/
    }
}
