/**
 * FTPDataConnection.java
 */

import java.net.*;
import java.io.*;

/**
 * FTP Data Connection class transfers data on a socket other than the control
 */
public class FTPDataConnection implements Runnable
{
	public static final int DTP_SEND = 1;
	public static final int DTP_RECEIVE = 2;

	private boolean server;
	private boolean abort;

	private InetAddress host;
	private String filename;
	private int port;
	private int direction;

	public boolean done = false;

	private boolean hasData = false;
	private byte[] ltCmdrData = new byte[ 0 ];

	private Socket rtp;
	private ServerSocket listen;

	private FTPServer parent = null;

	public InputStream in = null;
	public OutputStream out = null;

	private Thread listenOrRead = new Thread( this );

	/**
	 * 
	 * @param port
	 * @param direction
	 */
	public FTPDataConnection( int port, int direction )
	{
		this.port = port;
		this.direction = direction;
		this.server = true;

		try {
			listen = new ServerSocket( port );
			listenOrRead.start();
		}
		catch ( IOException ioex ) {

		}
	}

	/**
	 * 
	 * @param host
	 * @param port
	 * @param direction
	 */
	public FTPDataConnection( String host, int port, int direction )
	{
		this.port = port;
		this.direction = direction;
		this.server = false;

		try {
			this.host = InetAddress.getByName( host );
			rtp = new Socket( host, port );
			listenOrRead.start();
		}
		catch ( UnknownHostException uhex ) { }
		catch ( IOException ioex ) { }
	}

	/**
	 * 
	 * @param PORT
	 * @param direction
	 */
	public FTPDataConnection( String PORT, int direction )
	{
		this.server = false;

		String[] a = PORT.split( "," );

		byte[] h = new byte[ 4 ];
		int p1, p2;

		for ( int i = 0; i < 4; i++ ) {
			h[ i ] = transformByte( a[ i ] );
		}
		p1 = Integer.parseInt( a[ 4 ] );
		p2 = Integer.parseInt( a[ 5 ] );

		try {
			this.host = InetAddress.getByAddress( h );
			this.port = ( p1 << 8 ) | p2;
			rtp = new Socket( host, port );
		}
		catch ( UnknownHostException uhex ) { }
		catch ( IOException ioex ) { }

		this.direction = direction;

		listenOrRead.start();
	}

	private byte transformByte( String byt )
	{
		int j = Integer.parseInt( byt ) % 0x100;
		if ( j > 0x80 ) {
			return (byte)( - 0x80 + ( j % 0x80 ) );
		}
		else {
			return (byte)j;
		}
	}

	/**
	 * 
	 * @param host
	 * @param port
	 * @param direction
	 */
	public FTPDataConnection( InetAddress host, int port, int direction )
	{
		this.server = false;
		this.host = host;
		this.port = port;
		this.direction = direction;

		try {
			rtp = new Socket( host, port );
		}
		catch ( IOException ioex ) { }

		listenOrRead.start();
	}

	/**
	 * 
	 * @return
	 */
	public boolean getActive()
	{
		//////////////////////////
		////// MUST CHANGE ///////
		//////////////////////////
		//        if ( rtp != null ) {
		//            if ( rtp. )
		//        }
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public int getPort()
	{
		if ( port != 0 ) {
			return port;
		}
		else {
			if ( server ) {
				return listen.getLocalPort();
			}
			else {
				return rtp.getLocalPort();
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getPORT()
	{
		try {
			byte[] localhost = InetAddress.getLocalHost().getAddress();
			int a[] = new int[ 6 ];
			String PORT = "";

			for ( int i = 0; i < 4; i++ ) {
				a[ i ] = ( localhost[ i ] + 0x100 ) % 0x100;
				PORT += a[ i ] + ",";
			}

			int port = getPort();
			a[ 4 ] = (int)( port / 0x100 );
			a[ 5 ] = (int)( port % 0x100 );

			PORT += a[ 4 ] + "," + a[ 5 ];

			return PORT;
		}
		catch ( UnknownHostException uhex ) {
			return "";
		}
	}

	/**
	 * 
	 *
	 */
	public void abort()
	{
		abort = true;
		listenOrRead.stop();
		listen = null;
		rtp = null;
		out = null;
		in = null;
	}

	/**
	 * 
	 * @param data
	 */
	public void send( byte[] data )
	{
		ltCmdrData = data;
		hasData = true;
	}

	/**
	 * 
	 * @param data
	 */
	public void sendAsync( byte[] data )
	{	
		try {
			in = new ByteArrayInputStream( data, 0, data.length );
			out = new BufferedOutputStream( rtp.getOutputStream() );
			byte[] stor = new byte[ 1024 ];
			int len = 0;
			while ( (len = in.read( stor, 0, 1024 )) > 0 ) {
				out.write( stor, 0, len );
				out.flush();
			}
			done = true;
			rtp.close();
		}
		catch ( SocketException sex ) {}
		catch ( IOException ioex ) {}
	}

	public void sendAsync( InputStream is )
	{
		try {
			out = new BufferedOutputStream( rtp.getOutputStream() );
			byte[] stor = new byte[ 1024 ];
			int len = 0;
			while ( (len = is.read( stor, 0, 1024 )) > 0 ) {
				System.out.println( len );
				out.write( stor, 0, len );
				out.flush();
			}
			done = true;
			rtp.close();
		}
		catch ( SocketException sex ) {}
		catch ( IOException ioex ) {}
	}

	/**
	 * 
	 * 
	 */
	public byte[] receive()
	{
		return ltCmdrData;
	}

	/**
	 * 
	 */
	public byte[] receiveAsync()
	{
		return ltCmdrData;
	}

	/**
	 * 
	 */
	public void run()
	{
		if ( server ) {
			try {
				rtp = listen.accept();
				listen.close();
				listen = null;
			}
			catch ( IOException ioex ) { }
		}

		if ( direction == DTP_SEND ) {
			try {
				int slept = 0;
				while ( !hasData && slept < 1000 ) {
					Thread.sleep( 100 );
					slept += 100;
				}
				if ( !hasData ) {
					return;
				}
			}
			catch ( InterruptedException iex ) { }

			try {
				in = new BufferedInputStream( new ByteArrayInputStream( ltCmdrData ) );
				out = new BufferedOutputStream( rtp.getOutputStream() );
				byte[] stor = new byte[ 1024 ];
				int len = -1;
				while ( (len = in.read( stor, 0, 1024 )) > 0 ) {
					out.write( stor, 0, len );
					out.flush();
				}
				done = true;
				rtp.close();
			}
			catch ( IOException ioex ) { }
		}
		else if ( direction == DTP_RECEIVE ) {
			try {
				if ( out == null ) {
					out = new ByteArrayOutputStream(); 
				}

				in = new BufferedInputStream( rtp.getInputStream() );
				byte[] stor = new byte[ 1024 ];
				int len = -1;
				len = in.read( stor, 0, 1024 );
				while ( stor != null ) {
					System.out.println( "read-retr " + len );
					out.write( stor, 0, len );
					out.flush();
					len = in.read( stor, 0, 1024 );
				}

				if ( out instanceof ByteArrayOutputStream ) {
					ltCmdrData = ((ByteArrayOutputStream)out).toByteArray();
				}
				done = true;
				rtp.close();
			}
			catch ( IOException ioex ) { }
		}

		rtp = null;
		parent = null;
		in = null;
		out = null;
	}
}
