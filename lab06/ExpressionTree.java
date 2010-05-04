/*
 * ExpressionTree.java
 *
 * Version
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */


import java.io.*;

/**
 * A class that can be used to contruct binary trees that represent
 * simple Boolean expressions.
 *
 * @author     Paul Tymann
 */

public class ExpressionTree {

    // Instance Variables

    /**
     * The data held by this node in the tree
     */
    private Object data;           

    /**
     * The left sub-tree
     */
    private ExpressionTree left;   

    /**
     * The right sub-tree
     */
    private ExpressionTree right;  



    // Class Variables

    /**
     * Static StreamTokenizer
     */
    private static StreamTokenizer lex = null;

    /**
     * Create an expression tree that corresponds to a Boolean
     * expression that consists of a single <i>variable</i>.
     *
     * @param    variable   the variable to be placed in the tree.
     */

    public ExpressionTree( String variable ) {
        data = variable;
        left = right = null;
    }

    /**
     * Create an expression tree that corresponds to a Boolean expression
     * of the form:  <i>unaryOperator</i> <i>operand</i>.
     *
     * @param    unaryOperator  the operator to be placed at the root
     * @param    operand        the tree corresponding to the operand
     */

    public ExpressionTree( char unaryOperator, ExpressionTree operand ) {
        data = new Character( unaryOperator );
        left = null;
        right = operand;
    }

    /**
     * Create an expression tree that corresponds to a Boolean expression
     * of the form:  
     *    <i>leftOperand</i> <i>binaryOperator</i> <i>rightOperand</i>
     *
     * @param    binaryOperator the operator to be placed at the root
     * @param    leftOperand    the tree corresponding to the left operand
     * @param    rightOperand   the tree corresponding to the right operand
     *                     
     */

    public ExpressionTree( char binaryOperator, 
			   ExpressionTree leftOperand,
			   ExpressionTree rightOperand ) {

        data = new Character( binaryOperator );
        left = leftOperand;
        right = rightOperand;
    }

    /**
     * Print an inorder (LVR) traversal of this tree to standard output.
     */

    public void inOrder() {

	// Do the left tree

        if ( left != null ) {
	    if ( right != null ) {
		System.out.print( "(" );
	    }

	    left.inOrder();
	}


	// Process current node
	if ( left == null && right != null ) {
	    System.out.print( data.toString() );
	}
	else if ( left == null && right == null ) {
	    System.out.print( data.toString() );
	}
	else if ( left != null && right != null ) {
	    System.out.print( " " + data.toString() + " " );
	}

	// Do the right tree

        if ( right != null ) {
	    right.inOrder();

	    if ( left != null ) {
		System.out.print( ")" );
	    }
        }
    }

    /**
     * Print a postorder (LRV) traversal of this tree to standard output.
     */

    public void postOrder() {
	// Traverse left

        if ( left != null ) {
            left.postOrder();
        }

	// Traverse right

        if ( right != null ) {
            right.postOrder();
        }

	// Process the current node

        System.out.print( data.toString() + " " );
    }

    /**
     * Print a preorder (VLR) traversal of this tree to standard output.
     */

    public void preOrder() {
	// Process the current node

        System.out.print( data.toString() + " " );

	// Traverse left

        if ( left != null ) {
            left.preOrder();
        }

	// Traverse right

        if ( right != null ) {
            right.preOrder();
        }
    }

    /**
      * Return true if the current node contains a binary operator
      * and false otherwise.  Note a binary operator is the only
      * node that will have both a left and right child.
      *
      * @return   true if the current node is a binary operator and
      *           false otherwise.
      *
      */

    private boolean isBinaryOperator () {
        return (left != null && right != null);
    }

    /**
      * Read next prefix Boolean expression from input and
      * convert it into an expression tree.
      *
      * @return   an ExpressionTree containing the next boolean expression
      *           read from standard input.
      *
      * @exception IOException if an I/O error occurs during read.
      * @exception ExpressionTreeException if an illegal character is read.
                   Exception message field is: "Invalid expression". 
      */
    
    static public ExpressionTree read() throws ExpressionTreeException, 
                                               IOException {

	if ( lex == null ) {
	    lex = new StreamTokenizer( 
		new InputStreamReader( System.in ) );
	}

	// Eventually the return value

        ExpressionTree tree = null;

	if ( lex.nextToken() == StreamTokenizer.TT_EOF ) {
	    return null;
	}

	switch ( lex.ttype ) {
	    case '&':
		tree = new ExpressionTree( '&', read(), read() );
		break;
	    case '|':
		tree = new ExpressionTree( '|', read(), read() );
		break;
	    case '!':
		tree = new ExpressionTree( '!', read() );
		break;
	    case StreamTokenizer.TT_WORD:
		tree = new ExpressionTree( lex.sval );
		break;
	    default:
		break;	
	}

        return tree;
    }

} // ExpressionTree
