package ca.appgraph.models.cytoscape;

public class Node {

    private NodeData data;
    private boolean selected;

    public NodeData getData() {
        return data;
    }

    public void setData(NodeData data) {
        this.data = data;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
