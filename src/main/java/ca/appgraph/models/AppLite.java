package ca.appgraph.models;

/*
 * This is a projection interface for App, 
 * used to fetch only id and name of connected apps.
 */
public interface AppLite {

    public Long getId();
    public String getName();

}
