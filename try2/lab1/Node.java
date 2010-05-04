/*
 * Node.java
 *
 * Version:
 *    $Id: Node.java,v 1.1 2004/06/10 22:59:03 cs3 Exp $
 *
 * Revisions:
 *    $Log: Node.java,v $
 *    Revision 1.1  2004/06/10 22:59:03  cs3
 *    Initial revision
 *
 */

/**
 * This class represents objects that can be contained in nodes in a
 * binary tree.  At a minimum these objects know how to compare
 * themselves to objects belonging to the same class.
 *
 * @author     Hans-Peter Bischof
 * @author     Paul Tymann
 */

public abstract class Node {

    /**
     * Returns true if this Node is less than the Node referred to by the
     * argument.
     *
     * @param    aNode    the node to compare this node with
     *
     * @return   true if the this Node is less than the Node argument.
     */

    abstract public boolean isLess( Node aNode ) throws ClassCastException;

    /**
     * Returns false if the types of the Node passed in and this Node are
     * not the same.
     * Returns true if this Node is equal to the Node referred to by the
     * argument; otherwise returns false.
     *
     * @param    aNode    the node to compare this node with
     *
     * @return   true if the this Node is equal to the Node argument.
     */

    abstract public boolean isEqual( Node aNode );

    /**
     * Returns true if this Node is greater than the Node referred to by the
     * argument.
     *
     * @param    aNode    the node to compare this node with
     *
     * @return   true if the this Node is greater than the Node argument.
     */

    abstract public boolean isGreater( Node aNode ) throws ClassCastException;

} // Node
