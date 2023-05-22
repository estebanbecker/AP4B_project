package Graph;

public class Edge {
    
    public float weight;
    
    public String label;

    public Integer node_id_from;

    public Integer node_id_to;
    private Integer nodeToId;

    public Edge(float w, String l, Integer node_id_from, Integer node_id_to) {
        this.weight = w;
        this.label = l;
        this.node_id_from = node_id_from;
        this.node_id_to = node_id_to;
    }

    public Integer getNodeToId() {
        return nodeToId;
    }

    public void setNodeToId(Integer nodeToId) {
        this.nodeToId = nodeToId;
    }
}
