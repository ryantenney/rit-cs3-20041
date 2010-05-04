
The class file version is 48.0 (only 45.3, 46.0 and 47.0 are supported)
// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ArrayDiGraph.java

import java.util.*;

public class ArrayDiGraph
    implements DiGraph
{

    public ArrayDiGraph()
    {
        numVertices = 0;
        vKeys = new Object[5];
        vData = new Object[5];
        eFrom = new Vector();
        eTo = new Vector();
        eData = new Vector();
    }

    public void addVertex(Object obj, Object obj1)
    {
        int i = vertexNumber(obj);
        if(i == -1)
        {
            if(numVertices == vKeys.length)
            {
                Object aobj[] = new Object[vKeys.length + 5];
                Object aobj1[] = new Object[vKeys.length + 5];
                for(int j = 0; j < vKeys.length; j++)
                {
                    aobj[j] = vKeys[j];
                    aobj1[j] = vData[j];
                }

                vKeys = aobj;
                vData = aobj1;
            }
            vKeys[numVertices] = obj;
            vData[numVertices] = obj1;
            numVertices++;
        } else
        {
            vData[i] = obj1;
        }
    }

    public void addEdge(Object obj, Object obj1, Object obj2)
        throws NoSuchVertexException
    {
        int i = findEdge(obj, obj1);
        if(i == -1)
        {
            eFrom.add(obj);
            eTo.add(obj1);
            eData.add(obj2);
        } else
        {
            eData.setElementAt(obj2, i);
        }
    }

    public boolean isEdge(Object obj, Object obj1)
        throws NoSuchVertexException
    {
        return findEdge(obj, obj1) != -1;
    }

    public Object getEdgeData(Object obj, Object obj1)
        throws NoSuchVertexException
    {
        Object obj2 = null;
        int i = findEdge(obj, obj1);
        if(i != -1)
            obj2 = eData.elementAt(i);
        return obj2;
    }

    public boolean isVertex(Object obj)
    {
        return vertexNumber(obj) != -1;
    }

    public Object getVertexData(Object obj)
        throws NoSuchVertexException
    {
        int i = vertexNumber(obj);
        if(i == -1)
            throw new NoSuchVertexException("Invalid vertex");
        else
            return vData[i];
    }

    public int numVertices()
    {
        return numVertices;
    }

    public int inDegree(Object obj)
    {
        int i = -1;
        if(vertexNumber(obj) != -1)
        {
            i = 0;
            for(int j = 0; j < eTo.size(); j++)
                if(eTo.elementAt(j).equals(obj))
                    i++;

        }
        return i;
    }

    public int outDegree(Object obj)
    {
        int i = -1;
        if(vertexNumber(obj) != -1)
        {
            i = 0;
            for(int j = 0; j < eFrom.size(); j++)
                if(eFrom.elementAt(j).equals(obj))
                    i++;

        }
        return i;
    }

    public Collection neighborData(Object obj)
        throws NoSuchVertexException
    {
        ArrayList arraylist = new ArrayList();
        if(!isVertex(obj))
            throw new NoSuchVertexException("Invalid vertex");
        for(int i = 0; i < eFrom.size(); i++)
            if(eFrom.elementAt(i).equals(obj))
                arraylist.add(getVertexData(eTo.elementAt(i)));

        return arraylist;
    }

    public Collection neighborKeys(Object obj)
        throws NoSuchVertexException
    {
        ArrayList arraylist = new ArrayList();
        if(!isVertex(obj))
            throw new NoSuchVertexException("Invalid vertex");
        for(int i = 0; i < eFrom.size(); i++)
            if(eFrom.elementAt(i).equals(obj))
                arraylist.add(eTo.elementAt(i));

        return arraylist;
    }

    public Collection vertexData()
    {
        ArrayList arraylist = new ArrayList();
        for(int i = 0; i < numVertices; i++)
            arraylist.add(vData[i]);

        return arraylist;
    }

    public Collection vertexKeys()
    {
        ArrayList arraylist = new ArrayList();
        for(int i = 0; i < numVertices; i++)
            arraylist.add(vKeys[i]);

        return arraylist;
    }

    public Collection edgeData()
    {
        return eData.subList(0, eData.size());
    }

    public void clear()
    {
        numVertices = 0;
        vKeys = new Object[5];
        vData = new Object[5];
        eFrom = new Vector();
        eTo = new Vector();
        eData = new Vector();
    }

    private int vertexNumber(Object obj)
    {
        for(int i = 0; i < numVertices; i++)
            if(vKeys[i].equals(obj))
                return i;

        return -1;
    }

    private int findEdge(Object obj, Object obj1)
        throws NoSuchVertexException
    {
        int i = -1;
        if(vertexNumber(obj) == -1 || vertexNumber(obj1) == -1)
            throw new NoSuchVertexException("bad vertex");
        for(int j = 0; j < eFrom.size() && i == -1; j++)
            if(eFrom.elementAt(j).equals(obj) && eTo.elementAt(j).equals(obj1))
                i = j;

        return i;
    }

    private static final int EXTENT = 5;
    private int numVertices;
    private Object vKeys[];
    private Object vData[];
    private Vector eFrom;
    private Vector eTo;
    private Vector eData;
}
