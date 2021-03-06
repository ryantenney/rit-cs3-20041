/*
 * $Id: BFS.java,v 1.1 2005/01/13 04:59:22 rwt5629 Exp rwt5629 $
 *
 * $Log: BFS.java,v $
 * Revision 1.1  2005/01/13 04:59:22  rwt5629
 * Initial revision
 *
 *
 */

import java.io.*;
import java.util.*;

/**
 *
 * BFS.java
 *
 * @author    Ryan Tenney
 *
 */
public class BFS {

    public static void main( String args[] ) {
        
        ArrayDiGraph graph = new ArrayDiGraph();

        BufferedReader stdin = new BufferedReader(
                new InputStreamReader( System.in ));
        String line = null;

        try {
	    line = stdin.readLine();
            do {
                if ( line.length() == 1 ) {
                    graph.addVertex( line, null );
                } else if ( line.length() >= 3 ) {
                    graph.addEdge( line.substring( 0, 1 ),
                        line.substring( 1, 2 ), line.substring( 2 ) );
                }
                line = stdin.readLine();
            } while ( line != null );
        } catch ( IOException ioex ) {
            System.err.println( "Flagrant System Error:\r\n " +
                "Comptuer Over.\r\n IOException = Very Yes" );
        } catch ( NoSuchVertexException nsvex ) {
            System.err.println( "Flagrant System Error: No Such Vertex" );
        }

        try {
            Iterator keys = graph.vertexKeys().iterator();
            List queue = new ArrayList();
            queue.add( 0, keys.next() );

            while ( queue.size() > 0 ) {
                Object key = queue.get( 0 );
                queue.remove( 0 );
                Object data = graph.getVertexData( key );
                if ( data != "V" ) {
                    System.out.println( key );
                    graph.addVertex( key, "V" );
                    Iterator neighbors = 
                        graph.neighborKeys( key ).iterator();
                    while ( neighbors.hasNext() ) {
                        Object neighbor = neighbors.next();
                        if ( graph.getVertexData( neighbor ) != "V" ) {
                            queue.add( neighbor );
                        }                        
                    }
                }
            }
        } catch ( NoSuchVertexException nsvex ) {
            // CANNOT OCCUR
        }
    }
}
