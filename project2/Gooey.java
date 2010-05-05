/**
 * Gooey.java
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * Creates and manages the P2PFTP User Interface
 *
 * @author    Ryan Tenney
 */
public class Gooey extends JFrame implements ActionListener, Runnable
{
	/**
	 * Declares references to the other program modules
	 */
	public Manager manager = null;

	/**
	 * Thread used to update data bindings
	 */
	private Thread update = new Thread( this );

	/**
	 * Declares swing objects used in the GUI
	 */
	private JPanel jSearchBox;
	private JButton jListFiles;
	private JButton jDownload;
	private JButton jClose;
	private JPanel jNull;
	private JTextField jSearch;
	private JButton jSearchButton;
	private JPanel jBody;
	private JTabbedPane jTab;
	private JList jServerList;
	private JScrollPane jServers;

	/**
	 * Declares menu objects
	 */
	private JMenuBar jMenuBar = null;
	private JMenu jServer = null;
	private JMenuItem jServerStart = null;
	private JMenuItem jServerStop = null;
	private JMenuItem jServerOptions = null;
	private JMenuItem jServerExit = null;
	private JMenu jHelp = null;
	private JMenuItem jHelpStatus = null;
	private JMenuItem jHelpTopics = null;
	private JMenuItem jHelpAbout = null;

	/**
	 * Default constructor
	 */
	public Gooey( Manager man )
	{
		super( "P2Pftp" );
		manager = man;

		setSize( 800, 500 );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel( 
					UIManager.getSystemLookAndFeelClassName() );
		}
		catch ( Exception ex ) {
			System.err.println( "LookAndFeel Error" );
		}

		//
		jSearchBox = new JPanel( new GridLayout( 1, 5, 5, 5 ) );
		jListFiles = new JButton( "List Files" );
		jDownload = new JButton( "Download" );
		jClose = new JButton( "Close" );
		jNull = new JPanel();
		jSearch = new JTextField();
		jSearchButton = new JButton( "Search" );
		jBody = new JPanel( new BorderLayout() );
		jTab = new JTabbedPane( JTabbedPane.TOP,
				JTabbedPane.SCROLL_TAB_LAYOUT );
		jServers = new JScrollPane( jServerList );

		// create menus
		jMenuBar = new JMenuBar();
		jServer = new JMenu( "Server" );
		jMenuBar.add( jServer );
		jServerStart = jServer.add( "Start" );
		jServerStart.addActionListener( this );
		jServerStop = jServer.add( "Stop" );
		jServerStop.addActionListener( this );
		jServer.addSeparator();
		jServerOptions = jServer.add( "Options" );
		jServerOptions.addActionListener( this );
		jServer.addSeparator();
		jServerExit = jServer.add( "Exit" );
		jServerExit.addActionListener( this );
		jHelp = new JMenu( "Help" );
		jMenuBar.add( jHelp );
		jHelpTopics = jHelp.add( "Help Topics" );
		jHelpTopics.addActionListener( this );
		jHelp.addSeparator();
		jHelpAbout = jHelp.add( "About..." );
		jHelpAbout.addActionListener( this );


		// create search bar
		jListFiles.addActionListener( this );
		jSearchBox.add( jListFiles );
		jDownload.addActionListener( this );
		jSearchBox.add( jDownload );
		jClose.addActionListener( this );
		jSearchBox.add( jClose );
		jSearchBox.add( jNull );
		jSearchBox.add( jSearch );
		jSearchButton.addActionListener( this );
		jSearchBox.add( jSearchButton );

		// create tabbed pane
		//jTabs = new JTabbedPane();

		Container c = getContentPane();

		setJMenuBar( jMenuBar );

		// c.add( jSearchBox );

		File root = new File( manager.getRoot() );
		ArrayList outList = new ArrayList();
		String[] outArray = new String[ 0 ];
		File[] listing = root.listFiles();
		for ( int f = 0; f < listing.length; f++ ) {
			if ( listing[ f ].isFile() ) {
				outList.add( listing[ f ].getName() );
			}
		}
		outArray = (String[])outList.toArray( outArray );

		jTab.addTab( "local files", new JScrollPane( new JList( 
						outArray ) ) );

		jServerList = new JList();
		jServers = new JScrollPane( jServerList );
		jBody.add( jServers, BorderLayout.WEST );
		jBody.add( jTab, BorderLayout.CENTER );
		c.add( jSearchBox, BorderLayout.NORTH );
		c.add( jBody, BorderLayout.CENTER );

		update.start();

	}

	public void run()
	{
		// update server list
		NameClient nc = manager.getNameClient();
		boolean update = true;

		while ( update ) {
			// update items in list
			try {
				//                if ( jServerList.getSelectedValue() != null ) {
				//                    NameClient.ServerEntry selected = 
				//                    	(NameClient.ServerEntry)jServerList.getSelectedValue();
				//                    NameClient.ServerEntry[] servers = nc.getServers();
				//                    jServerList.setListData( servers );
				//                    for ( int i = 0; i < servers.length; i++ ) {
				//                    	if ( selected.name.equals( servers[ i ].name ) ) {
				//                            jServerList.setSelectedIndex( i );
				//                            System.out.println("set index to " + i);
				//                        }
				//                    }
				//                }
				//                else {
				jServerList.setListData( nc.getServers() );
				//                }

				Thread.sleep( 4000 );
			}
			catch ( InterruptedException iex ) {}
			catch ( NullPointerException npex ) {}
			finally {
				try {
					Thread.sleep( 1000 );
				}
				catch ( InterruptedException iex ) {

				}
			}
		}
	}

	public void addFileList( String workspace, String[] files )
	{
		jTab.addTab( workspace, new JScrollPane( new JList(	files ) ) );
	}

	public void actionPerformed( ActionEvent e )
	{
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
	}
}
