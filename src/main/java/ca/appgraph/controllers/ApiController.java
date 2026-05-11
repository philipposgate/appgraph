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
import ca.appgraph.models.select2.Select2Results;
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

    @DeleteMapping("/apps/{fromId}/connect/{toId}")
    public void removeAppConnection(@PathVariable Long fromId, @PathVariable Long toId) {
        appGraphService.removeAppConnection(fromId, toId);
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

    @DeleteMapping("/projects/{projectId}/apps/{appId}")
    public void removeAppFromProject(@PathVariable Long projectId, @PathVariable Long appId) {
        appGraphService.removeAppFromProject(projectId, appId);
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

        appGraphService.addEnvironmentToApp(appId, env.getType());
    }

    @PutMapping("/projects/{projectId}")
    public void updateProject(@PathVariable Long projectId, @RequestBody Project updatedProject) {

        appGraphService.updateProject(projectId, updatedProject);
    }

    @PutMapping("/apps/{appId}")
    public void updateApp(@PathVariable Long appId, @RequestBody App updatedApp) {

        appGraphService.updateApp(appId, updatedApp);
    }

    @GetMapping("/graph")
    public List<Object> getGraph() {
        return appGraphService.getGraph();
    }

    @GetMapping("/graph/{appId}")
    public List<Object> getGraph(@PathVariable Long appId) {
        return appGraphService.getGraph(appId);
    }

    @GetMapping("/select2/apps")
    public Select2Results getSelect2Apps() {
        return appGraphService.getSelect2Apps();
    }

}
