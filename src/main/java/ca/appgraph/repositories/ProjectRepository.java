package ca.appgraph.repositories;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import ca.appgraph.models.Project;

@Repository
public interface ProjectRepository extends Neo4jRepository<Project, Long> {

    public List<Project> findByName(String name);
}
