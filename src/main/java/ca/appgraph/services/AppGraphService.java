package ca.appgraph.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ca.appgraph.models.App;
import ca.appgraph.models.ConnectsTo;
import ca.appgraph.models.DeployedOn;
import ca.appgraph.models.Environment;
import ca.appgraph.models.EnvironmentType;
import ca.appgraph.models.Project;
import ca.appgraph.models.ProjectApp;
import ca.appgraph.repositories.AppRepository;
import ca.appgraph.repositories.EnvironmentRepository;
import ca.appgraph.repositories.ProjectRepository;

@Service
public class AppGraphService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

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

    public void addAppConnection(Long fromId, Long toId) {
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
            throw new ResponseStatusException(NOT_FOUND, "App with id " + id + " not found");
        }
        else {
            return appOptional.get();
        }
    }

    public Project  createProject(String name) {
        Project project = new Project();
        project.setName(name);
        return projectRepository.save(project);
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void addAppToProject(Long projectId, Long appId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Project with id " + projectId + " not found"));      
        App app = appRepository.findById(appId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "App with id " + appId + " not found"));

        ProjectApp projectApp = new ProjectApp();
        projectApp.setApp(app);

        if (project.getApps() == null) {
            project.setApps(List.of(projectApp));
        }

        if (project.getApps().stream().anyMatch(pa -> pa.getApp().getId().equals(appId))) {
            throw new ResponseStatusException(NOT_FOUND, "App with id " + appId + " is already in project with id " + projectId);
        }
        
        project.getApps().add(projectApp);
        projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Project with id " + projectId + " not found"));
        projectRepository.delete(project);
    }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Project with id " + projectId + " not found"));  
    }     
    
    public App addEnvironmentToApp(Long appId, EnvironmentType envType) {
        App app = appRepository.findById(appId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "App with id " + appId + " not found"));

        Environment env = new Environment();
        env.setType(envType);
        env = environmentRepository.save(env);

        DeployedOn deployment = new DeployedOn();
        deployment.setEnvironment(env);

        app.getDeployments().add(deployment);

        return appRepository.save(app); 
    }
}
