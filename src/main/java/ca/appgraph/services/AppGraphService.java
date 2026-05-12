package ca.appgraph.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ca.appgraph.models.App;
import ca.appgraph.models.AppEnvironment;
import ca.appgraph.models.AppLite;
import ca.appgraph.models.ConnectsTo;
import ca.appgraph.models.EnvironmentType;
import ca.appgraph.models.Project;
import ca.appgraph.models.ProjectApp;
import ca.appgraph.models.cytoscape.Edge;
import ca.appgraph.models.cytoscape.EdgeData;
import ca.appgraph.models.cytoscape.Node;
import ca.appgraph.models.cytoscape.NodeData;
import ca.appgraph.models.select2.Select2Result;
import ca.appgraph.models.select2.Select2Results;
import ca.appgraph.repositories.AppEnvironmentRepository;
import ca.appgraph.repositories.AppRepository;
import ca.appgraph.repositories.ProjectRepository;

@Service
public class AppGraphService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AppEnvironmentRepository appEnvironmentRepository;

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

     
            ConnectsTo connection = new ConnectsTo();
            connection.setApp(to);

            if (from.getConnectsTo() == null) {
                from.setConnectsTo(List.of(connection));
            } 
            
            from.getConnectsTo().add(connection);
            appRepository.save(from);
        }
    }

    public void removeAppConnection(Long fromId, Long toId) {
        App from = getAppById(fromId);

        if (from.getConnectsTo() != null) {
            from.getConnectsTo().removeIf(connection -> connection.getApp().getId().equals(toId));
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

        AppEnvironment env = new AppEnvironment();
        env.setType(envType);
        env.setApp(app);
        appEnvironmentRepository.save(env);

    }

    public Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Project with id " + projectId + " not found"));
    }


    public List<AppLite> findUpstreamApps(Long appId) {
        return appRepository.findUpstreamApps(appId);
    }

    public Project findProjectByAppId(Long appId) {
        Optional<Project> optionalProject = projectRepository.findProjectByAppId(appId);
        return optionalProject.isPresent() ? optionalProject.get() : null;
    }


    public void updateProject(Long projectId, Project updatedProject) {
        Project existingProject = getProjectById(projectId);
        
        existingProject.setName(updatedProject.getName());

        projectRepository.save(existingProject);    
    }


    public void updateApp(Long appId, App updatedApp) {
        App existingApp = getAppById(appId);

        existingApp.setName(updatedApp.getName());

        appRepository.save(existingApp);
    }

    public List<Object> getGraph() {
        return getGraph(null);
    }

    public List<Object> getGraph(Long appId) {
        List<Object> graph = new LinkedList<>();
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        List<App> apps = appRepository.findAll();


        for (App app : apps) {
            NodeData nodeData = new NodeData(app.getId().toString(), app.getName());
            Node node = new Node();
            node.setData(nodeData);
            node.setSelected(appId != null && app.getId().equals(appId));
            nodes.add(node);
            if (app.getConnectsTo() != null) {
                for (ConnectsTo connection : app.getConnectsTo()) {
                    EdgeData edgeData = new EdgeData("r" + connection.getId().toString(), app.getId().toString(), connection.getApp().getId().toString(), "CONNECTS TO");
                    Edge edge = new Edge();
                    edge.setData(edgeData);
                    edges.add(edge);
                }
            }   
        }
        graph.addAll(nodes);
        graph.addAll(edges);   
        return graph;
    }


    public Select2Results getSelect2Apps() {
        List<App> apps = appRepository.findAll();
        List<Select2Result> select2Results = new ArrayList<>();
        for (App app : apps) {
            Select2Result result = new ca.appgraph.models.select2.Select2Result();
            result.setId(app.getId());
            result.setText(app.getName());
            select2Results.add(result);
        }
        Select2Results results = new Select2Results();
        results.setResults(select2Results);
        return results;
    }
    
    public void removeAppFromProject(Long projectId, Long appId) {
        Project project = getProjectById(projectId);

        if (project.getApps() != null) {
            project.getApps().removeIf(pa -> pa.getApp().getId().equals(appId));
            projectRepository.save(project);
        }
    }


    public List<AppEnvironment> findEnvironmentsByAppId(Long appId) {
        return appEnvironmentRepository.findEnvironmentsByAppId(appId).stream().sorted().toList();
    }       
}