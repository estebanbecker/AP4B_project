package Graph;

import java.util.HashMap;

public class Node {
    
    private static int lastId = 0;

    int id;
    Integer position[] = new Integer[2]; // [x, y]

    HashMap<Integer, Edge> edges;


    public Node(Integer id) {
        this.id = ++lastId;
        edges = new HashMap<Integer, Edge>();

    }

    public Integer getId() {
        return id;
    }

    public void addEdge(Node node, Edge edge) {
        edges.put(node.getId(), edge);
    }
}
