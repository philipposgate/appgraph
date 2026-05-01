package ca.appgraph.repositories;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import ca.appgraph.models.App;
import ca.appgraph.models.AppLite;

@Repository
public interface AppRepository extends Neo4jRepository<App, Long> {

    public List<App> findByName(String name);


    @Query("MATCH (a:App)-[:CONNECTS_TO]->(b:App) WHERE id(b) = $appId RETURN a")
    public List<AppLite> findUpstreamApps(Long appId);

}
