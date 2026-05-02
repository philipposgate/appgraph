package ca.appgraph.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import ca.appgraph.models.Project;

@Repository
public interface ProjectRepository extends Neo4jRepository<Project, Long> {

    public List<Project> findByName(String name);

    @Query("MATCH (p:Project)-[:OWNER_OF]->(a:App) WHERE id(a) = $appId RETURN p")
    public Optional<Project> findProjectByAppId(Long appId);
}
