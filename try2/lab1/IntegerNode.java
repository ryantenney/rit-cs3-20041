/*
 * IntegerNode.java
 *
 * Version:
 *    $Id: IntegerNode.java,v 1.1 2004/12/03 16:23:11 rwt5629 Exp $
 *
 * Revisions:
 *    $Log: IntegerNode.java,v $
 *    Revision 1.1  2004/12/03 16:23:11  rwt5629
 *    Initial revision
 *
 *
 */

/**
 * This class allows nodes in a binary tree to hold integers.  Since
 * it is an extension of the Node class, these objects can compare
 * themselves to similar objects.
 *
 * Note that the methods in this class take Node objects as a
 * parameter.  The method then casts the Node reference to a
 * reference to an IntegerNode.  Since the caller may pass a reference
 * to a Node object that cannot be converted to an IntegerNode, some of the
 * methods in this class may throw a ClassCastException.
 *
 * @author     Ryan Tenney
 */

public class IntegerNode extends Node {

    // The integer that contains the data

    private Integer info;

    /**
     * Create a new IntegerNode.
     *
     * @param    info    the integer that is to be stored in this node
     */

    public IntegerNode( Integer info ) {
        this.info = info;
    }

    /**
     * Returns true if this IntegerNode is less than the IntegerNode 
     * referred to by the argument.
     *
     * @param    aNode    the IntegerNode to compare this 
     *                    StringNode with.
     *
     * @return   true if this IntegerNode is less than the 
     *           StringNode argument.
     *
     * @exception    ClassCastException    if the argument cannot be
     *                                     converted to an IntegerNode
     */

    public boolean isLess( Node aNode ) throws ClassCastException {
        IntegerNode anIntegerNode = (IntegerNode)aNode;
        Integer compare = Integer.valueOf( anIntegerNode.toString() );

        return info.compareTo( compare ) < 0;
    }

    /**
     * Returns true if this IntegerNode is greater than the IntegerNode 
     * referred to by the argument.
     *
     * @param    aNode    the IntegerNode to compare this 
     *                    IntegerNode with.
     *
     * @return   true if this IntegerNode is greater than the 
     *           IntegerNode argument.
     *
     * @exception    ClassCastException    if the argument cannot be
     *                                     converted to an IntegerNode
     */

    public boolean isGreater( Node aNode ) throws ClassCastException {
        IntegerNode anIntegerNode = (IntegerNode)aNode;
        Integer compare = Integer.valueOf( anIntegerNode.toString() );

        return info.compareTo( compare ) > 0;
    }


    /**
     * Return a string representation of the data contained in this
     * StringNode.
     *
     * @return    a string representation of this StringNode.
     */

    public String toString() {
        return info.toString();
    }

    /**
     * Compares the contents of this instance of IntegerNode to
     * another
     *
     * @return    a boolean value as to if they match
     */

     public boolean isEqual( Node aNode ) {
         if ( aNode instanceof IntegerNode ) {
             IntegerNode anIntegerNode = (IntegerNode)aNode;
             Integer compare = Integer.valueOf( anIntegerNode.toString() );

             return info.compareTo( compare ) == 0;
         }
         else {
             return false;
         }
     }

} // StringNode
