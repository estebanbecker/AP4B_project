package Graph;

import java.util.HashMap;

public class Graph {
    HashMap<Integer, Node> nodes;

    public Graph() {
        nodes = new HashMap<Integer, Node>();
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

}
