package ca.appgraph.neo4j;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends Neo4jRepository<App, Long> {

    public List<App> findByName(String name);
}
