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

import ca.appgraph.neo4j.App;
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

    @PostMapping("/app")
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

    @PutMapping("/app/{fromId}/connect/{toId}")
    public void addConnection(@PathVariable Long fromId, @PathVariable Long toId) {
        appGraphService.addConnection(fromId, toId);
    }

    @DeleteMapping("/app/{id}")
    public void deleteApp(@PathVariable Long id) {
        appGraphService.deleteApp(id);
    }

    @GetMapping("/app/{id}")
    public App getApp(@PathVariable Long id) {
        return appGraphService.getAppById(id);
    }

}
