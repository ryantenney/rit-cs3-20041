/*
 * $Id: GraphColorer.java,v 1.3 2005/01/08 21:22:09 rwt5629 Exp rwt5629 $
 *
 * $Log: GraphColorer.java,v $
 * Revision 1.3  2005/01/08 21:22:09  rwt5629
 * GRRRR TRY. Actually grrr me... Didn't read the specifications fully and outputted the colors incorrectly.
 *
 * Revision 1.2  2005/01/08 21:18:57  rwt5629
 * Try didn't like line 68... grrr.
 *
 * Revision 1.1  2005/01/08 21:16:49  rwt5629
 * Initial revision
 *
 *
 */

import java.util.*;

/**
 * GraphColorer.java
 *
 * @author    Ryan Tenney
 *
 */
public class GraphColorer {

    private DiGraph graph = null;
    private int numColors = 0;

    /**
     *
     *
     */
    public GraphColorer( DiGraph graph ) {
        this.graph = graph;
    }

    /**
     * 
     *
     */
    public void color() {
        Iterator keys = graph.vertexKeys().iterator();
        while ( keys.hasNext() ) {
            graph.addVertex( keys.next(), new Integer( 0 ) );
        }
        keys = null;

        keys = graph.vertexKeys().iterator();
        int nextColor = 1;
        while ( keys.hasNext() ) {
            int c = 1;
            Object vertex = keys.next();
            boolean same = false;

            // tries to paint vertex with all possible colors,
            // if the max color doesn't work, it is painted with nextColor
            while ( c < nextColor ) {
                Iterator neighbors = null;
                try {
                    // gets iterator for data contained in the neighboring
                    // vertices
                    neighbors = 
                        graph.neighborData( vertex ).iterator();
                } catch ( NoSuchVertexException nsvex ) {
                    //cannot occur
                    System.err.println( "Flagrant System Error\r\n" +
                        "NoSuchVertexException" );
                }
                
                same = false;
                while ( neighbors.hasNext() ) {
                    Integer next = (Integer)neighbors.next();
                    same |= ( c == next.intValue() );
                }

                if ( same ) {
                    c++;
                } else {
                    break;
                }
            }

            if ( c == nextColor ) nextColor++;
            
            graph.addVertex( vertex, new Integer( c ) );
        }
        numColors = nextColor - 1;
    }

    /**
     * 
     * 
     */
    public void displayColors() {
        try {
            Iterator keys = graph.vertexKeys().iterator();
            while ( keys.hasNext() ) {
                String key = (String)keys.next();
                Integer data = (Integer)graph.getVertexData( key );
                System.out.println( "Color of " + key + " is "
                    + data.intValue() + "." );
            }
        } catch ( NoSuchVertexException nsvex ) {
            // cannot occur
            System.out.println( "NSVEX" );
        }
    }

    /**
     * 
     * 
     * @return 
     */
    public int getNumColors() {
        return numColors;
    }

}
