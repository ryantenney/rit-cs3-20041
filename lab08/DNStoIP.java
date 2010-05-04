import java.net.*;

public class DNStoIP {

    public static void main( String[] args ) {

	for ( int i = 0; i < args.length; i++ ) {

	    try {
		System.out.println(  InetAddress.getByName( args[ i ] ) );
	    }
	    catch ( UnknownHostException uhex ) {
		System.out.println( args[ i ] + "/Invalid name" );
	    }
	}
    }
}
