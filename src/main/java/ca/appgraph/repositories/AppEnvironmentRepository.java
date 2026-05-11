package ca.appgraph.repositories;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import ca.appgraph.models.AppEnvironment;

@Repository
public interface AppEnvironmentRepository extends Neo4jRepository<AppEnvironment, Long> {

    @Query("MATCH (e:ENV)-[:ENV_FOR]->(a:App) WHERE id(a) = $appId RETURN e")    
    public List<AppEnvironment> findEnvironmentsByAppId(Long appId);
}
