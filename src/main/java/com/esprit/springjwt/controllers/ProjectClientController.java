package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.AdminProjects;
import com.esprit.springjwt.entity.ProjectClient;
import com.esprit.springjwt.entity.ProjectOwner;
import com.esprit.springjwt.exception.ResourceNotFoundException;
import com.esprit.springjwt.repository.AdminProjectsRepository;
import com.esprit.springjwt.service.AdminProjectsService;
import com.esprit.springjwt.service.ProjectClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ProjectClient")
@CrossOrigin("*")
public class ProjectClientController {
    @Autowired
    ProjectClientService projectClientServices;
    @Autowired
    AdminProjectsService adminProjectsService;
    @Autowired
    private AdminProjectsRepository adminProjectsRepository;

    /*******add****/

    @PostMapping("/add/{adminProjectId}")
    public ProjectClient add(@RequestBody ProjectClient projectClient, @PathVariable("adminProjectId") Long adminProjectId) {
        AdminProjects adminProject = adminProjectsService.findById(adminProjectId)
                .orElseThrow(() -> new ResourceNotFoundException("AdminProjects not found with ID: " + adminProjectId));

        projectClient.addAdminProject(adminProject);
        projectClient = projectClientServices.save(projectClient);

        adminProject.addProjectClient(projectClient);
        adminProjectsService.save(adminProject);

        return projectClient;
    }
    /*******DeleteRelation****/

    @DeleteMapping("/removeRelation/{adminProjectId}/{projectClientId}")
    public ProjectClient removeRelation(@PathVariable("adminProjectId") Long adminProjectId, @PathVariable("projectClientId") Long projectClientId) {
        AdminProjects adminProject = adminProjectsService.findById(adminProjectId)
                .orElseThrow(() -> new ResourceNotFoundException("AdminProjects not found with ID: " + adminProjectId));

        ProjectClient projectClient = projectClientServices.findById(projectClientId)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectClient not found with ID: " + projectClientId));

        adminProject.removeProjectClient(projectClient);
        adminProjectsService.save(adminProject);

        projectClient.removeAdminProject(adminProject);
        projectClientServices.save(projectClient);

        return projectClient;
    }
    /*******DeleteClient****/

    @DeleteMapping("/removeClient/{projectClientId}")
    public ProjectClient remove(@PathVariable("projectClientId") Long projectClientId) {
        ProjectClient projectClient = projectClientServices.findById(projectClientId)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectClient not found with ID: " + projectClientId));

        List<AdminProjects> adminProjects = projectClient.getAdminProjects();
        if (adminProjects != null && !adminProjects.isEmpty()) {
            for (AdminProjects adminProject : adminProjects) {
                adminProject.removeProjectClient(projectClient);
                adminProjectsService.save(adminProject);
            }
        }

        projectClientServices.delete(projectClient);

        return projectClient;
    }
/*******Update****/
    @PutMapping("/update/{projectClientId}")
    public ProjectClient update(@PathVariable("projectClientId") Long projectClientId, @RequestBody ProjectClient updatedProjectClient) {
        ProjectClient projectClient = projectClientServices.findById(projectClientId)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectClient not found with ID: " + projectClientId));

        List<AdminProjects> updatedAdminProjects = updatedProjectClient.getAdminProjects();
        if (updatedAdminProjects != null && !updatedAdminProjects.isEmpty()) {
            projectClient.setAdminProjects(updatedAdminProjects);
        }
        projectClient.setNom(updatedProjectClient.getNom());
        projectClient.setPrenom(updatedProjectClient.getPrenom());
        projectClient.setRemark(updatedProjectClient.getRemark());
        projectClient.setEmail(updatedProjectClient.getEmail());

        projectClient = projectClientServices.save(projectClient);

        return projectClient;
    }
    @GetMapping("/All")
    @ResponseBody
    public List<ProjectClient> getAll(){
        return projectClientServices.getAll();
    }
    @GetMapping("/adminProjects/{adminProjectId}/projectClients")
    @ResponseBody
    public List<ProjectClient> getProjectClientsByAdminProjectId(@PathVariable("adminProjectId") Long adminProjectId) {
        return projectClientServices.findByAdminProjectId(adminProjectId);
    }


}
