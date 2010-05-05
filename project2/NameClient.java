/**
 * NameClient.java
 */

import java.util.*;
import java.net.*;
import java.io.*;

/**
 * NameClient is a class that communicates with the P2PFTP name server
 *
 * @author: Ryan Tenney
 *
 */
public class NameClient extends Thread
{
	/**
	 * Thread that continually renews the NameClient registration
	 */
	private class RenewThread extends Thread
	{
		private NameClient toRenew;

		/**
		 * Default constructor that includes the NameClient instance
		 * to renew on a regular basis
		 */
		public RenewThread( NameClient toRenew )
		{
			this.toRenew = toRenew;
		}

		/**
		 * Method from Thread class executed when .start() is called
		 */
		public void run()
		{
			while ( toRenew.getEnabled() ) {
				try {
					if ( toRenew.registered ) {
						// System.out.println("registered");
						Thread.sleep( toRenew.getTime() - 200 );
						toRenew.renew();
					}
					else {
						// System.out.println("!registered");
						Thread.sleep( 50 );
					}
				}
				catch ( InterruptedException iex ) {
					toRenew.registered = false;
				}
			}
		}
	}

	/**
	 * ServerEntry class holds information regarding the servers retrieved
	 * from the NameServer
	 */
	public class ServerEntry
	{
		public String name;
		public String host;
		public int port;

		/**
		 * Sole constructor, stores all information regarding the server
		 */
		public ServerEntry( String name, String host, int port )
		{
			this.name = name;
			this.host = host;
			this.port = port;
		}

		/**
		 * Returns the workspace name associated with the server
		 */
		public String toString()
		{
			return this.name;
			// return this.name + " \\" + this.host + ":" + this.port;
		}
	}

	public static final byte REGISTER = 0;
	public static final byte TICKET = 1;
	public static final byte RENEW = 2;
	public static final byte QUERY = 3;
	public static final byte REPLY = 4;
	public static final byte UPDATE = 5;

	public boolean registered = false;

	private int ticket = 0;
	private int time = 0;
	private int lastSerial = 0;

	private String host = "localhost";
	private InetAddress server;
	private int remotePort = 0;
	private String workspace = "Default FTP Server";
	private int localPort = 0;
	private int udpPort = 0;
	private boolean enabled = false;

	private byte[] bufferIn = new byte[ 8192 ];
	private byte[] bufferOut = new byte[ 0 ];

	private RenewThread renewal = new RenewThread( this );
	private ServerEntry[] servers = new ServerEntry[ 0 ];

	private DatagramSocket rtp = null;
	private DatagramPacket data = null;

	private ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	private DataOutputStream dataOut = new DataOutputStream( byteOut );

	/**
	 * constructor that passes partial connection information for the
	 * nameserver
	 *
	 * @param     nameServer     hostname of the nameserver
	 * @param     remotePort     port that the nameserver is running on
	 * @param     udpPort        port used for udp communication
	 */
	public NameClient( String nameServer, int remotePort, int udpPort )
	{
		try {
			this.host = nameServer;
			this.server = InetAddress.getByName( nameServer );
			this.remotePort = remotePort;
			this.udpPort = udpPort;

			rtp = new DatagramSocket( udpPort );

			this.start();
		}
		catch ( SocketException sex ) {

		}
		catch ( UnknownHostException uhex ) {

		}
	}

	/**
	 * Returns ticket number registered with the nameserver
	 */
	public int getTicket()
	{
		return ticket;
	}

	/**
	 * Returns the amount of time the ticket will remain valid
	 */
	public int getTime()
	{
		return time;
	}

	/**
	 * Returns the last serial number issued in the update
	 */
	public int getLastSerial()
	{
		return lastSerial;
	}

	/**
	 * Returns the server entries returned during the last update
	 */
	public ServerEntry[] getServers()
	{
		return servers;
	}

	/**
	 * 
	 * @param workspace
	 * @return
	 */
	public ServerEntry getByName( String workspace )
	{
		for ( int i = 0; i < servers.length; i++ ) {
			if ( workspace.equalsIgnoreCase( servers[ i ].name ) ) {
				return servers[ i ];
			}
		}
		return null;
	}

	/**
	 * returns whether the renew thread is enabled
	 * 
	 * @return    returns whether the renew thread is enabled
	 */
	public boolean getEnabled()
	{
		return this.enabled;
	}

	/**
	 * Starts the thread that manages ticket renewal
	 */
	public void startRenewThread()
	{
		this.enabled = true;
		renewal.start();
	}

	/**
	 * Aborts the thread that manages ticket renewal
	 */
	public void stopRenewThread()
	{
		this.enabled = false;
		renewal.stop();
	}

	/**
	 * Clears the outgoing buffers
	 */
	private void resetOutBuffers()
	{
		byteOut.reset();
		bufferOut = new byte[ 0 ];
	}

	/**
	 * This class implements runnable, so when this.start() is called, the
	 * code in this method is executed. It engages a while loop that
	 * continually checks for new data on the UDP socket.
	 */
	public void run()
	{
		while ( true ) {
			bufferIn = receive();
			proc( bufferIn );
		}
	}

	/**
	 * sends a REGISTER packet to register the client information in the
	 * nameserver
	 *
	 * @param    name    name of the workspace being shared
	 * @param    port    port that the FTP server is running on
	 */
	public void register( String workspace, int localPort )
	{
		this.workspace = workspace;
		this.localPort = localPort;

		try {
			resetOutBuffers();
			dataOut.writeByte( NameClient.REGISTER );
			dataOut.writeUTF( workspace );
			dataOut.writeInt( localPort );
			bufferOut = byteOut.toByteArray();

			send( bufferOut );
		}
		catch ( IOException ioex ) {

		}
	}

	/**
	 * sends a RENEW packet to renew a NameServer ticket
	 */
	public void renew()
	{
		try {
			resetOutBuffers();
			dataOut.writeByte( NameClient.RENEW );
			dataOut.writeInt( this.ticket );
			bufferOut = byteOut.toByteArray();

			send( bufferOut );
		}
		catch ( IOException ioex ) {

		}
	}

	/**
	 * sends a QUERY packet to obtain registered names
	 *
	 * @param    query    string query to filter the registered names   
	 */
	public void query( String query )
	{
		try {
			resetOutBuffers();
			dataOut.writeByte( NameClient.QUERY );
			dataOut.writeUTF( query );
			bufferOut = byteOut.toByteArray();

			send( bufferOut );
		}
		catch ( IOException ioex ) {

		}
	}

	/**
	 * sends a DatagramPacket via UDP to the name server.
	 *
	 * @param    send    byte data of the DatagramPacket to send
	 */
	private void send( byte[] send )
	{
		try {
			data = new DatagramPacket( 
					send, send.length, this.server, this.remotePort );
			rtp.send( data );
		}
		catch ( SocketException sex ) {

		}
		catch ( IOException ioex ) {

		}
	}

	/**
	 * receives a packet 
	 */
	private byte[] receive()
	{
		DatagramPacket dataIn = null;
		bufferIn = new byte[ 8192 ];

		try {
			dataIn = new DatagramPacket( bufferIn, bufferIn.length );
			rtp.receive( dataIn );
			return dataIn.getData();
		}
		catch ( SocketException sex ) {
			return new byte[ 0 ];
		}
		catch ( IOException ioex ) {
			return new byte[ 0 ];
		}
		catch ( NullPointerException npex )
		{
			return new byte[ 0 ];
		}
	}

	//    /**
	//     * Called when a ticket packet is returned with a negative ticket number
	//     */
	//    private void expired()
	//    {
	//        
	//    }

	/**
	 * processes the reply from the UDP name server
	 *
	 * @param    reply    byte data of the server reply
	 */
	private void proc( byte[] reply )
	{
		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream( reply );
			DataInputStream dataIn = new DataInputStream( byteIn );

			byte type = dataIn.readByte();

			if ( type == NameClient.TICKET ) {
				// System.out.println( "TICKET" );
				this.ticket = dataIn.readInt();
				this.time = dataIn.readInt();
				if ( this.ticket > 0 ) {
					this.registered = true;
				}
				else {
					this.registered = false;
					// attempt to reregister
					this.register( this.workspace, this.localPort );
				}
			}
			else if ( type == NameClient.REPLY ) {
				// System.out.println( "REPLY" );
				int entries = dataIn.readInt();
				List servers = new ArrayList();
				for ( int i = 0; i < entries; i++ ) {
					servers.add( new ServerEntry( dataIn.readUTF(),
								dataIn.readUTF(), dataIn.readInt() ) );
				}
				this.servers = (ServerEntry[])servers.toArray();
			}
			else if ( type == NameClient.UPDATE ) {
				// System.out.println( "UPDATE" );
				this.lastSerial = dataIn.readInt();
				int entries = dataIn.readInt();
				List servers = new ArrayList();
				for ( int i = 0; i < entries; i++ ) {
					servers.add( new ServerEntry( dataIn.readUTF(),
								dataIn.readUTF(), dataIn.readInt() ) );
				}
				this.servers = ( ServerEntry[] )servers.toArray( this.servers );
			}
		}
		catch ( IOException ioex ) {
			// System.out.println( ioex );
		}
	}    
}
