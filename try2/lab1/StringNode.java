/*
 * StringNode.java
 *
 * Version:
 *    $Id: StringNode.java,v 1.3 2004/12/03 16:02:00 rwt5629 Exp rwt5629 $
 *
 * Revisions:
 *    $Log: StringNode.java,v $
 *    Revision 1.3  2004/12/03 16:02:00  rwt5629
 *    i am 1337.
 *
 *    Revision 1.2  2004/12/03 15:54:14  rwt5629
 *    changes made for lab1 activity 2
 *
 *    Revision 1.1  2004/06/10 22:59:03  cs3
 *    Initial revision
 *
 */

/**
 * This class allows nodes in a binary tree to hold strings.  Since
 * it is an extension of the Node class, these objects can compare
 * themselves to similar objects.
 *
 * Note that the methods in this class take Node objects as a
 * parameter.  The method then casts the Node reference to a
 * reference to a StringNode.  Since the caller may pass a reference
 * to a Node object that cannot be converted to a StringNode, some of the
 * methods in this class may throw a ClassCastException.
 *
 * @author     Hans-Peter Bischof
 * @author     Paul Tymann
 */

public class StringNode extends Node {

    // The string that contains the data

    private String info;

    /**
     * Create a new StringNode.
     *
     * @param    info    the string that is to be stored in this node
     */

    public StringNode( String info ) {
        this.info = info;
    }

    /**
     * Returns true if this StringNode is less than the StringNode 
     * referred to by the argument.
     *
     * @param    aNode    the StringNode to compare this 
     *                    StringNode with.
     *
     * @return   true if this StringNode is less than the 
     *           StringNode argument.
     *
     * @exception    ClassCastException    if the argument cannot be
     *                                     converted to a StringNode
     */

    public boolean isLess( Node aNode ) throws ClassCastException {
        StringNode aStringNode = (StringNode)aNode;

        return info.compareTo( aStringNode.toString() ) < 0;
    }

    /**
     * Returns true if this StringNode is greater than the StringNode 
     * referred to by the argument.
     *
     * @param    aNode    the StringNode to compare this 
     *                    StringNode with.
     *
     * @return   true if this StringNode is greater than the 
     *           StringNode argument.
     *
     * @exception    ClassCastException    if the argument cannot be
     *                                     converted to a StringNode
     */

    public boolean isGreater( Node aNode ) throws ClassCastException {
        StringNode aStringNode = (StringNode)aNode;
        return info.compareTo( aStringNode.toString() ) > 0;
    }


    /**
     * Return a string representation of the data contained in this
     * StringNode.
     *
     * @return    a string representation of this StringNode.
     */

    public String toString() {
        return info;
    }

    /**
     * Compares the contents of this  instance of StringNode to
     * another
     *
     * @return    a boolean value as to if they match
     */

     public boolean isEqual( Node aNode ) {
         if ( aNode instanceof StringNode ) {
             StringNode aStringNode = (StringNode)aNode;
             return info.compareTo( aStringNode.toString() ) == 0;
         }
         else {
             return false;
         }
     }

} // StringNode
