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

    /**
     * Connects two nodes with an edge in both directions
     * @param node_from_id  The id of the node from which the edge starts
     * @param node_to_id         The id of the node to which the edge ends
     * @param label         The label of the edge
     */
    public void connectUnidirectionalNodes(Integer node_from_id, Integer node_to_id, String label) {
        Node node_from = nodes.get(node_from_id);
        Node node_to = nodes.get(node_to_id);

        Float weight = (float) Math.sqrt(Math.pow(node_from.position[0] - node_to.position[0], 2)
                + Math.pow(node_from.position[1] - node_to.position[1], 2));

        node_from.addEdge(node_to_id, label, weight);
    }

    /**
     * Connects two nodes with an edge in both directions
     * @param node1     The id of one node that will be connected
     * @param node2     The id of the other node that will be connected
     * @param label     The label of the edge
     */
    public void connectBidirectionalNodes(Integer node1, Integer node2, String label) {
        Node node_from = nodes.get(node1);
        Node node_to = nodes.get(node2);

        Float weight = (float) Math.sqrt(Math.pow(node_from.position[0] - node_to.position[0], 2)
                + Math.pow(node_from.position[1] - node_to.position[1], 2));

        node_from.addEdge(node2, label, weight);
        node_to.addEdge(node1, label, weight);
    }

    /**
     * Creates nodes from a list of positions
     * @param positions     A list of positions in the form of [x, y]
     */
    public void createNodes(Integer[][] positions) {
        for (Integer[] position : positions) {
            Node node = new Node(position[0], position[1]);
            addNode(node);
        }
    }

    /**
     * Prints the graph
     */
    public void print() {
        for (Node node : nodes.values()) {
            System.out.println("Node " + node.getId() + " at position (" + node.position[0] + ", " + node.position[1]
                    + ") has edges:");
            for (Edge edge : node.edges.values()) {
                System.out.println("    Connected with Node: " + edge.node_id_to + " with label " + edge.label
                        + " and weight " + edge.weight);
            }
        }
    }

    /**
     * Prints a node
     * @param node_id       The id of the node
     */
    public void printNode(Integer node_id) {
        Node node = nodes.get(node_id);
        System.out.println("Node " + node.getId() + " at position (" + node.position[0] + ", " + node.position[1]
                + ") has edges:");
        for (Edge edge : node.edges.values()) {
            System.out.println("    " + edge.label + " with weight " + edge.weight);
        }
    }

    public void printEdge(Integer node_from_id, Integer node_to_id) {
        Node node_from = nodes.get(node_from_id);
        Edge edge = node_from.edges.get(node_to_id);
        System.out.println("Edge from " + node_from_id + " to " + node_to_id + " with label " + edge.label
                + " and weight " + edge.weight);
    }

    /**
     * Returns the neigbourghs of a node
     * @param node_id       The id of the node
     * @return              An array of the ids of the neigbourghs
     */
    public Integer[] getNeighgbor(Integer node_id) {
        Node node = nodes.get(node_id);
        return node.edges.keySet().toArray(new Integer[0]);
    }

    /**
     * Returns the weight of an edge
     * @param node_from_id  The id of the node from which the edge starts
     * @param node_to_id    The id of the node to which the edge ends
     * @return              The weight of the edge
     */
    public String getEdgeLabel(Integer node_from_id, Integer node_to_id) {
        Node node_from = nodes.get(node_from_id);
        Edge edge = node_from.edges.get(node_to_id);
        return edge.label;
    }

    /**
     * Returns the weight of an edge
     * @param node_from_id  The id of the node from which the edge starts
     * @param node_to_id    The id of the node to which the edge ends
     * @return              The weight of the edge
     */
    public Float getEdgeWeight(Integer node_from_id, Integer node_to_id) {
        Node node_from = nodes.get(node_from_id);
        Edge edge = node_from.edges.get(node_to_id);
        return edge.weight;
    }

   //create a getNodes methods that returns the nodes hashmap
    public HashMap<Integer, Node> getNodes(){
        return nodes;
    }

}