/**
 * Manager.java
 */

import java.io.*;
import java.util.*;

/**
 * Manages servers, nameclient and server connections
 */
public class Manager
{
	private static final String username = "343guiltySpark";
	private static final String password = "rwt5629@rit.edu";

	private String nameServer, workspace, root;
	private int remotePort, localPort, udpPort;
	private boolean running;

	private NameClient nc;
	private FTPServer serv;

	private Map clients = new HashMap();

	public File shareRoot;

	/**
	 * default constructor that contains all neccesary
	 * parameters for normal operation
	 * 
	 * @param nameServer host name of the name server
	 * @param remotePort port number on which the nameserver's
	 * 		 udp socket is running
	 * @param udpPort local udp port number
	 * @param workspace display name for the file share
	 * @param root directory shared
	 * @param localPort local port number for the ftp server
	 * @param start start the server upon construction
	 */
	public Manager( String nameServer, int remotePort, int udpPort,
			String workspace, String root, int localPort, boolean start )
	{
		this.nameServer = nameServer;
		this.remotePort = remotePort;
		this.udpPort = udpPort;
		this.workspace = workspace;
		this.root = root;
		this.localPort = localPort;

		this.shareRoot = new File( root );

		serv = new FTPServer( localPort, root, false );
		nc = new NameClient( nameServer, remotePort, udpPort );

		if ( start ) {
			nc.register( workspace, localPort );
			nc.startRenewThread();
		}
		running = start;
	}

	/**
	 *
	 *
	 * @return
	 */
	public NameClient getNameClient()
	{
		return nc;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public FTPServer getFTP()
	{
		return serv;
	}

	/**
	 *
	 *
	 * @return
	 */
	public String getNameServer()
	{
		return nameServer;
	}

	/**
	 *
	 *
	 * @param value
	 */
	public void setNameServer( String value )
	{
		nameServer = value;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String getWorkspace()
	{
		return workspace;
	}

	/**
	 *
	 * @param value
	 */
	public void setWorkspace( String value )
	{
		workspace = value;
	}

	/**
	 *
	 *
	 * @return
	 */
	public String getRoot()
	{
		return root;
	}

	/**
	 * 
	 * 
	 * @param value
	 */
	public void setRoot( String value )
	{
		root = value;
		serv.setRoot( value );
	}

	/**
	 *
	 */
	public int getRemotePort()
	{
		return remotePort;
	}

	/**
	 * 
	 * 
	 * @param value
	 */
	public void setRemotePort( int value )
	{
		remotePort = value;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public int getLocalPort()
	{
		return localPort;
	}

	/**
	 * 
	 * 
	 * @param value
	 */
	public void setLocalPort( int value )
	{
		localPort = value;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public int getUdpPort()
	{
		return udpPort;
	}

	/**
	 * 
	 * 
	 * @param value
	 */
	public void setUdpPort( int value )
	{
		udpPort = value;
	}

	/**
	 *
	 */
	public void ftpServerStart()
	{
		serv.start();
	}

	/**
	 *
	 */
	public void ftpServerStop()
	{
		serv.stop();
	}

	/**
	 *
	 */
	public void start()
	{
		nc.register( workspace, localPort );
		nc.startRenewThread();
	}

	/**
	 *
	 */
	public void shutdown()
	{
		ftpServerStop();
	}

	/**
	 * 
	 * 
	 * @param workspace
	 * @return
	 */
	public FTPClient addClient( String workspace )
	{
		NameClient.ServerEntry se = nc.getByName( workspace );
		return addClient( workspace, se.host, se.port );
	}

	/**
	 * 
	 * 
	 * @param workspace
	 * @param host
	 * @param port
	 * @return
	 */
	public FTPClient addClient( String workspace, String host, int port )
	{
		FTPClient temp = new FTPClient( host, port, true, workspace, this );
		temp.login( username, password );
		clients.put( workspace, temp );
		return temp;
	}

	/**
	 * 
	 * @param client
	 */
	public void removeClient( FTPClient client )
	{
		FTPClient remThis = getClient( client.getWorkspace() );
		remThis.disconnect();
		clients.remove( client.getWorkspace() );
	}  

	/**
	 * 
	 * @param workspace
	 */
	public void removeClient( String workspace )
	{
		clients.remove( workspace );
	}

	/**
	 * 
	 * 
	 * @param workspace
	 * @return
	 */
	public FTPClient getClient( String workspace )
	{
		if ( !clients.containsKey( workspace ) ) {
			return addClient( workspace );
		}
		else {
			Object pull = clients.get( workspace ); 
			if ( pull instanceof FTPClient ) {
				return (FTPClient)pull;
			}
			else {
				return null;
			}
		}
	}

	public void setClient( String workspace, FTPClient client )
	{

	}
}
