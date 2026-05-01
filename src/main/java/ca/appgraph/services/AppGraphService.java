package ca.appgraph.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ca.appgraph.models.App;
import ca.appgraph.models.ConnectsTo;
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
        App from = getAppById(fromId);
        App to = getAppById(toId);

        if (from.getConnectsTo().stream().anyMatch(connection -> connection.getApp().getId().equals(toId))) {
            throw new ResponseStatusException(NOT_FOUND, "Connection already exists");
        }
        else {

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
        App app = getAppById(id);
        appRepository.delete(app);
    }

    public App getAppById(Long id) {
        return appRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "App with id " + id + " not found"));
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
    
    public void addEnvironmentToApp(Long appId, EnvironmentType envType) {
        App app = appRepository.findById(appId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "App with id " + appId + " not found"));

        Environment env = new Environment();
        env.setType(envType);
        env.setApp(app);
        environmentRepository.save(env);

    }

    public Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Project with id " + projectId + " not found"));
    }
}
