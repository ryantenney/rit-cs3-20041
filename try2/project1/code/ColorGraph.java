// $Id: ColorGraph.java,v 1.1 2004/03/23 20:42:17 cs3 Exp rwt5629 $
//
// Revisions:
//	$Log: ColorGraph.java,v $
//	Revision 1.1  2004/03/23 20:42:17  cs3
//	Initial revision
//
public class ColorGraph {


   /**
    * The graph to be built and colored.
    *
    * CHANGE THE FOLLOWING LINE to test your own graph class!
    */
   private static DiGraph graph = new ArrayDiGraph();

   /**
    * Create a graph, color it, and print out the results.
    */
   public static void main( String[] args ) {
      if ( args.length != 0 ) {
         oldInit();
      }
      else {
         init();
      }

      GraphColorer colorer = new GraphColorer( graph );
      colorer.color();
      System.out.println( "This graph took " +
        colorer.getNumColors() + " colors." );
      colorer.displayColors();
   }

   /**
    * Set up the graph by reading simple lines from standard input.
    * A single-character line is a new vertex name (key).
    * A pair of characters is an edge specification.
    * Any other line is considered a comment and printed, except
    * blank lines are not printed.
    */
   private static void init() {

      java.io.BufferedReader in = new java.io.BufferedReader(
                            new java.io.InputStreamReader( System.in ) );

      String input = null;

      try {
          while ( ( input = in.readLine() ) != null ) {

            // Vertex lines consist of a single character
            switch ( input.length() ) {
            case 1:
               // Add the vertex -- no data to associate
               graph.addVertex( input.substring( 0, 1 ), null );
               break;
            case 2:
               // Add the edge -- no data again
               graph.addEdge( input.substring( 0, 1 ),
                              input.substring( 1 ), null );
               break;
            case 0:
               break;
            default:
               // Any other line is a comment.
               // By convention, they start with "# ".
               System.out.println( input );
            }
         }
      }
      catch ( NoSuchVertexException e ) {
          System.err.println( "Invalid input" );
          System.exit( 1 );
      }
      catch ( java.io.IOException e ) {
          System.err.println( e );
          System.exit( 1 );
      }
   }

   /**
    * Create a graph in a diamond pattern. It should require
    * two colors. This method is no longer called in the standard
    * system. Use it as an example from which you can write your
    * own tests. However, the input method is probably all you
    * will need.
    */
   private static void oldInit() {

      String[] NodeKeys = { "A", "B", "C", "D" };
      int NumNodes = NodeKeys.length;

      for ( int i=0; i < NumNodes; ++i ) {
         graph.addVertex( NodeKeys[i], new Integer( 0 ) );
      }

      try {
         graph.addEdge( NodeKeys[0], NodeKeys[1], null );
         graph.addEdge( NodeKeys[1], NodeKeys[0], null );
         graph.addEdge( NodeKeys[0], NodeKeys[2], null );
         graph.addEdge( NodeKeys[2], NodeKeys[0], null );
         graph.addEdge( NodeKeys[1], NodeKeys[3], null );
         graph.addEdge( NodeKeys[3], NodeKeys[1], null );
         graph.addEdge( NodeKeys[2], NodeKeys[3], null );
         graph.addEdge( NodeKeys[3], NodeKeys[2], null );
      }
      catch( NoSuchVertexException nsve ) {
         assert false : "Internal error (node keys)";
         // Must compile with "-source 1.4" option to get this to compile.
         // http://www.cs.rit.edu/usr/local/jdk/docs/guide/lang/assert.html
      }
   }

}
