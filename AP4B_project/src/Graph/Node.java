package Graph;

import java.util.HashMap;

public class Node {
    
    private static int lastId = 0;

    Integer id;
    Integer[] position = new Integer[2]; // [x, y]

    HashMap<Integer, Edge> edges;


    public Node(Integer x, Integer y) {
        this.id = ++lastId;
        this.position[0] = x;
        this.position[1] = y;
        edges = new HashMap<Integer, Edge>();

    }

    public Integer getId() {
        return id;
    }

    public void addEdge(Integer node_to_id, String label, Float weight) {
        Edge edge= new Edge(weight, label, this.id, node_to_id);
        edges.put(node_to_id, edge);
    }

    public Integer[] getPosition() {
        return position;
    }

    public void setPosition(Integer[] position) {
        this.position = position;
    }

    public HashMap<Integer, Edge> getEdges() {
        return edges;
    }

    public void setEdges(HashMap<Integer, Edge> edges) {
        this.edges = edges;
    }
}
