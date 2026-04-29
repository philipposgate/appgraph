package ca.appgraph.repositories;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import ca.appgraph.models.Environment;
import ca.appgraph.models.EnvironmentType;

@Repository
public interface EnvironmentRepository extends Neo4jRepository<Environment, Long> {

    public List<Environment> findByType(EnvironmentType type);
}
