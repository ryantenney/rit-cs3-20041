/*
 * ExpressionTreeTest.java
 *
 * $Id$
 *
 * $Log$
 *
 */

import java.io.*;

public class ExpressionTreeTest
{

    public static void main( String[] args )
    {
	
	ExpressionTree parent = null;

	try {
	    parent = ExpressionTree.read();
	}
	catch ( ExpressionTreeException ex ) {
	    System.out.print( "ExpressionTreeException" );
	}
	catch ( IOException ex ) {
	    System.out.print( "IOException" );
	}
	
	parent.inOrder();
	System.out.println();
	parent.preOrder();
	System.out.println();
	parent.postOrder();
	System.out.println();
    }

}
