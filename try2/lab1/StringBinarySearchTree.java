/*
 * StringBinarySearchTree.java
 *
 * Version:
 *    $Id: StringBinarySearchTree.java,v 1.1 2004/06/10 22:59:03 cs3 Exp $
 *
 * Revisions:
 *    $Log: StringBinarySearchTree.java,v $
 *    Revision 1.1  2004/06/10 22:59:03  cs3
 *    Initial revision
 *
 */

/**
 * This class can be used to build binary search trees whose
 * nodes contain strings.  The main() method in this class can
 * be used to test the basic functionality of the class.
 *
 * @author     Hans-Peter Bischof
 * @author     Paul Tymann
 */

public class StringBinarySearchTree {

    // Class attributes

    private Node data;                      // The data in this node
    private StringBinarySearchTree left;    // The left subtree
    private StringBinarySearchTree right;   // The right subtree

    /**
     * Create an empty binary search tree.
     */

    public StringBinarySearchTree() {
	this( null );
    }

    /**
     * Create a binary search tree that contains only the
     * data passed as an argument.
     *
     * @param    data     the data to be placed in the tree.
     */

    public StringBinarySearchTree( Node data ) {
        this.data = data;
        this.left = this.right = null;
    }

    /**
     * Insert a node into the binary search tree.
     *
     * @param    aNode    the node to be inserted into the tree.
     */

    public void insert( Node aNode ) {
        if ( aNode.isLess( data ) ) {
	    if ( left == null ) {
		left = new StringBinarySearchTree( aNode );
            }
	    else {
		left.insert( aNode );
            }
        } 
	else {
	    if ( right == null ) {
		right = new StringBinarySearchTree( aNode );
            }
	    else {
		right.insert( aNode );
            }
        }
    }

    /**
     * Find the given node in the binary search tree.  Returns a
     * reference to the node if it is found and null otherwise.
     *
     * @param    aNode    the node to search the tree for
     *
     * @return   a reference to the node found or null if the
     *           node is not in the tree.
     */

    public StringBinarySearchTree find( Node aNode ) {
        StringBinarySearchTree retVal = null;

        if ( aNode.isEqual( data ) ) {
            retVal = this;
        }
        else if ( this.left != null && aNode.isLess( data ) ) {
	    retVal = left.find( aNode );
        }
        else if ( this.right != null && aNode.isGreater( data ) ) {
	    retVal = right.find( aNode );
        }

        return retVal;
    }

    /**
     * Print an inorder (LVR) traversal of this tree.
     */
  
    public void printInorder() {
        if ( left != null )  {
            left.printInorder();
        }

        System.out.print( data.toString() + " " );

        if ( right != null ) {
            right.printInorder();
        }
    }

    /**
     * Print a postorder (LRV) traversal of this tree.
     */

    public void printPostOrder() {
        if ( left != null )  {
            left.printPostOrder();
        }

        if ( right != null )  {
            right.printPostOrder();
        }

        System.out.print( data.toString() + " " );
    }

    /**
     * Print a preorder (VLR) traversal of this tree.
     */

    public void printPreOrder() {
        System.out.print( data.toString() + " " );

        if ( left != null )  {
            left.printPreOrder();
        }

        if ( right != null )  {
            right.printPreOrder();
        }
    }

    // Some code to do a quick sanity test of this class.

    /**
     * This method performs a quick sanity check of this class.
     *
     * @param args  the command line arguments (ignored).
     */

    public static void main( String args[] ) {

        StringBinarySearchTree aBS = 
            new StringBinarySearchTree( new StringNode( "d" ) );

	// Create a little tree

        aBS.insert( new StringNode( "b" ) );  //            d
        aBS.insert( new StringNode( "a" ) );  //      b          f
        aBS.insert( new StringNode( "c" ) );  //    a   c      e   g
        aBS.insert( new StringNode( "f" ) );  //
        aBS.insert( new StringNode( "e" ) );  //
        aBS.insert( new StringNode( "g" ) );  //

	// Try the traversals

	// Inorder output should be:  a b c d e f g

        System.out.print( "Inorder:        " );
        aBS.printInorder();
        System.out.println();

	// Postorder output should be:  a c b e g f d
  
        System.out.print( "Postorder:      " );
        aBS.printPostOrder();
        System.out.println();

	// Preorder output should be:  d b a c f e g

        System.out.print( "Preorder:       " );
        aBS.printPreOrder();
        System.out.println();

	// Try looking for some stuff

        if ( aBS.find( new StringNode( "d" ) ) != null )
                System.out.println( "found d" );
        if ( aBS.find( new StringNode( "a" ) ) != null )
                System.out.println( "found a" );
        if ( aBS.find( new StringNode( "g" ) ) != null )
                System.out.println( "found g" );
        if ( aBS.find( new StringNode( "x" ) ) != null )
                System.out.println( "found x" );
  }

} // StringBinarySearchTree
