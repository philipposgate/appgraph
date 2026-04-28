package ca.appgraph.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(NOT_FOUND, "App with id " + fromId + " not found");
        }
        else if (toOptional.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "App with id " + toId + " not found");
        }
        else {
            App from = fromOptional.get();
            App to = toOptional.get();

            if (from.getConnectsTo().stream().anyMatch(connection -> connection.getApp().getId().equals(toId))) {
                throw new ResponseStatusException(NOT_FOUND, "Connection already exists");
            }

            ConnectsTo connection = new ConnectsTo();
            connection.setApp(to);

            if (from.getConnectsTo() == null) {
                from.setConnectsTo(List.of(connection));
            } 
            
            from.getConnectsTo().add(connection);
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
