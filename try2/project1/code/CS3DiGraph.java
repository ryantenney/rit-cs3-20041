/*
 * CS3DiGraph.java
 *
 * $Id: CS3DiGraph.java,v 1.4 2005/01/24 04:57:14 rwt5629 Exp rwt5629 $
 * 
 * $Log: CS3DiGraph.java,v $
 * Revision 1.4  2005/01/24 04:57:14  rwt5629
 * It would seem that I did not read the requiremtents too well... guess 
 * its getting submitted late.
 *
 * Revision 1.2  2005/01/24 04:46:46  rwt5629
 * yay i'm done
 *
 * Revision 1.1  2005/01/24 00:35:52  rwt5629
 * Initial revision
 *
 *
 */

import java.util.*;

/**
 * CS3DiGraph
 * 
 * @author    Ryan Tenney
 */
public class CS3DiGraph implements DiGraph {

    private Map edges, vertices;

    /**
     * Create a new CS3DiGraph
     */
    public CS3DiGraph() {
        edges = new HashMap();
        vertices = new HashMap();
    }

    public void addVertex( Object key, Object data ) {
        vertices.put( key, data );
    }

    public boolean isVertex( Object key ) {
        return vertices.containsKey( key );
    }

    public Object getVertexData( Object key ) 
            throws NoSuchVertexException {
        if ( vertices.containsKey( key ) ) {
            return vertices.get( key );
        } else {
            throw new NoSuchVertexException( "No Such Vetex" );
        }
    }

    public void addEdge( Object fromKey, Object toKey, Object data ) 
            throws NoSuchVertexException {
        String key = objectConcat( fromKey, toKey );
        if ( vertices.containsKey( fromKey ) &&
             vertices.containsKey( toKey) ) {
            edges.put( key, data );
        } else {
            throw new NoSuchVertexException( "No Such Vertex" );
        }
    }

    public boolean isEdge( Object fromKey, Object toKey ) 
            throws NoSuchVertexException {
        if ( vertices.containsKey( fromKey ) &&
             vertices.containsKey( toKey) ) {
            String key = objectConcat( fromKey, toKey );
            return edges.containsKey( key );
        } else {
            throw new NoSuchVertexException( "No Such Vertex" );
        }
    }

    public Object getEdgeData( Object fromKey, Object toKey )
            throws NoSuchVertexException {
        if ( vertices.containsKey( fromKey ) &&
             vertices.containsKey( toKey) ) {
            String key = objectConcat( fromKey, toKey );
            return edges.get( key );
        } else {
            throw new NoSuchVertexException( "No Such Vertex" );
        }        
    }

    public int numVertices() {
        return vertices.size();
    }

    public int inDegree( Object key ) {
        if ( !isVertex( key ) ) {
            return -1;
        }
        String thisKey = (String)key;
        Iterator keys = edges.keySet().iterator();
        int count = 0;
        while ( keys.hasNext() ) {
            String iter = (String)keys.next();
            if ( iter.endsWith( thisKey ) ) count++;
        }
        return count;
    }

    public int outDegree( Object key ) {
        if ( !isVertex( key ) ) {
            return -1;
        }
        String thisKey = (String)key;
        Iterator keys = edges.keySet().iterator();
        int count = 0;
        while ( keys.hasNext() ) {
            String iter = (String)keys.next();
            if ( iter.startsWith( thisKey ) ) count++;
        }
        return count;        
    }

    public Collection neighborData( Object key )
           throws NoSuchVertexException {
        if ( !isVertex( key ) ) {
            throw new NoSuchVertexException( "No Such Vertex" );
        }
        String thisKey = (String)key;
        Iterator keys = edges.keySet().iterator();
        Collection retobj = new ArrayList();
        while ( keys.hasNext() ) {
            String iter = (String)keys.next();
            if ( iter.startsWith( thisKey ) ) {
                String neighbor = Character.toString( iter.charAt( 1 ) );
                retobj.add( vertices.get( neighbor ) );
            }
        }
        return retobj;
    }

    public Collection neighborKeys( Object key ) 
           throws NoSuchVertexException {
        if ( !isVertex( key ) ) {
            throw new NoSuchVertexException( "No Such Vertex" );
        }
        String thisKey = (String)key;
        Iterator keys = edges.keySet().iterator();
        Collection retobj = new ArrayList();
        while ( keys.hasNext() ) {
            String iter = (String)keys.next();
            if ( iter.startsWith( thisKey ) ) {
                String neighbor = Character.toString( iter.charAt( 1 ) );
                retobj.add( neighbor );
            }
        }
        return retobj;
    }

    public Collection vertexData() {
        return vertices.values();
    }

    public Collection vertexKeys() {
        return vertices.keySet();
    }

    public Collection edgeData() {
        return edges.values();
    }

    public void clear() {
        vertices.clear();
        edges.clear();
    }

    public String toString() {
        return "Not Yet Implemented";
    }

    /**
     * Object concatenator, takes two Objects that are assumed to be
     * strings, and concatenates the strings.
     */
    private String objectConcat( Object object1, Object object2 ) {
        if ( object1 instanceof String && object2 instanceof String ) {
            return (String)object1 + (String)object2;
        } else {
            return "";
        }
    }

    public static void main( String args[] ) {
        CS3DiGraph graph = new CS3DiGraph();

        try {
            graph.addVertex( "a", new Integer( 10 ) );
            graph.addVertex( "b", new Integer( 3 ) );
            graph.addVertex( "c", new Integer( 9 ) );
            graph.addVertex( "d", new Integer( 19 ) );
            graph.addVertex( "e", new Integer( 2 ) );
            graph.addEdge( "a", "b", new Integer( 4 ) );
            graph.addEdge( "b", "c", new Integer( 7 ) );
            graph.addEdge( "c", "d", new Integer( 5 ) );
            graph.addEdge( "d", "a", new Integer( 1 ) );
            graph.addEdge( "a", "c", new Integer( 11 ) );
            graph.addEdge( "d", "e", new Integer( 3 ) );
        } catch ( NoSuchVertexException nsvex ) {
            System.err.println( "Something went wrong in the hardcode" );
        }
    }
}
