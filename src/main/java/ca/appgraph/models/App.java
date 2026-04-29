package ca.appgraph.models;

import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Node("App")
@JsonPropertyOrder({ "id", "name", "connectsTo" })
public class App {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    @Relationship(type = "CONNECTS_TO", direction = Relationship.Direction.OUTGOING)
    private List<ConnectsTo> connectsTo;



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

    public List<ConnectsTo> getConnectsTo() {
        return connectsTo;
    }

    public void setConnectsTo(List<ConnectsTo> connectsTos) {
        this.connectsTo = connectsTos;
    }



    
}
