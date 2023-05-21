package Graph;

import java.util.HashMap;

public class Node {
    
    private static int lastId = 0;

    Integer id;
    Integer position[] = new Integer[2]; // [x, y]

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

    public void addEdge(Integer node_to_id, Edge edge) {
        edges.put(node_to_id, edge);
    }
}
