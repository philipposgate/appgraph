package ca.appgraph.models;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Node("ENV")
@JsonPropertyOrder({ "id", "type", "app" })
public class Environment {

    @Id
    @GeneratedValue
    private Long id;

    private EnvironmentType type;

    @Relationship(type = "ENV_FOR", direction = Relationship.Direction.OUTGOING)
    private App app;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnvironmentType getType() {
        return type;
    }

    public void setType(EnvironmentType type) {
        this.type = type;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
