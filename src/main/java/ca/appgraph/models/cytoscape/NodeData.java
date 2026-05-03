package ca.appgraph.models.cytoscape;

public class NodeData {
    private String id;
    private String label;

    public NodeData(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
