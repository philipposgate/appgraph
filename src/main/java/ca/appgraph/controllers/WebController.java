package ca.appgraph.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ca.appgraph.services.AppGraphService;

@Controller
public class WebController {

	private static final Logger logger = LoggerFactory.getLogger(WebController.class);

	@Autowired
	private AppGraphService appGraphService;

	@GetMapping("/")
	public String home(Model model) {
		logger.info("GET /");
		model.addAttribute("page", "projects");
		model.addAttribute("projects", appGraphService.findAllProjects());
		return "main";
	}

	@GetMapping("/about")
	public String about(Model model) {
		logger.info("GET /about");
		model.addAttribute("page", "about");
		return "main";
	}

	@GetMapping("/projects/{projectId}")
	public String getProjectDetails(@PathVariable Long projectId, Model model) {
		logger.info("GET /projects/{}", projectId);
		model.addAttribute("page", "projectDetails");
		model.addAttribute("project", appGraphService.findProjectById(projectId));
		return "main";
	}

	@GetMapping("/apps")
	public String getApps(Model model) {
		logger.info("GET /apps");
		model.addAttribute("page", "apps");
		model.addAttribute("apps", appGraphService.findAllApps());
		return "main";
	}

	@GetMapping("/apps/{appId}")
	public String getAppDetails(@PathVariable Long appId, Model model) {
		logger.info("GET /apps/{}", appId);
		model.addAttribute("page", "appDetails");
		model.addAttribute("app", appGraphService.getAppById(appId));
		model.addAttribute("upstreamApps", appGraphService.findUpstreamApps(appId));
		model.addAttribute("project", appGraphService.findProjectByAppId(appId));
		return "main";
	}

	@GetMapping("/projects/new")
	public String newProject(Model model) {
		logger.info("GET /projects/new");
		model.addAttribute("page", "projectNew");
		return "main";
	}

	@GetMapping("/projects/{projectId}/edit")
	public String editProject(@PathVariable Long projectId, Model model) {
		logger.info("GET /projects/{}/edit", projectId);
		model.addAttribute("page", "projectEdit");
		model.addAttribute("project", appGraphService.findProjectById(projectId));
		return "main";
	}

	@GetMapping("/apps/new")
	public String newApp(Model model) {
		logger.info("GET /apps/new");
		model.addAttribute("page", "appNew");
		return "main";
	}

	@GetMapping("/graph")
	public String graph(Model model) {
		logger.info("GET /graph");
		model.addAttribute("page", "graph");
		return "main";
	}

	@GetMapping("/graph/{appId}")
	public String graph(@PathVariable Long appId, Model model) {
		logger.info("GET /graph/{}", appId);
		model.addAttribute("page", "graph");
		model.addAttribute("appId", appId);
		return "main";
	}

	@GetMapping("/apps/{appId}/edit")
	public String editApp(@PathVariable Long appId, Model model) {
		logger.info("GET /apps/{}/edit", appId);
		model.addAttribute("page", "appEdit");
		model.addAttribute("app", appGraphService.getAppById(appId));
		return "main";
	}
}
