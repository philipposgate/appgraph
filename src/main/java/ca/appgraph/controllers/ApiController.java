package ca.appgraph.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.appgraph.models.App;
import ca.appgraph.models.AppEnvironment;
import ca.appgraph.models.Project;
import ca.appgraph.services.AppGraphService;



@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private AppGraphService appGraphService;

    @GetMapping("/hello")
    public String hello() {
        return appGraphService.getModelName();
    }

    @PostMapping("/apps")
    public App createApp(@RequestBody App app) {
        return appGraphService.createApp(app.getName());
    }

    @GetMapping("/apps")
    public Iterable<App> getApps() {
        return appGraphService.findAllApps();
    }

    @GetMapping("/apps/search")    
    public List<App> getAppsByName(String name) {
        return appGraphService.findAppsByName(name);
    }   

    @PutMapping("/apps/{fromId}/connect/{toId}")
    public void addAppConnection(@PathVariable Long fromId, @PathVariable Long toId) {
        appGraphService.addAppConnection(fromId, toId);
    }

    @DeleteMapping("/apps/{id}")
    public void deleteApp(@PathVariable Long id) {
        appGraphService.deleteApp(id);
    }

    @GetMapping("/apps/{id}")
    public App getApp(@PathVariable Long id) {
        return appGraphService.getAppById(id);
    }

    @GetMapping("/projects")
    public Iterable<Project> getProjects() {
        return appGraphService.findAllProjects();
    }
    
    @PostMapping("/projects")
    public Project createProject(@RequestBody Project project) {
        return appGraphService.createProject(project.getName());
    }

    @PutMapping("/projects/{projectId}/apps/{appId}")
    public void addAppToProject(@PathVariable Long projectId, @PathVariable Long appId) {
        appGraphService.addAppToProject(projectId, appId);
    }

    @DeleteMapping("/projects/{projectId}")
    public void deleteProject(@PathVariable Long projectId) {
        appGraphService.deleteProject(projectId);
    }

    @GetMapping("/projects/{projectId}")
    public Project getProject(@PathVariable Long projectId) {
        return appGraphService.getProjectById(projectId);
    }

    @PostMapping("/apps/{appId}/environments")
    public void addEnvironmentToApp(@PathVariable Long appId, @RequestBody AppEnvironment env) {
        
        
        appGraphService.addEnvironmentToApp(appId, env.getEnv());
    }
    
}
