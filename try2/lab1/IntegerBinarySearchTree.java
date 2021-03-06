/*
 * IntegerBinarySearchTree.java
 *
 * Version:
 *    $Id: IntegerBinarySearchTree.java,v 1.1 2004/06/10 22:59:03 cs3 Exp $
 *
 * Revisions:
 *    $Log: IntegerBinarySearchTree.java,v $
 *    Revision 1.1  2004/06/10 22:59:03  cs3
 *    Initial revision
 *
 */

/**
 * A binary search tree that holds integer values.
 *
 * @author     Hans-Peter Bischof
 * @author     Paul Tymann
 */

public class IntegerBinarySearchTree {

    // Class attributes

    private Node data;                      // The data in this node
    private IntegerBinarySearchTree left;   // The left subtree
    private IntegerBinarySearchTree right;  // The right subtree

    /**
     * Create an empty binary search tree.
     */

    public IntegerBinarySearchTree() {
	this( null );
    }

    /**
     * Create a binary search tree that contains only the
     * data passed as an argument.
     *
     * @param    data     the data to be placed in the tree.
     */

    public IntegerBinarySearchTree( Node data ) {
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
		left = new IntegerBinarySearchTree( aNode );
            }
	    else {
		left.insert( aNode );
            }
        } 
	else {
	    if ( right == null ) {
		right = new IntegerBinarySearchTree( aNode );
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

    public IntegerBinarySearchTree find( Node aNode ) {
        IntegerBinarySearchTree retVal = null;

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
        if ( left != null ) {
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
        if ( left != null ) {
            left.printPostOrder();
        }

        if ( right != null ) {
            right.printPostOrder();
        }

        System.out.print( data.toString() + " " );
    }

    /**
     * Print a preorder (VLR) traversal of this tree.
     */

    public void printPreOrder() {
        System.out.print( data.toString() + " " );

        if ( left != null ) {
            left.printPreOrder();
        }

        if ( right != null ) {
            right.printPreOrder();
        }
    }

    /**
     * This method does a quick sanity check of this class.
     *
     * @param args the command line arguments (ignored).
     */

    public static void main( String args[] )  {

        IntegerBinarySearchTree aBS = 
            new IntegerBinarySearchTree( new IntegerNode( 4 ) );

	// Create a little tree

        aBS.insert( new IntegerNode( 2 ) );
        aBS.insert( new IntegerNode( 1 ) );
        aBS.insert( new IntegerNode( 3 ) );
        aBS.insert( new IntegerNode( 5 ) );
        aBS.insert( new IntegerNode( 6 ) );

	// Try the traversals

        System.out.print( "Inorder:\t" );
        aBS.printInorder();
        System.out.println();

        System.out.print( "Postorder:\t" );
        aBS.printPostOrder();
        System.out.println();

        System.out.print( "Preorder:\t" );
        aBS.printPreOrder();
        System.out.println();

        // Look for some stuff

        if ( aBS.find( new IntegerNode( 3 ) ) != null )
                System.out.println( "found 3" );
        if ( aBS.find( new IntegerNode( 0 ) ) != null )
                System.out.println( "found 0" );
        if ( aBS.find( new IntegerNode( 1 ) ) != null )
                System.out.println( "found 1" );
        if ( aBS.find( new IntegerNode( 3 ) ) != null )
                System.out.println( "found 3" );
  }

} // IntegerBinarySearchTree
