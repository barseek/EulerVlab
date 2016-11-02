package vlab.server_java.generate;

/**
 * Created by Niqera on 30.05.2016.
 */
public class Edge {
    private Node Sourse;
    private Node Destination;
    private String id;
    public Edge(Node s, Node d){
        Sourse = s;
        Destination = d;
        id = new StringBuilder().append(Sourse.getId()).append(Destination.getId()).toString();

    }

    public Node getSourse() {
        return Sourse;
    }

    public Node getDestination() {
        return Destination;
    }

    public String getId() {
        return id;
    }
}