package ca.appgraph.models;

/*
 * This is a "neo4j projection" interface for App, 
 * used to fetch only id and name properties of an App.
 */
public interface AppLite {

    public Long getId();
    public String getName();

}
