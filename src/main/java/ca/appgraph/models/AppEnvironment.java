package ca.appgraph.models;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Node("ENV")
@JsonPropertyOrder({ "id", "env", "app" })
public class AppEnvironment {

    @Id
    @GeneratedValue
    private Long id;

    @Property("type")
    private ENV env;

    @Relationship(type = "ENV_FOR", direction = Relationship.Direction.OUTGOING)
    private App app;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ENV getEnv() {
        return env;
    }

    public void setEnv(ENV type) {
        this.env = type;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
