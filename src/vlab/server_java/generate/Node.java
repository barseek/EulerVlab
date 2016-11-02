package vlab.server_java.generate;

import java.util.ArrayList;

/**
 * Created by Niqera on 30.05.2016.
 */
public class Node implements Cloneable{
    public int getNumId() {
        return numId;
    }

    private int numId;
    private char id;
    //private int incedenceCount;
    public ArrayList<Edge> incedentEdges;
    public ArrayList<Node> adjustedNodes;
    public char getId() {
        return id;
    }
    public String getIncedent()
    {
        String str = id +" ";
        for (Edge temp : incedentEdges) {
            str+=temp.getId()+" ";
        }

        return str;
    }
    public Node clone() {
        try {
            return (Node)super.clone();
        }
        catch( CloneNotSupportedException ex ) {
            throw new InternalError();
        }
    }
    public Node(char id, int numId)
    {   incedentEdges = new ArrayList();
        adjustedNodes = new ArrayList();
        this.id = id;
        this.numId = numId;
    }
    public void incedent(Edge incedentEdge)
    {
        incedentEdges.add(incedentEdge);
    }

}