package ca.appgraph.models.cytoscape;

public class EdgeData {
    private String id;
    private String source;
    private String target;
    private String label;

    public EdgeData(String id, String source, String target, String label) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getLabel() {
        return label;
    }
}
