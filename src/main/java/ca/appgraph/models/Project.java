package ca.appgraph.models;

import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Node("Project")
@JsonPropertyOrder({ "id", "name", "apps" })
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "OWNER_OF", direction = Relationship.Direction.OUTGOING)
    private List<ProjectApp> apps;

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

    public List<ProjectApp> getApps() {
        return apps;
    }

    public void setApps(List<ProjectApp> projectFor) {
        this.apps = projectFor;
    }

}
