/**
 * FTPServer.java
 */

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Runs a multithreaded FTP server
 * 
 * @author Ryan Tenney
 */
public class FTPServer implements Runnable
{
	/**
	 * 
	 * @author Ryan Tenney
	 */
	private class FTPServerInstance extends Thread
	{
		private boolean authenticated = false;

		private String username = null;
		private String password = null;

		private User user = null;

		private FTPServer parent = null;
		private Socket connection = null;
		private FTPDataConnection dc = null;

		private PrintWriter out = null;
		private BufferedReader in = null;

		private String lastIn;

		private List inBuffer = new ArrayList();
		private List outBuffer = new ArrayList();

		/**
		 * 
		 * @param parent
		 * @param connection
		 */
		public FTPServerInstance( FTPServer parent, Socket connection )
		{
			this.parent = parent;
			this.connection = connection;

			try {
				out = new PrintWriter( connection.getOutputStream(), true );
				in = new BufferedReader( new InputStreamReader( 
							connection.getInputStream() ) );

				this.start();

				printCode( 220 );
			}
			catch ( SocketException sex ) {
				System.out.println( sex );
			}
			catch ( IOException ioex ) {
				System.out.println( ioex );
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
						disconnectUser();
						parent.removeServer( this );
					}
					else {
						System.out.println( lastIn );
						process( lastIn );
					}
				}
				catch ( SocketException sex ) {
					run = false;
					disconnectUser();
					parent.removeServer( this );
				}
				catch ( IOException ioex ) {
					System.out.println( ioex );
				}
			}
		}

		/**
		 *
		 */
		public void close()
		{
			try {
				printCode( 221 );
				connection.close();
			}
			catch ( IOException ioex ) {
				printCode( 421 );
			}
		}

		/**
		 * 
		 * @param username
		 * @param password
		 */
		private void connectUser( String username, String password )
		{
			user = new User( username, password, 
					connection.getRemoteSocketAddress(), parent.allowAnon );
			parent.connectUser( user );
		}

		/**
		 * 
		 *
		 */
		private void disconnectUser()
		{
			if ( user != null ) {
				parent.disconnectUser( user );
				user = null;
			}
		}

		/**
		 * 
		 * @param commandString
		 */
		private void process( String commandString )
		{
			StringTokenizer st;
			String cmd;

			try {
				st = new StringTokenizer( commandString );
				cmd = st.nextToken().toUpperCase();
			}
			catch ( Exception ex ) {
				printCode( 500 );
				return;
			}

			if ( !authenticated ) {

				if ( cmd.equals( "USER" ) ) {
					// USER <SP> <username> <CRLF>
					try {
						username = st.nextToken();
						printCode( 331 );
					}
					catch ( NoSuchElementException nseex ) {
						printCode( 501 );
					}
				}

				else if ( cmd.equals( "PASS" ) ) {
					// PASS <SP> <password> <CRLF>
					try {
						if ( username != null ) {
							password = st.nextToken();
							authenticated = parent.authenticate( 
									username, password );
							if ( authenticated ) {
								printCode( 230 );
								connectUser( username, password );
							}
							else {
								printCode( 530 );
							}
						}
						else {
							printCode( 503 );
						}
					}
					catch ( NoSuchElementException nseex ) {
						printCode( 501 );
					}
				}

				else if ( cmd.equals( "ACCT" ) ) {
					// ACCT <SP> <account-information> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "NOOP" ) ) {
					// NOOP <CRLF>
					printCode( 200 );
				}

				else if ( cmd.equals( "QUIT" ) ) {
					// QUIT <CRLF>
					close();
				}

				else {
					printCode( 530 );
				}
			}

			else {

				if ( cmd.equals( "USER" ) ) {
					printCode( 503 );
				}

				else if ( cmd.equals( "PASS" ) ) {
					printCode( 503 );
				}

				else if ( cmd.equals( "ACCT" ) ) {
					printCode( 503 );
				}

				else if ( cmd.equals( "CWD" ) ) {
					// CWD <SP> <pathname> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "CDUP" ) ) {
					// CDUP <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "SMNT" ) ) {
					// SMNT <SP> <pathname> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "QUIT" ) ) {
					// QUIT <CRLF>
					disconnectUser();
					close();
				}

				else if ( cmd.equals( "REIN" ) ) {
					// REIN <CRLF>
					authenticated = false;
					username = null;
					password = null;
					inBuffer.clear();
					outBuffer.clear();
					disconnectUser();
					printCode( 220 );
				}

				else if ( cmd.equals( "PORT" ) ) {
					// PORT <SP> <host-port> <CRLF>
					String portCmd;

					int i1 = commandString.indexOf( "(" ) + 1;
					if ( i1 != 0 ) {
						int i2 = commandString.indexOf( ")", i1 );
						portCmd = commandString.substring( i1, i2 );
					}
					else {
						portCmd = commandString.substring( 5 );
					}

					dc = new FTPDataConnection( portCmd,
							FTPDataConnection.DTP_SEND );
				}

				else if ( cmd.equals( "PASV" ) ) {
					// PASV <CRLF>

					if ( dc == null ) {
						dc = new FTPDataConnection( 0,
								FTPDataConnection.DTP_SEND );
						printCode( 227, dc.getPORT() );
					}
					else {

					}
				}

				else if ( cmd.equals( "TYPE" ) ) {
					// TYPE <SP> <type-code> <CRLF>
					printCode( 200, "Type set to I." );
				}

				else if ( cmd.equals( "STRU" ) ) {
					// STRU <SP> <structure-code> <CRLF>
					printCode( 200, "STRU F ok." );
				}

				else if ( cmd.equals( "MODE" ) ) {
					// MODE <SP> <mode-code> <CRLF>
					printCode( 200, "Mode S ok." );
				}

				else if ( cmd.equals( "RETR" ) ) {
					// RETR <SP> <pathname> <CRLF>
					try {
						String filename = st.nextToken();
						File path = new File( parent.getRoot(), filename );
						InputStream in = new FileInputStream( path );
						dc.sendAsync( in );
					}
					catch ( FileNotFoundException fnfex ) {
						printCode( 550 );
					}
					catch ( NoSuchElementException nseex ) {
						printCode( 501 );
					}
				}

				else if ( cmd.equals( "STOR" ) ) {
					// STOR <SP> <pathname> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "STOU" ) ) {
					// STOU <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "APPE" ) ) {
					// APPE <SP> <pathname> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "ALLO" ) ) {
					// ALLO <SP> <decimal-integer>
					//    [ <SP> R <SP> <decimal-integer> ] <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "REST" ) ) {
					// REST <SP> <marker> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "RNFR" ) ) {
					// RNFR <SP> <pathname> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "RNTO" ) ) {
					// RNTO <SP> <pathname> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "ABOR" ) ) {
					// ABOR <CRLF>
					printCode( 226 );
				}

				else if ( cmd.equals( "DELE" ) ) {
					// DELE <SP> <pathname> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "RMD" ) ) {
					// RMD <SP> <pathname> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "MKD" ) ) {
					// MKD <SP> <pathname> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "PWD" ) ) {
					// PWD <CRLF>
					printCode( 257, "\"/\" is current directory." );
				}

				else if ( cmd.equals( "LIST" ) ) {
					// LIST [ <SP> <pathname> ] <CRLF>
					if ( dc != null ) {
						printCode( 125 );
						dc.sendAsync( parent.listFiles( true ) );
						//dc.abort();
						//dc = null;
					}
					printCode( 226 );
				}

				else if ( cmd.equals( "NLST" ) ) {
					// NLST [ <SP> <pathname> ] <CRLF>
					if ( dc != null ) {
						printCode( 125 );
						dc.sendAsync( parent.listFiles( false ) );
						dc.abort();
						dc = null;
					}
					printCode( 226 );
				}

				else if ( cmd.equals( "SITE" ) ) {
					// SITE <SP> <string> <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "SYST" ) ) {
					// SYST <CRLF>
					printCode( 215,
							"amd64_winnt p2pftp 4003-236 20041 rwt5629" );
				}

				else if ( cmd.equals( "STAT" ) ) {
					// STAT [ <SP> <pathname> ] <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "HELP" ) ) {
					// HELP [ <SP> <string> ] <CRLF>
					printCode( 502 );
				}

				else if ( cmd.equals( "NOOP" ) ) {
					// NOOP <CRLF>
					printCode( 200 );
				}



				////////////////////////////////////////////////////////////////////////////////
				// eliminate out of testing ////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////////////////////
				else if ( cmd.equals( "CODE" ) ) {
					// CODE <SP> <integer> <CRLF>
					// testing code, remove when finished
					try {
						int code = Integer.parseInt( st.nextToken() );
						printCode( code );
					}
					catch( NoSuchElementException nseex ) {
						printCode( 501 );
					}                
				}

				else if ( cmd.equals( "STOP" ) ) {
					// STOP <CRLF>
					// testing code, remove when finished
					printCode( 500 );
					parent.stop();
				}

				else if ( cmd.equals( "ECHO" ) ) {
					// ECHO <SP> <ascii-data> <CRLF>
					// testing code, remove when finished
					try {
						out.println( "ECHO" );
					}
					catch( NoSuchElementException nseex ) {
						printCode( 501 );
					}
				}

				else if ( cmd.equals( "KILL" ) ) {
					// KILL <CRLF>
					// testing code, remove when finished
					printCode( 500 );
					parent.kill();
				}
				////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////////////////////

				else {
					// command not recognized
					printCode( 500 );
				}
			}
		}

		/**
		 * 
		 * @param output
		 */
		public void print( Object output )
		{
			out.print( output );
		}

		/**
		 * 
		 * @param output
		 */
		public void println( Object output )
		{
			out.println( output );
		}

		/**
		 * 
		 * @param code
		 */
		public void printCode( int code )
		{
			out.println( code( code ) );
			System.out.println( ">" + code( code ) );
		}

		/**
		 * 
		 * @param code
		 * @param message
		 */
		public void printCode( int code, String message )
		{
			out.println( code( code, message ) );
			System.out.println( ">" + code( code, message ) );
		}

		/**
		 * 
		 * @param code
		 * @param args
		 */
		public void printCode( int code, String[] args )
		{
			out.println( code( code, args ) );
			System.out.println( ">" + code( code, args ) );
		}

		/**
		 * 
		 * @param code
		 * @return
		 */
		public String code( int code )
		{
			return code( code, new String[ 0 ] );
		}

		/**
		 * 
		 * @param code
		 * @param message
		 * @return
		 */
		public String code( int code, String message )
		{
			return code + " " + message;
		}

		/**
		 * 
		 * @param code
		 * @param args
		 * @return
		 */
		public String code( int code, String[] args )
		{
			String returnStr = Integer.toString( code );

			switch ( code ) {
				case 110: returnStr += " Restart marker reply."; break;
				case 120: returnStr += " Service ready in {0} minutes."; break;
				case 125: returnStr += " Data connection already open; "
						  + "transfer starting."; break;
				case 150: returnStr += " File status okay; about to open data "
						  + "connection."; break;
				case 200: returnStr += " Command okay."; break;
				case 202: returnStr += " Command not implemented, superfluous "
						  + "at this site."; break;
				case 211: returnStr += " System status, or system help reply.";
						  break;
				case 212: returnStr += " Directory status."; break;
				case 213: returnStr += " File status."; break;
				case 214: returnStr += " Help message."; break;
				case 215: returnStr += " NAME system type."; break;
				case 220: returnStr += " Service ready for new user."; break;
				case 221: returnStr += " Service closing control connection.";
						  break;
				case 225: returnStr += " Data connection open; no transfer in "
						  + "progress."; break;
				case 226: returnStr += " Closing data connection."; break;
				case 227: returnStr += " Entering Passive Mode (" + args[ 0 ]
						  + ")."; break;
				case 230: returnStr += " User logged in, proceed."; break;
				case 250: returnStr += " Requested file action okay, "
						  + "completed."; break;
				case 257: returnStr += " \"{0}\" created."; break;
				case 331: returnStr += " User name okay, need password.";
						  break;
				case 332: returnStr += " Need account for login."; break;
				case 350: returnStr += " Requested file action pending further "
						  + "information."; break;
				case 421: returnStr += " Service not available, closing "
						  + "control connection."; break;
				case 425: returnStr += " Can't open data connection."; break;
				case 426: returnStr += " Connection closed; transfer aborted.";
						  break;
				case 450: returnStr += " Requested file action not taken.";
						  break;
				case 451: returnStr += " Requested action aborted: local "
						  + "error in processing."; break;
				case 452: returnStr += " Requested action not taken."; break;
				case 500: returnStr += " Syntax error, command unrecognized.";
						  break;
				case 501: returnStr += " Syntax error in parameters or " 
						  + "arguments."; break;
				case 502: returnStr += " Command not implemented."; break;
				case 503: returnStr += " Bad sequence of commands."; break;
				case 504: returnStr += " Command not implemented for that "
						  + "parameter."; break;
				case 530: returnStr += " Not logged in."; break;
				case 532: returnStr += " Need account for storing files.";
						  break;
				case 550: returnStr += " Requested action not taken."; break;
				case 551: returnStr += " Requested action aborted: page type "
						  + "unknown."; break;
				case 552: returnStr += " Requested file action aborted."; break;
				case 553: returnStr += " Requested action not taken."; break;
				default: break;
			}

			return returnStr;
		}
	}

	private class User
	{
		private String username = null;
		private String password = null;
		private InetSocketAddress endpoint = null;
		private boolean anonymous = false;

		private Set filesRetr = null;

		/**
		 * 
		 * @param username
		 * @param password
		 * @param endpoint
		 * @param anonymous
		 */
		public User( String username, String password, 
				SocketAddress endpoint, boolean anonymous )
		{
			this.username = username;
			this.password = password;
			this.endpoint = ( InetSocketAddress )endpoint;
			this.anonymous = anonymous;

			this.filesRetr = new HashSet();
		}

		/**
		 * 
		 * @param filename
		 */
		public void addFileRetr( String filename )
		{
			filesRetr.add( filename );
		}

		/**
		 * 
		 * @return
		 */
		public String getUsername()
		{
			return this.username;
		}

		/**
		 * 
		 * @return
		 */
		public String getPassword()
		{
			return this.password;
		}

		/**
		 * 
		 * @return
		 */
		public InetSocketAddress getEndpoint()
		{
			return this.endpoint;
		}

		/**
		 * 
		 * @return
		 */
		public InetAddress getAddress()
		{
			return this.endpoint.getAddress();
		}

		/**
		 * 
		 * @return
		 */
		public int getPort()
		{
			return this.endpoint.getPort();
		}

		//        public byte[] getByteAddress()
		//        {
		//            return this.address;
		//        }
		//        
		//        public byte[] getBytePort()
		//        {
		//            return this.port;
		//        }

		/**
		 * 
		 */
		public boolean getAnonymous()
		{
			return this.anonymous;
		}

		/**
		 * 
		 * @return
		 */
		public String[] getFilesRetr()
		{
			String[] files = new String[ 0 ];
			files = ( String[] )this.filesRetr.toArray( files );
			return files;
		}

		/**
		 * 
		 */
		public String toString()
		{
			if ( !this.anonymous ) {
				return username + " " + endpoint;
			}
			else {
				return username + " <" + password + "> " + endpoint;
			}
		}
	}

	private int port;

	private boolean readOnly = true;
	private boolean allowAnon = true;
	private boolean running = false;

	private String root;
	private File rootDir;

	private Set users = new HashSet();
	private Set servers = new HashSet();
	private Set connections = new HashSet();

	private Thread listenerThread;
	private ServerSocket listener;

	/**
	 * 
	 * @param port
	 * @param root
	 * @param running
	 */
	public FTPServer( int port, String root, boolean running )
	{
		this.port = port;
		this.setRoot( root );

		try {
			listener = new ServerSocket( port );
			listenerThread = new Thread( this );
		}
		catch ( IOException ioex ) {
			System.err.println( ioex );
		}

		if ( running ) {
			start();
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean getRunning()
	{
		return running;
	}

	/**
	 * 
	 * @return
	 */
	public int getPort()
	{
		try {
			return listener.getLocalPort();
		}
		catch ( Exception ex ) {
			return -1;
		}
	}

	/**
	 * 
	 * @param value
	 */
	public void setPort( int value )
	{
		if ( !running ) {
			try {
				listener = new ServerSocket( port );
				port = value;
			}
			catch ( IOException ioex ) {

			}
		}        
	}

	/**
	 * 
	 * @return
	 */
	public String getRoot()
	{
		return root;
	}

	/**
	 * 
	 * @param value
	 */
	public void setRoot( String value )
	{
		root = value;
		File temp = new File( value );
		if ( temp.isDirectory() ){
			rootDir = temp;
		}
	}

	/**
	 * 
	 * @param user
	 * @param pass
	 * @return
	 */
	private boolean authenticate( String user, String pass )
	{
		return ( allowAnon && pass.indexOf( "@" ) != -1 );
	}

	/**
	 * 
	 *
	 */
	public void start()
	{
		running = true;
		listenerThread.start();
	}

	/**
	 * 
	 *
	 */
	public void stop()
	{
		running = false;
		listenerThread.stop();

		this.kill();
		users.clear();
	}

	/**
	 * 
	 *
	 */
	public void kill()
	{
		FTPServerInstance[] serverSet = new FTPServerInstance[ 0 ];
		serverSet = (FTPServerInstance[])servers.toArray( serverSet );

		for( int i = 0; i < serverSet.length; i++ ) {
			serverSet[ i ].close();
		}

		FTPDataConnection[] connectionSet = new FTPDataConnection[ 0 ];
		connectionSet = (FTPDataConnection[])connections.toArray( 
				connectionSet );

		for( int i = 0; i < connectionSet.length; i++ ) {
			connectionSet[ i ].abort();
		}

		servers.clear();
		connections.clear();
	}

	/**
	 * 
	 */
	public void run()
	{   
		while ( running ) {
			try {
				servers.add( new FTPServerInstance( this, 
							listener.accept() ) );
			}
			catch ( IOException ioex ) {
				System.err.println( ioex );
			}
		}
	}

	/**
	 * 
	 * @param user
	 */
	private void connectUser( User user )
	{
		users.add( user );
	}

	/**
	 * 
	 * @param user
	 */
	private void disconnectUser( User user )
	{
		users.remove( user );
	}

	/**
	 * 
	 * @param size
	 * @return
	 */
	private byte[] listFiles( boolean size )
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		PrintWriter listOut = new PrintWriter( byteOut );

		File[] listing = rootDir.listFiles();
		for ( int f = 0; f < listing.length; f++ ) {
			if ( listing[ f ].isFile() ) {
				if ( size ) {
					listOut.print( listing[ f ].getName() );
					listOut.print( " " );
					listOut.println( listing[ f ].length() );
				}
				else {
					listOut.println( listing[ f ].getName() );
				}	
			}
		}
		listOut.flush();
		return byteOut.toByteArray();
	}

	/**
	 * 
	 * @return
	 */
	public User[] getUsers()
	{
		User[] users = new User[ 0 ];
		users = (User[])this.users.toArray( users );
		return users;
	}

	/**
	 * 
	 * @param dc
	 */
	private void addDataConnection( Runnable dc )
	{
		connections.add( dc );
	}

	/**
	 * 
	 * @param dc
	 */
	private void removeDataCommection( Runnable dc )
	{
		connections.remove( dc );
	}   

	/**
	 * 
	 * @param rtp
	 */
	private void removeServer( FTPServerInstance rtp )
	{
		servers.remove( rtp );
	}
}
