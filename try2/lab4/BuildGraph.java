/*
 * $Id: BuildGraph.java,v 1.1 2005/01/13 03:57:13 rwt5629 Exp rwt5629 $
 *
 * $Log: BuildGraph.java,v $
 * Revision 1.1  2005/01/13 03:57:13  rwt5629
 * Initial revision
 *
 *
 */

import java.io.*;
import java.util.*;

/**
 *
 * Build Graph.java
 *
 * @author    Ryan Tenney
 *
 */
public class BuildGraph {

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
            while ( keys.hasNext() ) {
                Object key = keys.next();
                System.out.println ( key + " (in degree == "
                    + graph.inDegree( key ) + ", out degree == "
                    + graph.outDegree( key ) + ")" );
                Iterator neighbors = graph.neighborKeys( key ).iterator();
                while ( neighbors.hasNext() ) {
                    Object neighborKey = neighbors.next();
                    System.out.println( "to " + neighborKey + " cost "
                        + graph.getEdgeData( key, neighborKey ) );
                }
            }
        } catch ( NoSuchVertexException nsvex ) {
            // CANNOT OCCUR
        }
    }
}
