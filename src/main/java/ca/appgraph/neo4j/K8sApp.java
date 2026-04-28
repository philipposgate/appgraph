package ca.appgraph.neo4j;

import org.springframework.data.neo4j.core.schema.Node;

@Node("K8sApp")
public class K8sApp extends App {

    private String namespace;

    

    public K8sApp() {
        super();
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }


}
