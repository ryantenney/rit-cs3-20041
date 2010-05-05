/**
 * FTPClient.java
 */

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * FTPClient is a class that functions as a minimally functional FTP client.
 *
 * @author: Ryan Tenney
 *
 */
public class FTPClient extends Thread
{
	/**
	 * 
	 * @author Ryan Tenney
	 */
	public class FileReceive extends Thread
	{
		public FTPDataConnection dc;
		public File path;

		public FileReceive( FTPDataConnection dc, File path )
		{
			this.dc = dc;
			this.path = path;
			this.start();
		}

		public void run()
		{
			byte[] data = dc.receive();
			while ( !dc.done ) {
				try {
					Thread.sleep( 50 );
				}
				catch ( InterruptedException iex ) { }
			}

			try {
				FileOutputStream fileOut = new FileOutputStream( path );
				fileOut.write( data );
			}
			catch ( FileNotFoundException fnfex ) {}
			catch ( IOException ioex ) {}
		}
	}

	public class ListReceive extends Thread
	{
		public FTPDataConnection dc;
		public Gooey gui;
		public String workspace;

		public ListReceive( FTPDataConnection dc, Gooey gui, String workspace )
		{
			this.dc = dc;
			this.gui = gui;
			this.workspace = workspace;
			this.start();
		}

		public void run()
		{
			byte[] list = dc.receive();
			while ( !dc.done )
			{
				try {
					Thread.sleep( 50 );
				}
				catch ( InterruptedException iex ) { }
			}

			try {
				BufferedReader bufIn = new BufferedReader( new InputStreamReader(
							new ByteArrayInputStream( dc.receive() ) ) );

				List files = new ArrayList();
				String lastIn;
				while ( (lastIn = bufIn.readLine()) != null ) {
					files.add( lastIn.split( " " )[ 0 ] );
				}
				fileList = new String[ 0 ];
				fileList = (String[])files.toArray( fileList );
				gui.addFileList( workspace, fileList );
			}
			catch ( IOException ioex ) { }
		}
	}

	private boolean authenticated = false;
	private String server, workspace;
	private int port = 21;

	private String[] fileList = null;
	private Manager parent = null;

	private Socket connection = null;
	private FTPDataConnection dc = null;

	private PrintWriter out = null;
	private BufferedReader in = null;

	private String lastIn;

	private List inBuffer = new ArrayList();
	private List outBuffer = new ArrayList();

	//private 

	//    /**
	//     * Default constructor with no parameters. Creates an instance that 
	//     * is not connected to a server.
	//     */
	//    public FTPClient()
	//    {
	//    
	//    }

	/**
	 * Constructor that takes several arguments to define what server to
	 * connect to and if it should connect on initialization
	 *
	 * @param    server    the hostname of the server to connect to
	 * @param    port      the port number on the server to connect to
	 * @param    connect   specifies if the client should connect immediately
	 */
	public FTPClient( String server, int port, boolean connect, 
			String workspace, Manager parent )
	{
		this.workspace = workspace;
		this.server = server;
		this.port = port;
		this.parent = parent;

		if ( connect ) {
			connect();
		}
	}

	/**
	 * 
	 *
	 */
	public void connect()
	{
		try {
			connection = new Socket( server, port );
			out = new PrintWriter( connection.getOutputStream(), true );
			in = new BufferedReader( new InputStreamReader( 
						connection.getInputStream() ) );

			this.start();
		}
		catch ( SocketException sex ) {

		}
		catch ( UnknownHostException uhex ) {

		}
		catch ( IOException ioex ) {

		}
	}

	/**
	 * 
	 *
	 */
	public void disconnect()
	{
		this.stop();
		try {
			connection.close();
			if ( dc != null ) {
				dc.abort();
				dc = null;
			}
			connection = null;
		}
		catch ( IOException ioex ) {

		}
	}

	/**
	 * 
	 * @return
	 */
	public String getWorkspace()
	{
		return this.workspace;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getFileList()
	{
		if ( fileList == null ) {
			return new String[ 0 ];
		}
		else {
			return fileList;
		}
	}

	/**
	 * 
	 */
	public void run()
	{
		boolean run = true;

		while ( run ) {
			try {
				inBuffer.add( (lastIn = in.readLine()) );

				if ( lastIn == null ) {
					run = false;
					parent.removeClient( this );
				}
				else {
					process( lastIn );
				}
			}
			catch ( SocketException sex ) {
				run = false;
				parent.removeClient( this );
			}
			catch ( IOException ioex ) {
				System.out.println( ioex );
			}
		}
	}

	/**
	 * 
	 * @param username
	 * @param password
	 */
	public void login( String username, String password )
	{
		try {
			out.println( "USER " + username );
			out.println( "PASS " + password );
			authenticated = true;
		}
		catch ( NullPointerException npex ) {
			authenticated = false;
		}
	}

	/**
	 * 
	 *
	 */
	public void quit()
	{

	}

	/**
	 * 
	 * @param name
	 */
	public void getFile( String name )
	{
		dc = new FTPDataConnection( 0, FTPDataConnection.DTP_RECEIVE );
		out.println( "PORT " + dc.getPORT() );

		File path = new File( parent.shareRoot, name );
		try {
			if ( !path.createNewFile() ) {
				return;	// file already exists
			}
			FileOutputStream fileOut = new FileOutputStream( path );
			dc.out = fileOut;

			out.println( "RETR " + name );

			fileOut.close();
		}
		catch ( IOException ioex ) {
			return;
		}
	}

	/**
	 * 
	 */
	public void retrFileList( String workspace, Gooey gui )
	{
		dc = new FTPDataConnection( 0, FTPDataConnection.DTP_RECEIVE );    	
		out.println( "PORT " + dc.getPORT() );
		out.println( "LIST" );

		ListReceive lr = new ListReceive( dc, gui, workspace );
	}

	/**
	 * 
	 * @param command
	 */
	private void process( String command )
	{

	}
}
