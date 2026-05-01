package ca.appgraph.repositories;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import ca.appgraph.models.AppEnvironment;
import ca.appgraph.models.ENV;

@Repository
public interface AppEnvironmentRepository extends Neo4jRepository<AppEnvironment, Long> {

    public List<AppEnvironment> findByEnv(ENV env);
}
