package ca.appgraph.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.appgraph.neo4j.App;
import ca.appgraph.neo4j.AppRepository;
import ca.appgraph.neo4j.ConnectsTo;

@Service
public class AppGraphService {

    @Autowired
    private AppRepository appRepository;
    
    public String getModelName() {
        return "Default model";
    }

    public Optional<App> findAppById(Long id) {
        return appRepository.findById(id);
    }

    public App createApp(String name) {
        App app = new App();
        app.setName(name);  
        return appRepository.save(app);
    }

    public List<App> findAllApps() {
        return appRepository.findAll();
    }

    public List<App> findAppsByName(String name) {
        return appRepository.findByName(name);
    }

    public void addConnection(Long fromId, Long toId) {
        Optional<App> fromOptional = findAppById(fromId);
        Optional<App> toOptional = findAppById(toId);

        if (fromOptional.isEmpty()) {
            throw new RuntimeException("App with id " + fromId + " not found");
        }
        else if (toOptional.isEmpty()) {
            throw new RuntimeException("App with id " + toId + " not found");
        }
        else {
            App from = fromOptional.get();
            App to = toOptional.get();

            ConnectsTo connection = new ConnectsTo();
            connection.setApp(to);
            if (from.getOutgoingConnections() == null) {
                from.setOutgoingConnections(List.of(connection));
            } 
            from.getOutgoingConnections().add(connection);
            appRepository.save(from);
        }
    }

    public void deleteApp(Long id) {
        Optional<App> appOptional = findAppById(id);

        if (appOptional.isEmpty()) {
            throw new RuntimeException("App with id " + id + " not found");
        }
        else {
            appRepository.delete(appOptional.get());
        }
    }

    public App getAppById(Long id) {
        Optional<App> appOptional = findAppById(id);

        if (appOptional.isEmpty()) {
            throw new RuntimeException("App with id " + id + " not found");
        }
        else {
            return appOptional.get();
        }
    }

}
