import java.io.*;
import java.net.*;

public class QuoteClient {

    public static void main( String[] args ) {
	if ( args.length != 2 ) {
	    System.err.println( "Invalid arguments" );
	    System.exit( 1 );
	}

	try {
	    int port = Integer.parseInt( args[ 1 ] );
	    InetAddress server = InetAddress.getByName( args[ 0 ] );
	    byte[] buffer = new byte[ 1024 ];
	    DatagramSocket realtrueport = new DatagramSocket();
	    DatagramPacket data = new DatagramPacket(
			buffer, buffer.length, server, port);
	    realtrueport.send( data );
	    data = new DatagramPacket( buffer, buffer.length );
	    realtrueport.receive( data );
	    String received = new String( data.getData() );
	    System.out.println( received );
	}
	catch ( UnknownHostException uhex ) {
	    
	}
	catch ( SocketException sex ) {
	    
	}
	catch ( IOException ioex ) {
	    
	}
    }
}
