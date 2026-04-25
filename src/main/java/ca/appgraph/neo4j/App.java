package ca.appgraph.neo4j;

import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("App")
public class App {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    @Relationship(type = "CONNECTS_TO", direction = Relationship.Direction.OUTGOING)
    private List<ConnectsTo> outgoingConnections;

    @Relationship(type = "CONNECTS_TO", direction = Relationship.Direction.INCOMING)
    private List<ConnectsTo> incomingConnections;


    public App() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<ConnectsTo> getOutgoingConnections() {
        return outgoingConnections;
    }


    public void setOutgoingConnections(List<ConnectsTo> outgoingConnections) {
        this.outgoingConnections = outgoingConnections;
    }


    public List<ConnectsTo> getIncomingConnections() {
        return incomingConnections;
    }


    public void setIncomingConnections(List<ConnectsTo> incomingConnections) {
        this.incomingConnections = incomingConnections;
    }

    
}
